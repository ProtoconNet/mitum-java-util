package org.mitumc.sdk.operation.document.blockcity;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.HashMap;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mitumc.sdk.Generator;
import org.mitumc.sdk.exception.NumberRangeException;
import org.mitumc.sdk.exception.StringFormatException;
import org.mitumc.sdk.key.Keys;
import org.mitumc.sdk.operation.document.base.Document;

public class LandDocumentTest {

    @DisplayName("Test document@land-document")
    @Test
    void testLandDocument() {
        BlockCityGenerator gn = BlockCityGenerator.get();

        HashMap<String, Object> owner = Generator.randomKeys();
        String ownerAddr = ((Keys) owner.get(Keys.ID)).getAddress();

        HashMap<String, Object> account = Generator.randomKeys();
        String accountAddr = ((Keys) account.get(Keys.ID)).getAddress();

        Document doc = gn.landDocument(
            "abcdcli",
            ownerAddr,
            "address0",
            "area0",
            "renter0",
            accountAddr,
            "today",
            1
        );

        assertDoesNotThrow(() -> doc.toBytes());
        assertDoesNotThrow(() -> doc.toDict());
    }

    @DisplayName("Test document@land-document - wrong owner")
    @Test
    void testLandDocumentWrongOwner() {
        BlockCityGenerator gn = BlockCityGenerator.get();

        HashMap<String, Object> account = Generator.randomKeys();
        String accountAddr = ((Keys) account.get(Keys.ID)).getAddress();

        assertThrows(
        StringFormatException.class,
            () ->
                gn.landDocument(
                    "abcdcli",
                    "abcdefg123",
                    "address0",
                    "area0",
                    "renter0",
                    accountAddr,
                    "today",
                    1
                )
        );
    }

    @DisplayName("Test document@land-document - wrong account")
    @Test
    void testLandDocumentWrongAccount() {
        BlockCityGenerator gn = BlockCityGenerator.get();

        HashMap<String, Object> owner = Generator.randomKeys();
        String ownerAddr = ((Keys) owner.get(Keys.ID)).getAddress();

        assertThrows(
        StringFormatException.class,
            () ->
                gn.landDocument(
                    "abcdcli",
                    ownerAddr,
                    "address0",
                    "area0",
                    "renter0",
                    "abcdefg123",
                    "today",
                    1
                )
        );
    }

    @DisplayName("Test document@land-document - wrong document id")
    @Test
    void testLandDocumentWrongDocumentID() {
        BlockCityGenerator gn = BlockCityGenerator.get();

        HashMap<String, Object> owner = Generator.randomKeys();
        String ownerAddr = ((Keys) owner.get(Keys.ID)).getAddress();

        HashMap<String, Object> account = Generator.randomKeys();
        String accountAddr = ((Keys) account.get(Keys.ID)).getAddress();

        assertThrows(
        StringFormatException.class,
            () ->
                gn.landDocument(
                    "abcdchi",
                    ownerAddr,
                    "address0",
                    "area0",
                    "renter0",
                    accountAddr,
                    "today",
                    1
                )
        );
    }

    @DisplayName("Test document@land-document - wrong period")
    @Test
    void testLandDocumentWrongPeriod() {
        BlockCityGenerator gn = BlockCityGenerator.get();

        HashMap<String, Object> owner = Generator.randomKeys();
        String ownerAddr = ((Keys) owner.get(Keys.ID)).getAddress();

        HashMap<String, Object> account = Generator.randomKeys();
        String accountAddr = ((Keys) account.get(Keys.ID)).getAddress();

        assertThrows(
        NumberRangeException.class,
            () ->
                gn.landDocument(
                    "abcdcli",
                    ownerAddr,
                    "address0",
                    "area0",
                    "renter0",
                    accountAddr,
                    "today",
                    -1
                )
        );
    }
}
