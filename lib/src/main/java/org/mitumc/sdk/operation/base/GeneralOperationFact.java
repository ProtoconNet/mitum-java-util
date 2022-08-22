package org.mitumc.sdk.operation.base;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.HashMap;

import org.mitumc.sdk.exception.EmptyElementException;
import org.mitumc.sdk.exception.DummyMethodException;
import org.mitumc.sdk.key.Address;
import org.mitumc.sdk.util.Hint;
import org.mitumc.sdk.util.Util;

public class GeneralOperationFact<T extends Item> extends OperationFact {
    private Address sender;
    private ArrayList<T> items;

    protected GeneralOperationFact(String factType, String sender, T[] items) {
        super(factType);
        assertNotEmpty(items);
        this.items = new ArrayList<T>(Arrays.asList(items));
        this.sender = Address.get(sender);
        generateHash();
    }

    private void assertNotEmpty(T[] items) {
        if(items.length <= 0) {
            throw new EmptyElementException(Util.errMsg("empty items", Util.getName()));
        }
    }

    @Override
    public Hint getOperationHint() throws DummyMethodException {
        throw new DummyMethodException(Util.getName());
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
        for (Item item : items) {
            try {
                arr.add(item.toDict());
            } catch(DummyMethodException e) {
                Util.loggingAndExit(e);
            }
        }
        hashMap.put("items", arr);

        return hashMap;
    }
}
