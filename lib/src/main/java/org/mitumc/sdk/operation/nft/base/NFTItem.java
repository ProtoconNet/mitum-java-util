package org.mitumc.sdk.operation.nft.base;

import org.mitumc.sdk.operation.CurrencyID;
import org.mitumc.sdk.operation.base.Item;

public abstract class NFTItem extends Item {
    protected CurrencyID currency;

    protected NFTItem(String itemType, String currency) {
        super(itemType);
        this.currency = CurrencyID.get(currency);
    }
}
