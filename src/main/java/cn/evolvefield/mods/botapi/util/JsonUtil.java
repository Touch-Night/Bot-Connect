package cn.evolvefield.mods.botapi.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonReader;

import java.io.IOException;

public class JsonUtil {
    public static final Gson GSON = new GsonBuilder().disableHtmlEscaping().create();

    public static JsonObject fromJson(JsonReader reader) throws IOException {
        return (JsonObject)(GSON.fromJson(reader, JsonObject.class));
    }
}
