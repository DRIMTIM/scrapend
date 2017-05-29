package com.drimtim.scrapend.response;

import com.drimtim.scrapend.converters.LocalDateTimeJsonDeserializer;
import com.drimtim.scrapend.converters.LocalDateTimeJsonSerializer;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.time.LocalDateTime;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
@ApiModel
public class Response implements Serializable {

    private static final long serialVersionUID = -2817268529541249476L;

    @ApiModelProperty(
            value = "The timestamp of the response",
            example = "56465465545498",
            dataType = "LocalDateTime")
    @JsonDeserialize(using = LocalDateTimeJsonDeserializer.class)
    @JsonSerialize(using = LocalDateTimeJsonSerializer.class)
    private LocalDateTime timeStamp;
    @ApiModelProperty(
            value = "Indicates the error message",
            example = "A error has occurred!",
            dataType = "String")
    private String errorMessage;
    @ApiModelProperty(
            value = "Tell if the operation was successfully executed",
            example = "false",
            dataType = "Boolean")
    private boolean success;
    @ApiModelProperty(
            value = "The exceptionTrace of the response when is a error response and the showStackTrace property is active",
            dataType = "StackTraceElement[]")
    private StackTraceElement [] exceptionTrace;
    @ApiModelProperty(
            value = "The response content. This shows the entity generated or some data from the response when applies",
            dataType = "Object")
    private Object responseContent;

    public Response() {
        timeStamp = LocalDateTime.now();
        success = true;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public Object getResponseContent() {
        return responseContent;
    }

    public void setResponseContent(Object responseContent) {
        this.responseContent = responseContent;
    }

    public LocalDateTime getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(LocalDateTime timeStamp) {
        this.timeStamp = timeStamp;
    }

    public StackTraceElement[] getExceptionTrace() {
        return exceptionTrace;
    }

    public void setExceptionTrace(StackTraceElement[] exceptionTrace) {
        this.exceptionTrace = exceptionTrace;
    }
}
