package org.mitumc.sdk.operation.document;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mitumc.sdk.exception.StringFormatException;

public class DocumentIDTest {
    
    @DisplayName("Test document id")
    @Test
    void testDocumentID() {
        assertDoesNotThrow(() -> DocumentID.get("abcdcui"));
        assertDoesNotThrow(() -> DocumentID.get("abcdcli"));
        assertDoesNotThrow(() -> DocumentID.get("abcdcvi"));
        assertDoesNotThrow(() -> DocumentID.get("abcdchi"));
        assertDoesNotThrow(() -> DocumentID.get("abcdsdi"));
    
        assertThrows(StringFormatException.class, () -> DocumentID.get("abcdcci"));
        assertThrows(StringFormatException.class, () -> DocumentID.get("cci"));
    }
}
