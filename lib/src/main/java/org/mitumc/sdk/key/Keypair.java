package org.mitumc.sdk.key;

import com.wuin.ecdsakeyj.*;

import org.mitumc.sdk.Constant;
import org.mitumc.sdk.exception.NumberRangeException;
import org.mitumc.sdk.util.Util;

public class Keypair {
    public static final String ID = "KEYPAIR_ID";
    private BaseKey privateKey;
    private BaseKey publicKey;
    private BTCKeyPair keypair;

    private Keypair(String key) {
        this.privateKey = BaseKey.get(key);
        generatePublicKey();
    }

    @Deprecated
    public static Keypair create() {
        return Keypair.random();
    }

    public static Keypair random() {
        BTCKeyPair kp = BTCKeyPair.create();
        return fromPrivateKey(kp.getPrivateKey() + Constant.KEY_PRIVATE);
    }

    public static Keypair fromSeed(String seed) {
        assertSeed(seed);
        BTCKeyPair kp = BTCKeyPair.fromSeed(seed);
        return fromPrivateKey(kp.getPrivateKey() + Constant.KEY_PRIVATE);
    }

    public static Keypair fromSeed(byte[] seed) {
        return fromSeed(new String(seed));
    }

    public static Keypair fromPrivateKey(String key) {
        return new Keypair(key);
    }

    private static void assertSeed(String seed) {
        if (seed.length() < 36) {
            throw new NumberRangeException(Util.errMsg(
                    "seed length must be longer than or equal to 36 - now, seed.length() is " + seed.length(),
                    Util.getName()));
        }
    }

    @Deprecated
    public String getRawPrivateKey() {
        return this.getPrivateKeyWithoutType();
    }

    @Deprecated
    public String getRawPublicKey() {
        return this.getPublicKeyWithoutType();
    }

    public String getPrivateKeyWithoutType() {
        return this.privateKey.getKeyWithoutType();
    }

    public String getPublicKeyWithoutType() {
        return this.publicKey.getKeyWithoutType();
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

    private void generatePublicKey() {
        this.keypair = BTCKeyPair.fromPrivateKey(this.privateKey.getKeyWithoutType());
        this.publicKey = BaseKey.get(this.keypair.getPublicKey(), Constant.KEY_PUBLIC);
    }

    public byte[] sign(byte[] target) {
        return this.keypair.sign(target);
    }
}