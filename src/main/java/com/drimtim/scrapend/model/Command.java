package com.drimtim.scrapend.model;

import com.drimtim.scrapend.enums.Status;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Created by jonathan on 24/05/17.
 */
@ApiModel
@Document(collection = "commands")
public class Command extends AbstractModel {

    private static final long serialVersionUID = -8634472475750789108L;

    @Id
    private String id;
    private String spiderName;
    private boolean useDefaultCommand;
    private String customCommand;
    private Long waitTimeout;
    private Status status;
    private CommandResult result;

    public Command() {}

    @ApiModelProperty(
            value = "The spider name command to use.",
            dataType = "String",
            example = "Spider Man!!")
    public String getSpiderName() {
        return spiderName;
    }

    public void setSpiderName(String spiderName) {
        this.spiderName = spiderName;
    }
    @ApiModelProperty(
            value = "This sets if the system has to use the default command for the spider.",
            dataType = "Boolean")
    public boolean isUseDefaultCommand() {
        return useDefaultCommand;
    }

    public void setUseDefaultCommand(boolean useDefaultCommand) {
        this.useDefaultCommand = useDefaultCommand;
    }
    @ApiModelProperty(
            value = "The custom spider command to use.",
            dataType = "String",
            example = "ping -c 3 google.com")
    public String getCustomCommand() {
        return customCommand;
    }

    public void setCustomCommand(String customCommand) {
        this.customCommand = customCommand;
    }

    @ApiModelProperty(
            value = "The waiting timeout (in seconds) for execute the command.",
            dataType = "Long",
            example = "30")
    public Long getWaitTimeout() {
        return waitTimeout;
    }
    public void setWaitTimeout(Long waitTimeout) {
        this.waitTimeout = waitTimeout;
    }

    @ApiModelProperty(
            value = "Command status value.",
            dataType = "Status",
            example = "RUNNING",
            hidden = true)
    public Status getStatus() {
        return status;
    }
    public void setStatus(Status status) {
        this.status = status;
    }

    @ApiModelProperty(
            value = "Command process id on the system.",
            dataType = "String",
            example = "1501",
            hidden = true)
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }

    @ApiModelProperty(
            value = "The result of the executed command.",
            dataType = "CommandResult",
            hidden = true)
    public CommandResult getResult() {
        return result;
    }
    public void setResult(CommandResult result) {
        this.result = result;
    }

}
