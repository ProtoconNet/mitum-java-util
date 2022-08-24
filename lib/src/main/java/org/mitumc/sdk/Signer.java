package org.mitumc.sdk;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import org.bitcoinj.core.Base58;

import org.mitumc.sdk.util.Util;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import org.mitumc.sdk.util.Hash;
import org.mitumc.sdk.sign.FactSign;
import org.mitumc.sdk.sign.JsonFactSigns;

public class Signer {
    private String id;
    private String signKey;

    private Signer(String id, String signKey) {
        this.id = id;
        this.signKey = signKey;
    }

    public static Signer get(String id, String signKey) {
        return new Signer(id, signKey);
    }

    public HashMap<String, Object> addSignToOperation(JsonObject operation) {
        HashMap<String, Object> signedOper = new HashMap<>();

        String factHash = operation.getAsJsonObject("fact").get("hash").getAsString();

        byte[] bfactHash = Base58.decode(factHash);
        byte[] bmemo = operation.get("memo").getAsString().getBytes();

        signedOper.put("memo", operation.get("memo").getAsString());
        signedOper.put("_hint", operation.get("_hint").getAsString());
        signedOper.put("fact", operation.get("fact"));

        JsonArray _factSigns = operation.get("fact_signs").getAsJsonArray();
        Iterator<JsonElement> iterator = _factSigns.iterator();

        ArrayList<JsonObject> factSigns = new ArrayList<>();
        while (iterator.hasNext()) {
            factSigns.add(iterator.next().getAsJsonObject());
        }

        FactSign newFactSign = FactSign.get(Util.concatByteArray(bfactHash, this.id.getBytes()),
                this.signKey);
        factSigns.add(new Gson().toJsonTree(newFactSign.toDict()).getAsJsonObject());

        signedOper.put("fact_signs", factSigns);
        signedOper.put("hash", Hash.fromBytes(Util.concatByteArray(bfactHash, JsonFactSigns.get(factSigns).toBytes(), bmemo)).getSha3Hash());

        return signedOper;
    }

    public HashMap<String, Object> addSignToOperation(String operationPath) {
        JsonObject operation = JSONParser.getObjectFromJsonFile(operationPath);
        return addSignToOperation(operation);
    }
}