package com.drimtim.scrapend.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Created by jonathan on 24/05/17.
 */
@ApiModel
public class Command extends AbstractModel {

    private static final long serialVersionUID = -8634472475750789108L;

    private String spiderName;
    private boolean useDefaultCommand;
    private String customCommand;
    private Long waitTimeout;

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
}
