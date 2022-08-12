package org.mitumc.sdk.operation.nft;

import java.util.HashMap;

import org.mitumc.sdk.Constant;
import org.mitumc.sdk.operation.nft.base.NFTID;
import org.mitumc.sdk.operation.nft.base.NFTItem;
import org.mitumc.sdk.util.Util;

public class NFTSignItem extends NFTItem {
    private String qualification;
    private NFTID nid;
    private String currencyId;
    
    NFTSignItem(String qualification, NFTID nid, String currencyId) {
        super(Constant.MNFT_SIGN_ITEM, currencyId);
        this.qualification = qualification;
        this.nid = nid;
    }

    @Override
    public byte[] toBytes() {
        byte[] bqual = this.qualification.getBytes();
        byte[] bnid = this.nid.toBytes();
        byte[] bcurrencyId = this.currencyId.getBytes();
        return Util.concatByteArray(bqual, bnid, bcurrencyId);
    }

    @Override
    public HashMap<String, Object> toDict() {
        HashMap<String, Object> map = new HashMap<>();

        map.put("_hint", this.hint.getHint());
        map.put("qualification", this.qualification);
        map.put("nft", this.nid.toDict());
        map.put("currency", this.currencyId);

        return map;
    }
}
