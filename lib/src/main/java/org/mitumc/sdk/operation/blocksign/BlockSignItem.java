package org.mitumc.sdk.operation.blocksign;

import org.mitumc.sdk.Constant;
import org.mitumc.sdk.util.Hint;
import org.mitumc.sdk.util.Util;
import org.mitumc.sdk.operation.Item;

abstract class BlockSignItem extends Item {
    protected BlockSignItem(String itemType) {
        super(itemType);
        updateItemType();
    }

    @Override
    public void updateItemType() {
        switch (this.itemType) {
            case ITEM_TYPE_BS_CREATE_DOCUMENTS:
                this.hint = new Hint(Constant.MBS_CREATE_DOCUMENTS_SINGLE_FILE);
                break;
            case ITEM_TYPE_BS_SIGN_DOCUMENTS:
                this.hint = new Hint(Constant.MBS_SIGN_ITEM_SINGLE_DOCUMENT);
                break;
            default:
                Util.raiseError("Invalid item type; BlockSignItem.");
        }
    }
}
