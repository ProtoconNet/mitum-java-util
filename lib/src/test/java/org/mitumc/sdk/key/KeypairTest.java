package org.mitumc.sdk.key;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mitumc.sdk.util.RegExp;

class KeypairTest {
    
    @DisplayName("Test keypair - random")
    @Test
    void testRandomKeypair() {
        Keypair kp = Keypair.random();

        assertDoesNotThrow(() -> RegExp.assertKey(kp.getPrivateKey()));
        assertDoesNotThrow(() -> RegExp.assertKey(kp.getPublicKey()));
        assertDoesNotThrow(() -> RegExp.assertPrivateKey(kp.getPrivateKey()));
        assertDoesNotThrow(() -> RegExp.assertPublicKey(kp.getPublicKey()));
    }

    @DisplayName("Test keypair - from seed")
    @Test
    void testKeypairFromSeed() {
        String stringSeed = "abcdefghijklmnopqrstuvwxyz1234567890!@#$%^&*()";
        byte[] bytesSeed = stringSeed.getBytes();

        Keypair kpFromString = Keypair.fromSeed(stringSeed);
        Keypair kpFromBytes = Keypair.fromSeed(bytesSeed);

        assertDoesNotThrow(() -> RegExp.assertKey(kpFromString.getPrivateKey()));
        assertDoesNotThrow(() -> RegExp.assertKey(kpFromString.getPublicKey()));
        assertDoesNotThrow(() -> RegExp.assertPrivateKey(kpFromString.getPrivateKey()));
        assertDoesNotThrow(() -> RegExp.assertPublicKey(kpFromString.getPublicKey()));

        assertDoesNotThrow(() -> RegExp.assertKey(kpFromBytes.getPrivateKey()));
        assertDoesNotThrow(() -> RegExp.assertKey(kpFromBytes.getPublicKey()));
        assertDoesNotThrow(() -> RegExp.assertPrivateKey(kpFromBytes.getPrivateKey()));
        assertDoesNotThrow(() -> RegExp.assertPublicKey(kpFromBytes.getPublicKey()));

        assertEquals(kpFromString.getPrivateKey(), kpFromBytes.getPrivateKey());
        assertEquals(kpFromString.getPublicKey(), kpFromBytes.getPublicKey());

        stringSeed = "lwkejfl#@439080sdfklj1o48u3.33323li4j2l3";
        Keypair kp = Keypair.fromSeed(stringSeed);

        assertEquals(kp.getPrivateKey(), "KyuqYqJLC9oPtaUudToDFq1kdshADx1sAyDiRaeQHJTNGpziqZJvmpr");
        assertEquals(kp.getPublicKey(), "v99vuWLMn1rBcTi8GQna2wU61CpZh4GWzub3PGwqV7vfmpu");
    }

    @DisplayName("Test keypair - from private key")
    @Test
    void testKeypairFromPrivateKey() {
        Keypair kpRand = Keypair.random();
        Keypair kpFromPriv = Keypair.fromPrivateKey(kpRand.getPrivateKey());

        assertDoesNotThrow(() -> RegExp.assertKey(kpRand.getPrivateKey()));
        assertDoesNotThrow(() -> RegExp.assertKey(kpRand.getPublicKey()));
        assertDoesNotThrow(() -> RegExp.assertPrivateKey(kpRand.getPrivateKey()));
        assertDoesNotThrow(() -> RegExp.assertPublicKey(kpRand.getPublicKey()));

        assertDoesNotThrow(() -> RegExp.assertKey(kpFromPriv.getPrivateKey()));
        assertDoesNotThrow(() -> RegExp.assertKey(kpFromPriv.getPublicKey()));
        assertDoesNotThrow(() -> RegExp.assertPrivateKey(kpFromPriv.getPrivateKey()));
        assertDoesNotThrow(() -> RegExp.assertPublicKey(kpFromPriv.getPublicKey()));

        assertEquals(kpRand.getPrivateKey(), kpFromPriv.getPrivateKey());
        assertEquals(kpRand.getPublicKey(), kpFromPriv.getPublicKey());
    }

    @DisplayName("Test keypair - sign")
    @Test
    void testKeypairSign() {
        byte[] bsTarget = "this is the message to sign".getBytes();

        Keypair kp1 = Keypair.random();
        Keypair kp2 = Keypair.fromPrivateKey(kp1.getPrivateKey());

        assertDoesNotThrow(() -> RegExp.assertKey(kp1.getPrivateKey()));
        assertDoesNotThrow(() -> RegExp.assertKey(kp1.getPublicKey()));
        assertDoesNotThrow(() -> RegExp.assertPrivateKey(kp1.getPrivateKey()));
        assertDoesNotThrow(() -> RegExp.assertPublicKey(kp1.getPublicKey()));

        assertDoesNotThrow(() -> RegExp.assertKey(kp2.getPrivateKey()));
        assertDoesNotThrow(() -> RegExp.assertKey(kp2.getPublicKey()));
        assertDoesNotThrow(() -> RegExp.assertPrivateKey(kp2.getPrivateKey()));
        assertDoesNotThrow(() -> RegExp.assertPublicKey(kp2.getPublicKey()));

        byte[] sg1 = kp1.sign(bsTarget);
        byte[] sg2 = kp2.sign(bsTarget);
        assertEquals(sg1.length, sg2.length);

        for(int i = 0; i < sg1.length; i++) {
            assertEquals(sg1[i], sg2[i]);
        }

        Keypair kp3 = Keypair.fromSeed("abcdefghijklmnopqrstuvwxyz1234567890!@#$%^&*()");
        Keypair kp4 = Keypair.fromPrivateKey(kp3.getPrivateKey());

        assertDoesNotThrow(() -> RegExp.assertKey(kp3.getPrivateKey()));
        assertDoesNotThrow(() -> RegExp.assertKey(kp3.getPublicKey()));
        assertDoesNotThrow(() -> RegExp.assertPrivateKey(kp3.getPrivateKey()));
        assertDoesNotThrow(() -> RegExp.assertPublicKey(kp3.getPublicKey()));

        assertDoesNotThrow(() -> RegExp.assertKey(kp4.getPrivateKey()));
        assertDoesNotThrow(() -> RegExp.assertKey(kp4.getPublicKey()));
        assertDoesNotThrow(() -> RegExp.assertPrivateKey(kp4.getPrivateKey()));
        assertDoesNotThrow(() -> RegExp.assertPublicKey(kp4.getPublicKey()));

        byte[] sg3 = kp3.sign(bsTarget);
        byte[] sg4 = kp4.sign(bsTarget);
        assertEquals(sg3.length, sg4.length);

        for(int i = 0; i < sg3.length; i++) {
            assertEquals(sg3[i], sg4[i]);
        }
    }
}
