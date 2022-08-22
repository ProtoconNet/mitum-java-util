package org.mitumc.sdk.operation.feefi;

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

public class PoolWithdrawTest {

    @DisplayName("Test feefi@pool-withdraw")
    @Test
    @SuppressWarnings("unchecked")
    void testPoolWithdraw() {
        FeefiGenerator gn = FeefiGenerator.get();

        HashMap<String, Object> sender = Generator.randomKeys();
        Keys senderKs = (Keys) sender.get(Keys.ID);
        String senderAddr = senderKs.getAddress();
        String senderPriv =
        ((HashMap<String, Keypair>) sender.get(Keypair.ID)).get(
                senderKs.getKeys().get(0).getKey()
            )
            .getPrivateKey();

        HashMap<String, Object> pool = Generator.randomKeys();
        String poolAddr = ((Keys) pool.get(Keys.ID)).getAddress();

        Amount am = Amount.get("PEN", "1000");

        PoolWithdrawFact fact = gn.getPoolWithdrawFact(senderAddr, poolAddr, "PEN", new Amount[]{ am });

        assertDoesNotThrow(() -> fact.toBytes());
		assertDoesNotThrow(() -> fact.toDict());
		assertDoesNotThrow(() -> fact.getHash());
		assertDoesNotThrow(() -> fact.getOperationHint());
		assertEquals(
			Hint
				.get(Constant.MF_POOL_WITHDRAW_OPERATION, Constant.VERSION)
				.getHint(),
			fact.getOperationHint().getHint()
		);

        Operation op = Generator.get("mitum").getOperation(fact);
        op.sign(senderPriv);

        op.exportToJsonFile("../test-jsons/test-result/pool-withdraw.json");
    }

    @DisplayName("Test feefi@pool-withdraw - empty amounts")
    @Test
    void testPoolWithdrawEmptyAmounts() {
        FeefiGenerator gn = FeefiGenerator.get();

        HashMap<String, Object> sender = Generator.randomKeys();
        String senderAddr = ((Keys) sender.get(Keys.ID)).getAddress();

        HashMap<String, Object> pool = Generator.randomKeys();
        String poolAddr = ((Keys) pool.get(Keys.ID)).getAddress();

        assertThrows(EmptyElementException.class, () -> gn.getPoolWithdrawFact(
            senderAddr,
            poolAddr,
            "PEN",
            new Amount[]{ }
        ));
    }

    @DisplayName("Test feefi@pool-withdraw - wrong sender")
    @Test
    void testPoolWithdrawWrongSender() {
        FeefiGenerator gn = FeefiGenerator.get();

        HashMap<String, Object> pool = Generator.randomKeys();
        String poolAddr = ((Keys) pool.get(Keys.ID)).getAddress();

        Amount am = Amount.get("PEN", "1000");

        assertThrows(StringFormatException.class, () -> gn.getPoolWithdrawFact(
            "abcdefg123",
            poolAddr,
            "PEN",
            new Amount[]{ am }
        ));
    }

    @DisplayName("Test feefi@pool-withdraw - wrong pool")
    @Test
    void testPoolWithdrawWrongPool() {
        FeefiGenerator gn = FeefiGenerator.get();

        HashMap<String, Object> sender = Generator.randomKeys();
        String senderAddr = ((Keys) sender.get(Keys.ID)).getAddress();

        Amount am = Amount.get("PEN", "1000");

        assertThrows(StringFormatException.class, () -> gn.getPoolWithdrawFact(
            senderAddr,
            "abcdefg123",
            "PEN",
            new Amount[]{ am }
        ));
    }

    @DisplayName("Test feefi@pool-withdraw - wrong currency")
    @Test
    void testPoolWithdrawWrongCurrency() {
        FeefiGenerator gn = FeefiGenerator.get();

        HashMap<String, Object> sender = Generator.randomKeys();
        Keys senderKs = (Keys) sender.get(Keys.ID);
        String senderAddr = senderKs.getAddress();

        HashMap<String, Object> pool = Generator.randomKeys();
        String poolAddr = ((Keys) pool.get(Keys.ID)).getAddress();

        Amount am = Amount.get("PEN", "1000");

        assertThrows(StringFormatException.class, () -> gn.getPoolWithdrawFact(
            senderAddr,
            poolAddr,
            "",
            new Amount[]{ am }
        ));
        assertThrows(StringFormatException.class, () -> gn.getPoolWithdrawFact(
            senderAddr,
            poolAddr,
            "PE",
            new Amount[]{ am }
        ));
        assertThrows(StringFormatException.class, () -> gn.getPoolWithdrawFact(
            senderAddr,
            poolAddr,
            "PENPENPENPEN",
            new Amount[]{ am }
        ));
    }
}
