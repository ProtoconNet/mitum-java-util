package org.mitumc.sdk;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.google.gson.JsonObject;

public class JSONParserTest {
    @DisplayName("Test json parser - copy")
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
