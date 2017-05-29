package com.drimtim.scrapend.utils;

import com.drimtim.scrapend.constants.GlobalConstants;
import com.drimtim.scrapend.response.Response;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.Logger;

public class ExceptionUtils {

    public static Response manageException(Exception exception, boolean showStackTrace, Logger logger, String ... customMessages) {
        Response response = new Response();
        if (exception != null) {
            response.setSuccess(false);
            logger.error(exception);
            response.setErrorMessage(getExceptionMessage(exception.getMessage(), customMessages));
            if (showStackTrace) {
                response.setExceptionTrace(exception.getStackTrace());
            }
        }
        return response;
    }

    public static Response manageSuccess(Object ... responseContent) {
        Response response = new Response();
        if (responseContent != null && responseContent.length > 0) {
            response.setResponseContent(responseContent[0]);
        }
        return response;
    }

    private static String getExceptionMessage(String message, String ... customMessages) {
        String finalMessage = StringUtils.EMPTY;
        if (StringUtils.isNotBlank(message)) {
            if (customMessages != null && customMessages.length > 0) {
                for (String customMessage : customMessages) {
                    finalMessage += StringUtils.isBlank(finalMessage) ? customMessage : GlobalConstants.DOT + GlobalConstants.SPACE + customMessage;
                }
            }
        }
        return StringUtils.isBlank(finalMessage) ? message : finalMessage;
    }

}
