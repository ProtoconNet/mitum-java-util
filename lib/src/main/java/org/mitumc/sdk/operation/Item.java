package org.mitumc.sdk.operation;

import java.util.HashMap;

import org.mitumc.sdk.interfaces.BytesChangeable;
import org.mitumc.sdk.interfaces.Dictionariable;
import org.mitumc.sdk.util.Hint;

abstract public class Item implements BytesChangeable, Dictionariable {
    // mitum currency item type
    public static final String ITEM_TYPE_CREATE_ACCOUNTS = "item-create-accounts";
    public static final String ITEM_TYPE_TRANSFERS = "item-transfers";
    // mitum blocksign item type
    public static final String ITEM_TYPE_CREATE_DOCUMENTS = "item-create-documents";
    public static final String ITEM_TYPE_SIGN_DOCUMENTS = "item-sign-documents";
    public static final String ITEM_TYPE_TRANSFER_DOCUMENTS = "item-transfer-documents";

    protected Hint hint;
    protected String itemType;

    protected Item(String itemType) {
        this.itemType = itemType;
    }

    abstract public void updateItemType();

    abstract public byte[] toBytes();

    abstract public HashMap<String, Object> toDict();
}