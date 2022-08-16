package org.mitumc.sdk;

import java.io.FileReader;
import java.io.FileWriter;
import java.util.HashMap;

import org.mitumc.sdk.util.Util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class JSONParser {
    public static JsonObject getObjectFromJSONFile(String fpName) throws Exception {
        try {
            return (JsonObject) JsonParser.parseReader(new FileReader(fpName));
        } catch (Exception e) {
            throw new Exception(
                    Util.errMsg("failed to create json file", Util.getName()));
        }
    }

    public static JsonObject getObjectFromMap(HashMap<String, Object> target) {
        return new Gson().toJsonTree(target).getAsJsonObject();
    }

    @Deprecated
    public static void createJSON(JsonObject target, String fp) throws Exception {
        writeJsonFileFromJsonObject(target, fp);
    }

    @Deprecated
    public static void createJSON(HashMap<String, Object> target, String fp) throws Exception {
        writeJsonFileFromHashMap(target, fp);
    }

    public static void writeJsonFileFromJsonObject(JsonObject target, String fp) throws Exception {
        FileWriter writer;
        try {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            writer = new FileWriter(fp);
            gson.toJson(target, writer);
            try {
                writer.flush();
                writer.close();
            } catch (Exception e) {
                throw new Exception(
                        Util.errMsg("failed to flush file writer", Util.getName()));
            }
        } catch (Exception e) {
            throw new Exception(
                    Util.linkErrMsgs(
                            Util.errMsg("failed to write json file", Util.getName())));
        }
    }

    public static void writeJsonFileFromHashMap(HashMap<String, Object> target, String fp) throws Exception {
        writeJsonFileFromJsonObject(new Gson().toJsonTree(target).getAsJsonObject(), fp);
    }
}