package org.mitumc.sdk.operation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.HashMap;

import org.bitcoinj.core.Base58;
import org.mitumc.sdk.Constant;
import org.mitumc.sdk.util.Hash;
import org.mitumc.sdk.util.Util;

public class TransfersFact extends OperationFact {
    private Address sender;
    private ArrayList<TransfersItem> items;

    TransfersFact(String sender) {
        this(sender, new TransfersItem[0]);
    }

    TransfersFact(String sender, TransfersItem[] items) {
        super(Constant.MC_TRANSFERS_OPERATION_FACT);
        this.sender = new Address(sender);
        this.items = new ArrayList<TransfersItem>(Arrays.asList(items));
        generateHash();
    }

    private void generateHash() {
        this.hash = new Hash(toBytes());
    }

    @Override
    public byte[] toBytes() {
        byte[] btoken = this.token.getISO().getBytes();
        byte[] bsender = this.sender.toBytes();
        byte[] bitems = Util.<TransfersItem>concatItemArray(this.items);
        
        return Util.concatByteArray(btoken, bsender, bitems);
    }

    @Override
    public HashMap<String,Object> toDict() {
        HashMap<String, Object> hashMap = new HashMap<>();

        hashMap.put("_hint", this.hint.getHint());
        hashMap.put("hash", Base58.encode(this.hash.getSha3Digest()));
        hashMap.put("token", Base64.getEncoder().encodeToString(this.token.getISO().getBytes()));
        hashMap.put("sender", this.sender.getAddress());

        ArrayList<Object> arr = new ArrayList<>();
        for(TransfersItem item : this.items) {
            arr.add(item.toDict());
        }
        hashMap.put("items", arr);

        return hashMap;
    }
}