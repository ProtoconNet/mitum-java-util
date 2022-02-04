package org.mitumc.sdk.operation.blockcity;

import java.util.HashMap;

import org.mitumc.sdk.Constant;
import org.mitumc.sdk.operation.Item;
import org.mitumc.sdk.util.Hint;
import org.mitumc.sdk.util.Util;

public class BlockCityItem extends Item {
    private String docType;
    private Document document;
    private String currencyId;

    private BlockCityItem(String itemType) {
        super(itemType);
        updateItemType();
    }

    public BlockCityItem(String itemType, String docType, Document document, String currencyId) {
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
        return null;
    }

    @Override
    public HashMap<String, Object> toDict() {
        return null;
    }
}
