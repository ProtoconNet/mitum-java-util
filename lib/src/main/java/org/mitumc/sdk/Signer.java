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
import org.mitumc.sdk.util.TimeStamp;
import org.mitumc.sdk.sign.FactSign;

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

    private static byte[] factSignToBytes(JsonObject factSign) throws Exception {
        byte[] bsigner = factSign.get("signer").getAsString().getBytes();
        byte[] bsign = Base58.decode(factSign.get("signature").getAsString());
        byte[] bsignedAt = null;

        try {
            bsignedAt = TimeStamp.fromString(factSign.get("signed_at").getAsString()).getUTC().getBytes();
        } catch (Exception e) {
            throw new Exception(
                    Util.linkErrMsgs(
                            Util.errMsg("Failed to convert fact sign to bytes", Util.getName()),
                            e.getMessage()));
        }

        return Util.concatByteArray(bsigner, bsign, bsignedAt);
    }

    private static byte[] factSignsToBytes(ArrayList<JsonObject> factSigns) throws Exception {
        ArrayList<byte[]> tempArr = new ArrayList<>();
        int bytesLen = 0;

        for (JsonObject factSign : factSigns) {
            byte[] bfactSign = null;
            try {
                bfactSign = factSignToBytes(factSign);
            } catch (Exception e) {
                throw new Exception(
                        Util.linkErrMsgs(
                                Util.errMsg("failed to convert fact_sign to bytes", Util.getName()),
                                e.getMessage()));
            }
            tempArr.add(bfactSign);
            bytesLen += bfactSign.length;
        }

        byte[] bytes = new byte[bytesLen];
        int tempLen = 0;
        for (byte[] temp : tempArr) {
            System.arraycopy(temp, 0, bytes, tempLen, temp.length);
            tempLen += temp.length;
        }

        return bytes;
    }

    public HashMap<String, Object> addSignToOperation(JsonObject operation) throws Exception {
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

        try {
            FactSign newFactSign = FactSign.get(Util.concatByteArray(bfactHash, this.id.getBytes()),
                    this.signKey);
            factSigns.add(new Gson().toJsonTree(newFactSign.toDict()).getAsJsonObject());

            byte[] bfactSigns = factSignsToBytes(factSigns);
            signedOper.put("fact_signs", factSigns);
            signedOper.put("hash", Hash.fromBytes(Util.concatByteArray(bfactHash, bfactSigns, bmemo)).getSha3Hash());
        } catch (Exception e) {
            throw new Exception(
                    Util.linkErrMsgs(
                            Util.errMsg("failed to add new fact sign to fact_signs", Util.getName()),
                            e.getMessage()));
        }

        return signedOper;
    }

    public HashMap<String, Object> addSignToOperation(String operationPath) throws Exception {
        JsonObject operation = JSONParser.getObjectFromJSONFile(operationPath);
        return addSignToOperation(operation);
    }
}