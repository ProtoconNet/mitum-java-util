package org.mitumc.sdk.key;

import org.mitumc.sdk.Constant;
import org.mitumc.sdk.util.Hint;

import com.wuin.ecdsakeyj.*;

public class ETHERKeypair extends BaseKeypair {
    private ETHKeyPair keypair;

    ETHERKeypair(String key) {
        super(key);
        generatePublicKey();
    }

    ETHERKeypair(String key, String type) {
        super(key, type);
        generatePublicKey();
    }

    public static ETHERKeypair newKeypair() {
        ETHKeyPair kp = new ETHKeyPair();
        return new ETHERKeypair(kp.getPrivateKey() + ":" + new Hint(Constant.KEY_ETHER_PRIVATE).getHint());
    }

    public Object getKeypair() {
        return this.keypair;
    }

    @Override
    void generatePublicKey() {
        this.keypair = new ETHKeyPair(this.privateKey.getRawKey());
        this.publicKey = new BaseKey(this.keypair.getPublicKey(), Constant.KEY_ETHER_PUBLIC);
    }

    @Override
    public byte[] sign(byte[] target) {
        return this.keypair.sign(target);
    }
}