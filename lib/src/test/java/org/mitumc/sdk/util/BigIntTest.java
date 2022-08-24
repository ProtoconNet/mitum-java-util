package org.mitumc.sdk.util;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

public class BigIntTest {
    @DisplayName("Test BigInt")
    @Test
    void testBigInt() {
        assertDoesNotThrow(() -> BigInt.fromInt(0));
        assertDoesNotThrow(() -> BigInt.fromString("0"));
    }

    @DisplayName("Test BigInt - big")
    @Test
    void testBig() {
        BigInt big = BigInt.fromString(
            "99999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999"
        );

        int[] intBuf = new int[] { 
            77, 222, 99, -48, -95, 88, -66, 101, -60, 83, 48, 59, 32, 5, -37, 72, -54,
            1, -11, -92, -5, 71, 110, 69, -126, 57, -71, 98, -87, 88, 22, 71, -59, -121,
            -74, 16, -122, 87, -65, -56, -58, -5, 123, 28, -48, -8, -118, 51, 64, 18,
            27, 91, -94, 94, 27, 49, -43, -39, -3, 127, -119, -1, -1, -1, -1, -1, -1,
            -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
            -1
        };       
        byte[] byteBuf = new byte[intBuf.length];
        for(int i = 0; i < intBuf.length; i++) {
            byteBuf[i] = (byte) intBuf[i];
        }

        byte[] bigBuf = big.toBytes(BigInt.LITTLE_ENDIAN, true);
        for(int i = 0; i < intBuf.length; i++) {
            assertEquals(bigBuf[i], byteBuf[i]);
        }
    }

    @DisplayName("Test BigInt - 609...")
    @Test
    void test61() {
        BigInt big = BigInt.fromString(
            "609999999999999999999999"
        );

        int[] intBuf = new int[] { -127, 44, 42, -112, 17, 124, 121, 63, -1, -1 };       
        byte[] byteBuf = new byte[intBuf.length];
        for(int i = 0; i < intBuf.length; i++) {
            byteBuf[i] = (byte) intBuf[i];
        }

        byte[] bigBuf = big.toBytes(BigInt.LITTLE_ENDIAN, true);
        for(int i = 0; i < intBuf.length; i++) {
            assertEquals(bigBuf[i], byteBuf[i]);
        }
    }
}
