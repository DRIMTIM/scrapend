package com.drimtim.scrapend.services;

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
            return commandManager.executeTestCommand(command);
        } catch (Exception e) {
            return manageException(e);
        }
    }

    public Response getAllCommands() {
        try {
            return commandManager.getAllCommands();
        } catch (Exception e) {
            return manageException(e);
        }
    }

    @Override
    protected Logger getLogger() {
        return LOGGER;
    }
}
