package org.mitumc.sdk.operation.nft.base;

import java.util.HashMap;

import org.mitumc.sdk.Constant;
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

    private NFTSigner(String account, int share, boolean signed) throws Exception {
        this.hint = Hint.get(Constant.MNFT_SIGNER);
        this.account = Address.get(account);
        this.share = new BigInt(share + "");
        this.signed = signed;
    }

    public static NFTSigner get(String account, int share, boolean signed) throws Exception {
        try {
            return new NFTSigner(account, share, signed);
        } catch (Exception e) {
            throw new Exception(
                    Util.linkErrMsgs(
                            Util.errMsg("failed to create nft signer", Util.getName()),
                            e.getMessage()));
        }
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
