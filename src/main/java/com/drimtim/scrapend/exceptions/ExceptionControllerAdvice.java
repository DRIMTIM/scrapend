package com.drimtim.scrapend.exceptions;

import com.drimtim.scrapend.response.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * This handles the exceptions and make the error response from those.
 */
@ControllerAdvice
public class ExceptionControllerAdvice {

    private static final Logger LOGGER = LogManager.getLogger(ExceptionControllerAdvice.class);

    @Value("${configuration.exceptionControllerAdvice.showStackTrace:false}")
    private Boolean showStackTrace;

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Response> exceptionHandler(Exception exception) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug(exception);
        }
        Response apiResponse = new Response();
        apiResponse.setErrorMessage(exception.getMessage());
        if (showStackTrace) {
            apiResponse.setExceptionTrace(exception.getStackTrace());
        }
        apiResponse.setSuccess(false);
        return new ResponseEntity<>(apiResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
