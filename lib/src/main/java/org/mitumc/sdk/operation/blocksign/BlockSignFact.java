package org.mitumc.sdk.operation.blocksign;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.HashMap;

import org.mitumc.sdk.util.Hash;
import org.mitumc.sdk.util.Util;
import org.mitumc.sdk.key.Address;
import org.mitumc.sdk.operation.OperationFact;

public class BlockSignFact<T extends BlockSignItem> extends OperationFact {
    private Address sender;
    private ArrayList<T> items;

    BlockSignFact(String type, String sender, T[] items) {
        super(type);
        this.sender = new Address(sender);
        this.items = new ArrayList<T>(Arrays.asList(items));

        generateHash();
    }

    private void generateHash() {
        this.hash = new Hash(toBytes());
    }

    @Override
    public byte[] toBytes() {
        byte[] btoken = this.token.getISO().getBytes();
        byte[] bsender = this.sender.toBytes();
        byte[] bitems = Util.<T>concatItemArray(this.items);

        return Util.concatByteArray(btoken, bsender, bitems);
    }

    @Override
    public HashMap<String, Object> toDict() {
        HashMap<String, Object> hashMap = new HashMap<>();

        hashMap.put("_hint", this.hint.getHint());
        hashMap.put("hash", this.hash.getSha3Hash());
        hashMap.put("token", Base64.getEncoder().encodeToString(this.token.getISO().getBytes()));
        hashMap.put("sender", this.sender.getAddress());

        ArrayList<Object> arr = new ArrayList<>();
        for(T item : items) {
            arr.add(item.toDict());
        }
        hashMap.put("items", arr);

        return hashMap;
    }
}
