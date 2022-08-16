package org.mitumc.sdk.operation.nft;

import java.util.HashMap;

import org.mitumc.sdk.Constant;
import org.mitumc.sdk.operation.nft.base.NFTItem;
import org.mitumc.sdk.util.Util;

public class MintItem extends NFTItem {
    private String collection;
    private MintForm form;

    MintItem(String collection, MintForm form, String currencyId) throws Exception {
        super(Constant.MNFT_MINT_ITEM, currencyId);
        this.collection = collection;
        this.form = form;
    }

    @Override
    public byte[] toBytes() throws Exception {
        byte[] bcollection = this.collection.getBytes();
        byte[] bform = null;
        byte[] bcurrencyId = this.currencyId.getBytes();

        try {
            bform = this.form.toBytes();
        } catch (Exception e) {
            throw new Exception(
                    Util.linkErrMsgs(
                            Util.errMsg("failed to convert mint item to bytes", Util.getName()),
                            e.getMessage()));
        }

        return Util.concatByteArray(bcollection, bform, bcurrencyId);
    }

    @Override
    public HashMap<String, Object> toDict() {
        HashMap<String, Object> map = new HashMap<>();

        map.put("_hint", this.hint.getHint());
        map.put("collection", this.collection);
        map.put("form", this.form.toDict());
        map.put("currency", this.currencyId);

        return map;
    }
}
