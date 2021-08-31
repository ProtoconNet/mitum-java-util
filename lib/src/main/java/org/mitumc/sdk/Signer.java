package org.mitumc.sdk;

import java.util.HashMap;

public class Signer {
    public static HashMap<String, Object> addSignToOperation(HashMap<String, Object> operation) throws Exception {
        throw new Exception("Signer.addSignToOperation(Map) must be implemented.");
    }

    public static HashMap<String, Object> addSignToOperation(String operationPath) throws Exception {
        throw new Exception("Signer.addSignToOperation(String) must be implemented.");
    }
}