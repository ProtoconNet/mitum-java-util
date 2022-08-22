package org.mitumc.sdk.operation.currency.extension;

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

public class WithdrawsTest {

    @DisplayName("Test currency.extension@withdraws")
    @Test
    @SuppressWarnings("unchecked")
    void testWithdraws() {
        Generator gn = Generator.get("mitum");

        HashMap<String, Object> sender = Generator.randomKeys();
        Keys senderKs = (Keys) sender.get(Keys.ID);
		String senderAddr = senderKs.getAddress();
		String senderPriv =
		((HashMap<String, Keypair>) sender.get(Keypair.ID)).get(
				senderKs.getKeys().get(0).getKey()
			)
			.getPrivateKey();

        HashMap<String, Object> target = Generator.randomKeys();
        String targetAddr = ((Keys) target.get(Keys.ID)).getAddress();

        Amount am = Amount.get("PEN", "100");

        WithdrawsItem item = gn.currency.extension.getWithdrawsItem(
            targetAddr,
            new Amount[] { am }
        );
        WithdrawsFact fact = gn.currency.extension.getWithdrawsFact(
            senderAddr,
            new WithdrawsItem[] { item }
        );

        assertDoesNotThrow(() -> fact.toBytes());
        assertDoesNotThrow(() -> fact.toDict());
        assertDoesNotThrow(() -> fact.getHash());
        assertDoesNotThrow(() -> fact.getOperationHint());
        assertEquals(Hint.get(Constant.MC_EXT_WITHDRAWS_OPERATION, Constant.VERSION).getHint(), fact.getOperationHint().getHint());

        Operation op = gn.getOperation(fact);
        op.sign(senderPriv);

        op.exportToJsonFile("../test-jsons/test-result/withdraws.json");
    }

    @DisplayName("Test curerncy.extension@withdraws - wrong sender")
    @Test
    void testWithdrawsWrongSender() {
        Generator gn = Generator.get("mitum");

        HashMap<String, Object> target = Generator.randomKeys();
        String targetAddr = ((Keys) target.get(Keys.ID)).getAddress();

        Amount am = Amount.get("PEN", "100");

        WithdrawsItem item = gn.currency.extension.getWithdrawsItem(
            targetAddr,
            new Amount[] { am }
        );

        assertThrows(
        StringFormatException.class,
        () ->
            gn.currency.extension.getWithdrawsFact(
                "abcdefg123",
                new WithdrawsItem[] { item }
            )
        );
    }

    @DisplayName("Test curerncy.extension@withdraws - wrong target")
    @Test
    void testWithdrawsWrongTarget() {
        Generator gn = Generator.get("mitum");

        Amount am = Amount.get("PEN", "100");

        assertThrows(
        StringFormatException.class,
        () ->
            gn.currency.extension.getWithdrawsItem(
                "abcdefg123",
                new Amount[] { am }
            )
        );
    }

    @DisplayName("Test curerncy.extension@withdraws - empty items")
    @Test
    void testWithdrawsEmptyItems() {
        Generator gn = Generator.get("mitum");

        HashMap<String, Object> sender = Generator.randomKeys();
        String senderAddr = ((Keys) sender.get(Keys.ID)).getAddress();

        assertThrows(
        EmptyElementException.class,
        () ->
            gn.currency.extension.getWithdrawsFact(
                senderAddr,
                new WithdrawsItem[] {}
            )
        );
    }

    @DisplayName(
		"Test currency.extension@withdraws - empty amounts"
	)
	@Test
	void testWithdrawsEmptyAmounts() {
        Generator gn = Generator.get("mitum");

        HashMap<String, Object> target = Generator.randomKeys();
        String targetAddr = ((Keys) target.get(Keys.ID)).getAddress();

        assertThrows(EmptyElementException.class, 
            () -> gn.currency.extension.getWithdrawsItem(
                targetAddr,
                new Amount[] { }
            )
        );
    }
}
