package org.mitumc.sdk.key;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mitumc.sdk.exception.EmptyElementException;
import org.mitumc.sdk.exception.NotEnoughtSumException;
import org.mitumc.sdk.exception.NumberLimitExceededException;
import org.mitumc.sdk.exception.NumberRangeException;
import org.mitumc.sdk.exception.StringFormatException;
import org.mitumc.sdk.util.RegExp;

public class KeyClassTest {

    @DisplayName("Test key")
    @Test
    void testKey() {
        Keypair kp = Keypair.random();

        assertDoesNotThrow(() -> Key.get(kp.getPublicKey(), 100));
        assertDoesNotThrow(() -> Key.get(kp.getPublicKey(), 1));
        assertDoesNotThrow(() -> Key.get(kp.getPublicKey(), 100));

        Key k0 = Key.get(kp.getPublicKey(), 100);
        Key k1 = Key.get(kp.getPublicKey(), 1);
        Key ck = Key.get(kp.getPublicKey(), 100);

        assertEquals(kp.getPublicKey(), k0.getKey());
        assertEquals(kp.getPublicKey(), k1.getKey());
        assertEquals(kp.getPublicKey(), ck.getKey());

        assertEquals(k0.getWeight(), 100);
        assertEquals(k1.getWeight(), 1);
        assertEquals(ck.getWeight(), 100);

        assertEquals(k0.getKey(), ck.getKey());
        assertEquals(k0.getKeyWithoutType(), ck.getKeyWithoutType());
        assertEquals(k0.getWeight(), ck.getWeight());

        assertNotEquals(k0.getWeight(), k1.getWeight());
    }

    @DisplayName("Test key - invalid")
    @Test
    void testKeyInvalid() {
        Keypair kp = Keypair.random();

        assertThrows(NumberRangeException.class, () -> Key.get(kp.getPublicKey(), 0));
        assertThrows(NumberRangeException.class, () -> Key.get(kp.getPublicKey(), 101));
        assertThrows(StringFormatException.class, () -> Key.get("invalid key", 100));
        assertThrows(StringFormatException.class, () -> Key.get("", 100));
    }

    @DisplayName("Test keys")
    @Test
    void testKeys() {
        Keypair[] kps = new Keypair[10];
        Key[] ks = new Key[10];
        for (int i = 0; i < 10; i++) {
            kps[i] = Keypair.random();
            ks[i] = Key.get(kps[i].getPublicKey(), 10);
        }

        assertDoesNotThrow(() -> Keys.get(ks, 100));

        Keys keys = Keys.get(ks, 100);

        assertDoesNotThrow(() -> RegExp.assertAddress(keys.getAddress()));

        assertEquals(keys.getThreshold(), 100);
        assertEquals(keys.getKeys().size(), 10);

        for (int i = 0; i < keys.getKeys().size(); i++) {
            int j = doesKeyExist(keys, kps[i].getPublicKey());
            assertTrue(j >= 0);
            assertEquals(keys.getKeys().get(j).getWeight(), 10);
        }
    }

    @DisplayName("Test keys - invalid")
    @Test
    void testKeysInvalid() {
        Key k = Key.get(Keypair.random().getPublicKey(), 50);

        // zero keys
        assertThrows(NumberRangeException.class, () -> Keys.get(new Key[] {}, 0));
        assertThrows(EmptyElementException.class, () -> Keys.get(new Key[] {}, 100));
        assertThrows(NumberRangeException.class, () -> Keys.get(new Key[] { k }, 0));
        assertThrows(NumberRangeException.class, () -> Keys.get(new Key[] { k }, 101));
        assertThrows(NotEnoughtSumException.class, () -> Keys.get(new Key[] { k }, 100));

        Key[] ks = new Key[11];
        for(int i = 0; i < 11; i ++) {
            ks[i] = Key.get(Keypair.random().getPublicKey(), 100);
        }
        assertThrows(NumberLimitExceededException.class, () -> Keys.get(ks, 100));
    }

    @DisplayName("Test address")
    @Test
    void testAddress() {
        Keypair[] kps0 = new Keypair[10];
        Keypair[] kps1 = new Keypair[10];

        Key[] ks0 = new Key[10];
        Key[] ks1 = new Key[10];
        Key[] cks = new Key[10];


        for(int i = 0; i < 10; i++) {
            kps0[i] = Keypair.random();
            kps1[i] = Keypair.random();
            ks0[i] = Key.get(kps0[i].getPublicKey(), 10 * i + 1);
            ks1[i] = Key.get(kps1[i].getPublicKey(), 11 * i + 1);
            cks[i] = Key.get(kps0[i].getPublicKey(), 10 * i + 1);
        }

        Keys keys0 = Keys.get(ks0, 100);
        Keys keys1 = Keys.get(ks1, 100);
        Keys ckeys = Keys.get(cks, 100);

        assertNotEquals(keys0.getAddress(), keys1.getAddress());
        assertEquals(keys0.getAddress(), ckeys.getAddress());

        assertEquals(keys0.getKeys().size(), 10);
        assertEquals(keys1.getKeys().size(), 10);
        assertEquals(ckeys.getKeys().size(), 10);

        for (int i = 0; i < keys0.getKeys().size(); i++) {
            assertEquals(keys0.getThreshold(), ckeys.getThreshold());
            assertEquals(keys0.getKeys().get(i).getKey(), ckeys.getKeys().get(i).getKey());
            assertEquals(keys0.getKeys().get(i).getWeight(), ckeys.getKeys().get(i).getWeight());
        }
    }

    int doesKeyExist(Keys ks, String k) {
        ArrayList<Key> keys = ks.getKeys();

        for (int i = 0; i < keys.size(); i++) {
            if (keys.get(i).getKey().equals(k)) {
                return i;
            }
        }
        
        return -1;
    }
}
