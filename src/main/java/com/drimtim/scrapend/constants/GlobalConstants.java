package com.drimtim.scrapend.constants;

import java.time.format.DateTimeFormatter;

public interface GlobalConstants {
    String LOCAL_DATE_DEFAULT_FORMAT = "M/d/yyyy";
    String LOCAL_DATE_ACTIVATIONS_FORMAT = "d/M/yyyy";
    String LOCAL_DATE_PAYMENTS_FORMAT = "d/M/yyyy";
    String LOCAL_DATE_TIME_DEFAULT_FORMAT = "M/d/yyyy HH:mm:ss";
    String END_LINE = "\n";
    String COMMA = ",";
    String SPACE = " ";
    String SCORE = "-";
    DateTimeFormatter LOCAL_DATE_TIME_DEFAULT_DATE_FORMATTER = DateTimeFormatter.ofPattern(LOCAL_DATE_TIME_DEFAULT_FORMAT);
    DateTimeFormatter LOCAL_DATE_DEFAULT_DATE_FORMATTER = DateTimeFormatter.ofPattern(LOCAL_DATE_DEFAULT_FORMAT);
    DateTimeFormatter SALES_EXCEL_DATE_FORMATTER = DateTimeFormatter.ofPattern(LOCAL_DATE_DEFAULT_FORMAT);
    DateTimeFormatter ACTIVATIONS_TXT_DATE_FORMATTER = DateTimeFormatter.ofPattern(LOCAL_DATE_ACTIVATIONS_FORMAT);
    DateTimeFormatter PAYMENTS_TXT_DATE_FORMATTER = DateTimeFormatter.ofPattern(LOCAL_DATE_PAYMENTS_FORMAT);
    String ZONE_PREFIX = "Z";
    String TAB = "\t";
    String STAR = "*";
    String DOT = ".";
    String PHONE_PREFIX = "0000";
    String OPEN_BRACKET = "[";
    String CLOSE_BRACKET = "]";
    String UNDERSCORE = "_";
    String SEMICOLON = ";";
}
