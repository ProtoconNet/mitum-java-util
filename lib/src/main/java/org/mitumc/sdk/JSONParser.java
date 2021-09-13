/* Copyright 2021 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 */
package org.mitumc.sdk;

import java.io.FileReader;
import java.io.FileWriter;
import java.util.HashMap;

import org.mitumc.sdk.util.Util;

import shadow.com.google.gson.Gson;
import shadow.com.google.gson.GsonBuilder;
import shadow.com.google.gson.JsonObject;
import shadow.com.google.gson.JsonParser;

public class JSONParser {

    public static JsonObject getObjectFromJSONFile(String fpName) {
        try {
            JsonParser parser = new JsonParser();
            Object obj = parser.parse(new FileReader(fpName));
			JsonObject jsonObject = (JsonObject) obj;
            return jsonObject;
        } catch (Exception e) {
            Util.raiseError("Fail to create JSON file... :(");
            return null;
        }
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
                Util.raiseError("Fail to Flush JSON file writer.");
                return;
            }
        } catch (Exception e) {
            Util.log("Fail to create JSON file... :(");
            return;
        }
        Util.log("Success to generate JSON file. :D");
    }

    public static void createJSON(HashMap<String, Object> target, String fpName) {
        createJSON(new Gson().toJsonTree(target).getAsJsonObject(), fpName);
    }
}