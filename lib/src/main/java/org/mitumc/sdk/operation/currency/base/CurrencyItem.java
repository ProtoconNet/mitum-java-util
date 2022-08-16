package org.mitumc.sdk.operation.currency.base;

import java.util.ArrayList;
import java.util.Arrays;
import org.mitumc.sdk.operation.base.Item;

public abstract class CurrencyItem extends Item {
    protected ArrayList<Amount> amounts;

    protected CurrencyItem(String itemType, Amount[] amounts) throws Exception {
        super(itemType);
        this.amounts = new ArrayList<Amount>(Arrays.asList(amounts));
    }
}