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

public class CreateContractAccountsTest {

	@DisplayName("Test currency.extension@create-contract-accounts")
	@Test
	@SuppressWarnings("unchecked")
	void testCreateContractAccounts() {
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

		CreateContractAccountsItem item = gn.currency.extension.getCreateContractAccountsItem(
			(Keys) target.get(Keys.ID),
			new Amount[] { am }
		);
		CreateContractAccountsFact fact = gn.currency.extension.getCreateContractAccountsFact(
			senderAddr,
			new CreateContractAccountsItem[] { item }
		);

		assertDoesNotThrow(() -> fact.toBytes());
		assertDoesNotThrow(() -> fact.toDict());
		assertDoesNotThrow(() -> fact.getHash());
		assertDoesNotThrow(() -> fact.getOperationHint());
		assertEquals(
			Hint
				.get(
					Constant.MC_EXT_CREATE_CONTRACT_ACCOUNTS_OPERATION,
					Constant.VERSION
				)
				.getHint(),
			fact.getOperationHint().getHint()
		);

		Operation op = gn.getOperation(fact);
		op.sign(senderPriv);
		op.exportToJsonFile("../test-jsons/test-result/create-contract-accounts.json");
	}

	@DisplayName(
		"Test currency.extension@create-contract-accounts - wrong sender"
	)
	@Test
	void testCreateContractAccountsWrongSender() {
		Generator gn = Generator.get("mitum");

		HashMap<String, Object> target = Generator.randomKeys();
		Amount am = Amount.get("PEN", "100");

		CreateContractAccountsItem item = gn.currency.extension.getCreateContractAccountsItem(
			(Keys) target.get(Keys.ID),
			new Amount[] { am }
		);

		assertThrows(
		StringFormatException.class,
			() ->
				gn.currency.extension.getCreateContractAccountsFact(
					"abcdefg123",
					new CreateContractAccountsItem[] { item }
				)
		);
	}

	@DisplayName("Test currency.extension@create-contract-accounts - empty items")
	@Test
	void testCreateContractAccountsEmptyItems() {
		Generator gn = Generator.get("mitum");

		HashMap<String, Object> sender = Generator.randomKeys();
		String senderAddr = ((Keys) sender.get(Keys.ID)).getAddress();

		assertThrows(
			EmptyElementException.class,
			() ->
				gn.currency.extension.getCreateContractAccountsFact(
					senderAddr,
					new CreateContractAccountsItem[] {}
				)
		);
	}

	@DisplayName(
		"Test currency.extension@create-contract-accounts - empty amounts"
	)
	@Test
	void testCreateContractAccountsEmptyAmounts() {
		Generator gn = Generator.get("mitum");

		HashMap<String, Object> target = Generator.randomKeys();

		assertThrows(
		EmptyElementException.class,
			() ->
				gn.currency.extension.getCreateContractAccountsItem(
					(Keys) target.get(Keys.ID),
					new Amount[] {}
				)
		);
	}
}
