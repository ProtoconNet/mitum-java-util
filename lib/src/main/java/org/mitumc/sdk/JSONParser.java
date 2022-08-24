package org.mitumc.sdk;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import org.bitcoinj.core.Base58;
import org.mitumc.sdk.exception.InvalidOperationException;
import org.mitumc.sdk.exception.JsonHandleException;
import org.mitumc.sdk.sign.JsonFactSigns;
import org.mitumc.sdk.util.Hash;
import org.mitumc.sdk.util.Util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
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

    public static JsonObject getObjectFromHashMap(HashMap<String, Object> target) {
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
        writeJsonFileFromJsonObject(getObjectFromHashMap(target), fp);
    }

    public static HashMap<String, Object> mergeOperations(JsonObject[] operations) {
        HashMap<String, Object> merged = new HashMap<>();
        byte[] bfactHash = null;

        for(JsonObject obj : operations) {
            byte[] bhash = Base58.decode(obj.getAsJsonObject("fact").get("hash").getAsString());
            if (bfactHash == null) {
                bfactHash = bhash;
                continue;
            }
            if (bfactHash.length != bhash.length) {
                throw new InvalidOperationException(Util.errMsg("different operation found", Util.getName()));
            }
            for (int i = 0; i < bfactHash.length; i++) {
                if (bfactHash[i] != bhash[i]) {
                    throw new InvalidOperationException(Util.errMsg("different operation found", Util.getName()));
                }
            }
        }

        byte[] bmemo = operations[0].get("memo").getAsString().getBytes();

        merged.put("memo", operations[0].get("memo").getAsString());
        merged.put("_hint", operations[0].get("_hint").getAsString());
        merged.put("fact", operations[0].get("fact"));

        ArrayList<JsonObject> factSigns = new ArrayList<>();
        HashMap<String, Object> signed = new HashMap<>(); 

        for (JsonObject obj : operations) {
            JsonArray fsgns = obj.get("fact_signs").getAsJsonArray();
            Iterator<JsonElement> iterator = fsgns.iterator();

            while (iterator.hasNext()) {
                JsonObject fsgn = iterator.next().getAsJsonObject();
                String signer = fsgn.get("signer").getAsString();
                
                if (signed.get(signer) != null) {
                    continue;
                }
                signed.put(signer, new Object());
                
                factSigns.add(fsgn);
            }
        }

        merged.put("fact_signs", factSigns);
        merged.put("hash", Hash.fromBytes(Util.concatByteArray(bfactHash, JsonFactSigns.get(factSigns).toBytes(), bmemo)).getSha3Hash());
        
        return merged;
    }

    public static HashMap<String, Object> mergeOperations(HashMap<String, Object>[] operations) {
        JsonObject[] objs = new JsonObject[operations.length];
        
        for (int i = 0; i < operations.length; i++) {
            objs[i] = getObjectFromHashMap(operations[i]);
        }

        return mergeOperations(objs);
    }
}