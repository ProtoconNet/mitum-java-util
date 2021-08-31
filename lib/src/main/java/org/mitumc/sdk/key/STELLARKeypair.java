package org.mitumc.sdk.key;

import org.mitumc.sdk.Constant;
import org.stellar.sdk.KeyPair;

public class STELLARKeypair extends BaseKeypair {
    private KeyPair keypair;

    STELLARKeypair(String key) {
        super(key);
        generatePublicKey();
    }

    STELLARKeypair(String key, String type) {
        super(key, type);
        generatePublicKey();
    }

    public static STELLARKeypair newKeypair() {
        KeyPair stellarKeypair = KeyPair.random();
        return new STELLARKeypair(String.valueOf(stellarKeypair.getSecretSeed()), Constant.KEY_STELLAR_PRIVATE);
    }

    public Object getKeypair() {
        return this.keypair;
    }

    @Override
    void generatePublicKey() {
        this.keypair = KeyPair.fromSecretSeed(this.privateKey.getRawKey());
        this.publicKey = new BaseKey(keypair.getAccountId(), Constant.KEY_STELLAR_PUBLIC);

    }

    @Override
    public byte[] sign(byte[] target) {
        return this.keypair.sign(target);
    }
}