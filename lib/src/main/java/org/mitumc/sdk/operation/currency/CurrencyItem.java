package org.mitumc.sdk.operation.currency;

import java.util.ArrayList;
import java.util.Arrays;

import org.mitumc.sdk.Constant;
import org.mitumc.sdk.util.Hint;
import org.mitumc.sdk.util.Util;
import org.mitumc.sdk.operation.Item;

abstract class CurrencyItem extends Item {
    protected ArrayList<Amount> amounts;

    protected CurrencyItem(String itemType, Amount[] amounts) {
        super(itemType);
        this.amounts = new ArrayList<Amount>(Arrays.asList(amounts));
        updateItemType();
    }

    @Override
    public void updateItemType() {
        switch (this.itemType) {
            case ITEM_TYPE_C_CREATE_ACCOUNTS:
                if (this.amounts.size() > 1) {
                    this.hint = new Hint(Constant.MC_CREATE_ACCOUNTS_MUL_AMOUNTS);
                } else {
                    this.hint = new Hint(Constant.MC_CREATE_ACCOUNTS_SIN_AMOUNT);
                }
                break;
            case ITEM_TYPE_C_TRANSFERS:
                if (this.amounts.size() > 1) {
                    this.hint = new Hint(Constant.MC_TRANSFERS_ITEM_MUL_AMOUNTS);
                } else {
                    this.hint = new Hint(Constant.MC_TRANSFERS_ITEM_SIN_AMOUNT);
                }
                break;
            default:
                Util.raiseError("Invalid item type for Item.");
        }
    }
}