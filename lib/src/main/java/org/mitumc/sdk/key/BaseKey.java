package org.mitumc.sdk.key;

import java.util.HashMap;

import org.mitumc.sdk.BytesChangeable;
import org.mitumc.sdk.Constant;
import org.mitumc.sdk.util.Hint;
import org.mitumc.sdk.util.Util;

public class BaseKey implements BytesChangeable {
    private Hint hint;
    private String key;

    BaseKey(String key) {
        setHint(key);
    }

    BaseKey(String key, String type) {
        this.key = key;
        this.hint = new Hint(type);

        if(!isTypeValid()) {
            Util.raiseError("Invalid key type for BaseKey.");
        }
    }

    private void setHint(String hinted) {
        HashMap<String, String> parsed = Util.parseHint(hinted);
        this.key = parsed.get("raw");
        this.hint = Util.getHintFromString(parsed.get("hint"));

        if(!isTypeValid()) {
            Util.raiseError("Invalid key type for BaseKey.");
        }
    }

    private boolean isTypeValid() {
        if (!(this.hint.getType().equals(Constant.KEY_BTC_PRIVATE) || this.hint.getType().equals(Constant.KEY_BTC_PUBLIC) || this.hint.getType().equals(Constant.KEY_ETHER_PRIVATE)
                || this.hint.getType().equals(Constant.KEY_ETHER_PUBLIC) || this.hint.getType().equals(Constant.KEY_STELLAR_PRIVATE)
                || this.hint.getType().equals(Constant.KEY_STELLAR_PUBLIC))) {
            return false;
        }

        return true;
    }

    public String getRawKey() {
        return this.key;
    }

    public String getKey() {
        return this.key + ':' + this.hint.getHint();
    }

    public String getType() {
        return this.hint.getType();
    }

    public byte[] toBytes() {
        return getKey().getBytes();
    }

    public byte[] rawToBytes() {
        return getRawKey().getBytes();
    }
}