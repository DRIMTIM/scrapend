package com.drimtim.scrapend.managers;

import com.drimtim.scrapend.constants.GlobalConstants;
import com.drimtim.scrapend.enums.Status;
import com.drimtim.scrapend.interfaces.CommandManager;
import com.drimtim.scrapend.model.Command;
import com.drimtim.scrapend.model.CommandResult;
import com.drimtim.scrapend.repositories.CommandRepository;
import com.drimtim.scrapend.response.Response;
import com.drimtim.scrapend.utils.CommandUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * Created by jonathan on 24/05/17.
 */
@Component
public class CommandManagerImpl extends AbstractManager implements CommandManager {

    private static final Logger LOGGER = LogManager.getLogger(CommandManagerImpl.class);

    @Autowired
    public CommandManagerImpl(CommandRepository commandResultRepository) {
        super(commandResultRepository);
    }

    @Override
    public Process callCommand(Command command) throws Exception {
        String stringCommand = command.isUseDefaultCommand() ? CommandUtils.getDefaultCommand(command.getSpiderName()) : command.getCustomCommand();
        File tempScript = createTempScript(stringCommand);
        try {
            ProcessBuilder processBuilder = new ProcessBuilder("bash", tempScript.toString());
            processBuilder.inheritIO();
            Process process = processBuilder.start();
            LOGGER.info("Calling command: " + stringCommand);
            command.setStatus(Status.RUNNING);
            String pid = CommandUtils.getPidFromProcessRunning(process).toString();
            LOGGER.info("Command pid: " + pid);
            command.setId(pid);
            commandRepository.save(command);
            return process;
        } finally {
            tempScript.delete();
        }
    }

    @Async
    public void waitCommandAsync(Command command, Process process) throws Exception {
        waitCommand(command, process);
    }

    public Command waitCommandSync(Command command, Process process) throws Exception {
        waitCommand(command, process);
        return commandRepository.findOne(command.getId());
    }

    private void waitCommand(Command command, Process process) throws Exception {
        LOGGER.info("Wait for the command to execute for " + command.getWaitTimeout() + " seconds.");
        process.waitFor(command.getWaitTimeout(), TimeUnit.SECONDS);
        LOGGER.info("End wait, now saving the result");
        command = commandRepository.findOne(command.getId());
        CommandResult commandResult = new CommandResult();
        if (process.getErrorStream() != null && process.getErrorStream().available() > 0) {
            command.setStatus(Status.ERROR);
            try {
                commandResult.setResultLines((readLines(process.getErrorStream())));
            } catch (Exception e) {
                if (LOGGER.isDebugEnabled()) {
                    LOGGER.debug(e);
                }
                command.setStatus(Status.ERROR);
                commandResult.getResultLines().add(e.getMessage());
            }
        } else {
            if (command.getStatus().equals(Status.RUNNING)) {
                command.setStatus(Status.DONE);
            }
            try {
                commandResult.setResultLines((readLines(process.getInputStream())));
            } catch (Exception e) {
                if (LOGGER.isDebugEnabled()) {
                    LOGGER.debug(e);
                }
                command.setStatus(Status.ERROR);
                commandResult.getResultLines().add(e.getMessage());
            }
        }
        command.setResult(commandResult);
        commandRepository.save(command);
    }

    private List<String> readLines(InputStream inputStream) throws Exception {
        List<String> lines = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        while (reader.ready()) {
            String line = reader.readLine();
            if (line != null) {
                lines.add(line);
            }
        }
        return lines;
    }

    private File createTempScript(String commandString) throws IOException {
        File tempScript = File.createTempFile("script", null);
        Writer streamWriter = new OutputStreamWriter(new FileOutputStream(tempScript));
        PrintWriter printWriter = new PrintWriter(streamWriter);
        printWriter.println(commandString);
        printWriter.close();
        return tempScript;
    }

    @Override
    public Response killCommand(String commandId) throws Exception {
        try {
            Command command = commandRepository.findOne(commandId);
            if (command.getStatus().equals(Status.RUNNING)) {
                Process process = Runtime.getRuntime().exec("kill -9 " + commandId);
                LOGGER.info("Killing process with pid " + commandId);
                process.waitFor();
                command.setStatus(Status.KILLED);
                commandRepository.save(command);
            }
            return manageSuccess(command);
        } catch (Exception e) {
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug(e);
            }
            return manageException(e);
        }
    }

    @Override
    public Response getAllCommands(Status commandStatus) {
        try {
            Map<String, Object> commandsMap = new HashMap<>();
            List<Command> commands = commandRepository.findAll();
            if (commands != null && !commands.isEmpty()) {
                commands = commands.stream().filter(command -> command.getStatus().equals(commandStatus)).collect(Collectors.toList());
            }
            commandsMap.put("commandList", commands);
            return manageSuccess(commandsMap);
        } catch (Exception e) {
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug(e);
            }
            return manageException(e);
        }
    }

    @Override
    protected Logger getLogger() {
        return LOGGER;
    }
}
