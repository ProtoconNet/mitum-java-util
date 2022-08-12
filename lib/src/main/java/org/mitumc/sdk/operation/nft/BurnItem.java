package org.mitumc.sdk.operation.nft;

import java.util.HashMap;

import org.mitumc.sdk.Constant;
import org.mitumc.sdk.operation.nft.base.NFTID;
import org.mitumc.sdk.operation.nft.base.NFTItem;
import org.mitumc.sdk.util.Util;

public class BurnItem extends NFTItem {
    private NFTID nid;
    
    BurnItem(NFTID nid, String currencyId) {
        super(Constant.MNFT_BURN_ITEM, currencyId);
        this.nid = nid;
    }

    @Override
    public byte[] toBytes() {
        byte[] bnid = this.nid.toBytes();
        byte[] bcurrencyId = this.currencyId.getBytes();
        return Util.concatByteArray(bnid, bcurrencyId);
    }

    @Override
    public HashMap<String, Object> toDict() {
        HashMap<String, Object> map = new HashMap<>();

        map.put("_hint", this.hint.getHint());
        map.put("nft", this.nid.toDict());
        map.put("currency", this.currencyId);

        return map;
    }
}