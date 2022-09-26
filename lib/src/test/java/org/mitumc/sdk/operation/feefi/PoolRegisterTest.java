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

public class PoolRegisterTest {

    @DisplayName("Test feefi@pool-register")
    @Test
    @SuppressWarnings("unchecked")
    void testPoolRegister() {
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

        PoolRegisterFact fact = gn.getPoolRegisterFact(senderAddr, targetAddr, "1000", "PEN", "MCC", "MCC");

        assertDoesNotThrow(() -> fact.toBytes());
		assertDoesNotThrow(() -> fact.toDict());
		assertDoesNotThrow(() -> fact.getHash());
		assertDoesNotThrow(() -> fact.getOperationHint());
		assertEquals(
			Hint
				.get(Constant.MF_POOL_REGISTER_OPERATION, Constant.VERSION)
				.getHint(),
			fact.getOperationHint().getHint()
		);

        Operation op = Generator.get("mitum").getOperation(fact);
        op.sign(senderPriv);

        op.exportToJsonFile("../test-jsons/test-result/pool-register.json");
    }

    @DisplayName("Test feefi@pool-register - wrong sender")
    @Test
    void testPoolRegisterWrongSender() {
        FeefiGenerator gn = FeefiGenerator.get();

        HashMap<String, Object> target = Generator.randomKeys();
        String targetAddr = ((Keys) target.get(Keys.ID)).getAddress();

        assertThrows(StringFormatException.class, () -> gn.getPoolRegisterFact("abcdefg123", targetAddr, "1000", "PEN", "MCC", "MCC"));
    }

    @DisplayName("Test feefi@pool-register - wrong target")
    @Test
    void testPoolRegisterWrongTarget() {
        FeefiGenerator gn = FeefiGenerator.get();

        HashMap<String, Object> sender = Generator.randomKeys();
        String senderAddr = ((Keys) sender.get(Keys.ID)).getAddress();

        assertThrows(StringFormatException.class, () -> gn.getPoolRegisterFact(senderAddr, "abcdefg123", "1000", "PEN", "MCC", "MCC"));
    }

    @DisplayName("Test feefi@pool-register - wrong fee")
    @Test
    void testPoolRegisterWrongFee() {
        FeefiGenerator gn = FeefiGenerator.get();

        HashMap<String, Object> sender = Generator.randomKeys();
        String senderAddr = ((Keys) sender.get(Keys.ID)).getAddress();

        HashMap<String, Object> target = Generator.randomKeys();
        String targetAddr = ((Keys) target.get(Keys.ID)).getAddress();

        assertThrows(NumberRangeException.class, () -> gn.getPoolRegisterFact(senderAddr, targetAddr, "-1", "PEN", "MCC", "MCC"));
        assertThrows(NumberFormatException.class, () -> gn.getPoolRegisterFact(senderAddr, targetAddr, "abc", "PEN", "MCC", "MCC"));
    }

    @DisplayName("Test feefi@pool-register - wrong currency")
    @Test
    void testPoolRegisterWrongCurrency() {
        FeefiGenerator gn = FeefiGenerator.get();

        HashMap<String, Object> sender = Generator.randomKeys();
        Keys senderKs = (Keys) sender.get(Keys.ID);
        String senderAddr = senderKs.getAddress();

        HashMap<String, Object> target = Generator.randomKeys();
        String targetAddr = ((Keys) target.get(Keys.ID)).getAddress();

        assertThrows(StringFormatException.class, () -> gn.getPoolRegisterFact(senderAddr, targetAddr, "1000", "", "MCC", "MCC"));
        assertThrows(StringFormatException.class, () -> gn.getPoolRegisterFact(senderAddr, targetAddr, "1000", "PE", "MCC", "MCC"));
        assertThrows(StringFormatException.class, () -> gn.getPoolRegisterFact(senderAddr, targetAddr, "1000", "PENPENPENPEN", "MCC", "MCC"));
        assertThrows(StringFormatException.class, () -> gn.getPoolRegisterFact(senderAddr, targetAddr, "1000", "PEN", "", "MCC"));
        assertThrows(StringFormatException.class, () -> gn.getPoolRegisterFact(senderAddr, targetAddr, "1000", "PEN", "MC", "MCC"));
        assertThrows(StringFormatException.class, () -> gn.getPoolRegisterFact(senderAddr, targetAddr, "1000", "PEN", "MCCMCCMCCMCC", "MCC"));
        assertThrows(StringFormatException.class, () -> gn.getPoolRegisterFact(senderAddr, targetAddr, "1000", "PEN", "MCC", ""));
        assertThrows(StringFormatException.class, () -> gn.getPoolRegisterFact(senderAddr, targetAddr, "1000", "PEN", "MCC", "MC"));
        assertThrows(StringFormatException.class, () -> gn.getPoolRegisterFact(senderAddr, targetAddr, "1000", "PEN", "MCC", "MCCMCCMCCMCC"));
    }
}
