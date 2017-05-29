package com.drimtim.scrapend.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.StringUtils;

/**
 * Created by jonathan on 24/05/17.
 */
@ApiModel
public class CommandResult extends AbstractModel {

    private static final long serialVersionUID = -8634472475750789108L;

    private String resultString;

    public CommandResult() {}

    @ApiModelProperty(
            value = "The result string of the command executed.",
            dataType = "String")
    public String getResultString() {
        return resultString == null ? StringUtils.EMPTY : resultString;
    }

    public void setResultString(String resultString) {
        this.resultString = resultString;
    }

}
