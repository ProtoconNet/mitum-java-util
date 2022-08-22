package org.mitumc.sdk.operation.document.blocksign;

import java.util.HashMap;

import org.mitumc.sdk.util.RegExp;
import org.mitumc.sdk.util.Util;
import org.mitumc.sdk.Constant;
import org.mitumc.sdk.key.Address;
import org.mitumc.sdk.operation.CurrencyID;
import org.mitumc.sdk.operation.document.DocumentID;
import org.mitumc.sdk.operation.document.base.PurposedDocumentsItem;

public class SignDocumentsItem extends PurposedDocumentsItem {
    private DocumentID documentId;
    private Address owner;
    private CurrencyID currency;

    SignDocumentsItem(String documentId, String owner, String currency) {
        super(Constant.MBS_SIGN_ITEM_SINGLE_DOCUMENT);
        RegExp.assertBlockSignDocumentID(documentId);
        this.documentId = DocumentID.get(documentId);
        this.owner = Address.get(owner);
        this.currency = CurrencyID.get(currency);
    }

    @Override
    public byte[] toBytes() {
        byte[] bdocumentId = this.documentId.toBytes();
        byte[] bowner = this.owner.toBytes();
        byte[] bcurrencyId = this.currency.toBytes();
        return Util.concatByteArray(bdocumentId, bowner, bcurrencyId);
    }

    @Override
    public HashMap<String, Object> toDict() {
        HashMap<String, Object> hashMap = new HashMap<>();

        hashMap.put("_hint", this.hint.getHint());
        hashMap.put("documentid", this.documentId.getDocumentID());
        hashMap.put("owner", this.owner.getAddress());
        hashMap.put("currency", this.currency.toString());

        return hashMap;
    }
}
