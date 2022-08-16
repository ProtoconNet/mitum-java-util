package org.mitumc.sdk.operation.nft;

import java.util.HashMap;

import org.mitumc.sdk.Constant;
import org.mitumc.sdk.key.Address;
import org.mitumc.sdk.operation.nft.base.NFTID;
import org.mitumc.sdk.operation.nft.base.NFTItem;
import org.mitumc.sdk.util.Util;

public class NFTTransferItem extends NFTItem {
    private Address receiver;
    private NFTID nid;

    NFTTransferItem(String receiver, NFTID nid, String currencyId) throws Exception {
        super(Constant.MNFT_TRANSFER_ITEM, currencyId);
        this.receiver = Address.get(receiver);
        this.nid = nid;
    }

    @Override
    public byte[] toBytes() {
        byte[] breceiver = this.receiver.toBytes();
        byte[] bnid = this.nid.toBytes();
        byte[] bcurrencyId = this.currencyId.getBytes();
        return Util.concatByteArray(breceiver, bnid, bcurrencyId);
    }

    @Override
    public HashMap<String, Object> toDict() {
        HashMap<String, Object> map = new HashMap<>();

        map.put("_hint", this.hint.getHint());
        map.put("receiver", this.receiver.getAddress());
        map.put("nft", this.nid.toDict());
        map.put("currency", this.currencyId);

        return map;
    }
}
