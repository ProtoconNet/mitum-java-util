package org.mitumc.sdk.operation.document.blocksign;

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

public class BlockSignDocumentTest {

    @DisplayName("Test document@sign-document")
    @Test
    void testSignDocument() {
        BlockSignGenerator gn = BlockSignGenerator.get();

        HashMap<String, Object> owner = Generator.randomKeys();
        String ownerAddr = ((Keys) owner.get(Keys.ID)).getAddress();

        HashMap<String, Object> creator = Generator.randomKeys();
        String creatorAddr = ((Keys) creator.get(Keys.ID)).getAddress();

        HashMap<String, Object> signer0 = Generator.randomKeys();
        String signer0Addr = ((Keys) signer0.get(Keys.ID)).getAddress();

        HashMap<String, Object> signer1 = Generator.randomKeys();
        String signer1Addr = ((Keys) signer1.get(Keys.ID)).getAddress();

        BlockSignUser creatorUser = gn.user(creatorAddr, "creator-0", true);
        BlockSignUser signer0User = gn.user(signer0Addr, "signer-0", false);
        BlockSignUser signer1User = gn.user(signer1Addr, "signer-1", false);

        Document doc = gn.document(
            "abcdsdi",
            ownerAddr,
            "filehash1",
            creatorUser,
            "title0",
            "1234",
            new BlockSignUser[] { signer0User, signer1User }
        );

        assertDoesNotThrow(() -> doc.toBytes());
        assertDoesNotThrow(() -> doc.toDict());
    }

    @DisplayName("Test document@sign-document - wrong owner")
    @Test
    void testSignDocumentWrongOwner() {
        BlockSignGenerator gn = BlockSignGenerator.get();

        HashMap<String, Object> creator = Generator.randomKeys();
        String creatorAddr = ((Keys) creator.get(Keys.ID)).getAddress();

        HashMap<String, Object> signer0 = Generator.randomKeys();
        String signer0Addr = ((Keys) signer0.get(Keys.ID)).getAddress();

        HashMap<String, Object> signer1 = Generator.randomKeys();
        String signer1Addr = ((Keys) signer1.get(Keys.ID)).getAddress();

        BlockSignUser creatorUser = gn.user(creatorAddr, "creator-0", true);
        BlockSignUser signer0User = gn.user(signer0Addr, "signer-0", false);
        BlockSignUser signer1User = gn.user(signer1Addr, "signer-1", false);

        assertThrows(
        StringFormatException.class,
            () ->
                gn.document(
                    "abcdsdi",
                    "abcdefg123",
                    "filehash1",
                    creatorUser,
                    "title0",
                    "1234",
                    new BlockSignUser[] { signer0User, signer1User }
                )
        );
    }

    @DisplayName("Test document@sign-document - wrong user")
    @Test
    void testSignDocumentWrongCreator() {
        BlockSignGenerator gn = BlockSignGenerator.get();
        assertThrows(
            StringFormatException.class,
            () -> gn.user("abcdefg123", "creator-0", true)
        );
    }

    @DisplayName("Test document@sign-document - wrong size")
    @Test
    void testSignDocumentWrongSize() {
        BlockSignGenerator gn = BlockSignGenerator.get();

        HashMap<String, Object> owner = Generator.randomKeys();
        String ownerAddr = ((Keys) owner.get(Keys.ID)).getAddress();

        HashMap<String, Object> creator = Generator.randomKeys();
        String creatorAddr = ((Keys) creator.get(Keys.ID)).getAddress();

        HashMap<String, Object> signer0 = Generator.randomKeys();
        String signer0Addr = ((Keys) signer0.get(Keys.ID)).getAddress();

        HashMap<String, Object> signer1 = Generator.randomKeys();
        String signer1Addr = ((Keys) signer1.get(Keys.ID)).getAddress();

        BlockSignUser creatorUser = gn.user(creatorAddr, "creator-0", true);
        BlockSignUser signer0User = gn.user(signer0Addr, "signer-0", false);
        BlockSignUser signer1User = gn.user(signer1Addr, "signer-1", false);

        assertThrows(
            NumberRangeException.class,
            () ->
                gn.document(
                    "abcdsdi",
                    ownerAddr,
                    "filehash1",
                    creatorUser,
                    "title0",
                    "-1",
                    new BlockSignUser[] { signer0User, signer1User }
                )
        );
        
        assertThrows(
            NumberFormatException.class,
            () ->
                gn.document(
                    "abcdsdi",
                    ownerAddr,
                    "filehash1",
                    creatorUser,
                    "title0",
                    "abcd123",
                    new BlockSignUser[] { signer0User, signer1User }
                )
        );
    }

    @DisplayName("Test document@sign-document - wrong document id")
    @Test
    void testSignDocumentDocumentID() {
        BlockSignGenerator gn = BlockSignGenerator.get();

        HashMap<String, Object> owner = Generator.randomKeys();
        String ownerAddr = ((Keys) owner.get(Keys.ID)).getAddress();

        HashMap<String, Object> creator = Generator.randomKeys();
        String creatorAddr = ((Keys) creator.get(Keys.ID)).getAddress();

        HashMap<String, Object> signer0 = Generator.randomKeys();
        String signer0Addr = ((Keys) signer0.get(Keys.ID)).getAddress();

        HashMap<String, Object> signer1 = Generator.randomKeys();
        String signer1Addr = ((Keys) signer1.get(Keys.ID)).getAddress();

        BlockSignUser creatorUser = gn.user(creatorAddr, "creator-0", true);
        BlockSignUser signer0User = gn.user(signer0Addr, "signer-0", false);
        BlockSignUser signer1User = gn.user(signer1Addr, "signer-1", false);

        assertThrows(
        StringFormatException.class,
        () ->
            gn.document(
                "abcdcvi",
                ownerAddr,
                "filehash1",
                creatorUser,
                "title0",
                "1234",
                new BlockSignUser[] { signer0User, signer1User }
            )
        );
    }
}
