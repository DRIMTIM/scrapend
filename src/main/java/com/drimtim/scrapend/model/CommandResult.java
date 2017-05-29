package com.drimtim.scrapend.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Created by jonathan on 24/05/17.
 */
@ApiModel
@Document(collection = "executed_commands")
public class CommandResult extends AbstractModel {

    private static final long serialVersionUID = -8634472475750789108L;

    @Id
    private String id;
    private Command commandExecuted;
    private String resultString;

    public CommandResult() {}

    public CommandResult(Command commandExecuted) {
        this.resultString = StringUtils.EMPTY;
        this.commandExecuted = commandExecuted;
    }

    @ApiModelProperty(
            value = "The command executed.",
            dataType = "Command")
    public Command getCommandExecuted() {
        return commandExecuted;
    }

    public void setCommandExecuted(Command commandExecuted) {
        this.commandExecuted = commandExecuted;
    }

    @ApiModelProperty(
            value = "The result string of the command executed.",
            dataType = "String")
    public String getResultString() {
        return resultString;
    }

    public void setResultString(String resultString) {
        this.resultString = resultString;
    }

    @ApiModelProperty(
            value = "The document id.",
            dataType = "String")
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
