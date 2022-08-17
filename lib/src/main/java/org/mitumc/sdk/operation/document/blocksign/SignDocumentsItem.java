package org.mitumc.sdk.operation.document.blocksign;

import java.util.HashMap;

import org.mitumc.sdk.util.Util;
import org.mitumc.sdk.Constant;
import org.mitumc.sdk.key.Address;
import org.mitumc.sdk.operation.document.DocumentId;
import org.mitumc.sdk.operation.document.base.PurposedDocumentsItem;

public class SignDocumentsItem extends PurposedDocumentsItem {
    private DocumentId documentId;
    private Address owner;
    private String currencyId;

    SignDocumentsItem(String documentId, String owner, String currencyId) {
        super(Constant.MBS_SIGN_ITEM_SINGLE_DOCUMENT);
        this.documentId = DocumentId.get(documentId);
        this.owner = Address.get(owner);
        this.currencyId = currencyId;
    }

    @Override
    public byte[] toBytes() {
        byte[] bdocumentId = this.documentId.toBytes();
        byte[] bowner = this.owner.toBytes();
        byte[] bcurrencyId = this.currencyId.getBytes();
        return Util.concatByteArray(bdocumentId, bowner, bcurrencyId);
    }

    @Override
    public HashMap<String, Object> toDict() {
        HashMap<String, Object> hashMap = new HashMap<>();

        hashMap.put("_hint", this.hint.getHint());
        hashMap.put("documentid", this.documentId.getDocumentId());
        hashMap.put("owner", this.owner.getAddress());
        hashMap.put("currency", this.currencyId);

        return hashMap;
    }
}
