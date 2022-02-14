package org.mitumc.sdk.key;

import java.util.HashMap;

import org.mitumc.sdk.interfaces.BytesChangeable;
import org.mitumc.sdk.util.RegExp;
import org.mitumc.sdk.util.Util;

public class BaseKey implements BytesChangeable {
    private String type;
    private String key;

    BaseKey(String key) {
        setType(key);
    }

    BaseKey(String key, String type) {
        RegExp.assertKey(key + type);
        this.key = key;
        this.type = type;
    }

    private void setType(String typed) {
        RegExp.assertKey(typed);
        HashMap<String, String> parsed = Util.parseType(typed);
        this.key = parsed.get("raw");
        this.type = parsed.get("type");
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