package org.mitumc.sdk.operation.nft;

import java.util.HashMap;

import org.mitumc.sdk.Constant;
import org.mitumc.sdk.key.Address;
import org.mitumc.sdk.operation.nft.base.NFTID;
import org.mitumc.sdk.operation.nft.base.NFTItem;
import org.mitumc.sdk.util.Util;

public class ApproveItem extends NFTItem {
    private Address approved;
    private NFTID nid;

    ApproveItem(String approved, NFTID nid, String currency) {
        super(Constant.MNFT_APPROVE_ITEM, currency);
        this.approved = Address.get(approved);
        this.nid = nid;
    }

    @Override
    public byte[] toBytes() {
        byte[] bapproved = this.approved.toBytes();
        byte[] bnid = this.nid.toBytes();
        byte[] bcurrencyId = this.currency.toBytes();
        return Util.concatByteArray(bapproved, bnid, bcurrencyId);
    }

    @Override
    public HashMap<String, Object> toDict() {
        HashMap<String, Object> map = new HashMap<>();

        map.put("_hint", this.hint.getHint());
        map.put("approved", this.approved.getAddress());
        map.put("nft", this.nid.toDict());
        map.put("currency", this.currency.toString());

        return map;
    }
}
