package com.drimtim.scrapend.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jonathan on 24/05/17.
 */
@ApiModel
public class CommandResult extends AbstractModel {

    private static final long serialVersionUID = -8634472475750789108L;

    private List<String> resultLines = new ArrayList<>();

    public CommandResult() {}

    @ApiModelProperty(
            value = "The result lines of the command executed.",
            dataType = "String")
    public List<String> getResultLines() {
        return resultLines;
    }
    public void setResultLines(List<String> resultLines) {
        this.resultLines = resultLines;
    }

}
