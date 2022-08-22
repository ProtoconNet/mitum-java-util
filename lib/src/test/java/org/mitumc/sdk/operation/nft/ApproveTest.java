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

public class ApproveTest {
    
    @DisplayName("Test nft@approve")
    @Test
    @SuppressWarnings("unchecked")
    void testApprove() {
        NFTGenerator gn = NFTGenerator.get();

        HashMap<String, Object> sender = Generator.randomKeys();
        Keys senderKs = (Keys) sender.get(Keys.ID);
        String senderAddr = senderKs.getAddress();
        String senderPriv =
        ((HashMap<String, Keypair>) sender.get(Keypair.ID)).get(
                senderKs.getKeys().get(0).getKey()
            )
            .getPrivateKey();

        HashMap<String, Object> approved = Generator.randomKeys();
        String approvedAddr = ((Keys) approved.get(Keys.ID)).getAddress();
    
        ApproveItem item = gn.getApproveItem(approvedAddr, NFTID.get("COL-00001"), "PEN");
        ApproveFact fact = gn.getApproveFact(senderAddr, new ApproveItem[] { item });

        assertDoesNotThrow(() -> fact.toBytes());
		assertDoesNotThrow(() -> fact.toDict());
		assertDoesNotThrow(() -> fact.getHash());
		assertDoesNotThrow(() -> fact.getOperationHint());
		assertEquals(
			Hint
				.get(Constant.MNFT_APPROVE_OPERATION, Constant.VERSION)
				.getHint(),
			fact.getOperationHint().getHint()
		);

        Operation op = Generator.get("mitum").getOperation(fact);
        op.sign(senderPriv);

        op.exportToJsonFile("../test-jsons/test-result/approve.json");
    }

    @DisplayName("Test nft@approve - wrong sender")
    @Test
    void testApproveWrongSender() {
        NFTGenerator gn = NFTGenerator.get();

        HashMap<String, Object> approved = Generator.randomKeys();
        String approvedAddr = ((Keys) approved.get(Keys.ID)).getAddress();
    
        ApproveItem item = gn.getApproveItem(approvedAddr, NFTID.get("COL-00001"), "PEN");
        
        assertThrows(StringFormatException.class, () -> gn.getApproveFact("abcdefg123", new ApproveItem[] { item }));
    }

    @DisplayName("Test nft@approve - wrong approved")
    @Test
    void testApproveWrongApproved() {
        NFTGenerator gn = NFTGenerator.get();
        assertThrows(StringFormatException.class, () -> gn.getApproveItem("abcdefg123", NFTID.get("COL-00001"), "PEN"));
    }

    @DisplayName("Test nft@approve - wrong currency")
    @Test
    void testApproveWrongCurrency() {
        NFTGenerator gn = NFTGenerator.get();

        HashMap<String, Object> approved = Generator.randomKeys();
        String approvedAddr = ((Keys) approved.get(Keys.ID)).getAddress();
    
        assertThrows(StringFormatException.class, () -> gn.getApproveItem(approvedAddr, NFTID.get("COL-00001"), ""));
        assertThrows(StringFormatException.class, () -> gn.getApproveItem(approvedAddr, NFTID.get("COL-00001"), "PE"));
        assertThrows(StringFormatException.class, () -> gn.getApproveItem(approvedAddr, NFTID.get("COL-00001"), "PENPENPENPEN"));
    }

    @DisplayName("Test nft@approve - empty items")
    @Test
    void testApproveWrongSenderEmptyItems() {
        NFTGenerator gn = NFTGenerator.get();

        HashMap<String, Object> sender = Generator.randomKeys();
        String senderAddr = ((Keys) sender.get(Keys.ID)).getAddress();

        assertThrows(EmptyElementException.class, () -> gn.getApproveFact(senderAddr, new ApproveItem[]{ }));
    }
}
