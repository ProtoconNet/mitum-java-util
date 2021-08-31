package org.mitumc.sdk.key;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.Security;
import java.security.spec.ECGenParameterSpec;

import org.bitcoinj.core.Base58;
import org.bouncycastle.jce.provider.*;
import org.mitumc.sdk.Constant;
import org.mitumc.sdk.util.Hash;
import org.mitumc.sdk.util.Util;

public class BTCKeypair extends BaseKeypair {
    private KeyPair keypair;

    BTCKeypair(String key) {
        super(key);
        generatePublicKey();
    }

    private BTCKeypair(String key, String type) {
        super(key, type);
        generatePublicKey();
    }

    public static BTCKeypair newKeypair() {
        Security.addProvider(new BouncyCastleProvider());
        
        try {    
            KeyPairGenerator gen = KeyPairGenerator.getInstance("ECDSA", "BC");
            ECGenParameterSpec spec = new ECGenParameterSpec("secp256k1");
            gen.initialize(spec);

            KeyPair btcKeyPair = gen.generateKeyPair();

            String _temp = btcKeyPair.getPrivate().toString();
            int idx = _temp.indexOf(":") + 2;
            String _pk = "80" + _temp.substring(idx, idx+64) + "01";

            byte[] bytePk = Util.hexStringToBytes(_pk);

            byte[] _hash = new Hash(new Hash(bytePk).getSha256Digest()).getSha256Digest();
            byte[] checksum = new byte[4];
            System.arraycopy(_hash, 0, checksum, 0, 4);

            byte[] pk = new byte[bytePk.length + 4];
            System.arraycopy(bytePk, 0, pk, 0, bytePk.length);
            System.arraycopy(checksum, 0, pk, bytePk.length, 4);

            String encoded = Base58.encode(pk);

            return new BTCKeypair(encoded, Constant.KEY_BTC_PRIVATE);
        } catch(Exception e) {
            Util.raiseError("Fail to generate new BTC Keypair.");
            return null;
        }
    }

    public KeyPair getKeypair() {
        return this.keypair;
    }

    @Override
    void generatePublicKey() {
        Util.raiseError("BTCKeypair.generatePublicKey() must be implemented.");
    }

    @Override
    public byte[] sign(byte[] target) {
        Util.raiseError("BTCKeypair.sign() must be implemented.");
        return new byte[0];
    }
}