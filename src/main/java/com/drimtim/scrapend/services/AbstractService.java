package com.drimtim.scrapend.services;

import com.drimtim.scrapend.response.Response;
import com.drimtim.scrapend.utils.ExceptionUtils;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;

public abstract class AbstractService {

    @Value("${configuration.exceptionControllerAdvice.showStackTrace:false}")
    private Boolean showStackTrace;

    protected Response manageException(Exception exception) {
        return ExceptionUtils.manageException(exception, showStackTrace, getLogger());
    }
    protected Response manageSuccess(Object ... responseContent) {
        return ExceptionUtils.manageSuccess(responseContent);
    }


    protected abstract Logger getLogger();
}
