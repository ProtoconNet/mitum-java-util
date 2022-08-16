package org.mitumc.sdk.operation.document.base;

import java.util.HashMap;

import org.mitumc.sdk.util.Util;

public class GeneralDocumentsItem extends DocumentsItem {
    private Document document;
    private String currencyId;

    protected GeneralDocumentsItem(String itemType, Document document, String currencyId) throws Exception {
        super(itemType);
        this.document = document;
        this.currencyId = currencyId;
    }

    @Override
    public byte[] toBytes() throws Exception {
        byte[] bdocument = null;
        byte[] bcurrencyId = this.currencyId.getBytes();

        try {
            this.document.toBytes();
        } catch (Exception e) {
            throw new Exception(
                    Util.linkErrMsgs(
                            Util.errMsg("failed to convert document to bytes", Util.getName()),
                            e.getMessage()));
        }

        return Util.concatByteArray(bdocument, bcurrencyId);
    }

    @Override
    public HashMap<String, Object> toDict() throws Exception {
        HashMap<String, Object> hashMap = new HashMap<>();

        hashMap.put("_hint", this.hint.getHint());
        hashMap.put("currency", this.currencyId);

        try {
            hashMap.put("doc", this.document.toDict());
        } catch (Exception e) {
            throw new Exception(
                    Util.linkErrMsgs(
                            Util.errMsg("failed to convert general document item to hashmap", Util.getName()),
                            e.getMessage()));
        }

        return hashMap;
    }
}
