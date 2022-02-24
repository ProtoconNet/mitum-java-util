package org.mitumc.sdk.operation.document.base;

import java.util.HashMap;

import org.mitumc.sdk.operation.document.Document;
import org.mitumc.sdk.util.Util;

public class GeneralDocumentsItem extends DocumentsItem {
    private Document document;
    private String currencyId;

    protected GeneralDocumentsItem(String itemType, Document document, String currencyId) {
        super(itemType);
        this.document = document;
        this.currencyId = currencyId;
    }

    @Override
    public byte[] toBytes() {
        byte[] bdocument = this.document.toBytes();
        byte[] bcurrencyId = this.currencyId.getBytes();
        return Util.concatByteArray(bdocument, bcurrencyId);
    }

    @Override
    public HashMap<String, Object> toDict() {
        HashMap<String, Object> hashMap = new HashMap<>();

        hashMap.put("_hint", this.hint.getHint());
        hashMap.put("doc", this.document.toDict());
        hashMap.put("currency", this.currencyId);
        
        return hashMap;
    }
}
