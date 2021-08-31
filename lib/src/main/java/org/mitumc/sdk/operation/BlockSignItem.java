package org.mitumc.sdk.operation;

import org.mitumc.sdk.Constant;
import org.mitumc.sdk.util.Hint;
import org.mitumc.sdk.util.Util;

abstract class BlockSignItem extends Item {
    protected BlockSignItem(String itemType) {
        super(itemType);
        updateItemType();
    }

    @Override
    void updateItemType() {
        switch (this.itemType) {
            case ITEM_TYPE_CREATE_DOCUMENTS:
                this.hint = new Hint(Constant.MBS_CREATE_DOCUMENTS_SINGLE_FILE);
                break;
            case ITEM_TYPE_SIGN_DOCUMENTS:
                this.hint = new Hint(Constant.MBS_SIGN_ITEM_SINGLE_DOCUMENT);
                break;
            case ITEM_TYPE_TRANSFER_DOCUMENTS:
                this.hint = new Hint(Constant.MBS_TRANSFER_ITEM_SINGLE_DOCUMENT);
                break;
            default:
                Util.raiseError("Invalid item type for Item.");
        }
    }
}
