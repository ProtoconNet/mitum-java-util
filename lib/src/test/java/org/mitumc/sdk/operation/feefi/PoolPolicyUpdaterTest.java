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

public class PoolPolicyUpdaterTest {

    @DisplayName("Test feefi@pool-policy-updater")
    @Test
    @SuppressWarnings("unchecked")
    void testPoolPolicyUpdater() {
        FeefiGenerator gn = FeefiGenerator.get();

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

        PoolPolicyUpdaterFact fact = gn.getPoolPolicyUpdaterFact(senderAddr, targetAddr, "1000", "PEN", "AAA", "MCC");

        assertDoesNotThrow(() -> fact.toBytes());
		assertDoesNotThrow(() -> fact.toDict());
		assertDoesNotThrow(() -> fact.getHash());
		assertDoesNotThrow(() -> fact.getOperationHint());
		assertEquals(
			Hint
				.get(Constant.MF_POOL_POLICY_UPDATER_OPERATION, Constant.VERSION)
				.getHint(),
			fact.getOperationHint().getHint()
		);

        Operation op = Generator.get("mitum").getOperation(fact);
        op.sign(senderPriv);

        op.exportToJsonFile("../test-jsons/test-result/pool-policy-updater.json");
    }

    @DisplayName("Test feefi@pool-policy-updater - wrong sender")
    @Test
    void testPoolPolicyUpdaterWrongSender() {
        FeefiGenerator gn = FeefiGenerator.get();

        HashMap<String, Object> target = Generator.randomKeys();
        String targetAddr = ((Keys) target.get(Keys.ID)).getAddress();

        assertThrows(StringFormatException.class, () -> gn.getPoolPolicyUpdaterFact("abcdefg123", targetAddr, "1000", "PEN", "AAA", "MCC"));
    }

    @DisplayName("Test feefi@pool-policy-updater - wrong target")
    @Test
    void testPoolPolicyUpdaterWrongTarget() {
        FeefiGenerator gn = FeefiGenerator.get();

        HashMap<String, Object> sender = Generator.randomKeys();
        String senderAddr = ((Keys) sender.get(Keys.ID)).getAddress();

        assertThrows(StringFormatException.class, () -> gn.getPoolPolicyUpdaterFact(senderAddr, "abcdefg123", "1000", "PEN", "AAA", "MCC"));
    }

    @DisplayName("Test feefi@pool-policy-updater - wrong fee")
    @Test
    void testPoolPolicyUpdaterWrongFee() {
        FeefiGenerator gn = FeefiGenerator.get();

        HashMap<String, Object> sender = Generator.randomKeys();
        String senderAddr = ((Keys) sender.get(Keys.ID)).getAddress();

        HashMap<String, Object> target = Generator.randomKeys();
        String targetAddr = ((Keys) target.get(Keys.ID)).getAddress();

        assertThrows(NumberRangeException.class, () -> gn.getPoolPolicyUpdaterFact(senderAddr, targetAddr, "-1", "PEN", "AAA", "PEN"));
        assertThrows(NumberFormatException.class, () -> gn.getPoolPolicyUpdaterFact(senderAddr, targetAddr, "abc", "PEN", "AAA", "PEN"));
    }

    @DisplayName("Test feefi@pool-policy-updater - wrong currency")
    @Test
    void testPoolPolicyUpdaterWrongCurrency() {
        FeefiGenerator gn = FeefiGenerator.get();

        HashMap<String, Object> sender = Generator.randomKeys();
        Keys senderKs = (Keys) sender.get(Keys.ID);
        String senderAddr = senderKs.getAddress();

        HashMap<String, Object> target = Generator.randomKeys();
        String targetAddr = ((Keys) target.get(Keys.ID)).getAddress();

        assertThrows(StringFormatException.class, () -> gn.getPoolPolicyUpdaterFact(senderAddr, targetAddr, "1000", "","AAA", "MCC"));
        assertThrows(StringFormatException.class, () -> gn.getPoolPolicyUpdaterFact(senderAddr, targetAddr, "1000", "PE", "AAA", "MCC"));
        assertThrows(StringFormatException.class, () -> gn.getPoolPolicyUpdaterFact(senderAddr, targetAddr, "1000", "PENPENPENPEN", "AAA", "MCC"));
        assertThrows(StringFormatException.class, () -> gn.getPoolPolicyUpdaterFact(senderAddr, targetAddr, "1000", "PEN", "AAA", ""));
        assertThrows(StringFormatException.class, () -> gn.getPoolPolicyUpdaterFact(senderAddr, targetAddr, "1000", "PEN", "AAA", "MC"));
        assertThrows(StringFormatException.class, () -> gn.getPoolPolicyUpdaterFact(senderAddr, targetAddr, "1000", "PEN", "AAA", "MCCMCCMCCMCC"));
        assertThrows(StringFormatException.class, () -> gn.getPoolPolicyUpdaterFact(senderAddr, targetAddr, "1000", "PEN", "", "MCC"));
        assertThrows(StringFormatException.class, () -> gn.getPoolPolicyUpdaterFact(senderAddr, targetAddr, "1000", "PEN", "AA", "MCC"));
        assertThrows(StringFormatException.class, () -> gn.getPoolPolicyUpdaterFact(senderAddr, targetAddr, "1000", "PEN", "AAAAAAAAAAAA", "MCC"));
    }
}
