package org.mitumc.sdk.operation.nft;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.HashMap;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mitumc.sdk.Constant;
import org.mitumc.sdk.Generator;
import org.mitumc.sdk.exception.NumberRangeException;
import org.mitumc.sdk.exception.StringFormatException;
import org.mitumc.sdk.key.Keypair;
import org.mitumc.sdk.key.Keys;
import org.mitumc.sdk.operation.Operation;
import org.mitumc.sdk.util.Hint;

public class CollectionPolicyUpdaterTest {
    
    @DisplayName("Test nft@collection-regsiter")
    @Test
    @SuppressWarnings("unchecked")
    void testCollectionPolicyUpdater() {
        NFTGenerator gn = NFTGenerator.get();

        HashMap<String, Object> sender = Generator.randomKeys();
        Keys senderKs = (Keys) sender.get(Keys.ID);
        String senderAddr = senderKs.getAddress();
        String senderPriv =
        ((HashMap<String, Keypair>) sender.get(Keypair.ID)).get(
                senderKs.getKeys().get(0).getKey()
            )
            .getPrivateKey();

        HashMap<String, Object> white0 = Generator.randomKeys();
        String white0Addr = ((Keys) white0.get(Keys.ID)).getAddress();

        HashMap<String, Object> white1 = Generator.randomKeys();
        String white1Addr = ((Keys) white1.get(Keys.ID)).getAddress();

        CollectionPolicy policy = gn.collectionPolicy("Collection", 1, "https://localhost:5000", new String[] { white0Addr, white1Addr });
        CollectionPolicyUpdaterFact fact = gn.getCollectionPolicyUpdaterFact(senderAddr, "COL", policy, "PEN");
    
        assertDoesNotThrow(() -> fact.toBytes());
		assertDoesNotThrow(() -> fact.toDict());
		assertDoesNotThrow(() -> fact.getHash());
		assertDoesNotThrow(() -> fact.getOperationHint());
		assertEquals(
			Hint
				.get(Constant.MNFT_COLLECTION_POLICY_UPDATER_OPERATION, Constant.VERSION)
				.getHint(),
			fact.getOperationHint().getHint()
		);

        Operation op = Generator.get("mitum").getOperation(fact);
        op.sign(senderPriv);

        op.exportToJsonFile("../test-jsons/test-result/collection-policy-updater.json");
    }

    @DisplayName("Test nft@collection-regsiter - wrong sender")
    @Test
    void testCollectionPolicyUpdaterWrongSender() {
        NFTGenerator gn = NFTGenerator.get();

        HashMap<String, Object> white0 = Generator.randomKeys();
        String white0Addr = ((Keys) white0.get(Keys.ID)).getAddress();

        HashMap<String, Object> white1 = Generator.randomKeys();
        String white1Addr = ((Keys) white1.get(Keys.ID)).getAddress();

        CollectionPolicy policy = gn.collectionPolicy("Collection", 1, "https://localhost:5000", new String[] { white0Addr, white1Addr });
        assertThrows(StringFormatException.class, () -> gn.getCollectionPolicyUpdaterFact("abcdefg123", "COL", policy, "PEN"));
    }

    @DisplayName("Test nft@collection-regsiter - wrong whites")
    @Test
    void testCollectionPolicyUpdaterWrongWhite() {
        NFTGenerator gn = NFTGenerator.get();

        assertThrows(StringFormatException.class, () -> gn.collectionPolicy("Collection", 1, "https://localhost:5000", new String[] { "abcdefg123" }));
    }

    @DisplayName("Test nft@collection-regsiter - wrong symbol")
    @Test
    void testCollectionPolicyUpdaterWrongSymbol() {
        NFTGenerator gn = NFTGenerator.get();

        HashMap<String, Object> sender = Generator.randomKeys();
        String senderAddr = ((Keys) sender.get(Keys.ID)).getAddress();

        HashMap<String, Object> white0 = Generator.randomKeys();
        String white0Addr = ((Keys) white0.get(Keys.ID)).getAddress();

        HashMap<String, Object> white1 = Generator.randomKeys();
        String white1Addr = ((Keys) white1.get(Keys.ID)).getAddress();

        CollectionPolicy policy = gn.collectionPolicy("Collection", 1, "https://localhost:5000", new String[] { white0Addr, white1Addr });
        
        assertThrows(StringFormatException.class, () -> gn.getCollectionPolicyUpdaterFact(senderAddr, "", policy, "PEN"));
        assertThrows(StringFormatException.class, () -> gn.getCollectionPolicyUpdaterFact(senderAddr, "CO", policy, "PEN"));
        assertThrows(StringFormatException.class, () -> gn.getCollectionPolicyUpdaterFact(senderAddr, "COLCOLCOLCOL", policy, "PEN"));
    }

    @DisplayName("Test nft@collection-regsiter - wrong royalty")
    @Test
    void testCollectionPolicyUpdaterWrongRoyalty() {
        NFTGenerator gn = NFTGenerator.get();

        HashMap<String, Object> white0 = Generator.randomKeys();
        String white0Addr = ((Keys) white0.get(Keys.ID)).getAddress();

        HashMap<String, Object> white1 = Generator.randomKeys();
        String white1Addr = ((Keys) white1.get(Keys.ID)).getAddress();

        assertThrows(NumberRangeException.class, () -> gn.collectionPolicy("Collection", -1, "https://localhost:5000", new String[]{ white0Addr, white1Addr }));
        assertThrows(NumberRangeException.class, () -> gn.collectionPolicy("Collection", 100, "https://localhost:5000", new String[]{ white0Addr, white1Addr }));
    }

    @DisplayName("Test nft@collection-policy-updater - wrong currency")
    @Test
    void testCollectionPolicyUpdaterWrongCurrency() {
        NFTGenerator gn = NFTGenerator.get();

        HashMap<String, Object> sender = Generator.randomKeys();
        Keys senderKs = (Keys) sender.get(Keys.ID);
        String senderAddr = senderKs.getAddress();

        HashMap<String, Object> white0 = Generator.randomKeys();
        String white0Addr = ((Keys) white0.get(Keys.ID)).getAddress();

        HashMap<String, Object> white1 = Generator.randomKeys();
        String white1Addr = ((Keys) white1.get(Keys.ID)).getAddress();

        CollectionPolicy policy = gn.collectionPolicy("Collection", 1, "https://localhost:5000", new String[] { white0Addr, white1Addr });
        
        assertThrows(StringFormatException.class, () -> gn.getCollectionPolicyUpdaterFact(senderAddr, "COL", policy, ""));
        assertThrows(StringFormatException.class, () -> gn.getCollectionPolicyUpdaterFact(senderAddr, "COL", policy, "PE"));
        assertThrows(StringFormatException.class, () -> gn.getCollectionPolicyUpdaterFact(senderAddr, "COL", policy, "PENPENPENPEN"));
    }
}
