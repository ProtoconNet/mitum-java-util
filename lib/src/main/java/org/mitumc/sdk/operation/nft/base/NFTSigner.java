package org.mitumc.sdk.operation.nft.base;

import java.util.HashMap;

import org.mitumc.sdk.Constant;
import org.mitumc.sdk.exception.NumberRangeException;
import org.mitumc.sdk.interfaces.BytesConvertible;
import org.mitumc.sdk.interfaces.HashMapConvertible;
import org.mitumc.sdk.key.Address;
import org.mitumc.sdk.util.BigInt;
import org.mitumc.sdk.util.Hint;
import org.mitumc.sdk.util.Util;

public class NFTSigner implements BytesConvertible, HashMapConvertible {
    private Hint hint;
    private Address account;
    private BigInt share;
    private boolean signed;

    private NFTSigner(String account, int share, boolean signed) {
        assertShareValidRange(share);
        this.hint = Hint.get(Constant.MNFT_SIGNER);
        this.account = Address.get(account);
        this.share = BigInt.fromInt(share);
        this.signed = signed;
    }

    public static NFTSigner get(String account, int share, boolean signed) {
        return new NFTSigner(account, share, signed);
    }

    private static void assertShareValidRange(int share) {
        if (share < 0 || share > 100) {
            throw new NumberRangeException(Util.errMsg("invalid share - now, " + share, Util.getName()));
        }
    }

    public int getShare() {
        return Integer.parseInt(this.share.getValue());
    }

    @Override
    public byte[] toBytes() {
        byte[] baccount = this.account.toBytes();
        byte[] bshare = this.share.toBytes();
        byte[] bsigned = new byte[] { 0 };

        if (this.signed) {
            bsigned[0] = 1;
        }

        return Util.concatByteArray(baccount, bshare, bsigned);
    }

    @Override
    public HashMap<String, Object> toDict() {
        HashMap<String, Object> map = new HashMap<>();

        map.put("_hint", this.hint.getHint());
        map.put("account", this.account.getAddress());
        map.put("share", Integer.parseInt(this.share.getValue()));
        map.put("signed", this.signed);

        return map;
    }
}
