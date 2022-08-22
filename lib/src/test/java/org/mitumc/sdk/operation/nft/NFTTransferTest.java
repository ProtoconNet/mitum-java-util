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

public class NFTTransferTest {
    
    @DisplayName("Test nft@nft-transfer")
    @Test
    @SuppressWarnings("unchecked")
    void testNFTTransfer() {
        NFTGenerator gn = NFTGenerator.get();

        HashMap<String, Object> sender = Generator.randomKeys();
        Keys senderKs = (Keys) sender.get(Keys.ID);
        String senderAddr = senderKs.getAddress();
        String senderPriv =
        ((HashMap<String, Keypair>) sender.get(Keypair.ID)).get(
                senderKs.getKeys().get(0).getKey()
            )
            .getPrivateKey();

        HashMap<String, Object> receiver = Generator.randomKeys();
        String receiverAddr = ((Keys) receiver.get(Keys.ID)).getAddress();
    
        NFTTransferItem item = gn.getTransferItem(receiverAddr, NFTID.get("COL-00001"), "PEN");
        NFTTransferFact fact = gn.getTransferFact(senderAddr, new NFTTransferItem[] { item });

        assertDoesNotThrow(() -> fact.toBytes());
		assertDoesNotThrow(() -> fact.toDict());
		assertDoesNotThrow(() -> fact.getHash());
		assertDoesNotThrow(() -> fact.getOperationHint());
		assertEquals(
			Hint
				.get(Constant.MNFT_TRANSFER_OPERATION, Constant.VERSION)
				.getHint(),
			fact.getOperationHint().getHint()
		);

        Operation op = Generator.get("mitum").getOperation(fact);
        op.sign(senderPriv);

        op.exportToJsonFile("../test-jsons/test-result/nft-transfer.json");
    }

    @DisplayName("Test nft@nft-transfer - wrong sender")
    @Test
    void testNFTTransferWrongSender() {
        NFTGenerator gn = NFTGenerator.get();

        HashMap<String, Object> receiver = Generator.randomKeys();
        String receiverAddr = ((Keys) receiver.get(Keys.ID)).getAddress();
    
        NFTTransferItem item = gn.getTransferItem(receiverAddr, NFTID.get("COL-00001"), "PEN");
        
        assertThrows(StringFormatException.class, () -> gn.getTransferFact("abcdefg123", new NFTTransferItem[] { item }));
    }

    @DisplayName("Test nft@nft-transfer - wrong receiver")
    @Test
    void testNFTTransferWrongNFTTransferd() {
        NFTGenerator gn = NFTGenerator.get();
        assertThrows(StringFormatException.class, () -> gn.getTransferItem("abcdefg123", NFTID.get("COL-00001"), "PEN"));
    }

    @DisplayName("Test nft@nft-transfer - wrong currency")
    @Test
    void testNFTTransferWrongCurrency() {
        NFTGenerator gn = NFTGenerator.get();

        HashMap<String, Object> receiver = Generator.randomKeys();
        String receiverAddr = ((Keys) receiver.get(Keys.ID)).getAddress();
    
        assertThrows(StringFormatException.class, () -> gn.getTransferItem(receiverAddr, NFTID.get("COL-00001"), ""));
        assertThrows(StringFormatException.class, () -> gn.getTransferItem(receiverAddr, NFTID.get("COL-00001"), "PE"));
        assertThrows(StringFormatException.class, () -> gn.getTransferItem(receiverAddr, NFTID.get("COL-00001"), "PENPENPENPEN"));
    }

    @DisplayName("Test nft@nft-transfer - empty items")
    @Test
    void testNFTTransferWrongSenderEmptyItems() {
        NFTGenerator gn = NFTGenerator.get();

        HashMap<String, Object> sender = Generator.randomKeys();
        String senderAddr = ((Keys) sender.get(Keys.ID)).getAddress();

        assertThrows(EmptyElementException.class, () -> gn.getTransferFact(senderAddr, new NFTTransferItem[]{ }));
    }
}
