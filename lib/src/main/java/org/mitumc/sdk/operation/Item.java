package org.mitumc.sdk.operation;

import java.util.HashMap;

import org.mitumc.sdk.interfaces.BytesChangeable;
import org.mitumc.sdk.interfaces.Dictionariable;
import org.mitumc.sdk.util.Hint;

abstract public class Item implements BytesChangeable, Dictionariable {
    // mitum currency item type
    public static final String ITEM_TYPE_C_CREATE_ACCOUNTS = "item-c-create-accounts";
    public static final String ITEM_TYPE_C_TRANSFERS = "item-c-transfers";
    // mitum blocksign item type
    public static final String ITEM_TYPE_BS_CREATE_DOCUMENTS = "item-bs-create-documents";
    public static final String ITEM_TYPE_BS_SIGN_DOCUMENTS = "item-bs-sign-documents";
    // mitum blockcity item type
    public static final String ITEM_TYPE_BC_CREATE_DOCUMENTS = "item-bc-create-documents";
    public static final String ITEM_TYPE_BC_UPDATE_DOCUMENTS = "item-bc-update-documents";

    protected Hint hint;
    protected String itemType;

    protected Item(String itemType) {
        this.itemType = itemType;
    }

    abstract public void updateItemType();

    abstract public byte[] toBytes();

    abstract public HashMap<String, Object> toDict();
}