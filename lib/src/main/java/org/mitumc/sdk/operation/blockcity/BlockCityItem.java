package org.mitumc.sdk.operation.blockcity;

import java.util.HashMap;

import org.mitumc.sdk.Constant;
import org.mitumc.sdk.operation.Item;
import org.mitumc.sdk.util.Hint;
import org.mitumc.sdk.util.Util;

public class BlockCityItem<T extends Document> extends Item {
    private String docType;
    private T document;
    private String currencyId;

    private BlockCityItem(String itemType) {
        super(itemType);
        updateItemType();
    }

    BlockCityItem(String itemType, String docType, T document, String currencyId) {
        this(itemType);
        this.docType = docType;
        this.document = document;
        this.currencyId = currencyId;
    }

    @Override
    public void updateItemType() {
        switch (this.itemType) {
            case ITEM_TYPE_BC_CREATE_DOCUMENTS:
                this.hint = new Hint(Constant.MBC_CREATE_DOCUMENTS_ITEM);
                break;
            case ITEM_TYPE_BC_UPDATE_DOCUMENTS:
                this.hint = new Hint(Constant.MBC_UPDATE_DOCUMENTS_ITEM);
                break;
            default:
                Util.raiseError("Invalid item type for Item.");
        }
    }

    @Override
    public byte[] toBytes() {
        byte[] bdocType = this.docType.getBytes();
        byte[] bdocument = this.document.toBytes();
        byte[] bcurrencyId = this.currencyId.getBytes();
        return Util.concatByteArray(bdocType, bdocument, bcurrencyId);
    }

    @Override
    public HashMap<String, Object> toDict() {
        HashMap<String, Object> hashMap = new HashMap<>();

        hashMap.put("_hint", this.hint.getHint());
        hashMap.put("doctype", this.docType);
        hashMap.put("doc", this.document.toDict());
        hashMap.put("currency", this.currencyId);
        
        return hashMap;
    }
}
