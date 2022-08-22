package org.mitumc.sdk;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;

import org.mitumc.sdk.exception.JsonHandleException;
import org.mitumc.sdk.util.Util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class JSONParser {
    @Deprecated
    public static JsonObject getObjectFromJSONFile(String fpName) {
        return getObjectFromJsonFile(fpName);
    }

    public static JsonObject getObjectFromJsonFile(String fpName) {
        try {
            return (JsonObject) JsonParser.parseReader(new FileReader(fpName));
        } catch (Exception e) {
            throw new JsonHandleException(Util.errMsg(e.getMessage(), Util.getName()));
        }
    }

    public static JsonObject getObjectFromMap(HashMap<String, Object> target) {
        return new Gson().toJsonTree(target).getAsJsonObject();
    }

    @Deprecated
    public static void createJSON(JsonObject target, String fp) {
        writeJsonFileFromJsonObject(target, fp);
    }

    @Deprecated
    public static void createJSON(HashMap<String, Object> target, String fp) {
        writeJsonFileFromHashMap(target, fp);
    }

    public static void writeJsonFileFromJsonObject(JsonObject target, String fp) { 
        try {
            File file = new File(fp);
            file.getParentFile().mkdirs();
            FileWriter writer = new FileWriter(file, false);

            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            gson.toJson(target, writer);
            
            writer.flush();
            writer.close();
        } catch (IOException e) {
            throw new JsonHandleException(Util.errMsg(e.getMessage(), Util.getName()));
        }
    }

    public static void writeJsonFileFromHashMap(HashMap<String, Object> target, String fp) {
        writeJsonFileFromJsonObject(new Gson().toJsonTree(target).getAsJsonObject(), fp);
    }
}