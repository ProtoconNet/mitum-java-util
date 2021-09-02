package org.mitumc.sdk.key;

import org.mitumc.sdk.Constant;
import org.mitumc.sdk.util.Util;

public abstract class BaseKeypair implements KeyTypeFixable {
    public static final String KEYPAIR_TYPE_BTC = "btc-keypair";
    public static final String KEYPAIR_TYPE_ETHER = "ether-keypair";
    public static final String KEYPAIR_TYPE_STELLAR = "stellar-keypair";

    protected BaseKey privateKey;
    protected BaseKey publicKey;
    protected String keypairType;

    protected BaseKeypair(String key) {
        this.privateKey = new BaseKey(key);
        setType();
    }

    protected BaseKeypair(String key, String privateKeyType) {
        this.privateKey =  new BaseKey(key, privateKeyType);
        setType();
    }

    private void setType() {
        if(Constant.KEY_BTC_PRIVATE.equals(this.privateKey.getType())) {
            this.keypairType = KEYPAIR_TYPE_BTC;
        }
        else if(Constant.KEY_ETHER_PRIVATE.equals(this.privateKey.getType())) {
            this.keypairType = KEYPAIR_TYPE_ETHER;
        }
        else if(Constant.KEY_STELLAR_PRIVATE.equals(this.privateKey.getType())) {
            this.keypairType = KEYPAIR_TYPE_STELLAR;
        }
        else {
            Util.raiseError("Invalid private key type for Keypair.");
        }
    }

    @Override
    public String getKeypairType() {
        return this.keypairType;
    }

    public String getPrivateKey() {
        return this.privateKey.getKey();
    }

    public String getPublicKey() {
        return this.publicKey.getKey();
    }

    abstract void generatePublicKey();
    abstract public byte[] sign(byte[] target);
}