package org.mitumc.sdk.operation.nft.base;

import java.util.HashMap;

import org.mitumc.sdk.Constant;
import org.mitumc.sdk.interfaces.BytesConvertible;
import org.mitumc.sdk.interfaces.HashMapConvertible;
import org.mitumc.sdk.util.BigInt;
import org.mitumc.sdk.util.Hint;
import org.mitumc.sdk.util.Util;

public class NFTID implements BytesConvertible, HashMapConvertible {
    private Hint hint;
    private String collection;
    private BigInt idx;

    private NFTID(String collection, BigInt idx) {
        this.hint = Hint.get(Constant.MNFT_NFT_ID);
        this.collection = collection;
        this.idx = idx;
    }

    public static NFTID get(String collection, BigInt idx) {
        return new NFTID(collection, idx);
    }

    public static NFTID get(String nid) {
        HashMap<String, String> parsed = Util.parseNFTID(nid);
        return NFTID.get(parsed.get("collection"), BigInt.fromString(parsed.get("idx")));
    }

    @Override
    public byte[] toBytes() {
        byte[] bcollection = this.collection.getBytes();
        byte[] bidx = this.idx.toBytes();
        return Util.concatByteArray(bcollection, bidx);
    }

    @Override
    public HashMap<String, Object> toDict() {
        HashMap<String, Object> map = new HashMap<>();

        map.put("_hint", this.hint.getHint());
        map.put("collection", this.collection);
        map.put("idx", Long.parseLong(this.idx.getValue()));

        return map;
    }
}
