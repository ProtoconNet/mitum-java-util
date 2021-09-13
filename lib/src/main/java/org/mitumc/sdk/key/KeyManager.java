package org.mitumc.sdk.key;

import java.util.HashMap;

import org.mitumc.sdk.Constant;
import org.mitumc.sdk.util.Hint;
import org.mitumc.sdk.util.Util;

public class KeyManager {
    public static Key newKey(String key, int weight) {
        return new Key(key, weight);
    }

    public static Keys newKeys(int threshold) {
        return new Keys(threshold);
    }

    public static Keys newKeys(Key[] keys, int threshold) {
        return new Keys(keys, threshold);
    }

    public static Object getNewKeypair(String keypairType) {
        switch (keypairType) {
            case BaseKeypair.KEYPAIR_TYPE_BTC:
                return BTCKeypair.newKeypair();
            case BaseKeypair.KEYPAIR_TYPE_ETHER:
                return ETHERKeypair.newKeypair();
            case BaseKeypair.KEYPAIR_TYPE_STELLAR:
                return STELLARKeypair.newKeypair();
            default:
                Util.log("Invalid keypair type for getNewKeypair(keypairType)");
        }

        return null;
    }

    public static Object getKeypairFromPrivateKey(String key) {
        HashMap<String, String> parsed = Util.parseHint(key);
        String hint = parsed.get("hint");
        Hint type = Util.getHintFromString(hint);

        switch (type.getType()) {
            case Constant.KEY_BTC_PRIVATE:
                return new BTCKeypair(key);
            case Constant.KEY_ETHER_PRIVATE:
                return new ETHERKeypair(key);
            case Constant.KEY_STELLAR_PRIVATE:
                return new STELLARKeypair(key);
            default:
                Util.log("Invalid key hint for getKeypairFromPrivateKey(key)");
        }

        return null;
    }

    public static Object getKeypairFromPrivateKey(String key, String type) {

        switch (type) {
            case BaseKeypair.KEYPAIR_TYPE_BTC:
                return new BTCKeypair(key + new Hint(Constant.KEY_BTC_PRIVATE).getHint());
            case BaseKeypair.KEYPAIR_TYPE_ETHER:
                return new ETHERKeypair(key + new Hint(Constant.KEY_ETHER_PRIVATE).getHint());
            case BaseKeypair.KEYPAIR_TYPE_STELLAR:
                return new STELLARKeypair(key + new Hint(Constant.KEY_STELLAR_PRIVATE).getHint());
            default:
                Util.log("Invalid keypair type for getKeypairFromPrivateKey(key, type)");
        }

        return null;
    }
}