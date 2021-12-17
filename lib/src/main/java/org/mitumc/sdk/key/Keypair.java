package org.mitumc.sdk.key;

import org.mitumc.sdk.Constant;

import com.wuin.ecdsakeyj.*;

public class Keypair {
    private BaseKey privateKey;
    private BaseKey publicKey;
    private BTCKeyPair keypair;

    private Keypair(String key) {
        this.privateKey = new BaseKey(key);
        generatePublicKey();
    }

    public static Keypair create() throws Exception {
        BTCKeyPair kp = new BTCKeyPair();
        return Keypair.fromSeed(kp.getPrivateKey());
    }

    public static Keypair fromSeed(String seed) throws Exception {
        throw new Exception("fromSeed; not yet implemented");
    }

    public static Keypair fromPrivateKey(String key) {
        return new Keypair(key);
    }

    public String getRawPrivateKey() {
        return this.privateKey.getRawKey();
    }

    public String getRawPublicKey() {
        return this.publicKey.getRawKey();
    }

    public String getPrivateKey() {
        return this.privateKey.getKey();
    }

    public String getPublicKey() {
        return this.publicKey.getKey();
    }

    public Object getKeypair() {
        return this.keypair;
    }

    void generatePublicKey() {
        this.keypair = new BTCKeyPair(this.privateKey.getRawKey());
        this.publicKey = new BaseKey(this.keypair.getPublicKey(), Constant.KEY_PUBLIC);
    }

    public byte[] sign(byte[] target) {
        return this.keypair.sign(target);
    }
}