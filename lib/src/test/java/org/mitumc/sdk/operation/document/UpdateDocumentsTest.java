package org.mitumc.sdk.operation.document;

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
import org.mitumc.sdk.operation.Operation;
import org.mitumc.sdk.operation.document.base.Document;
import org.mitumc.sdk.util.Hint;

public class UpdateDocumentsTest {

    @DisplayName("Test document@update-documents")
    @Test
    @SuppressWarnings("unchecked")
    void testUpdateDocuments() {
        DocumentGenerator gn = DocumentGenerator.get();

        HashMap<String, Object> owner = Generator.randomKeys();
        Keys ownerKs = (Keys) owner.get(Keys.ID);
		String ownerAddr = ownerKs.getAddress();
		String ownerPriv =
		((HashMap<String, Keypair>) owner.get(Keypair.ID)).get(
                ownerKs.getKeys().get(0).getKey()
			)
			.getPrivateKey();

        HashMap<String, Object> account = Generator.randomKeys();
        String accountAddr = ((Keys) account.get(Keys.ID)).getAddress();

        Document doc = gn.blockcity.landDocument(
            "abcdcli",
            ownerAddr,
            "address0",
            "area0",
            "render0",
            accountAddr,
            "today",
            1
        );

        UpdateDocumentsItem item = gn.getUpdateDocumentsItem(doc, "PEN");
        UpdateDocumentsFact fact = gn.getUpdateDocumentsFact(ownerAddr, new UpdateDocumentsItem[]{ item });

		assertDoesNotThrow(() -> fact.toBytes());
		assertDoesNotThrow(() -> fact.toDict());
		assertDoesNotThrow(() -> fact.getHash());
		assertDoesNotThrow(() -> fact.getOperationHint());
		assertEquals(
			Hint
				.get(Constant.MD_UPDATE_DOCUMENTS_OPERATION, Constant.VERSION)
				.getHint(),
			fact.getOperationHint().getHint()
		);

        Operation op = Generator.get("mitum").getOperation(fact);
        op.sign(ownerPriv);

        op.exportToJsonFile("../test-jsons/test-result/update-documents.json");
    }

    @DisplayName("Test document@update-documents - wrong sender")
    @Test
    void testUpdateDocumentsWrongSender() {
        DocumentGenerator gn = DocumentGenerator.get();

        HashMap<String, Object> owner = Generator.randomKeys();
		String ownerAddr = ((Keys) owner.get(Keys.ID)).getAddress();

        HashMap<String, Object> account = Generator.randomKeys();
        String accountAddr = ((Keys) account.get(Keys.ID)).getAddress();

        Document doc = gn.blockcity.landDocument(
            "abcdcli",
            ownerAddr,
            "address0",
            "area0",
            "render0",
            accountAddr,
            "today",
            1
        );

        UpdateDocumentsItem item = gn.getUpdateDocumentsItem(doc, "PEN");
        assertThrows(StringFormatException.class, () ->  gn.getUpdateDocumentsFact("abcdefg123", new UpdateDocumentsItem[]{ item }));
    }

    @DisplayName("Test document@update-documents - empty items")
    @Test
    void testUpdateDocumentsEmptyItems() {
        DocumentGenerator gn = DocumentGenerator.get();

        HashMap<String, Object> owner = Generator.randomKeys();
		String ownerAddr = ((Keys) owner.get(Keys.ID)).getAddress();

        assertThrows(EmptyElementException.class, () ->  gn.getUpdateDocumentsFact(ownerAddr, new UpdateDocumentsItem[]{ }));
    }


    @DisplayName("Test document@update-documents - wrong currency")
    @Test
    void testUpdateDocumentsWrongCurrency() {
        DocumentGenerator gn = DocumentGenerator.get();

        HashMap<String, Object> owner = Generator.randomKeys();
		String ownerAddr = ((Keys) owner.get(Keys.ID)).getAddress();

        HashMap<String, Object> account = Generator.randomKeys();
        String accountAddr = ((Keys) account.get(Keys.ID)).getAddress();

        Document doc = gn.blockcity.landDocument(
            "abcdcli",
            ownerAddr,
            "address0",
            "area0",
            "render0",
            accountAddr,
            "today",
            1
        );

        assertThrows(StringFormatException.class, () -> gn.getUpdateDocumentsItem(doc, ""));
        assertThrows(StringFormatException.class, () -> gn.getUpdateDocumentsItem(doc, "PE"));
        assertThrows(StringFormatException.class, () -> gn.getUpdateDocumentsItem(doc, "PENPENPENPEN"));
    }

}
