package org.mitumc.sdk.operation.feefi;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.HashMap;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mitumc.sdk.Constant;
import org.mitumc.sdk.Generator;
import org.mitumc.sdk.exception.StringFormatException;
import org.mitumc.sdk.key.Keypair;
import org.mitumc.sdk.key.Keys;
import org.mitumc.sdk.operation.Amount;
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

        Amount fee = Amount.get("PEN", "1000");

        PoolPolicyUpdaterFact fact = gn.getPoolPolicyUpdaterFact(senderAddr, targetAddr, fee, "PEN", "MCC");

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

        Amount fee = Amount.get("PEN", "1000");

        assertThrows(StringFormatException.class, () -> gn.getPoolPolicyUpdaterFact("abcdefg123", targetAddr, fee, "PEN", "MCC"));
    }

    @DisplayName("Test feefi@pool-policy-updater - wrong target")
    @Test
    void testPoolPolicyUpdaterWrongTarget() {
        FeefiGenerator gn = FeefiGenerator.get();

        HashMap<String, Object> sender = Generator.randomKeys();
        String senderAddr = ((Keys) sender.get(Keys.ID)).getAddress();

        Amount fee = Amount.get("PEN", "1000");

        assertThrows(StringFormatException.class, () -> gn.getPoolPolicyUpdaterFact(senderAddr, "abcdefg123", fee, "PEN", "MCC"));
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

        Amount fee = Amount.get("PEN", "1000");

        assertThrows(StringFormatException.class, () -> gn.getPoolPolicyUpdaterFact(senderAddr, targetAddr, fee, "", "MCC"));
        assertThrows(StringFormatException.class, () -> gn.getPoolPolicyUpdaterFact(senderAddr, targetAddr, fee, "PE", "MCC"));
        assertThrows(StringFormatException.class, () -> gn.getPoolPolicyUpdaterFact(senderAddr, targetAddr, fee, "PENPENPENPEN", "MCC"));
        assertThrows(StringFormatException.class, () -> gn.getPoolPolicyUpdaterFact(senderAddr, targetAddr, fee, "PEN", ""));
        assertThrows(StringFormatException.class, () -> gn.getPoolPolicyUpdaterFact(senderAddr, targetAddr, fee, "PEN", "MC"));
        assertThrows(StringFormatException.class, () -> gn.getPoolPolicyUpdaterFact(senderAddr, targetAddr, fee, "PEN", "MCCMCCMCCMCC"));
    }
}
