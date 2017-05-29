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

import java.io.BufferedReader;
import java.io.InputStreamReader;
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
        Process process = Runtime.getRuntime().exec(stringCommand);
        command.setStatus(Status.RUNNING);
        String pid = CommandUtils.getPidFromProcessRunning(process).toString();
        command.setId(pid);
        commandRepository.save(command);
        return process;
    }

    @Async
    public void waitCommand(Command command, Process process) throws Exception {
        process.waitFor(command.getWaitTimeout(), TimeUnit.SECONDS);
        command = commandRepository.findOne(command.getId());
        CommandResult commandResult = new CommandResult();
        if (process.getErrorStream() != null && process.getErrorStream().available() > 0) {
            command.setStatus(Status.ERROR);
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getErrorStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                commandResult.setResultString(commandResult.getResultString().concat(line + GlobalConstants.END_LINE));
            }
        } else {
            if (command.getStatus().equals(Status.RUNNING)) {
                command.setStatus(Status.DONE);
            }
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                commandResult.setResultString(commandResult.getResultString().concat(line + GlobalConstants.END_LINE));
            }
        }
        command.setResult(commandResult);
        commandRepository.save(command);
    }

    @Override
    public Response killCommand(String commandId) throws Exception {
        try {
            Command command = commandRepository.findOne(commandId);
            if (command.getStatus().equals(Status.RUNNING)) {
                Process process = Runtime.getRuntime().exec("kill -9 " + commandId);
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
