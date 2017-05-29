package com.drimtim.scrapend.managers;

import com.drimtim.scrapend.repositories.CommandResultRepository;
import com.drimtim.scrapend.response.Response;
import com.drimtim.scrapend.utils.ExceptionUtils;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

public abstract class AbstractManager {

    protected final CommandResultRepository commandResultRepository;

    @Value("${configuration.exceptionControllerAdvice.showStackTrace:false}")
    private Boolean showStackTrace;

    @Autowired
    public AbstractManager(CommandResultRepository commandResultRepository) {
        this.commandResultRepository = commandResultRepository;
    }

    protected Response manageException(Exception exception) {
        return ExceptionUtils.manageException(exception, showStackTrace, getLogger());
    }

    protected Response manageSuccess(Object ... responseContent) {
        return ExceptionUtils.manageSuccess(responseContent);
    }

    protected abstract Logger getLogger();

}
