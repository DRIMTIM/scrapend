package com.drimtim.scrapend.services;

import com.drimtim.scrapend.enums.Status;
import com.drimtim.scrapend.interfaces.CommandManager;
import com.drimtim.scrapend.model.Command;
import com.drimtim.scrapend.response.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommandService extends AbstractService {

    @Autowired
    private CommandManager commandManager;

    private static final Logger LOGGER = LogManager.getLogger(CommandService.class);

    public Response executeCommand(Command command) {
        try {
            Process process = commandManager.callCommand(command);
            if (command.isAsync()) {
                commandManager.waitCommandAsync(command, process);
            } else {
                command = commandManager.waitCommandSync(command, process);
            }
            return manageSuccess(command);
        } catch (Exception e) {
            return manageException(e);
        }
    }

    public Response getAllCommands(Status commandStatus) {
        try {
            return commandManager.getAllCommands(commandStatus);
        } catch (Exception e) {
            return manageException(e);
        }
    }

    public Response killCommand(String commandId) {
        try {
            return commandManager.killCommand(commandId);
        } catch (Exception e) {
            return manageException(e);
        }
    }

    @Override
    protected Logger getLogger() {
        return LOGGER;
    }
}
