package org.mitumc.sdk.operation.nft;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.HashMap;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mitumc.sdk.Constant;
import org.mitumc.sdk.Generator;
import org.mitumc.sdk.exception.NumberRangeException;
import org.mitumc.sdk.exception.StringFormatException;
import org.mitumc.sdk.key.Keypair;
import org.mitumc.sdk.key.Keys;
import org.mitumc.sdk.operation.Operation;
import org.mitumc.sdk.util.Hint;

public class CollectionRegisterTest {

    @DisplayName("Test nft@collection-regsiter")
    @Test
    @SuppressWarnings("unchecked")
    void testCollectionRegister() {
        NFTGenerator gn = NFTGenerator.get();

        HashMap<String, Object> sender = Generator.randomKeys();
        Keys senderKs = (Keys) sender.get(Keys.ID);
        String senderAddr = senderKs.getAddress();
        String senderPriv =
        ((HashMap<String, Keypair>) sender.get(Keypair.ID)).get(
                senderKs.getKeys().get(0).getKey()
            )
            .getPrivateKey();

        HashMap<String, Object> target = Generator.randomKeys();
        String targetAddr = ((Keys) target.get(Keys.ID)).getAddress();

        HashMap<String, Object> white0 = Generator.randomKeys();
        String white0Addr = ((Keys) white0.get(Keys.ID)).getAddress();

        HashMap<String, Object> white1 = Generator.randomKeys();
        String white1Addr = ((Keys) white1.get(Keys.ID)).getAddress();

        CollectionRegisterForm form = gn.collectionRegisterForm(
            targetAddr,
            "COL",
            "Collection",
            0,
            "https://localhost:5000",
            new String[] { white0Addr, white1Addr }
        );
        CollectionRegisterFact fact = gn.getCollectionRegisterFact(
            senderAddr,
            form,
            "PEN"
        );

        assertDoesNotThrow(() -> fact.toBytes());
        assertDoesNotThrow(() -> fact.toDict());
        assertDoesNotThrow(() -> fact.getHash());
        assertDoesNotThrow(() -> fact.getOperationHint());
        assertEquals(
            Hint
                .get(Constant.MNFT_COLLECTION_REGISTER_OPERATION, Constant.VERSION)
                .getHint(),
            fact.getOperationHint().getHint()
        );

        Operation op = Generator.get("mitum").getOperation(fact);
        op.sign(senderPriv);

        op.exportToJsonFile("../test-jsons/test-result/collection-register.json");
    }

    @DisplayName("Test nft@collection-regsiter - wrong sender")
    @Test
    void testCollectionRegisterWrongSender() {
        NFTGenerator gn = NFTGenerator.get();

        HashMap<String, Object> target = Generator.randomKeys();
        String targetAddr = ((Keys) target.get(Keys.ID)).getAddress();

        HashMap<String, Object> white0 = Generator.randomKeys();
        String white0Addr = ((Keys) white0.get(Keys.ID)).getAddress();

        HashMap<String, Object> white1 = Generator.randomKeys();
        String white1Addr = ((Keys) white1.get(Keys.ID)).getAddress();

        CollectionRegisterForm form = gn.collectionRegisterForm(
            targetAddr,
            "COL",
            "Collection",
            0,
            "https://localhost:5000",
            new String[] { white0Addr, white1Addr }
        );

        assertThrows(
            StringFormatException.class,
            () -> gn.getCollectionRegisterFact("abcdefg123", form, "PEN")
        );
    }

    @DisplayName("Test nft@collection-regsiter - wrong target")
    @Test
    void testCollectionRegisterWrongTarget() {
        NFTGenerator gn = NFTGenerator.get();

        HashMap<String, Object> white0 = Generator.randomKeys();
        String white0Addr = ((Keys) white0.get(Keys.ID)).getAddress();

        HashMap<String, Object> white1 = Generator.randomKeys();
        String white1Addr = ((Keys) white1.get(Keys.ID)).getAddress();

        assertThrows(
            StringFormatException.class,
            () ->
                gn.collectionRegisterForm(
                    "abcdefg123",
                    "COL",
                    "Collection",
                    0,
                    "https://localhost:5000",
                    new String[] { white0Addr, white1Addr }
                )
        );
    }

    @DisplayName("Test nft@collection-regsiter - wrong whites")
    @Test
    void testCollectionRegisterWrongWhite() {
        NFTGenerator gn = NFTGenerator.get();

        assertThrows(
        StringFormatException.class,
            () ->
                gn.collectionRegisterForm(
                    "abcdefg123",
                    "COL",
                    "Collection",
                    0,
                    "https://localhost:5000",
                    new String[] { "abcdefg123" }
                )
        );
    }

    @DisplayName("Test nft@collection-regsiter - wrong symbol")
    @Test
    void testCollectionRegisterWrongSymbol() {
        NFTGenerator gn = NFTGenerator.get();

        HashMap<String, Object> target = Generator.randomKeys();
        String targetAddr = ((Keys) target.get(Keys.ID)).getAddress();

        HashMap<String, Object> white0 = Generator.randomKeys();
        String white0Addr = ((Keys) white0.get(Keys.ID)).getAddress();

        HashMap<String, Object> white1 = Generator.randomKeys();
        String white1Addr = ((Keys) white1.get(Keys.ID)).getAddress();

        assertThrows(
        StringFormatException.class,
            () ->
                gn.collectionRegisterForm(
                    targetAddr,
                    "",
                    "Collection",
                    0,
                    "https://localhost:5000",
                    new String[] { white0Addr, white1Addr }
                )
        );
        assertThrows(
        StringFormatException.class,
            () ->
                gn.collectionRegisterForm(
                    targetAddr,
                    "CO",
                    "Collection",
                    0,
                    "https://localhost:5000",
                    new String[] { white0Addr, white1Addr }
                )
        );
        assertThrows(
        StringFormatException.class,
            () ->
                gn.collectionRegisterForm(
                    targetAddr,
                    "COLCOLCOLCOL",
                    "Collection",
                    0,
                    "https://localhost:5000",
                    new String[] { white0Addr, white1Addr }
                )
        );
    }

    @DisplayName("Test nft@collection-regsiter - wrong royalty")
    @Test
    void testCollectionRegisterWrongRoyalty() {
        NFTGenerator gn = NFTGenerator.get();

        HashMap<String, Object> target = Generator.randomKeys();
        String targetAddr = ((Keys) target.get(Keys.ID)).getAddress();

        HashMap<String, Object> white0 = Generator.randomKeys();
        String white0Addr = ((Keys) white0.get(Keys.ID)).getAddress();

        HashMap<String, Object> white1 = Generator.randomKeys();
        String white1Addr = ((Keys) white1.get(Keys.ID)).getAddress();

        assertThrows(
            NumberRangeException.class,
            () ->
                gn.collectionRegisterForm(
                    targetAddr,
                    "COL",
                    "Collection",
                    -1,
                    "https://localhost:5000",
                    new String[] { white0Addr, white1Addr }
                )
        );
        assertThrows(
            NumberRangeException.class,
            () ->
                gn.collectionRegisterForm(
                    targetAddr,
                    "COL",
                    "Collection",
                    100,
                    "https://localhost:5000",
                    new String[] { white0Addr, white1Addr }
                )
        );
    }

    @DisplayName("Test nft@collection-regsiter - wrong currency")
    @Test
    void testCollectionRegisterWrongCurrency() {
        NFTGenerator gn = NFTGenerator.get();

        HashMap<String, Object> sender = Generator.randomKeys();
        String senderAddr = (((Keys) sender.get(Keys.ID))).getAddress();

        HashMap<String, Object> target = Generator.randomKeys();
        String targetAddr = ((Keys) target.get(Keys.ID)).getAddress();

        HashMap<String, Object> white0 = Generator.randomKeys();
        String white0Addr = ((Keys) white0.get(Keys.ID)).getAddress();

        HashMap<String, Object> white1 = Generator.randomKeys();
        String white1Addr = ((Keys) white1.get(Keys.ID)).getAddress();

        CollectionRegisterForm form = gn.collectionRegisterForm(
            targetAddr,
            "COL",
            "Collection",
            0,
            "https://localhost:5000",
            new String[] { white0Addr, white1Addr }
        );

        assertThrows(
            StringFormatException.class,
            () -> gn.getCollectionRegisterFact(senderAddr, form, "")
        );
        assertThrows(
            StringFormatException.class,
            () -> gn.getCollectionRegisterFact(senderAddr, form, "PE")
        );
        assertThrows(
            StringFormatException.class,
            () -> gn.getCollectionRegisterFact(senderAddr, form, "PENPENPENPEN")
        );
    }
}
