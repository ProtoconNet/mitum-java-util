package org.mitumc.sdk.operation.nft;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mitumc.sdk.exception.NumberRangeException;
import org.mitumc.sdk.exception.StringFormatException;
import org.mitumc.sdk.util.BigInt;

public class NFTIDTest {
    @DisplayName("Test nft@nft-id")
    @Test
    void testNFTID() {
        assertDoesNotThrow(() -> NFTID.get("COL", BigInt.fromInt(1)));
        assertDoesNotThrow(() -> NFTID.get("COL", BigInt.fromInt(100000)));

        assertThrows(StringFormatException.class, () -> NFTID.get("", BigInt.fromInt(1)));
        assertThrows(StringFormatException.class, () -> NFTID.get("CO", BigInt.fromInt(1)));
        assertThrows(StringFormatException.class, () -> NFTID.get("COLCOLCOLCOL", BigInt.fromInt(1)));

        assertThrows(NumberRangeException.class, () -> NFTID.get("COL", BigInt.fromInt(-1, true)));
        assertThrows(NumberRangeException.class, () -> NFTID.get("COL", BigInt.fromInt(0)));
    
        assertDoesNotThrow(() -> NFTID.get("COL-00001"));
        assertThrows(NumberRangeException.class, () -> NFTID.get("COL-00000"));
        assertThrows(StringFormatException.class, () -> NFTID.get("COL-ABCDE"));
        
        assertDoesNotThrow(() -> NFTID.get("COL-1"));
        assertDoesNotThrow(() -> NFTID.get("COL-01"));
        assertDoesNotThrow(() -> NFTID.get("COL-001"));
        assertDoesNotThrow(() -> NFTID.get("COL-101"));
        assertDoesNotThrow(() -> NFTID.get("COL-1001"));
        assertDoesNotThrow(() -> NFTID.get("COL-0000000000000001"));
        assertDoesNotThrow(() -> NFTID.get("COL-11234324298720"));
        
        assertThrows(StringFormatException.class, () -> NFTID.get("COL--11"));
    }
}
