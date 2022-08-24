package org.mitumc.sdk.sign;

import java.util.ArrayList;

import org.bitcoinj.core.Base58;
import org.mitumc.sdk.interfaces.BytesConvertible;
import org.mitumc.sdk.util.TimeStamp;
import org.mitumc.sdk.util.Util;

import com.google.gson.JsonObject;

public class JsonFactSigns implements BytesConvertible {
    private ArrayList<JsonObject> factSigns;

    private JsonFactSigns(ArrayList<JsonObject> factSigns) {
        this.factSigns = factSigns;
    }

    public static JsonFactSigns get(ArrayList<JsonObject> factSigns) {
        return new JsonFactSigns(factSigns);
    }

    private byte[] factSignToBytes(JsonObject factSign) {
        byte[] bsigner = factSign.get("signer").getAsString().getBytes();
        byte[] bsign = Base58.decode(factSign.get("signature").getAsString());
        byte[] bsignedAt = TimeStamp.fromString(factSign.get("signed_at").getAsString()).getUTC().getBytes();
        return Util.concatByteArray(bsigner, bsign, bsignedAt);
    }

    private byte[] factSignsToBytes() {
        ArrayList<byte[]> tempArr = new ArrayList<>();
        int bytesLen = 0;

        for (JsonObject factSign : this.factSigns) {
            byte[] bfactSign = factSignToBytes(factSign);
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
    
    @Override
    public byte[] toBytes() {
        return factSignsToBytes();
    }

    public ArrayList<JsonObject> getFactSigns() {
        return this.factSigns;
    }
}
