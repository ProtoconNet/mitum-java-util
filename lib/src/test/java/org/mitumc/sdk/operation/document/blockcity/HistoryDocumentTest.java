package org.mitumc.sdk.operation.document.blockcity;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.HashMap;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mitumc.sdk.Generator;
import org.mitumc.sdk.exception.StringFormatException;
import org.mitumc.sdk.key.Keys;
import org.mitumc.sdk.operation.document.base.Document;

public class HistoryDocumentTest {

    @DisplayName("Test document@history-document")
    @Test
    void testHistoryDocument() {
        BlockCityGenerator gn = BlockCityGenerator.get();

        HashMap<String, Object> owner = Generator.randomKeys();
        String ownerAddr = ((Keys) owner.get(Keys.ID)).getAddress();

        HashMap<String, Object> account = Generator.randomKeys();
        String accountAddr = ((Keys) account.get(Keys.ID)).getAddress();

        Document doc = gn.historyDocument(
            "abcdchi",
            ownerAddr,
            "abcd",
            accountAddr,
            "today",
            "usage",
            "application"
        );

        assertDoesNotThrow(() -> doc.toBytes());
        assertDoesNotThrow(() -> doc.toDict());
    }

    @DisplayName("Test document@history-document - wrong owner")
    @Test
    void testHistoryDocumentWrongOwner() {
        BlockCityGenerator gn = BlockCityGenerator.get();

        HashMap<String, Object> account = Generator.randomKeys();
        String accountAddr = ((Keys) account.get(Keys.ID)).getAddress();

        assertThrows(
        StringFormatException.class,
            () ->
                gn.historyDocument(
                    "abcdchi",
                    "abcdefg123",
                    "abcd",
                    accountAddr,
                    "today",
                    "usage",
                    "application"
                )
        );
    }

    @DisplayName("Test document@history-document - wrong account")
    @Test
    void testHistoryDocumentWrongAccount() {
        BlockCityGenerator gn = BlockCityGenerator.get();

        HashMap<String, Object> owner = Generator.randomKeys();
        String ownerAddr = ((Keys) owner.get(Keys.ID)).getAddress();

        assertThrows(
            StringFormatException.class,
            () ->
                gn.historyDocument(
                    "abcdchi",
                    ownerAddr,
                    "abcd",
                    "abcdefg123",
                    "today",
                    "usage",
                    "application"
                )
        );
    }

    @DisplayName("Test document@history-document - wrong document id")
    @Test
    void testHistoryDocumentWrongDocumentID() {
        BlockCityGenerator gn = BlockCityGenerator.get();

        HashMap<String, Object> owner = Generator.randomKeys();
        String ownerAddr = ((Keys) owner.get(Keys.ID)).getAddress();

        HashMap<String, Object> account = Generator.randomKeys();
        String accountAddr = ((Keys) account.get(Keys.ID)).getAddress();

        assertThrows(
            StringFormatException.class,
            () ->
                gn.historyDocument(
                    "abcdcli",
                    ownerAddr,
                    "abcd",
                    accountAddr,
                    "today",
                    "usage",
                    "application"
                )
        );
    }
}
