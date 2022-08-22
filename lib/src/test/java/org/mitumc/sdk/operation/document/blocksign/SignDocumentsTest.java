package org.mitumc.sdk.operation.document.blocksign;

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
import org.mitumc.sdk.util.Hint;

public class SignDocumentsTest {

    @DisplayName("Test document@sign-documents")
    @Test
    @SuppressWarnings("unchecked")
    void testSignDocuments() {
        BlockSignGenerator gn = BlockSignGenerator.get();

        HashMap<String, Object> sender = Generator.randomKeys();
        Keys senderKs = (Keys) sender.get(Keys.ID);
		String senderAddr = senderKs.getAddress();
		String senderPriv =
		((HashMap<String, Keypair>) sender.get(Keypair.ID)).get(
				senderKs.getKeys().get(0).getKey()
			)
			.getPrivateKey();

        HashMap<String, Object> owner = Generator.randomKeys();
        String ownerAddr = ((Keys) owner.get(Keys.ID)).getAddress();

        SignDocumentsItem item = gn.getSignDocumentsItem(
            "abcdsdi",
            ownerAddr,
            "PEN"
        );
        SignDocumentsFact fact = gn.getSignDocumentsFact(
            senderAddr,
            new SignDocumentsItem[] { item }
        );

        assertDoesNotThrow(() -> fact.toBytes());
        assertDoesNotThrow(() -> fact.toDict());
        assertDoesNotThrow(() -> fact.getHash());
        assertDoesNotThrow(() -> fact.getOperationHint());
        assertEquals(
            Hint
                .get(Constant.MBS_SIGN_DOCUMENTS_OPERATION, Constant.VERSION)
                .getHint(),
            fact.getOperationHint().getHint()
        );

        Operation op = Generator.get("mitum").getOperation(fact);
        op.sign(senderPriv);

        op.exportToJsonFile("../test-jsons/test-result/sign-documents.json");
    }

    @DisplayName("Test document@sign-documents - wrong sender")
    @Test
    void testSignDocumentsWrongSender() {
        BlockSignGenerator gn = BlockSignGenerator.get();

        HashMap<String, Object> owner = Generator.randomKeys();
        String ownerAddr = ((Keys) owner.get(Keys.ID)).getAddress();

        SignDocumentsItem item = gn.getSignDocumentsItem(
            "abcdsdi",
            ownerAddr,
            "PEN"
        );

        assertThrows(
            StringFormatException.class,
            () ->
                gn.getSignDocumentsFact("abcdefg123", new SignDocumentsItem[] { item })
        );
    }

    @DisplayName("Test document@sign-documents - wrong owner")
    @Test
    void testSignDocumentsWrongOwner() {
        BlockSignGenerator gn = BlockSignGenerator.get();
        assertThrows(
            StringFormatException.class,
            () -> gn.getSignDocumentsItem("abcdsdi", "abcdefg123", "PEN")
        );
    }

    @DisplayName("Test document@sign-documents - wrong document id")
    @Test
    void testSignDocumentsDocumentID() {
        BlockSignGenerator gn = BlockSignGenerator.get();

        HashMap<String, Object> owner = Generator.randomKeys();
        String ownerAddr = ((Keys) owner.get(Keys.ID)).getAddress();

        assertThrows(
            StringFormatException.class,
            () -> gn.getSignDocumentsItem("abcdcui", ownerAddr, "PEN")
        );
    }

    @DisplayName("Test document@sign-documents - wrong empty items")
    @Test
    void testSignDocumentsEmptyItems() {
        BlockSignGenerator gn = BlockSignGenerator.get();

        HashMap<String, Object> sender = Generator.randomKeys();
        String senderAddr = ((Keys) sender.get(Keys.ID)).getAddress();

        assertThrows(
            EmptyElementException.class,
            () -> gn.getSignDocumentsFact(senderAddr, new SignDocumentsItem[] {})
        );
    }
}
