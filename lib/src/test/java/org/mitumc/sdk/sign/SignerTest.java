package org.mitumc.sdk.sign;

import java.util.HashMap;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mitumc.sdk.JSONParser;
import org.mitumc.sdk.Signer;
import org.mitumc.sdk.key.Keypair;

import com.google.gson.JsonObject;

public class SignerTest {
    
    @DisplayName("Test signer - from hashmap")
    @Test
    void testSignerFromHashMap() {
        Signer signer = Signer.get("mitum", Keypair.random().getPrivateKey());

        HashMap<String, Object> signed0 = signer.addSignToOperation("../test-jsons/sample/create-accounts.json");
        HashMap<String, Object> signed1 = signer.addSignToOperation("../test-jsons/sample/key-updater.json");
        HashMap<String, Object> signed2 = signer.addSignToOperation("../test-jsons/sample/transfers.json");

        HashMap<String, Object> signed3 = signer.addSignToOperation("../test-jsons/sample/create-contract-accounts.json");
        HashMap<String, Object> signed4 = signer.addSignToOperation("../test-jsons/sample/withdraws.json");

        HashMap<String, Object> signed5 = signer.addSignToOperation("../test-jsons/sample/create-documents.json");
        HashMap<String, Object> signed6 = signer.addSignToOperation("../test-jsons/sample/update-documents.json");
        HashMap<String, Object> signed7 = signer.addSignToOperation("../test-jsons/sample/sign-documents.json");

        HashMap<String, Object> signed8 = signer.addSignToOperation("../test-jsons/sample/pool-deposits.json");
        HashMap<String, Object> signed9 = signer.addSignToOperation("../test-jsons/sample/pool-withdraw.json");
        HashMap<String, Object> signed10 = signer.addSignToOperation("../test-jsons/sample/pool-register.json"); 
        HashMap<String, Object> signed11 = signer.addSignToOperation("../test-jsons/sample/pool-policy-updater.json");

        HashMap<String, Object> signed12 = signer.addSignToOperation("../test-jsons/sample/collection-register.json");
        HashMap<String, Object> signed13 = signer.addSignToOperation("../test-jsons/sample/collection-policy-updater.json");
        HashMap<String, Object> signed14 = signer.addSignToOperation("../test-jsons/sample/mint.json");
        HashMap<String, Object> signed15 = signer.addSignToOperation("../test-jsons/sample/burn.json");
        HashMap<String, Object> signed16 = signer.addSignToOperation("../test-jsons/sample/nft-sign.json");
        HashMap<String, Object> signed17 = signer.addSignToOperation("../test-jsons/sample/nft-transfer.json");
        HashMap<String, Object> signed18 = signer.addSignToOperation("../test-jsons/sample/approve.json");
        HashMap<String, Object> signed19 = signer.addSignToOperation("../test-jsons/sample/delegate.json");

        JSONParser.writeJsonFileFromHashMap(signed0, "../test-jsons/test-result/signed0-create-accounts.json");
        JSONParser.writeJsonFileFromHashMap(signed1, "../test-jsons/test-result/signed0-key-updater.json");
        JSONParser.writeJsonFileFromHashMap(signed2, "../test-jsons/test-result/signed0-transfers.json");

        JSONParser.writeJsonFileFromHashMap(signed3, "../test-jsons/test-result/signed0-create-contract-accounts.json");
        JSONParser.writeJsonFileFromHashMap(signed4, "../test-jsons/test-result/signed0-withdraws.json");
        
        JSONParser.writeJsonFileFromHashMap(signed5, "../test-jsons/test-result/signed0-create-documents.json");
        JSONParser.writeJsonFileFromHashMap(signed6, "../test-jsons/test-result/signed0-update-documents.json");
        JSONParser.writeJsonFileFromHashMap(signed7, "../test-jsons/test-result/signed0-sign-documents.json");

        JSONParser.writeJsonFileFromHashMap(signed8, "../test-jsons/test-result/signed0-pool-deposits.json");
        JSONParser.writeJsonFileFromHashMap(signed9, "../test-jsons/test-result/signed0-pool-withdraw.json");
        JSONParser.writeJsonFileFromHashMap(signed10, "../test-jsons/test-result/signed0-pool-register.json"); 
        JSONParser.writeJsonFileFromHashMap(signed11, "../test-jsons/test-result/signed0-pool-policy-updater.json");

        JSONParser.writeJsonFileFromHashMap(signed12, "../test-jsons/test-result/signed0-collection-register.json");
        JSONParser.writeJsonFileFromHashMap(signed13, "../test-jsons/test-result/signed0-collection-policy-updater.json");
        JSONParser.writeJsonFileFromHashMap(signed14, "../test-jsons/test-result/signed0-mint.json");
        JSONParser.writeJsonFileFromHashMap(signed15, "../test-jsons/test-result/signed0-burn.json");
        JSONParser.writeJsonFileFromHashMap(signed16, "../test-jsons/test-result/signed0-nft-sign.json");
        JSONParser.writeJsonFileFromHashMap(signed17, "../test-jsons/test-result/signed-nft-transfer.json");
        JSONParser.writeJsonFileFromHashMap(signed18, "../test-jsons/test-result/signed-approve.json");
        JSONParser.writeJsonFileFromHashMap(signed19, "../test-jsons/test-result/signed-delegate.json");

    }

    @DisplayName("Test signer - from file path")
    @Test
    void testSignerFromFp() {
        Signer signer = Signer.get("mitum", Keypair.random().getPrivateKey());

        JsonObject json0 = JSONParser.getObjectFromJsonFile("../test-jsons/sample/create-accounts.json");
        JsonObject json1 = JSONParser.getObjectFromJsonFile("../test-jsons/sample/key-updater.json");
        JsonObject json2 = JSONParser.getObjectFromJsonFile("../test-jsons/sample/transfers.json");

        JsonObject json3 = JSONParser.getObjectFromJsonFile("../test-jsons/sample/create-contract-accounts.json");
        JsonObject json4 = JSONParser.getObjectFromJsonFile("../test-jsons/sample/withdraws.json");

        JsonObject json5 = JSONParser.getObjectFromJsonFile("../test-jsons/sample/create-documents.json");
        JsonObject json6 = JSONParser.getObjectFromJsonFile("../test-jsons/sample/update-documents.json");
        JsonObject json7 = JSONParser.getObjectFromJsonFile("../test-jsons/sample/sign-documents.json");

        JsonObject json8 = JSONParser.getObjectFromJsonFile("../test-jsons/sample/pool-deposits.json");
        JsonObject json9 = JSONParser.getObjectFromJsonFile("../test-jsons/sample/pool-withdraw.json");
        JsonObject json10 = JSONParser.getObjectFromJsonFile("../test-jsons/sample/pool-register.json"); 
        JsonObject json11 = JSONParser.getObjectFromJsonFile("../test-jsons/sample/pool-policy-updater.json");

        JsonObject json12 = JSONParser.getObjectFromJsonFile("../test-jsons/sample/collection-register.json");
        JsonObject json13 = JSONParser.getObjectFromJsonFile("../test-jsons/sample/collection-policy-updater.json");
        JsonObject json14 = JSONParser.getObjectFromJsonFile("../test-jsons/sample/mint.json");
        JsonObject json15 = JSONParser.getObjectFromJsonFile("../test-jsons/sample/burn.json");
        JsonObject json16 = JSONParser.getObjectFromJsonFile("../test-jsons/sample/nft-sign.json");
        JsonObject json17 = JSONParser.getObjectFromJsonFile("../test-jsons/sample/nft-transfer.json");
        JsonObject json18 = JSONParser.getObjectFromJsonFile("../test-jsons/sample/approve.json");
        JsonObject json19 = JSONParser.getObjectFromJsonFile("../test-jsons/sample/delegate.json");

        HashMap<String, Object> signed0 = signer.addSignToOperation(json0);
        HashMap<String, Object> signed1 = signer.addSignToOperation(json1);
        HashMap<String, Object> signed2 = signer.addSignToOperation(json2);

        HashMap<String, Object> signed3 = signer.addSignToOperation(json3);
        HashMap<String, Object> signed4 = signer.addSignToOperation(json4);

        HashMap<String, Object> signed5 = signer.addSignToOperation(json5);
        HashMap<String, Object> signed6 = signer.addSignToOperation(json6);
        HashMap<String, Object> signed7 = signer.addSignToOperation(json7);

        HashMap<String, Object> signed8 = signer.addSignToOperation(json8);
        HashMap<String, Object> signed9 = signer.addSignToOperation(json9);
        HashMap<String, Object> signed10 = signer.addSignToOperation(json10); 
        HashMap<String, Object> signed11 = signer.addSignToOperation(json11);

        HashMap<String, Object> signed12 = signer.addSignToOperation(json12);
        HashMap<String, Object> signed13 = signer.addSignToOperation(json13);
        HashMap<String, Object> signed14 = signer.addSignToOperation(json14);
        HashMap<String, Object> signed15 = signer.addSignToOperation(json15);
        HashMap<String, Object> signed16 = signer.addSignToOperation(json16);
        HashMap<String, Object> signed17 = signer.addSignToOperation(json17);
        HashMap<String, Object> signed18 = signer.addSignToOperation(json18);
        HashMap<String, Object> signed19 = signer.addSignToOperation(json19);

        JSONParser.writeJsonFileFromHashMap(signed0, "../test-jsons/test-result/signed1-create-accounts.json");
        JSONParser.writeJsonFileFromHashMap(signed1, "../test-jsons/test-result/signed1-key-updater.json");
        JSONParser.writeJsonFileFromHashMap(signed2, "../test-jsons/test-result/signed1-transfers.json");

        JSONParser.writeJsonFileFromHashMap(signed3, "../test-jsons/test-result/signed1-create-contract-accounts.json");
        JSONParser.writeJsonFileFromHashMap(signed4, "../test-jsons/test-result/signed1-withdraws.json");
        
        JSONParser.writeJsonFileFromHashMap(signed5, "../test-jsons/test-result/signed1-create-documents.json");
        JSONParser.writeJsonFileFromHashMap(signed6, "../test-jsons/test-result/signed1-update-documents.json");
        JSONParser.writeJsonFileFromHashMap(signed7, "../test-jsons/test-result/signed1-sign-documents.json");

        JSONParser.writeJsonFileFromHashMap(signed8, "../test-jsons/test-result/signed1-pool-deposits.json");
        JSONParser.writeJsonFileFromHashMap(signed9, "../test-jsons/test-result/signed1-pool-withdraw.json");
        JSONParser.writeJsonFileFromHashMap(signed10, "../test-jsons/test-result/signed1-pool-register.json"); 
        JSONParser.writeJsonFileFromHashMap(signed11, "../test-jsons/test-result/signed1-pool-policy-updater.json");

        JSONParser.writeJsonFileFromHashMap(signed12, "../test-jsons/test-result/signed1-collection-register.json");
        JSONParser.writeJsonFileFromHashMap(signed13, "../test-jsons/test-result/signed1-collection-policy-updater.json");
        JSONParser.writeJsonFileFromHashMap(signed14, "../test-jsons/test-result/signed1-mint.json");
        JSONParser.writeJsonFileFromHashMap(signed15, "../test-jsons/test-result/signed1-burn.json");
        JSONParser.writeJsonFileFromHashMap(signed16, "../test-jsons/test-result/signed1-nft-sign.json");
        JSONParser.writeJsonFileFromHashMap(signed17, "../test-jsons/test-result/signed1-nft-transfer.json");
        JSONParser.writeJsonFileFromHashMap(signed18, "../test-jsons/test-result/signed1-approve.json");
        JSONParser.writeJsonFileFromHashMap(signed19, "../test-jsons/test-result/signed1-delegate.json");
    }

    @DisplayName("Test signer - copy")
    @Test
    void testSignerCopy() {
        JsonObject json0 = JSONParser.getObjectFromJsonFile("../test-jsons/sample/create-accounts.json");
        JsonObject json1 = JSONParser.getObjectFromJsonFile("../test-jsons/sample/key-updater.json");
        JsonObject json2 = JSONParser.getObjectFromJsonFile("../test-jsons/sample/transfers.json");

        JsonObject json3 = JSONParser.getObjectFromJsonFile("../test-jsons/sample/create-contract-accounts.json");
        JsonObject json4 = JSONParser.getObjectFromJsonFile("../test-jsons/sample/withdraws.json");

        JsonObject json5 = JSONParser.getObjectFromJsonFile("../test-jsons/sample/create-documents.json");
        JsonObject json6 = JSONParser.getObjectFromJsonFile("../test-jsons/sample/update-documents.json");
        JsonObject json7 = JSONParser.getObjectFromJsonFile("../test-jsons/sample/sign-documents.json");

        JsonObject json8 = JSONParser.getObjectFromJsonFile("../test-jsons/sample/pool-deposits.json");
        JsonObject json9 = JSONParser.getObjectFromJsonFile("../test-jsons/sample/pool-withdraw.json");
        JsonObject json10 = JSONParser.getObjectFromJsonFile("../test-jsons/sample/pool-register.json"); 
        JsonObject json11 = JSONParser.getObjectFromJsonFile("../test-jsons/sample/pool-policy-updater.json");

        JsonObject json12 = JSONParser.getObjectFromJsonFile("../test-jsons/sample/collection-register.json");
        JsonObject json13 = JSONParser.getObjectFromJsonFile("../test-jsons/sample/collection-policy-updater.json");
        JsonObject json14 = JSONParser.getObjectFromJsonFile("../test-jsons/sample/mint.json");
        JsonObject json15 = JSONParser.getObjectFromJsonFile("../test-jsons/sample/burn.json");
        JsonObject json16 = JSONParser.getObjectFromJsonFile("../test-jsons/sample/nft-sign.json");
        JsonObject json17 = JSONParser.getObjectFromJsonFile("../test-jsons/sample/nft-transfer.json");
        JsonObject json18 = JSONParser.getObjectFromJsonFile("../test-jsons/sample/approve.json");
        JsonObject json19 = JSONParser.getObjectFromJsonFile("../test-jsons/sample/delegate.json");

        JSONParser.writeJsonFileFromJsonObject(json0, "../test-jsons/test-result/copy-create-accounts.json");
        JSONParser.writeJsonFileFromJsonObject(json1, "../test-jsons/test-result/copy-key-updater.json");
        JSONParser.writeJsonFileFromJsonObject(json2, "../test-jsons/test-result/copy-transfers.json");

        JSONParser.writeJsonFileFromJsonObject(json3, "../test-jsons/test-result/copy-create-contract-accounts.json");
        JSONParser.writeJsonFileFromJsonObject(json4, "../test-jsons/test-result/copy-withdraws.json");
        
        JSONParser.writeJsonFileFromJsonObject(json5, "../test-jsons/test-result/copy-create-documents.json");
        JSONParser.writeJsonFileFromJsonObject(json6, "../test-jsons/test-result/copy-update-documents.json");
        JSONParser.writeJsonFileFromJsonObject(json7, "../test-jsons/test-result/copy-sign-documents.json");

        JSONParser.writeJsonFileFromJsonObject(json8, "../test-jsons/test-result/copy-pool-deposits.json");
        JSONParser.writeJsonFileFromJsonObject(json9, "../test-jsons/test-result/copy-pool-withdraw.json");
        JSONParser.writeJsonFileFromJsonObject(json10, "../test-jsons/test-result/copy-pool-register.json"); 
        JSONParser.writeJsonFileFromJsonObject(json11, "../test-jsons/test-result/copy-pool-policy-updater.json");

        JSONParser.writeJsonFileFromJsonObject(json12, "../test-jsons/test-result/copy-collection-register.json");
        JSONParser.writeJsonFileFromJsonObject(json13, "../test-jsons/test-result/copy-collection-policy-updater.json");
        JSONParser.writeJsonFileFromJsonObject(json14, "../test-jsons/test-result/copy-mint.json");
        JSONParser.writeJsonFileFromJsonObject(json15, "../test-jsons/test-result/copy-burn.json");
        JSONParser.writeJsonFileFromJsonObject(json16, "../test-jsons/test-result/copy-nft-sign.json");
        JSONParser.writeJsonFileFromJsonObject(json17, "../test-jsons/test-result/copy-nft-transfer.json");
        JSONParser.writeJsonFileFromJsonObject(json18, "../test-jsons/test-result/copy-approve.json");
        JSONParser.writeJsonFileFromJsonObject(json19, "../test-jsons/test-result/copy-delegate.json");
    }
}
