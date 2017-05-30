package com.drimtim.scrapend.utils;

import org.apache.commons.lang3.reflect.FieldUtils;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Created by jonathan on 24/05/17.
 */
public class CommandUtils {
    public static String getDefaultCommand(String spiderName) throws Exception {
        return new String(Files.readAllBytes(Paths.get(CommandUtils.class.getResource("/scrapend-default-spiders" + File.separator + spiderName).toURI())));
    }
    public static Integer getPidFromProcessRunning(Process process) throws Exception {
        return (Integer) FieldUtils.readField(process, "pid", true);
    }
}
