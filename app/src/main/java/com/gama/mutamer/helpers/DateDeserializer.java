package com.gama.mutamer.helpers;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by mustafa on 7/26/17.
 * Release the GEEK
 */

public class DateDeserializer implements JsonDeserializer<Date> {

    @Override
    public Date deserialize(JsonElement element, Type arg1, JsonDeserializationContext arg2) throws JsonParseException {
        String date = element.getAsString();

        Date result = GetDateWithMilliseconds(date);
        if (result == null) {
            result = GetDateWithoutMilliseconds(date);
        }
        return result;
    }

    private Date GetDateWithMilliseconds(String value) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");
        format.setTimeZone(TimeZone.getTimeZone("AST"));


        try {
            return format.parse(value);
        } catch (ParseException exp) {
            return null;
        }
    }

    private Date GetDateWithoutMilliseconds(String value) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        format.setTimeZone(TimeZone.getTimeZone("AST"));

        try {
            return format.parse(value);
        } catch (ParseException exp) {
            return null;
        }
    }
}
