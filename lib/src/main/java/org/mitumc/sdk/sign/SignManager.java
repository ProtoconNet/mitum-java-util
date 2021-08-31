package org.mitumc.sdk.sign;

import java.security.KeyPair;
import java.util.HashMap;

import org.mitumc.sdk.Constant;
import org.mitumc.sdk.key.BTCKeypair;
import org.mitumc.sdk.key.ETHERKeypair;
import org.mitumc.sdk.key.KeyManager;
import org.mitumc.sdk.key.STELLARKeypair;
import org.mitumc.sdk.util.Hint;
import org.mitumc.sdk.util.Util;

public class SignManager {
    public static FactSign getFactSignWithSignKey(byte[] target, String signKey) {
        HashMap<String, String> parsed = Util.parseHint(signKey);
        Hint hint = Util.getHintFromString(parsed.get("hint"));

        Object keypair;
        byte[] signature;
        String signer;
        switch (hint.getType()) {
            case Constant.KEY_BTC_PRIVATE:
                keypair = KeyManager.getKeypairFromPrivateKey(signKey);
                signer = ((BTCKeypair) keypair).getPublicKey();
                signature = ((BTCKeypair) keypair).sign(target);
                return new FactSign(signer, signature);
            case Constant.KEY_ETHER_PRIVATE:
                keypair = KeyManager.getKeypairFromPrivateKey(signKey);
                signer = ((ETHERKeypair) keypair).getPublicKey();
                signature = ((ETHERKeypair) keypair).sign(target);
                return new FactSign(signer, signature);
            case Constant.KEY_STELLAR_PRIVATE:
                keypair = KeyManager.getKeypairFromPrivateKey(signKey);
                signer = ((STELLARKeypair) keypair).getPublicKey();
                signature = ((STELLARKeypair) keypair).sign(target);
                return new FactSign(signer, signature);
            default:
                Util.raiseError("Invalid key type for getFactSignWithSignKey(target, signKey).");
        }

        return null;
    }
}
