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
import org.mitumc.sdk.exception.NoSuchOptionException;
import org.mitumc.sdk.exception.StringFormatException;
import org.mitumc.sdk.key.Keypair;
import org.mitumc.sdk.key.Keys;
import org.mitumc.sdk.operation.Operation;
import org.mitumc.sdk.operation.nft.base.NFTID;
import org.mitumc.sdk.util.Hint;

public class NFTSignTest {
    
    @DisplayName("Test nft@nft-sign")
    @Test
    @SuppressWarnings("unchecked")
    void testNFTSign() {
        NFTGenerator gn = NFTGenerator.get();

        HashMap<String, Object> sender = Generator.randomKeys();
        Keys senderKs = (Keys) sender.get(Keys.ID);
        String senderAddr = senderKs.getAddress();
        String senderPriv =
        ((HashMap<String, Keypair>) sender.get(Keypair.ID)).get(
                senderKs.getKeys().get(0).getKey()
            )
            .getPrivateKey();
    
        NFTSignItem item = gn.getSignItem(NFTSignItem.CREATOR, NFTID.get("COL-00001"), "PEN");
        NFTSignFact fact = gn.getSignFact(senderAddr, new NFTSignItem[] { item });

        assertDoesNotThrow(() -> fact.toBytes());
		assertDoesNotThrow(() -> fact.toDict());
		assertDoesNotThrow(() -> fact.getHash());
		assertDoesNotThrow(() -> fact.getOperationHint());
		assertEquals(
			Hint
				.get(Constant.MNFT_SIGN_OPERATION, Constant.VERSION)
				.getHint(),
			fact.getOperationHint().getHint()
		);

        Operation op = Generator.get("mitum").getOperation(fact);
        op.sign(senderPriv);

        op.exportToJsonFile("../test-jsons/test-result/nft-sign.json");
    }

    @DisplayName("Test nft@nft-sign - wrong sender")
    @Test
    void testNFTSignWrongSender() {
        NFTGenerator gn = NFTGenerator.get();
    
        NFTSignItem item = gn.getSignItem(NFTSignItem.CREATOR, NFTID.get("COL-00001"), "PEN");
        
        assertThrows(StringFormatException.class, () -> gn.getSignFact("abcdefg123", new NFTSignItem[] { item }));
    }

    @DisplayName("Test nft@nft-sign - wrong currency")
    @Test
    void testNFTSignWrongCurrency() {
        NFTGenerator gn = NFTGenerator.get();
    
        assertThrows(StringFormatException.class, () -> gn.getSignItem(NFTSignItem.CREATOR, NFTID.get("COL-00001"), ""));
        assertThrows(StringFormatException.class, () -> gn.getSignItem(NFTSignItem.CREATOR, NFTID.get("COL-00001"), "PE"));
        assertThrows(StringFormatException.class, () -> gn.getSignItem(NFTSignItem.CREATOR, NFTID.get("COL-00001"), "PENPENPENPEN"));
    }

    @DisplayName("Test nft@nft-sign - wrong qualification")
    @Test
    void testNFTSignWrongQualification() {
        NFTGenerator gn = NFTGenerator.get();
    
        assertDoesNotThrow(() -> gn.getSignItem(NFTSignItem.CREATOR, NFTID.get("COL-00001"), "PEN"));
        assertDoesNotThrow(() -> gn.getSignItem(NFTSignItem.COPYRIGHTER, NFTID.get("COL-00001"), "PEN"));

        assertThrows(NoSuchOptionException.class, () -> gn.getSignItem("qual", NFTID.get("COL-00001"), "PEN"));
    }

    @DisplayName("Test nft@nft-sign - empty items")
    @Test
    void testNFTSignWrongSenderEmptyItems() {
        NFTGenerator gn = NFTGenerator.get();

        HashMap<String, Object> sender = Generator.randomKeys();
        String senderAddr = ((Keys) sender.get(Keys.ID)).getAddress();

        assertThrows(EmptyElementException.class, () -> gn.getSignFact(senderAddr, new NFTSignItem[]{ }));
    }
}
