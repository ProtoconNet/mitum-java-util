package org.mitumc.sdk.key;

import com.wuin.ecdsakeyj.*;

import org.mitumc.sdk.Constant;
import org.mitumc.sdk.util.Util;

public class Keypair {
    private BaseKey privateKey;
    private BaseKey publicKey;
    private BTCKeyPair keypair;

    private Keypair(String key) throws Exception {
        this.privateKey = BaseKey.get(key);
        generatePublicKey();
    }

    @Deprecated
    public static Keypair create() throws Exception {
        try {
            return Keypair.random();
        } catch (Exception e) {
            throw new Exception(
                    Util.linkErrMsgs(
                            Util.errMsg("failed to create random keypair", Util.getName()),
                            e.getMessage()));
        }
    }

    public static Keypair random() throws Exception {
        BTCKeyPair kp = BTCKeyPair.create();
        try {
            return fromPrivateKey(kp.getPrivateKey() + Constant.KEY_PRIVATE);
        } catch (Exception e) {
            throw new Exception(
                    Util.linkErrMsgs(
                            Util.errMsg("failed to create random keypair", Util.getName()),
                            e.getMessage()));
        }
    }

    public static Keypair fromSeed(String seed) throws Exception {
        String err = Util.errMsg("failed to create keypair from string seed", Util.getName());

        try {
            assertSeed(seed);
        } catch (Exception e) {
            throw new Exception(Util.linkErrMsgs(err, e.getMessage()));
        }

        BTCKeyPair kp = BTCKeyPair.fromSeed(seed);
        try {
            return fromPrivateKey(kp.getPrivateKey() + Constant.KEY_PRIVATE);
        } catch (Exception e) {
            throw new Exception(Util.linkErrMsgs(err, e.getMessage()));
        }
    }

    public static Keypair fromSeed(byte[] seed) throws Exception {
        try {
            return fromSeed(new String(seed));
        } catch (Exception e) {
            throw new Exception(Util.linkErrMsgs(
                    Util.errMsg("failed to create keypair from bytes seed", Util.getName()), e.getMessage()));
        }
    }

    public static Keypair fromPrivateKey(String key) throws Exception {
        try {
            return new Keypair(key);
        } catch (Exception e) {
            throw new Exception(
                    Util.linkErrMsgs(
                            Util.errMsg("failed to create keypair from private key", Util.getName()),
                            e.getMessage()));
        }
    }

    private static void assertSeed(String seed) throws Exception {
        if (seed.length() < 36) {
            throw new Exception(Util.errMsg(
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

    void generatePublicKey() throws Exception {
        this.keypair = BTCKeyPair.fromPrivateKey(this.privateKey.getKeyWithoutType());
        try {
            this.publicKey = BaseKey.get(this.keypair.getPublicKey(), Constant.KEY_PUBLIC);
        } catch (Exception e) {
            throw new Exception(
                    Util.linkErrMsgs(
                            Util.errMsg("failed to generate public key", Util.getName()),
                            e.getMessage()));
        }
    }

    public byte[] sign(byte[] target) {
        return this.keypair.sign(target);
    }
}