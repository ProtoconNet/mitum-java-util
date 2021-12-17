package org.mitumc.sdk.sign;

import org.mitumc.sdk.key.Keypair;

public class SignManager {
    public static FactSign getFactSignWithSignKey(byte[] target, String signKey) {
        Keypair keypair = Keypair.fromPrivateKey(signKey);
        byte[] signature = keypair.sign(target);
        String signer = keypair.getPublicKey();

        return new FactSign(signer, signature);
    }
}
