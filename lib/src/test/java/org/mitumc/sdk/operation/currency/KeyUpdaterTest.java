package org.mitumc.sdk.operation.currency;

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
import org.mitumc.sdk.operation.Operation;
import org.mitumc.sdk.util.Hint;

public class KeyUpdaterTest {

    @DisplayName("Test currency@key-updater")
    @Test
    @SuppressWarnings("unchecked")
    void testKeyUpdater() {
        Generator gn = Generator.get("mitum");

        HashMap<String, Object> target = Generator.randomKeys();
        Keys targetKs = (Keys) target.get(Keys.ID);
		String targetAddr = targetKs.getAddress();
		String targetPriv =
		((HashMap<String, Keypair>) target.get(Keypair.ID)).get(
			    targetKs.getKeys().get(0).getKey()
			)
			.getPrivateKey();

        KeyUpdaterFact fact = gn.currency.getKeyUpdaterFact(
            targetAddr,
            "PEN",
            (Keys) Generator.randomKeys().get(Keys.ID)
        );

        assertDoesNotThrow(() -> fact.toBytes());
		assertDoesNotThrow(() -> fact.toDict());
		assertDoesNotThrow(() -> fact.getHash());
		assertDoesNotThrow(() -> fact.getOperationHint());
		assertEquals(
			Hint
				.get(
					Constant.MC_KEY_UPDATER_OPERATION,
					Constant.VERSION
				)
				.getHint(),
			fact.getOperationHint().getHint()
		);

        Operation op = gn.getOperation(fact);
        op.sign(targetPriv);
        op.exportToJsonFile("../test-jsons/test-result/key-updater.json");
    }

    @DisplayName("Test currency@key-updater - wrong sender")
    @Test
    void testKeyUpdaterWrongTarget() {
        Generator gn = Generator.get("mitum");

        assertThrows(
        StringFormatException.class,
        () ->
            gn.currency.getKeyUpdaterFact(
                "abcdefg123",
                "PEN",
                (Keys) Generator.randomKeys().get(Keys.ID)
            )
        );
    }
}
