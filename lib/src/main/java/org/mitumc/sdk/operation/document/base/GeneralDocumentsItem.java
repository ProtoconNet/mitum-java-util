package org.mitumc.sdk.operation.document.base;

import java.util.HashMap;

import org.mitumc.sdk.exception.DummyMethodException;
import org.mitumc.sdk.operation.CurrencyID;
import org.mitumc.sdk.util.Util;

public class GeneralDocumentsItem extends DocumentsItem {
    private Document document;
    private CurrencyID currency;

    protected GeneralDocumentsItem(String itemType, Document document, String currency) {
        super(itemType);
        this.document = document;
        this.currency = CurrencyID.get(currency);
    }

    @Override
    public byte[] toBytes() {
        byte[] bdocument = null;
        byte[] bcurrencyId = this.currency.toBytes();

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
        hashMap.put("currency", this.currency.toString());
        
        try {
            hashMap.put("doc", this.document.toDict());
        } catch(DummyMethodException e) {
            Util.loggingAndExit(e);
        }

        return hashMap;
    }
}
