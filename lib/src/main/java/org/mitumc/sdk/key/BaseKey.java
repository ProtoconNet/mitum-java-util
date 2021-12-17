package org.mitumc.sdk.key;

import java.util.HashMap;

import org.mitumc.sdk.BytesChangeable;
import org.mitumc.sdk.util.Util;

public class BaseKey implements BytesChangeable {
    private String type;
    private String key;

    BaseKey(String key) {
        setType(key);
    }

    BaseKey(String key, String type) {
        this.key = key;
        this.type = type;

        if(!isTypeValid()) {
            Util.raiseError("Invalid key type for BaseKey.");
        }
    }

    private void setType(String typed) {
        HashMap<String, String> parsed = Util.parseType(typed);
        this.key = parsed.get("raw");
        this.type = parsed.get("type");

        if(!isTypeValid()) {
            Util.raiseError("Invalid key type for BaseKey.");
        }
    }

    private boolean isTypeValid() {
        return Util.isTypeValid(type);
    }

    public String getRawKey() {
        return this.key;
    }

    public String getKey() {
        return this.key + this.type;
    }

    public String getType() {
        return this.type;
    }

    public byte[] toBytes() {
        return getKey().getBytes();
    }

    public byte[] rawToBytes() {
        return getRawKey().getBytes();
    }
}