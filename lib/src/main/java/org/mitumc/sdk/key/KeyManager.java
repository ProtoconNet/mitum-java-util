package org.mitumc.sdk.key;

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
}