package org.mitumc.sdk.operation.nft;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.HashMap;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mitumc.sdk.Constant;
import org.mitumc.sdk.Generator;
import org.mitumc.sdk.exception.EmptyElementException;
import org.mitumc.sdk.exception.StringFormatException;
import org.mitumc.sdk.key.Keypair;
import org.mitumc.sdk.key.Keys;
import org.mitumc.sdk.operation.Operation;
import org.mitumc.sdk.operation.nft.base.NFTID;
import org.mitumc.sdk.util.Hint;

public class BurnTest {
    
    @DisplayName("Test nft@burn")
    @Test
    @SuppressWarnings("unchecked")
    void testBurn() {
        NFTGenerator gn = NFTGenerator.get();

        HashMap<String, Object> sender = Generator.randomKeys();
        Keys senderKs = (Keys) sender.get(Keys.ID);
        String senderAddr = senderKs.getAddress();
        String senderPriv =
        ((HashMap<String, Keypair>) sender.get(Keypair.ID)).get(
                senderKs.getKeys().get(0).getKey()
            )
            .getPrivateKey();
    
        BurnItem item = gn.getBurnItem(NFTID.get("COL-00001"), "PEN");
        BurnFact fact = gn.getBurnFact(senderAddr, new BurnItem[] { item });

        assertDoesNotThrow(() -> fact.toBytes());
		assertDoesNotThrow(() -> fact.toDict());
		assertDoesNotThrow(() -> fact.getHash());
		assertDoesNotThrow(() -> fact.getOperationHint());
		assertEquals(
			Hint
				.get(Constant.MNFT_BURN_OPERATION, Constant.VERSION)
				.getHint(),
			fact.getOperationHint().getHint()
		);

        Operation op = Generator.get("mitum").getOperation(fact);
        op.sign(senderPriv);

        op.exportToJsonFile("../test-jsons/test-result/burn.json");
    }

    @DisplayName("Test nft@burn - wrong sender")
    @Test
    void testBurnWrongSender() {
        NFTGenerator gn = NFTGenerator.get();
    
        BurnItem item = gn.getBurnItem(NFTID.get("COL-00001"), "PEN");
        
        assertThrows(StringFormatException.class, () -> gn.getBurnFact("abcdefg123", new BurnItem[] { item }));
    }

    @DisplayName("Test nft@burn - wrong currency")
    @Test
    void testBurnWrongCurrency() {
        NFTGenerator gn = NFTGenerator.get();
    
        assertThrows(StringFormatException.class, () -> gn.getBurnItem(NFTID.get("COL-00001"), ""));
        assertThrows(StringFormatException.class, () -> gn.getBurnItem(NFTID.get("COL-00001"), "PE"));
        assertThrows(StringFormatException.class, () -> gn.getBurnItem(NFTID.get("COL-00001"), "PENPENPENPEN"));
    }

    @DisplayName("Test nft@burn - empty items")
    @Test
    void testBurnWrongSenderEmptyItems() {
        NFTGenerator gn = NFTGenerator.get();

        HashMap<String, Object> sender = Generator.randomKeys();
        String senderAddr = ((Keys) sender.get(Keys.ID)).getAddress();

        assertThrows(EmptyElementException.class, () -> gn.getBurnFact(senderAddr, new BurnItem[]{ }));
    }
}
