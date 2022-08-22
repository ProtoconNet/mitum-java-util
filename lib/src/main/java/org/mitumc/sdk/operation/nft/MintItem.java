package org.mitumc.sdk.operation.nft;

import java.util.HashMap;

import org.mitumc.sdk.Constant;
import org.mitumc.sdk.operation.ContractID;
import org.mitumc.sdk.operation.nft.base.NFTItem;
import org.mitumc.sdk.util.Util;

public class MintItem extends NFTItem {
    private ContractID collection;
    private MintForm form;

    MintItem(String collection, MintForm form, String currency) {
        super(Constant.MNFT_MINT_ITEM, currency);
        this.collection = ContractID.get(collection);
        this.form = form;
    }

    @Override
    public byte[] toBytes() {
        byte[] bcollection = this.collection.toBytes();
        byte[] bform = this.form.toBytes();
        byte[] bcurrencyId = this.currency.toBytes();
        return Util.concatByteArray(bcollection, bform, bcurrencyId);
    }

    @Override
    public HashMap<String, Object> toDict() {
        HashMap<String, Object> map = new HashMap<>();

        map.put("_hint", this.hint.getHint());
        map.put("collection", this.collection.toString());
        map.put("form", this.form.toDict());
        map.put("currency", this.currency.toString());

        return map;
    }
}
