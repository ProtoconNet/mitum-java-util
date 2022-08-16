package org.mitumc.sdk.operation.nft.base;

import org.mitumc.sdk.operation.base.Item;

public abstract class NFTItem extends Item {
    protected String currencyId;

    protected NFTItem(String itemType, String currencyId) throws Exception {
        super(itemType);
        this.currencyId = currencyId;
    }
}
