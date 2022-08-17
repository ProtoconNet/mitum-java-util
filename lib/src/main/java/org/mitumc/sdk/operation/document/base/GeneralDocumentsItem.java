package org.mitumc.sdk.operation.document.base;

import java.util.HashMap;

import org.mitumc.sdk.exception.DummyMethodException;
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
        byte[] bdocument = null;
        byte[] bcurrencyId = this.currencyId.getBytes();

        try {
            bdocument = this.document.toBytes();
        } catch(DummyMethodException e) {
            Util.loggingAndExit(e);
        }

        return Util.concatByteArray(bdocument, bcurrencyId);
    }

    @Override
    public HashMap<String, Object> toDict() {
        HashMap<String, Object> hashMap = new HashMap<>();

        hashMap.put("_hint", this.hint.getHint());
        hashMap.put("currency", this.currencyId);
        
        try {
            hashMap.put("doc", this.document.toDict());
        } catch(DummyMethodException e) {
            Util.loggingAndExit(e);
        }

        return hashMap;
    }
}
