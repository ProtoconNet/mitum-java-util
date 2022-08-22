package org.mitumc.sdk;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashMap;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mitumc.sdk.key.Keypair;
import org.mitumc.sdk.key.Keys;
import org.mitumc.sdk.util.RegExp;

import com.google.gson.JsonObject;

public class GeneratorTest {
    @DisplayName("Test generator@random-keys - simple")
    @Test
    @SuppressWarnings("unchecked")
    void testSimpleRandomKeys() {
        HashMap<String, Object> rks = Generator.randomKeys();
        Keys ks = (Keys) rks.get(Keys.ID);
        HashMap<String, Keypair> kps = (HashMap<String, Keypair>) rks.get(Keypair.ID);

        assertEquals(ks.getKeys().size(), 1);
        assertEquals(kps.size(), 1);

        String pub = ks.getKeys().get(0).getKey();
        assertEquals(pub, kps.get(pub).getPublicKey());
        
        assertDoesNotThrow(() -> RegExp.assertAddress(ks.getAddress()));
    }

    @DisplayName("Test generator@random-keys")
    @Test
    @SuppressWarnings("unchecked")
    void testRandomKeys() {
        HashMap<String, Object> rks = Generator.randomKeys(10);
        Keys ks = (Keys) rks.get(Keys.ID);
        HashMap<String, Keypair> kps = (HashMap<String, Keypair>) rks.get(Keypair.ID);

        assertEquals(ks.getKeys().size(), 10);
        assertEquals(kps.size(), 10);

        for(int i = 0; i < 10; i++) {
            String pub = ks.getKeys().get(i).getKey();
            assertEquals(pub, kps.get(pub).getPublicKey());
        }

        assertDoesNotThrow(() -> RegExp.assertAddress(ks.getAddress()));
    }

    @DisplayName("Test generator@getSeal")
    @Test
    @SuppressWarnings("unchecked")
    void testGetSeal() {
        HashMap<String, Object> rks = Generator.randomKeys();
        Keys ks = (Keys) rks.get(Keys.ID);
        HashMap<String, Keypair> kps = (HashMap<String, Keypair>) rks.get(Keypair.ID);

        JsonObject op0 = JSONParser.getObjectFromJsonFile("../test-jsons/sample/create-accounts.json");
        JsonObject op1 = JSONParser.getObjectFromJsonFile("../test-jsons/sample/key-updater.json");
        JsonObject op2 = JSONParser.getObjectFromJsonFile("../test-jsons/sample/transfers.json");

        HashMap<String, Object> seal = Generator.get("mitum").getSeal(
            kps.get(ks.getKeys().get(0).getKey()).getPrivateKey(), new JsonObject[]{ op0, op1, op2 });

        JSONParser.writeJsonFileFromHashMap(seal, "../test-jsons/test-result/seal.json");
    }
}
