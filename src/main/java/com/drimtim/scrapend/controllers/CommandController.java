package com.drimtim.scrapend.controllers;

import com.drimtim.scrapend.constants.URLConstants;
import com.drimtim.scrapend.enums.Status;
import com.drimtim.scrapend.model.Command;
import com.drimtim.scrapend.response.Response;
import com.drimtim.scrapend.services.CommandService;
import com.drimtim.scrapend.utils.ValidationUtils;
import io.swagger.annotations.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Api
@RestController
public class CommandController extends AbstractController {

	private static final Logger LOGGER = LogManager.getLogger(CommandController.class);

	@Autowired
	private CommandService commandService;

	@ApiOperation(value = "Executes a command for scraping.")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "OK") })
	@PostMapping(value = URLConstants.COMMAND_PREFIX)
	public Response executeTestCommand(
			@ApiParam(
				name = "command",
				value = "The command to execute.",
				type = "Command",
				required = true)
				final @RequestBody Command command) throws Exception {
		ValidationUtils.validateRequiredRequestParams(new String [] {"command"}, command);
	    return commandService.executeCommand(command);
	}

	@ApiOperation(value = "Gets all executed commands with given status.")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "OK") })
	@GetMapping(value = URLConstants.COMMAND_PREFIX)
	public Response getAllCommands(@RequestParam Status commandStatus) {
		return commandService.getAllCommands(commandStatus);
	}

	@ApiOperation(value = "Kill a command with the given id.")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "OK") })
	@DeleteMapping(value = URLConstants.COMMAND_PREFIX)
	public Response killCommand(@RequestParam String commandId) {
		return commandService.killCommand(commandId);
	}
}
