package org.mitumc.sdk.operation.blocksign;

import java.util.HashMap;

import org.mitumc.sdk.util.BigInt;
import org.mitumc.sdk.util.Util;
import org.mitumc.sdk.key.Address;

public class SignDocumentsItem extends BlockSignItem {
    private Address owner;
    private BigInt documentId;
    private String currencyId;

    SignDocumentsItem(String owner, int documentId, String currencyId) {
        super(ITEM_TYPE_SIGN_DOCUMENTS);
        this.owner = new Address(owner);
        this.documentId = new BigInt(Integer.toString(documentId));
        this.currencyId = currencyId;
    }

    @Override
    public byte[] toBytes() {
        byte[] bdocumentId = this.documentId.toBytes(BigInt.LITTLE_ENDIAN, true);
        byte[] bowner = this.owner.toBytes();
        byte[] bcurrencyId = this.currencyId.getBytes();

        if(bdocumentId.length < 1) {
            return Util.concatByteArray(bowner, bcurrencyId);
        }

        return Util.concatByteArray(bdocumentId, bowner, bcurrencyId);
    }

    @Override
    public HashMap<String, Object> toDict() {
        HashMap<String, Object> hashMap = new HashMap<>();

        hashMap.put("_hint", this.hint.getHint());
        hashMap.put("documentid", this.documentId.getValue());
        hashMap.put("owner", this.owner.getAddress());
        hashMap.put("currency", this.currencyId);

        return hashMap;
    }
}