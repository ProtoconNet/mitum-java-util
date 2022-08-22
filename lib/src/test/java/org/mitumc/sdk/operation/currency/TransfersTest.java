package org.mitumc.sdk.operation.currency;

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
import org.mitumc.sdk.operation.Amount;
import org.mitumc.sdk.operation.Operation;
import org.mitumc.sdk.util.Hint;

public class TransfersTest {

    @DisplayName("Test currency@transfers")
    @Test
    @SuppressWarnings("unchecked")
    void testTransfers() {
        Generator gn = Generator.get("mitum");

        HashMap<String, Object> sender = Generator.randomKeys();
        Keys senderKs = (Keys) sender.get(Keys.ID);
		String senderAddr = senderKs.getAddress();
		String senderPriv =
		((HashMap<String, Keypair>) sender.get(Keypair.ID)).get(
			    senderKs.getKeys().get(0).getKey()
			)
			.getPrivateKey();

        HashMap<String, Object> receiver = Generator.randomKeys();

        Amount am = Amount.get("PEN", "100");

        TransfersItem item = gn.currency.getTransfersItem(
            ((Keys) receiver.get(Keys.ID)).getAddress(),
            new Amount[] { am }
        );
        TransfersFact fact = gn.currency.getTransfersFact(
            senderAddr,
            new TransfersItem[] { item }
        );

        assertDoesNotThrow(() -> fact.toBytes());
        assertDoesNotThrow(() -> fact.toDict());
        assertDoesNotThrow(() -> fact.getHash());
        assertDoesNotThrow(() -> fact.getOperationHint());
        assertEquals(
            Hint.get(Constant.MC_TRANSFERS_OPERATION, Constant.VERSION).getHint(),
            fact.getOperationHint().getHint()
        );

        Operation op = gn.getOperation(fact);
        op.sign(senderPriv);
        op.exportToJsonFile("../test-jsons/test-result/transfers.json");
    }

    @DisplayName("Test currency@transfers - wrong sender")
    @Test
    void testTransfersWrongSender() {
        Generator gn = Generator.get("mitum");

        HashMap<String, Object> receiver = Generator.randomKeys();

        Amount am = Amount.get("PEN", "100");

        TransfersItem item = gn.currency.getTransfersItem(
            ((Keys) receiver.get(Keys.ID)).getAddress(),
            new Amount[] { am }
        );

        assertThrows(
            StringFormatException.class,
            () ->
                gn.currency.getTransfersFact("abcdefg123", new TransfersItem[] { item })
        );
    }

    @DisplayName("Test currency@transfers - wrong receiver")
    @Test
    void testTransfersWrongReceiver() {
        Generator gn = Generator.get("mitum");

        Amount am = Amount.get("PEN", "100");

        assertThrows(
            StringFormatException.class,
            () -> gn.currency.getTransfersItem("abcdefg123", new Amount[] { am })
        );
    }

    @DisplayName("Test currency@transfers - empty items")
    @Test
    void testTransfersEmptyItems() {
        Generator gn = Generator.get("mitum");

        HashMap<String, Object> sender = Generator.randomKeys();
		String senderAddr = ((Keys) sender.get(Keys.ID)).getAddress();

        assertThrows(
            EmptyElementException.class,
            () -> gn.currency.getTransfersFact(senderAddr, new TransfersItem[] {})
        );
    }

    @DisplayName("Test currency@transfers - empty amounts")
    @Test
    void testTransfersEmptyAmounts() {
        Generator gn = Generator.get("mitum");

        HashMap<String, Object> receiver = Generator.randomKeys();

        assertThrows(
        EmptyElementException.class,
            () ->
                gn.currency.getTransfersItem(
                    ((Keys) receiver.get(Keys.ID)).getAddress(),
                    new Amount[] {}
                )
        );
    }
}
