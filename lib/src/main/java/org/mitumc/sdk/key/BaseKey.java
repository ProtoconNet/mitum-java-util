package org.mitumc.sdk.key;

import java.util.HashMap;

import org.mitumc.sdk.interfaces.BytesConvertible;
import org.mitumc.sdk.util.RegExp;
import org.mitumc.sdk.util.Util;

class BaseKey implements BytesConvertible {
    private String type;
    private String key;

    private BaseKey(String key) throws Exception {
        setType(key);
    }

    private BaseKey(String key, String type) throws Exception {
        RegExp.assertKey(key + type);
        this.key = key;
        this.type = type;
    }

    public static BaseKey get(String key) throws Exception {
        try {
            return new BaseKey(key);
        } catch (Exception e) {
            throw new Exception(
                    Util.linkErrMsgs(
                            Util.errMsg("failed to create base key from key", Util.getName()),
                            e.getMessage()));
        }
    }

    public static BaseKey get(String key, String type) throws Exception {
        try {
            return new BaseKey(key, type);
        } catch (Exception e) {
            throw new Exception(
                    Util.linkErrMsgs(
                            Util.errMsg("failed to create base key from key and type", Util.getName()),
                            e.getMessage()));
        }
    }

    private void setType(String typed) throws Exception {
        HashMap<String, String> parsed = null;
        try {
            RegExp.assertKey(typed);
            parsed = Util.parseType(typed);
        } catch (Exception e) {
            throw new Exception(
                    Util.linkErrMsgs(
                            Util.errMsg("failed to set type of base key", Util.getName()),
                            e.getMessage()));
        }
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