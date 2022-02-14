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

    public static JsonObject getObjectFromJSONFile(String fpName) {
        try {
            return (JsonObject) JsonParser.parseReader(new FileReader(fpName));
        } catch (Exception e) {
            Util.raiseError("Fail to create JSON file; JSONParser.");
            return null;
        }
    }

    public static JsonObject getObjectFromMap(HashMap<String, Object> target) {
        return new Gson().toJsonTree(target).getAsJsonObject();
    }

    public static void createJSON(JsonObject target, String fpName) {
        FileWriter writer;

        try {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            writer = new FileWriter(fpName);
            gson.toJson(target, writer);

            try {
                writer.flush();
                writer.close();
            } catch (Exception e) {
                Util.raiseError("Fail to Flush JSON file writer; JSONParser.");
                return;
            }
        } catch (Exception e) {
            Util.log("Fail to create JSON file; JSONParser.");
            return;
        }
        Util.log("Success to create JSON file - " + fpName + ".");
    }

    public static void createJSON(HashMap<String, Object> target, String fpName) {
        createJSON(new Gson().toJsonTree(target).getAsJsonObject(), fpName);
    }
}