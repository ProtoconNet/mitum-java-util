package org.mitumc.sdk.operation.nft;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.HashMap;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mitumc.sdk.Generator;
import org.mitumc.sdk.exception.NumberLimitExceededException;
import org.mitumc.sdk.exception.NumberRangeException;
import org.mitumc.sdk.exception.StringFormatException;
import org.mitumc.sdk.key.Keys;
import org.mitumc.sdk.operation.nft.base.NFTSigner;

public class NFTSignersTest {
    
    @DisplayName("Test nft@nft-signer")
    @Test
    void testNFTSigner() {
        NFTGenerator gn = NFTGenerator.get();

        assertDoesNotThrow(() -> gn.signer(address(), 0, false));
        assertDoesNotThrow(() -> gn.signer(address(), 0, true));
        assertDoesNotThrow(() -> gn.signer(address(), 100, false));
        
        assertThrows(NumberRangeException.class, () -> gn.signer(address(), -1, false));
        assertThrows(NumberRangeException.class, () -> gn.signer(address(), 101, false));
        assertThrows(StringFormatException.class, () -> gn.signer("abcdefg123", 100, false));
    }

    @DisplayName("Test nft@nft-signers")
    @Test
    void testNFTSigners() {
        NFTGenerator gn = NFTGenerator.get();

        assertDoesNotThrow(() -> gn.signers(100, new NFTSigner[]{ gn.signer(address(), 100, false) }));
        assertDoesNotThrow(() -> gn.signers(0, new NFTSigner[]{ gn.signer(address(), 0, false) }));
        assertDoesNotThrow(() -> gn.signers(0, new NFTSigner[]{ }));
        assertDoesNotThrow(() -> gn.signers(100, new NFTSigner[]{ 
            gn.signer(address(), 50, false),
            gn.signer(address(), 50, false),
        }));

        assertDoesNotThrow(() -> gn.signers(100, new NFTSigner[]{ 
            gn.signer(address(), 100, false),
            gn.signer(address(), 0, false),
        }));

        assertThrows(NumberRangeException.class, () -> gn.signers(-1, new NFTSigner[]{ gn.signer(address(), 0, false) }));
        assertThrows(NumberRangeException.class, () -> gn.signers(101, new NFTSigner[]{ 
            gn.signer(address(), 50, false),
            gn.signer(address(), 51, false),
        }));

        NFTSigner[] signers0 = new NFTSigner[10];
        NFTSigner[] signers1 = new NFTSigner[11];

        for(int i = 0; i < 11; i++) {
            if (i != 10) {
                signers0[i] = gn.signer(address(), 10, false);
                signers1[i] = gn.signer(address(), 10, false);
            } else {
                signers1[i] = gn.signer(address(), 0, false);
            }
        }

        assertDoesNotThrow(() -> gn.signers(100, signers0));
        assertThrows(NumberLimitExceededException.class, () -> gn.signers(100, signers1));
    }

    String address() {
        HashMap<String, Object> map = Generator.randomKeys();
        return ((Keys) map.get(Keys.ID)).getAddress();
    }
}
