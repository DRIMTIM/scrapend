package com.drimtim.scrapend.utils;

import org.apache.commons.lang3.reflect.FieldUtils;

/**
 * Created by jonathan on 24/05/17.
 */
public class CommandUtils {
    public static String getDefaultCommand(String spiderName) {
        return "ping -c 3 google.com";
    }
    public static Integer getPidFromProcessRunning(Process process) throws Exception {
        return (Integer) FieldUtils.readField(process, "pid", true);
    }
}
