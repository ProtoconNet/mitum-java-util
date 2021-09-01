package org.mitumc.sdk.operation;

import java.util.HashMap;

import org.mitumc.sdk.util.BigInt;
import org.mitumc.sdk.util.Util;

public class TransferDocumentsItem extends BlockSignItem {
    private Address owner;
    private Address receiver;
    private BigInt documentId;
    private String currencyId;

    TransferDocumentsItem(String owner, String receiver, int documentId, String currencyId) {
        super(Item.ITEM_TYPE_TRANSFER_DOCUMENTS);

        this.owner = new Address(owner);
        this.receiver = new Address(receiver);
        this.documentId = new BigInt(documentId);
        this.currencyId = currencyId;
    }

    @Override
    public byte[] toBytes() {
        byte[] bdocumentId= this.documentId.toBytes(true);
        byte[] bowner = this.owner.toBytes();
        byte[] breceiver = this.receiver.toBytes();
        byte[] bcurrencyId = this.currencyId.getBytes();

        if(bdocumentId.length < 1) {
            return Util.concatByteArray(bowner, breceiver, bcurrencyId);
        }

        return Util.concatByteArray(bdocumentId, bowner, breceiver, bcurrencyId);
    }

    @Override
    public HashMap<String, Object> toDict() {
        HashMap<String, Object> hashMap = new HashMap<>();

        hashMap.put("_hint", this.hint.getHint());
        hashMap.put("documentid", ""+ this.documentId.getValue());
        hashMap.put("owner", this.owner.getAddress());
        hashMap.put("receiver", this.receiver.getAddress());
        hashMap.put("currency", this.currencyId);

        return hashMap;
    }
}
