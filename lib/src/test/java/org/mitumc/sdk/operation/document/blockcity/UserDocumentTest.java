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

public class UserDocumentTest {
    
    @DisplayName("Test document@user-documet")
    @Test
    void testUserDocument() {
        BlockCityGenerator gn = BlockCityGenerator.get();

        HashMap<String, Object> owner = Generator.randomKeys();
        String ownerAddr = ((Keys) owner.get(Keys.ID)).getAddress();

        UserStatistics st = gn.userStatistics(1, 1, 1, 1, 1, 1, 1);
        Document doc = gn.userDocument("abcdcui", ownerAddr, 1, 1, st);

        assertDoesNotThrow(() -> doc.toBytes());
        assertDoesNotThrow(() -> doc.toDict());
    }

    @DisplayName("Test document@user-document - wrong owner")
    @Test
    void testUserDocumentWrongOwner() {
        BlockCityGenerator gn = BlockCityGenerator.get();

        UserStatistics st = gn.userStatistics(1, 1, 1, 1, 1, 1, 1);
        
        assertThrows(StringFormatException.class, () -> gn.userDocument("abcdcui", "abcdefg123", 1, 1, st));
    }

    @DisplayName("Test document@user-document - wrong statistics")
    @Test
    void testUserDocumentWrongStatistics() {
        BlockCityGenerator gn = BlockCityGenerator.get();

        assertThrows(NumberRangeException.class, () -> gn.userStatistics(-1, 1, 1, 1, 1, 1, 1));
        assertThrows(NumberRangeException.class, () -> gn.userStatistics(1, -1, 1, 1, 1, 1, 1));
        assertThrows(NumberRangeException.class, () -> gn.userStatistics(1, 1, -1, 1, 1, 1, 1));
        assertThrows(NumberRangeException.class, () -> gn.userStatistics(1, 1, 1, -1, 1, 1, 1));
        assertThrows(NumberRangeException.class, () -> gn.userStatistics(1, 1, 1, 1, -1, 1, 1));
        assertThrows(NumberRangeException.class, () -> gn.userStatistics(1, 1, 1, 1, 1, -1, 1));
        assertThrows(NumberRangeException.class, () -> gn.userStatistics(1, 1, 1, 1, 1, 1, -1));
    }

    @DisplayName("Test document@user-document - wrong gold")
    @Test
    void testUserDocumentWrongGold() {
        BlockCityGenerator gn = BlockCityGenerator.get();

        HashMap<String, Object> owner = Generator.randomKeys();
        String ownerAddr = ((Keys) owner.get(Keys.ID)).getAddress();

        UserStatistics st = gn.userStatistics(1, 1, 1, 1, 1, 1, 1);
        
        assertThrows(NumberRangeException.class, () -> gn.userDocument("abcdcui", ownerAddr, -1, 1, st));
        assertThrows(NumberRangeException.class, () -> gn.userDocument("abcdcui", ownerAddr, 1, -1, st));
    }

}
