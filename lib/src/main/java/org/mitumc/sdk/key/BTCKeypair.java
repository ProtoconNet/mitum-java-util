package org.mitumc.sdk.key;

import org.mitumc.sdk.Constant;
import org.mitumc.sdk.util.Hint;

import com.wuin.ecdsakeyj.*;

public class BTCKeypair extends BaseKeypair {
    private BTCKeyPair keypair;

    BTCKeypair(String key) {
        super(key);
        generatePublicKey();
    }

    private BTCKeypair(String key, String type) {
        super(key, type);
        generatePublicKey();
    }

    public static BTCKeypair newKeypair() {
        BTCKeyPair kp = new BTCKeyPair();
        return new BTCKeypair(kp.getPrivateKey() + "~" +  new Hint(Constant.KEY_BTC_PRIVATE).getHint());
    }

    public Object getKeypair() {
        return this.keypair;
    }

    @Override
    void generatePublicKey() {
        this.keypair = new BTCKeyPair(this.privateKey.getRawKey());
        this.publicKey = new BaseKey(this.keypair.getPublicKey(), Constant.KEY_BTC_PUBLIC);
    }

    @Override
    public byte[] sign(byte[] target) {
        return this.keypair.sign(target);
    }
}