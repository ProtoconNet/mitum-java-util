package org.mitumc.sdk.sign;

import java.util.HashMap;

import org.bitcoinj.core.Base58;
import org.mitumc.sdk.BytesChangeable;
import org.mitumc.sdk.Constant;
import org.mitumc.sdk.util.Hint;
import org.mitumc.sdk.util.TimeStamp;
import org.mitumc.sdk.util.Util;

public class FactSign implements BytesChangeable {
    private Hint hint;
    private String signer;
    private byte[] signature;
    private TimeStamp signedAt;

    FactSign(String signer, byte[] signature) {
        this.hint = new Hint(Constant.BASE_FACT_SIGN);
        this.signer = signer;
        this.signature = Util.copyByteArray(signature);
        this.signedAt = Util.getDateTimeStamp();
    }

    public byte[] toBytes() {
        byte[] bsigner = this.signer.getBytes();
        byte[] bsignedAt = this.signedAt.getUTC().getBytes();

        return Util.concatByteArray(bsigner, this.signature, bsignedAt);
    }

    public HashMap<String, Object> toDict() {
        HashMap<String,Object> hashMap = new HashMap<>();

        hashMap.put("_hint", this.hint.getHint());
        hashMap.put("signer", this.signer);
        hashMap.put("signature", Base58.encode(this.signature));
        hashMap.put("signed_at", this.signedAt.getISO());

        return hashMap;
    }
}