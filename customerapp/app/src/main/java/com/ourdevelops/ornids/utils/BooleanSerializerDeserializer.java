package com.ourdevelops.ornids.utils;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;

/**
 * Created by Ourdevelops Team on 10/29/2019.
 */

public class BooleanSerializerDeserializer implements JsonSerializer<Boolean>, JsonDeserializer<Boolean> {
    @Override
    public JsonElement serialize(Boolean src, Type typeOfSrc, JsonSerializationContext context) {
        return new JsonPrimitive(src ? 1 : 0);
    }

    @Override
    public Boolean deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        String parse = json.getAsString();
        return (parse.equalsIgnoreCase("true") || parse.equalsIgnoreCase("1"));

    }
}
