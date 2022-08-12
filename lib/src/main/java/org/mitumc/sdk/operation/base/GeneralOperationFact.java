package org.mitumc.sdk.operation.base;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.HashMap;

import org.mitumc.sdk.key.Address;
import org.mitumc.sdk.util.Hint;
import org.mitumc.sdk.util.Util;

public class GeneralOperationFact<T extends Item> extends OperationFact {
    private Address sender;
    private ArrayList<T> items;

    protected GeneralOperationFact(String factType, String sender, T[] items) {
        super(factType);
        this.sender = Address.get(sender);
        this.items = new ArrayList<T>(Arrays.asList(items));
        
        generateHash();
    }

    @Override
    public Hint getOperationHint() {
        Util.raiseError("Unimplemented method getOperationHint(); GeneralOperationFact.");
        return null;
    }

    @Override
    public byte[] toBytes() {
        byte[] btoken = this.token.getISO().getBytes();
        byte[] bsender = this.sender.toBytes();
        byte[] bitems = Util.<T>concatItemArray(this.items);

        return Util.concatByteArray(btoken, bsender, bitems);
    }

    @Override
    public HashMap<String,Object> toDict() {
        HashMap<String, Object> hashMap = new HashMap<>();

        hashMap.put("_hint", this.hint.getHint());
        hashMap.put("hash", this.hash.getSha3Hash());
        hashMap.put("token", Base64.getEncoder().encodeToString(this.token.getISO().getBytes()));
        hashMap.put("sender", this.sender.getAddress());

        ArrayList<Object> arr = new ArrayList<>();
        for(Item item : items) {
            arr.add(item.toDict());
        }
        hashMap.put("items", arr);

        return hashMap;
    }
}
