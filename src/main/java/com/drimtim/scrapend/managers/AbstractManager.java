package com.drimtim.scrapend.managers;

import com.drimtim.scrapend.repositories.CommandRepository;
import com.drimtim.scrapend.response.Response;
import com.drimtim.scrapend.utils.ExceptionUtils;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

public abstract class AbstractManager {

    protected final CommandRepository commandRepository;

    @Value("${configuration.exceptionControllerAdvice.showStackTrace:false}")
    private Boolean showStackTrace;

    @Autowired
    public AbstractManager(CommandRepository commandRepository) {
        this.commandRepository = commandRepository;
    }

    protected Response manageException(Exception exception) {
        return ExceptionUtils.manageException(exception, showStackTrace, getLogger());
    }

    protected Response manageSuccess(Object ... responseContent) {
        return ExceptionUtils.manageSuccess(responseContent);
    }

    protected abstract Logger getLogger();

}
