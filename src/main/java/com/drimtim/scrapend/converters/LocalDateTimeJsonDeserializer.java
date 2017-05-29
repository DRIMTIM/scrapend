package com.drimtim.scrapend.converters;

import com.drimtim.scrapend.constants.GlobalConstants;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.time.LocalDateTime;

public class LocalDateTimeJsonDeserializer extends JsonDeserializer<LocalDateTime> {
    @Override
    public LocalDateTime deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        String localDateString = p.readValueAs(String.class);
        if (StringUtils.isNotBlank(localDateString)) {
            return LocalDateTime.parse(localDateString, GlobalConstants.LOCAL_DATE_TIME_DEFAULT_DATE_FORMATTER);
        }
        return null;
    }
}