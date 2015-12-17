package com.ufo.core.web.json;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.ufo.core.utils.DateUtils;

import java.io.IOException;
import java.util.Date;

public class LongTimestampJsonSerializer extends JsonSerializer<Date> {

    @Override
    public void serialize(Date value, JsonGenerator jgen, SerializerProvider provider) throws IOException,
            JsonProcessingException {
        if (value != null) {
            jgen.writeString(DateUtils.format(value, DateUtils.FT_LONG_DATE_TIME));
        }
    }
}
