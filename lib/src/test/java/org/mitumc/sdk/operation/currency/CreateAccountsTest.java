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

public class CreateAccountsTest {

	@DisplayName("Test currency@create-accounts")
	@Test
	@SuppressWarnings("unchecked")
	void testCreateAccounts() {
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

		Amount am = Amount.get("PEN", "100");

		CreateAccountsItem item = gn.currency.getCreateAccountsItem(
			(Keys) target.get(Keys.ID),
			new Amount[] { am }
		);
		CreateAccountsFact fact = gn.currency.getCreateAccountsFact(
			senderAddr,
			new CreateAccountsItem[] { item }
		);

		assertDoesNotThrow(() -> fact.toBytes());
		assertDoesNotThrow(() -> fact.toDict());
		assertDoesNotThrow(() -> fact.getHash());
		assertDoesNotThrow(() -> fact.getOperationHint());
		assertEquals(
			Hint
				.get(Constant.MC_CRAETE_ACCOUNTS_OPERATION, Constant.VERSION)
				.getHint(),
			fact.getOperationHint().getHint()
		);

		Operation op = gn.getOperation(fact);
		op.sign(senderPriv);
		op.exportToJsonFile("../test-jsons/test-result/create-accounts.json");
	}

	@DisplayName("Test currency@create-accounts - wrong sender")
	@Test
	void testCreateAccountsWrongSender() {
		Generator gn = Generator.get("mitum");

		HashMap<String, Object> target = Generator.randomKeys();
		Amount am = Amount.get("PEN", "100");

		CreateAccountsItem item = gn.currency.getCreateAccountsItem(
			(Keys) target.get(Keys.ID),
			new Amount[] { am }
		);

		assertThrows(
			StringFormatException.class,
			() ->
				gn.currency.getCreateAccountsFact(
					"abcdefg123",
					new CreateAccountsItem[] { item }
				)
		);
	}

	@DisplayName("Test currency@create-accounts - empty items")
	@Test
	void testCreateAccountsEmptyItems() {
		Generator gn = Generator.get("mitum");

		HashMap<String, Object> sender = Generator.randomKeys();
		String senderAddr = ((Keys) sender.get(Keys.ID)).getAddress();

		assertThrows(
		EmptyElementException.class,
			() ->
				gn.currency.getCreateAccountsFact(
					senderAddr,
					new CreateAccountsItem[] {}
				)
		);
	}

	@DisplayName("Test currency@create-accounts - empty amounts")
	@Test
	void testCreateAccountsEmptyAmounts() {
		Generator gn = Generator.get("mitum");

		HashMap<String, Object> target = Generator.randomKeys();

		assertThrows(
		EmptyElementException.class,
			() ->
				gn.currency.getCreateAccountsItem(
					(Keys) target.get(Keys.ID),
					new Amount[] {}
				)
		);
	}
}
