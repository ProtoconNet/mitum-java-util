package org.mitumc.sdk.operation.nft.base;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import org.mitumc.sdk.Constant;
import org.mitumc.sdk.interfaces.BytesConvertible;
import org.mitumc.sdk.interfaces.HashMapConvertible;
import org.mitumc.sdk.util.BigInt;
import org.mitumc.sdk.util.Hint;
import org.mitumc.sdk.util.Util;

public class NFTSigners implements BytesConvertible, HashMapConvertible {
    private Hint hint;
    private BigInt total;
    private ArrayList<NFTSigner> signers;

    private NFTSigners(int total, NFTSigner[] signers) throws Exception {
        this.hint = Hint.get(Constant.MNFT_SIGNERS);
        this.total = new BigInt(total + "");
        this.signers = new ArrayList<NFTSigner>(Arrays.asList(signers));
    }

    public static NFTSigners get(int total, NFTSigner[] signers) throws Exception {
        try {
            return new NFTSigners(total, signers);
        } catch (Exception e) {
            throw new Exception(
                    Util.linkErrMsgs(
                            Util.errMsg("failed to create nft signers", Util.getName()),
                            e.getMessage()));
        }
    }

    @Override
    public byte[] toBytes() throws Exception {
        byte[] btotal = this.total.toBytes();
        byte[] bsigners = null;

        try {
            bsigners = Util.<NFTSigner>concatItemArray(this.signers);
        } catch (Exception e) {
            throw new Exception(
                    Util.linkErrMsgs(
                            Util.errMsg("failed to convert nft signers to bytes", Util.getName()),
                            e.getMessage()));
        }

        return Util.concatByteArray(btotal, bsigners);
    }

    @Override
    public HashMap<String, Object> toDict() {
        HashMap<String, Object> map = new HashMap<>();

        map.put("_hint", this.hint.getHint());
        map.put("total", Integer.parseInt(this.total.getValue()));

        ArrayList<Object> arr = new ArrayList<>();
        for (NFTSigner s : this.signers) {
            arr.add(s.toDict());
        }
        map.put("signers", arr);

        return map;
    }
}
