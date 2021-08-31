package org.mitumc.sdk.key;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.Security;
import java.security.spec.ECGenParameterSpec;

import org.bouncycastle.jce.provider.*;
import org.mitumc.sdk.Constant;
import org.mitumc.sdk.util.Util;

public class ETHERKeypair extends BaseKeypair {
    private KeyPair keypair;

    ETHERKeypair(String key) {
        super(key);
        generatePublicKey();
    }

    ETHERKeypair(String key, String type) {
        super(key, type);
        generatePublicKey();
    }

    public static ETHERKeypair newKeypair() {
        Security.addProvider(new BouncyCastleProvider());

        try {
            KeyPairGenerator gen = KeyPairGenerator.getInstance("ECDSA", "BC");
            ECGenParameterSpec spec = new ECGenParameterSpec("secp256k1");
            gen.initialize(spec);

            KeyPair etherKeypair = gen.generateKeyPair();

            String _temp = etherKeypair.getPrivate().toString();
            int idx = _temp.indexOf(":") + 2;
            String pk = _temp.substring(idx, idx + 64);

            return new ETHERKeypair(pk, Constant.KEY_ETHER_PRIVATE);
        } catch (Exception e) {
            Util.raiseError("Fail to generate new ETHER Keypair.");
            return null;
        }
    }

    public KeyPair getKeypair() {
        return this.keypair;
    }

    @Override
    void generatePublicKey() {
        Util.raiseError("ETHERKeypair.generatePublicKey() must be implemented.");
    }

    @Override
    public byte[] sign(byte[] target) {
        Util.raiseError("ETHERKeypair.sign() must be implemented.");
        return new byte[0];
    }
}