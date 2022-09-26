package org.mitumc.sdk.operation.nft;

import java.util.HashMap;

import org.mitumc.sdk.Constant;
import org.mitumc.sdk.exception.NumberRangeException;
import org.mitumc.sdk.interfaces.BytesConvertible;
import org.mitumc.sdk.interfaces.HashMapConvertible;
import org.mitumc.sdk.operation.ContractID;
import org.mitumc.sdk.util.BigInt;
import org.mitumc.sdk.util.Hint;
import org.mitumc.sdk.util.Util;

public class NFTID implements BytesConvertible, HashMapConvertible {
    private Hint hint;
    private ContractID collection;
    private BigInt idx;

    private NFTID(String collection, BigInt idx) {
        assertIDXNotZero(idx);
        this.hint = Hint.get(Constant.MNFT_NFT_ID);
        this.collection = ContractID.get(collection);
        this.idx = idx;
    }

    public static NFTID get(String collection, BigInt idx) {
        return new NFTID(collection, idx);
    }

    public static NFTID get(String nid) {
        HashMap<String, String> parsed = Util.parseNFTID(nid);
        return NFTID.get(parsed.get("collection"), BigInt.fromString(parsed.get("idx")));
    }

    private static void assertIDXNotZero(BigInt idx) {
        if (idx.signum() <= 0) {
            throw new NumberRangeException(Util.errMsg("zero idx", Util.getName()));
        }
    }

    @Override
    public byte[] toBytes() {
        byte[] bcollection = this.collection.toBytes();
        byte[] bidx = this.idx.toBytes();
        return Util.concatByteArray(bcollection, bidx);
    }

    @Override
    public HashMap<String, Object> toDict() {
        HashMap<String, Object> map = new HashMap<>();

        map.put("_hint", this.hint.getHint());
        map.put("collection", this.collection.toString());
        map.put("idx", Long.parseLong(this.idx.getValue()));

        return map;
    }
}
