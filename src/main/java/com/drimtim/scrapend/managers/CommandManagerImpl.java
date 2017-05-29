package com.drimtim.scrapend.managers;

import com.drimtim.scrapend.annotations.Benchmark;
import com.drimtim.scrapend.constants.GlobalConstants;
import com.drimtim.scrapend.interfaces.CommandManager;
import com.drimtim.scrapend.model.Command;
import com.drimtim.scrapend.model.CommandResult;
import com.drimtim.scrapend.repositories.CommandResultRepository;
import com.drimtim.scrapend.response.Response;
import com.drimtim.scrapend.utils.CommandUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Created by jonathan on 24/05/17.
 */
@Component
public class CommandManagerImpl extends AbstractManager implements CommandManager {

    private static final Logger LOGGER = LogManager.getLogger(CommandManagerImpl.class);

    @Autowired
    public CommandManagerImpl(CommandResultRepository commandResultRepository) {
        super(commandResultRepository);
    }

    @Benchmark
    @Override
    public Response executeTestCommand(Command command) {
        try {
            String stringCommand = command.isUseDefaultCommand() ? CommandUtils.getDefaultCommand(command.getSpiderName()) : command.getCustomCommand();
            Process process = Runtime.getRuntime().exec(stringCommand);
            process.waitFor(command.getWaitTimeout(), TimeUnit.SECONDS);
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            CommandResult commandResult = new CommandResult(command);
            while ((line = reader.readLine()) != null) {
                commandResult.setResultString(commandResult.getResultString().concat(line + GlobalConstants.END_LINE));
            }
            commandResultRepository.save(commandResult);
            return manageSuccess(commandResult);
        } catch (Exception e) {
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug(e);
            }
            return manageException(e);
        }
    }

    @Override
    public Response getAllCommands() {
        try {
            Map<String, Object> commandsMap = new HashMap<>();
            List<CommandResult> commands = commandResultRepository.findAll();
            commandsMap.put("commands", commands);
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
