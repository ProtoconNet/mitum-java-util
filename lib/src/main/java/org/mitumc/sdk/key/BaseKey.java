package org.mitumc.sdk.key;

import java.util.HashMap;

import org.mitumc.sdk.interfaces.BytesConvertible;
import org.mitumc.sdk.util.RegExp;
import org.mitumc.sdk.util.Util;

class BaseKey implements BytesConvertible {
    private String type;
    private String key;

    private BaseKey(String key) {
        setType(key);
    }

    private BaseKey(String key, String type) {
        RegExp.assertKey(key + type);
        this.key = key;
        this.type = type;
    }

    public static BaseKey get(String key) {
        return new BaseKey(key);
    }

    public static BaseKey get(String key, String type) {
        return new BaseKey(key, type);
    }

    private void setType(String typed) {
        RegExp.assertKey(typed);

        HashMap<String, String> parsed = Util.parseType(typed);
        this.key = parsed.get("raw");
        this.type = parsed.get("type");
    }

    public String getKeyWithoutType() {
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
        return getKeyWithoutType().getBytes();
    }
}