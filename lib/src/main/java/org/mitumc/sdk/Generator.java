package org.mitumc.sdk;

import java.util.ArrayList;
import java.util.HashMap;

import org.mitumc.sdk.operation.Operation;

public class Generator {
    public static HashMap<String, Object> newSeal(String signKey, ArrayList<Operation> operations) throws Exception {
        throw new Exception("Generator.newSeal(String, ArrayList) must be implemented.");
    }
}