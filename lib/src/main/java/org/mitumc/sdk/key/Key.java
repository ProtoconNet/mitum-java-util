package org.mitumc.sdk.key;

import java.util.HashMap;

import org.mitumc.sdk.BytesChangeable;
import org.mitumc.sdk.Constant;
import org.mitumc.sdk.Dictionariable;
import org.mitumc.sdk.util.BigInt;
import org.mitumc.sdk.util.Hint;
import org.mitumc.sdk.util.Util;

public class Key implements BytesChangeable, Dictionariable {
    private Hint hint;
    private BaseKey key;
    private BigInt weight;

    public Key(String key, int weight) {
        this.hint = new Hint(Constant.MC_KEY);
        this.key = new BaseKey(key);
        this.weight = new BigInt(weight);

        if (!isWeightValid()) {
            Util.raiseError("Invalid weight for Key.");
        }
    }

    private boolean isWeightValid() {
        if (this.weight.getValue() < 1 || this.weight.getValue() > 100) {
            return false;
        }

        return true;
    }

    public long getWeight() {
        return this.weight.getValue();
    }

    public String getKey() {
        return this.key.getKey();
    }

    public String getRawKey() {
        return this.key.getRawKey();
    }

    @Override
    public byte[] toBytes() {
        byte[] bkey = this.key.toBytes();
        byte[] bweight = this.weight.toBytes();

        return Util.concatByteArray(bkey, bweight);
    }

    @Override
    public HashMap<String, Object> toDict() {
        HashMap<String, Object> hashMap = new HashMap<>();

        hashMap.put("_hint", this.hint.getHint());
        hashMap.put("weight", this.weight.getValue());
        hashMap.put("key", this.key.getKey());

        return hashMap;
    }
}