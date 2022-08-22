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
import org.mitumc.sdk.util.Hint;

public class DelegateTest {
    
    @DisplayName("Test nft@delegate")
    @Test
    @SuppressWarnings("unchecked")
    void testDelegate() {
        NFTGenerator gn = NFTGenerator.get();

        HashMap<String, Object> sender = Generator.randomKeys();
        Keys senderKs = (Keys) sender.get(Keys.ID);
        String senderAddr = senderKs.getAddress();
        String senderPriv =
        ((HashMap<String, Keypair>) sender.get(Keypair.ID)).get(
                senderKs.getKeys().get(0).getKey()
            )
            .getPrivateKey();
    
        HashMap<String, Object> agent = Generator.randomKeys();
        String agentAddr = ((Keys) agent.get(Keys.ID)).getAddress();

        DelegateItem item = gn.getDelegateItem("COL", agentAddr, DelegateItem.ALLOW,  "PEN");
        DelegateFact fact = gn.getDelegateFact(senderAddr, new DelegateItem[] { item });

        assertDoesNotThrow(() -> fact.toBytes());
		assertDoesNotThrow(() -> fact.toDict());
		assertDoesNotThrow(() -> fact.getHash());
		assertDoesNotThrow(() -> fact.getOperationHint());
		assertEquals(
			Hint
				.get(Constant.MNFT_DELEGATE_OPERATION, Constant.VERSION)
				.getHint(),
			fact.getOperationHint().getHint()
		);

        Operation op = Generator.get("mitum").getOperation(fact);
        op.sign(senderPriv);

        op.exportToJsonFile("../test-jsons/test-result/delegate.json");
    }

    @DisplayName("Test nft@delegate - wrong sender")
    @Test
    void testDelegateWrongSender() {
        NFTGenerator gn = NFTGenerator.get();
    
        HashMap<String, Object> agent = Generator.randomKeys();
        String agentAddr = ((Keys) agent.get(Keys.ID)).getAddress();

        DelegateItem item = gn.getDelegateItem("COL", agentAddr, DelegateItem.ALLOW,  "PEN");
        
        assertThrows(StringFormatException.class, () -> gn.getDelegateFact("abcdefg123", new DelegateItem[] { item }));
    }

    @DisplayName("Test nft@delegate - wrong agent")
    @Test
    void testDelegateWrongAgent() {
        NFTGenerator gn = NFTGenerator.get();
        assertThrows(StringFormatException.class, () -> gn.getDelegateItem("COL", "abcdefg123", DelegateItem.ALLOW,  "PEN"));
    }

    @DisplayName("Test nft@delegate - wrong currency")
    @Test
    void testDelegateWrongCurrency() {
        NFTGenerator gn = NFTGenerator.get();
    
        HashMap<String, Object> agent = Generator.randomKeys();
        String agentAddr = ((Keys) agent.get(Keys.ID)).getAddress();

        assertThrows(StringFormatException.class, () -> gn.getDelegateItem("COL", agentAddr, DelegateItem.ALLOW,  ""));
        assertThrows(StringFormatException.class, () -> gn.getDelegateItem("COL", agentAddr, DelegateItem.ALLOW,  "PE"));
        assertThrows(StringFormatException.class, () -> gn.getDelegateItem("COL", agentAddr, DelegateItem.ALLOW,  "PENPENPENPEN"));
    }

    @DisplayName("Test nft@delegate - wrong mode")
    @Test
    void testDelegateWrongQualification() {
        NFTGenerator gn = NFTGenerator.get();
            
        HashMap<String, Object> agent = Generator.randomKeys();
        String agentAddr = ((Keys) agent.get(Keys.ID)).getAddress();
    
        assertDoesNotThrow(() -> gn.getDelegateItem("COL", agentAddr, DelegateItem.ALLOW,  "PEN"));
        assertDoesNotThrow(() -> gn.getDelegateItem("COL", agentAddr, DelegateItem.CANCEL,  "PEN"));

        assertThrows(NoSuchOptionException.class, () -> gn.getDelegateItem("COL", agentAddr, "mode",  "PEN"));
    }

    @DisplayName("Test nft@delegate - empty items")
    @Test
    void testDelegateWrongSenderEmptyItems() {
        NFTGenerator gn = NFTGenerator.get();

        HashMap<String, Object> sender = Generator.randomKeys();
        String senderAddr = ((Keys) sender.get(Keys.ID)).getAddress();

        assertThrows(EmptyElementException.class, () -> gn.getDelegateFact(senderAddr, new DelegateItem[]{ }));
    }
}
