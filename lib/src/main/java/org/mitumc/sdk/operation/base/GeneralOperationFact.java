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

    protected GeneralOperationFact(String factType, String sender, T[] items) throws Exception {
        super(factType);
        this.items = new ArrayList<T>(Arrays.asList(items));

        try {
            this.sender = Address.get(sender);
        } catch (Exception e) {
            throw new Exception(
                    Util.linkErrMsgs(
                            Util.errMsg("failed to create general operation fact", Util.getName()),
                            e.getMessage()));
        }

        generateHash();
    }

    @Override
    public Hint getOperationHint() throws Exception {
        throw new Exception(Util.errMsg("unimplemented method", Util.getName()));
    }

    @Override
    public byte[] toBytes() throws Exception {
        byte[] btoken = this.token.getISO().getBytes();
        byte[] bsender = this.sender.toBytes();
        byte[] bitems = null;
        try {
            bitems = Util.<T>concatItemArray(this.items);
        } catch (Exception e) {
            throw new Exception(
                    Util.linkErrMsgs(
                            Util.errMsg("failed to convert general operation fact to bytes", Util.getName()),
                            e.getMessage()));
        }
        return Util.concatByteArray(btoken, bsender, bitems);
    }

    @Override
    public HashMap<String, Object> toDict() throws Exception {
        HashMap<String, Object> hashMap = new HashMap<>();

        hashMap.put("_hint", this.hint.getHint());
        hashMap.put("hash", this.hash.getSha3Hash());
        hashMap.put("token", Base64.getEncoder().encodeToString(this.token.getISO().getBytes()));
        hashMap.put("sender", this.sender.getAddress());

        ArrayList<Object> arr = new ArrayList<>();
        try {
            for (Item item : items) {
                arr.add(item.toDict());
            }
        } catch (Exception e) {
            throw new Exception(
                    Util.linkErrMsgs(
                            Util.errMsg("failed to convert general operation fact to hashmap", Util.getName()),
                            e.getMessage()));
        }
        hashMap.put("items", arr);

        return hashMap;
    }
}
