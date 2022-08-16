package org.mitumc.sdk.sign;

import java.util.HashMap;

import org.bitcoinj.core.Base58;

import org.mitumc.sdk.interfaces.BytesConvertible;
import org.mitumc.sdk.Constant;
import org.mitumc.sdk.util.Hint;
import org.mitumc.sdk.util.TimeStamp;
import org.mitumc.sdk.util.Util;
import org.mitumc.sdk.key.Keypair;

public class FactSign implements BytesConvertible {
    private Hint hint;
    private String signer;
    private byte[] signature;
    private TimeStamp signedAt;

    private FactSign(String signer, byte[] signature) throws Exception {
        this.signer = signer;
        this.signature = Util.copyByteArray(signature);
        this.signedAt = Util.getDateTimeStamp();
        this.hint = Hint.get(Constant.BASE_FACT_SIGN);
    }

    public static FactSign get(byte[] target, String signKey) throws Exception {
        Keypair keypair = Keypair.fromPrivateKey(signKey);
        byte[] signature = keypair.sign(target);
        String signer = keypair.getPublicKey();

        try {
            return new FactSign(signer, signature);
        } catch (Exception e) {
            throw new Exception(
                    Util.linkErrMsgs(
                            Util.errMsg("failed to create fact sign", Util.getName()),
                            e.getMessage()));
        }
    }

    public byte[] toBytes() {
        byte[] bsigner = this.signer.getBytes();
        byte[] bsignedAt = this.signedAt.getUTC().getBytes();

        return Util.concatByteArray(bsigner, this.signature, bsignedAt);
    }

    public HashMap<String, Object> toDict() {
        HashMap<String, Object> hashMap = new HashMap<>();

        hashMap.put("_hint", this.hint.getHint());
        hashMap.put("signer", this.signer);
        hashMap.put("signature", Base58.encode(this.signature));
        hashMap.put("signed_at", this.signedAt.getISO());

        return hashMap;
    }
}