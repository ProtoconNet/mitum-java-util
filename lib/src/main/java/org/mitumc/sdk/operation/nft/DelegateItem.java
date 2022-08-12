package org.mitumc.sdk.operation.nft;

import java.util.HashMap;

import org.mitumc.sdk.Constant;
import org.mitumc.sdk.key.Address;
import org.mitumc.sdk.operation.nft.base.NFTItem;
import org.mitumc.sdk.util.Util;

public class DelegateItem extends NFTItem {
    private String collection;
    private Address agent;
    private String mode;
    
    DelegateItem(String collection, String agent, String mode, String currencyId) {
        super(Constant.MNFT_DELEGATE_ITEM, currencyId);
        this.collection = collection;
        this.agent = new Address(agent);
        this.mode = mode;
    }

    @Override
    public byte[] toBytes() {
        byte[] bcollection = this.collection.getBytes();
        byte[] bagent = this.agent.toBytes();
        byte[] bmode = this.mode.getBytes();
        byte[] bcurrencyId = this.currencyId.getBytes();
        return Util.concatByteArray(bcollection, bagent, bmode, bcurrencyId);
    }

    @Override
    public HashMap<String, Object> toDict() {
        HashMap<String, Object> map = new HashMap<>();

        map.put("_hint", this.hint.getHint());
        map.put("collection", this.collection);
        map.put("agent", this.agent.getAddress());
        map.put("mode", this.mode);
        map.put("currency", this.currencyId);

        return map;
    }
}
