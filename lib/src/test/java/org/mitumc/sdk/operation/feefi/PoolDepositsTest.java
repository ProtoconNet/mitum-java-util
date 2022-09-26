package org.mitumc.sdk.operation.feefi;

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

public class PoolDepositsTest {
    @DisplayName("Test feefi@pool-deposits")
    @Test
    @SuppressWarnings("unchecked")
    void testPoolDeposits() {
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

        PoolDepositsFact fact = gn.getPoolDepositsFact(
            senderAddr,
            poolAddr,
            "PEN",
            "AAA",
            "1000"
        );

        assertDoesNotThrow(() -> fact.toBytes());
		assertDoesNotThrow(() -> fact.toDict());
		assertDoesNotThrow(() -> fact.getHash());
		assertDoesNotThrow(() -> fact.getOperationHint());
		assertEquals(
			Hint
				.get(Constant.MF_POOL_DEPOSITS_OPERATION, Constant.VERSION)
				.getHint(),
			fact.getOperationHint().getHint()
		);

        Operation op = Generator.get("mitum").getOperation(fact);
        op.sign(senderPriv);

        op.exportToJsonFile("../test-jsons/test-result/pool-deposits.json");
    }

    @DisplayName("Test feefi@pool-deposits - wrong sender")
    @Test
    void testPoolDepositsWrongSender() {
        FeefiGenerator gn = FeefiGenerator.get();

        HashMap<String, Object> pool = Generator.randomKeys();
        String poolAddr = ((Keys) pool.get(Keys.ID)).getAddress();

        assertThrows(StringFormatException.class, () -> gn.getPoolDepositsFact(
            "abcdefg123",
            poolAddr,
            "PEN",
            "AAA",
            "1000"
        ));
    }

    @DisplayName("Test feefi@pool-deposits - wrong pool")
    @Test
    void testPoolDepositsWrongPool() {
        FeefiGenerator gn = FeefiGenerator.get();

        HashMap<String, Object> sender = Generator.randomKeys();
        String senderAddr = ((Keys) sender.get(Keys.ID)).getAddress();

        assertThrows(StringFormatException.class, () -> gn.getPoolDepositsFact(
            senderAddr,
            "abcdefg123",
            "PEN",
            "AAA",
            "1000"
        ));
    }
    
    @DisplayName("Test feefi@pool-deposits - wrong amount")
    @Test
    void testPoolDepositsWrongAmount() {
        FeefiGenerator gn = FeefiGenerator.get();

        HashMap<String, Object> sender = Generator.randomKeys();
        String senderAddr = ((Keys) sender.get(Keys.ID)).getAddress();

        HashMap<String, Object> target = Generator.randomKeys();
        String targetAddr = ((Keys) target.get(Keys.ID)).getAddress();

        assertThrows(NumberRangeException.class, () -> gn.getPoolDepositsFact(
            senderAddr,
            targetAddr,
            "PEN",
            "AAA",
            "-1"
        ));

        assertThrows(NumberFormatException.class, () -> gn.getPoolDepositsFact(
            senderAddr,
            targetAddr,
            "PEN",
            "AAA",
            "abc"
        ));

        assertThrows(NumberRangeException.class, () -> gn.getPoolDepositsFact(
            senderAddr,
            targetAddr,
            "PEN",
            "AAA",
            "0"
        ));
    }

    @DisplayName("Test feefi@pool-deposits - wrong currency")
    @Test
    void testPoolDepositsWrongCurrency() {
        FeefiGenerator gn = FeefiGenerator.get();

        HashMap<String, Object> sender = Generator.randomKeys();
        Keys senderKs = (Keys) sender.get(Keys.ID);
        String senderAddr = senderKs.getAddress();

        HashMap<String, Object> pool = Generator.randomKeys();
        String poolAddr = ((Keys) pool.get(Keys.ID)).getAddress();

        assertThrows(StringFormatException.class, () -> gn.getPoolDepositsFact(
            senderAddr,
            poolAddr,
            "",
            "AAA",
            "1000"
        ));
        assertThrows(StringFormatException.class, () -> gn.getPoolDepositsFact(
            senderAddr,
            poolAddr,
            "PE",
            "AAA",
            "1000"
        ));
        assertThrows(StringFormatException.class, () -> gn.getPoolDepositsFact(
            senderAddr,
            poolAddr,
            "PENPENPENPEN",
            "AAA",
            "1000"
        ));
        assertThrows(StringFormatException.class, () -> gn.getPoolDepositsFact(
            senderAddr,
            poolAddr,
            "PEN",
            "",
            "1000"
        ));
        assertThrows(StringFormatException.class, () -> gn.getPoolDepositsFact(
            senderAddr,
            poolAddr,
            "PEN",
            "AA",
            "1000"
        ));
        assertThrows(StringFormatException.class, () -> gn.getPoolDepositsFact(
            senderAddr,
            poolAddr,
            "PEN",
            "AAAAAAAAAAAA",
            "1000"
        ));
    }
}
