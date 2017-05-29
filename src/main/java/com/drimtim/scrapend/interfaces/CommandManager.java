package com.drimtim.scrapend.interfaces;

import com.drimtim.scrapend.model.Command;
import com.drimtim.scrapend.response.Response;

/**
 * Created by jonathan on 24/05/17.
 */
public interface CommandManager {
    Response executeTestCommand(Command command);
    Response getAllCommands();
}
