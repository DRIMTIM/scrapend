package com.drimtim.scrapend.interfaces;

import com.drimtim.scrapend.enums.Status;
import com.drimtim.scrapend.model.Command;
import com.drimtim.scrapend.response.Response;

/**
 * Created by jonathan on 24/05/17.
 */
public interface CommandManager {
    Process callCommand(Command command) throws Exception;
    void waitCommand(Command command, Process process) throws Exception;
    Response killCommand(String commandId) throws Exception;
    Response getAllCommands(Status commandStatus);
}
