package org.mitumc.sdk.operation.nft;

import java.util.HashMap;

import org.mitumc.sdk.Constant;
import org.mitumc.sdk.exception.NoSuchOptionException;
import org.mitumc.sdk.operation.nft.base.NFTItem;
import org.mitumc.sdk.util.Util;

public class NFTSignItem extends NFTItem {
    public static final String CREATOR = "creator";
    public static final String COPYRIGHTER = "copyrighter";
    private String qualification;
    private NFTID nid;

    NFTSignItem(String qualification, NFTID nid, String currency) {
        super(Constant.MNFT_SIGN_ITEM, currency);
        assertQualificationValid(qualification);
        this.qualification = qualification;
        this.nid = nid;
    }

    private static void assertQualificationValid(String qual) {
        if (!(qual.equals(CREATOR) || qual.equals(COPYRIGHTER))) {
            throw new NoSuchOptionException(Util.errMsg("invalid qualification - use 'creator', 'copyrighter'", Util.getName()));
        }
    }

    @Override
    public byte[] toBytes() {
        byte[] bqual = this.qualification.getBytes();
        byte[] bnid = this.nid.toBytes();
        byte[] bcurrencyId = this.currency.toBytes();
        return Util.concatByteArray(bqual, bnid, bcurrencyId);
    }

    @Override
    public HashMap<String, Object> toDict() {
        HashMap<String, Object> map = new HashMap<>();

        map.put("_hint", this.hint.getHint());
        map.put("qualification", this.qualification);
        map.put("nft", this.nid.toDict());
        map.put("currency", this.currency.toString());

        return map;
    }
}
