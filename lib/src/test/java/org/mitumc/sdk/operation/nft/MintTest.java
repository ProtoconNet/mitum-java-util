package org.mitumc.sdk.operation.nft;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.HashMap;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mitumc.sdk.Constant;
import org.mitumc.sdk.Generator;
import org.mitumc.sdk.exception.EmptyElementException;
import org.mitumc.sdk.exception.EmptyStringException;
import org.mitumc.sdk.exception.StringFormatException;
import org.mitumc.sdk.key.Keypair;
import org.mitumc.sdk.key.Keys;
import org.mitumc.sdk.operation.Operation;
import org.mitumc.sdk.util.Hint;

public class MintTest {
    @DisplayName("Test nft@mint")
    @Test
    @SuppressWarnings("unchecked")
    void testMint() {
        NFTGenerator gn = NFTGenerator.get();

        HashMap<String, Object> sender = Generator.randomKeys();
        Keys senderKs = (Keys) sender.get(Keys.ID);
        String senderAddr = senderKs.getAddress();
        String senderPriv =
        ((HashMap<String, Keypair>) sender.get(Keypair.ID)).get(
                senderKs.getKeys().get(0).getKey()
            )
            .getPrivateKey();
    
        HashMap<String, Object> sgn0 = Generator.randomKeys();
        String signer0Addr = ((Keys) sgn0.get(Keys.ID)).getAddress();

        HashMap<String, Object> sgn1 = Generator.randomKeys();
        String signer1Addr = ((Keys) sgn1.get(Keys.ID)).getAddress();

        NFTSigner signer0 = gn.signer(signer0Addr, 50, false);
        NFTSigner signer1 = gn.signer(signer1Addr, 50, false); 
        NFTSigners signers = gn.signers(100, new NFTSigner[]{ signer0, signer1 });

        MintForm form = gn.mintForm("nfthash", "https://localhost:5000", signers, signers);
        MintItem item = gn.getMintItem("COL", form, "PEN");

        MintFact fact = gn.getMintFact(senderAddr, new MintItem[]{ item });

        assertDoesNotThrow(() -> fact.toBytes());
		assertDoesNotThrow(() -> fact.toDict());
		assertDoesNotThrow(() -> fact.getHash());
		assertDoesNotThrow(() -> fact.getOperationHint());
		assertEquals(
			Hint
				.get(Constant.MNFT_MINT_OPERATION, Constant.VERSION)
				.getHint(),
			fact.getOperationHint().getHint()
		);

        Operation op = Generator.get("mitum").getOperation(fact);
        op.sign(senderPriv);

        op.exportToJsonFile("../test-jsons/test-result/mint.json");
    }

    @DisplayName("Test nft@mint - wrong sender")
    @Test
    void testMintWrongSender() {
        NFTGenerator gn = NFTGenerator.get();
    
        HashMap<String, Object> sgn0 = Generator.randomKeys();
        String signer0Addr = ((Keys) sgn0.get(Keys.ID)).getAddress();

        HashMap<String, Object> sgn1 = Generator.randomKeys();
        String signer1Addr = ((Keys) sgn1.get(Keys.ID)).getAddress();

        NFTSigner signer0 = gn.signer(signer0Addr, 50, false);
        NFTSigner signer1 = gn.signer(signer1Addr, 50, false); 
        NFTSigners signers = gn.signers(100, new NFTSigner[]{ signer0, signer1 });

        MintForm form = gn.mintForm("nfthash", "https://localhost:5000", signers, signers);
        MintItem item = gn.getMintItem("COL", form, "PEN");

        assertThrows(StringFormatException.class, () -> gn.getMintFact("abcdefg123", new MintItem[]{ item }));
    }

    @DisplayName("Test nft@mint - wrong uri")
    @Test
    void testMintWrongURI() {
        NFTGenerator gn = NFTGenerator.get();
    
        HashMap<String, Object> sgn0 = Generator.randomKeys();
        String signer0Addr = ((Keys) sgn0.get(Keys.ID)).getAddress();

        HashMap<String, Object> sgn1 = Generator.randomKeys();
        String signer1Addr = ((Keys) sgn1.get(Keys.ID)).getAddress();

        NFTSigner signer0 = gn.signer(signer0Addr, 50, false);
        NFTSigner signer1 = gn.signer(signer1Addr, 50, false); 
        NFTSigners signers = gn.signers(100, new NFTSigner[]{ signer0, signer1 });

        assertThrows(EmptyStringException.class, () -> gn.mintForm("nfthash", "", signers, signers));
    }

    @DisplayName("Test nft@mint - wrong symbol")
    @Test
    void testMintWrongSymbol() {
        NFTGenerator gn = NFTGenerator.get();
    
        HashMap<String, Object> sgn0 = Generator.randomKeys();
        String signer0Addr = ((Keys) sgn0.get(Keys.ID)).getAddress();

        HashMap<String, Object> sgn1 = Generator.randomKeys();
        String signer1Addr = ((Keys) sgn1.get(Keys.ID)).getAddress();

        NFTSigner signer0 = gn.signer(signer0Addr, 50, false);
        NFTSigner signer1 = gn.signer(signer1Addr, 50, false); 
        NFTSigners signers = gn.signers(100, new NFTSigner[]{ signer0, signer1 });

        MintForm form = gn.mintForm("nfthash", "https://localhost:5000", signers, signers);
        
        assertThrows(StringFormatException.class, () -> gn.getMintItem("", form, "PEN"));
        assertThrows(StringFormatException.class, () -> gn.getMintItem("CO", form, "PEN"));
        assertThrows(StringFormatException.class, () -> gn.getMintItem("COLCOLCOLCOL", form, "PEN"));
    }

    @DisplayName("Test nft@mint - wrong currency")
    @Test
    void testMintWrongCurrency() {
        NFTGenerator gn = NFTGenerator.get();
    
        HashMap<String, Object> sgn0 = Generator.randomKeys();
        String signer0Addr = ((Keys) sgn0.get(Keys.ID)).getAddress();

        HashMap<String, Object> sgn1 = Generator.randomKeys();
        String signer1Addr = ((Keys) sgn1.get(Keys.ID)).getAddress();

        NFTSigner signer0 = gn.signer(signer0Addr, 50, false);
        NFTSigner signer1 = gn.signer(signer1Addr, 50, false); 
        NFTSigners signers = gn.signers(100, new NFTSigner[]{ signer0, signer1 });

        MintForm form = gn.mintForm("nfthash", "https://localhost:5000", signers, signers);
        
        assertThrows(StringFormatException.class, () -> gn.getMintItem("COL", form, ""));
        assertThrows(StringFormatException.class, () -> gn.getMintItem("COL", form, "PE"));
        assertThrows(StringFormatException.class, () -> gn.getMintItem("COL", form, "PENPENPENPEN"));
    }


    @DisplayName("Test nft@mint - empty items")
    @Test
    void testMintEmptyItems() {
        NFTGenerator gn = NFTGenerator.get();

        HashMap<String, Object> sender = Generator.randomKeys();
        String senderAddr = ((Keys) sender.get(Keys.ID)).getAddress();

        assertThrows(EmptyElementException.class, () -> gn.getMintFact(senderAddr, new MintItem[]{ }));
    }

}
