package org.mitumc.sdk.key;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;

import org.mitumc.sdk.interfaces.BytesConvertible;
import org.mitumc.sdk.interfaces.HashMapConvertible;
import org.mitumc.sdk.Constant;
import org.mitumc.sdk.exception.EmptyElementException;
import org.mitumc.sdk.exception.NotEnoughtSumException;
import org.mitumc.sdk.exception.NumberLimitExceededException;
import org.mitumc.sdk.exception.NumberRangeException;
import org.mitumc.sdk.util.BigInt;
import org.mitumc.sdk.util.Hash;
import org.mitumc.sdk.util.Hint;
import org.mitumc.sdk.util.Util;

public class Keys implements BytesConvertible, HashMapConvertible {
    public static final String ID = "KEYS_ID";
    private Hint hint;
    private ArrayList<Key> keys;
    private BigInt threshold;
    private Hash hash;

    private Keys(Key[] keys, int threshold) {
        assertThresholdValidRange(threshold);
        assertNumberOfKeysValidRange(keys);
        assertWeightsSumEnough(keys, threshold);
        this.hint = Hint.get(Constant.MC_KEYS);
        this.keys = new ArrayList<Key>(Arrays.asList(keys));
        this.threshold = BigInt.fromInt(threshold);
        generateHash();
    }

    public static Keys get(Key[] keys, int threshold) {
        return new Keys(keys, threshold);
    }

    private static void assertThresholdValidRange(int threshold) {
        if (threshold < 1 || threshold > 100) {
            throw new NumberRangeException(
                    Util.errMsg("invalid threshold - now, " + threshold, Util.getName()));
        }
    }

    private static void assertWeightsSumEnough(Key[] keys, int threshold) {
        long sum = 0;

        for (Key key : keys) {
            sum += key.getWeight();
        }

        if (sum < threshold) {
            throw new NotEnoughtSumException(
                    Util.errMsg("the sum of all weights doesn't satisfy the condition - now, weight-sum(" + sum
                            + ") < threshold(" + threshold + ")", Util.getName()));
        }
    }

    private static void assertNumberOfKeysValidRange(Key[] keys) {
        if (keys.length <= 0) {
            throw new EmptyElementException(Util.errMsg("empty keys", Util.getName()));
        }

        if (keys.length > 10) {
            throw new NumberLimitExceededException(
                Util.errMsg("the number of keys exceeds max - now, " + keys.length, Util.getName()
            ));
        }
    }

    private void assertNumberOfKeysInRange() {
        if (this.keys.size() <= 0) {
            throw new EmptyElementException(Util.errMsg("empty keys", Util.getName()));
        }

        if (this.keys.size() > 10) {
            throw new NumberLimitExceededException(
                Util.errMsg("the number of keys exceeds max - now, " + this.keys.size(), Util.getName()
            ));
        }
    }

    private void generateHash() {
        assertNumberOfKeysInRange();
        this.hash = Hash.fromBytes(this.toBytes());
    }

    public ArrayList<Key> getKeys() {
        return this.keys;
    }

    public int getThreshold() {
        return Integer.parseInt(this.threshold.getValue());
    }

    public String getAddress() {
        return this.hash.getSha3Hash() + Constant.MC_ADDRESS;
    }

    public String getHash() {
        return this.hash.getSha3Hash();
    }

    @Override
    public byte[] toBytes() {
        this.keys.sort(new Keys.KeyComparator());
        byte[] bkeys = Util.<Key>concatItemArray(this.keys);
        byte[] bthreshold = this.threshold.toBytes();
        return Util.concatByteArray(bkeys, bthreshold);
    }

    @Override
    public HashMap<String, Object> toDict() {
        HashMap<String, Object> hashMap = new HashMap<>();

        hashMap.put("_hint", this.hint.getHint());
        hashMap.put("hash", this.hash.getSha3Hash());

        this.keys.sort(new Keys.KeyComparator());
        ArrayList<Object> _keys = new ArrayList<>();
        for (Key k : this.keys) {
            _keys.add(k.toDict());
        }

        hashMap.put("keys", _keys);
        hashMap.put("threshold", Integer.parseInt(this.threshold.getValue()));

        return hashMap;
    }

    public static class KeyComparator implements Comparator<Key> {
        @Override
        public int compare(Key k1, Key k2) {
            ByteBuffer b1 = ByteBuffer.wrap(k1.getKeyWithoutType().getBytes());
            ByteBuffer b2 = ByteBuffer.wrap(k2.getKeyWithoutType().getBytes());

            return b1.compareTo(b2);
        }
    }
}