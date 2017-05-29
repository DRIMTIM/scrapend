package com.drimtim.scrapend.utils;

import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public class ValidationUtils {
    public static void validateRequiredRequestParams(String [] paramNames, Object ... paramValues) throws Exception {
        if (paramNames != null && paramValues != null) {
            if (paramNames.length != paramValues.length) {
                throw new IllegalArgumentException("paramNames has to be the same size that paramValues");
            }
            Map<String, Object> paramsMap = Arrays.stream(paramNames).collect(
                    Collectors.toMap(i -> i, i -> paramValues[Arrays.asList(paramNames).indexOf(i)]));
            for (String paramName : paramsMap.keySet()) {
                Object paramValue = paramsMap.get(paramName);
                if (paramValue == null || StringUtils.isBlank(paramValue.toString())) {
                    throw new Exception("The param " + paramName + " is required!");
                }
            }
        }
    }

}
