package org.mitumc.sdk.key;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;

import org.mitumc.sdk.BytesChangeable;
import org.mitumc.sdk.Constant;
import org.mitumc.sdk.Dictionariable;
import org.mitumc.sdk.util.BigInt;
import org.mitumc.sdk.util.Hash;
import org.mitumc.sdk.util.Hint;
import org.mitumc.sdk.util.Util;

public class Keys implements BytesChangeable, Dictionariable {
    private Hint hint;
    private ArrayList<Key> keys;
    private BigInt threshold;
    private Hash hash;

    public Keys(int threshold) {
        this(new Key[0], threshold);
    }

    public Keys(Key[] keys, int threshold) {
        this.hint = new Hint(Constant.MC_KEYS);
        this.keys = new ArrayList<Key>(Arrays.asList(keys));
        setThreshold(threshold);
    }

    private boolean isThresholdValid() {
        if (Integer.parseInt(this.threshold.getValue()) < 1 || Integer.parseInt(this.threshold.getValue()) > 100) {
            return false;
        }

        return true;
    }

    private void generateHash() {
        this.hash = new Hash(toBytes());
    }

    public void setThreshold(int threshold) {
        this.threshold = new BigInt(Integer.toString(threshold));

        if (!isThresholdValid()) {
            Util.raiseError("Invalid threshold for Keys.");
        }

        generateHash();
    }

    public void addKey(Key key) {
        this.keys.add(key);
        generateHash();
    }

    public String getAddress() {
        return this.hash.getSha3Hash() + Constant.MC_ADDRESS;
    }

    public String getHash() {
        return this.hash.getSha3Hash();
    }

    public boolean isOverThreshold() {
        long sum = 0;

        for (Key key : this.keys) {
            sum += key.getWeight();
        }

        if (sum < Integer.parseInt(threshold.getValue())) {
            return false;
        }

        return true;
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
            ByteBuffer b1 = ByteBuffer.wrap(k1.getRawKey().getBytes());
            ByteBuffer b2 = ByteBuffer.wrap(k2.getRawKey().getBytes());

            return b1.compareTo(b2);
        }

    }
}