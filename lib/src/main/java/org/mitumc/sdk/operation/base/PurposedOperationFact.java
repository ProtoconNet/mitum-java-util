package org.mitumc.sdk.operation.base;

import java.util.HashMap;

import org.mitumc.sdk.util.Hint;
import org.mitumc.sdk.util.Util;

public class PurposedOperationFact extends OperationFact {
    protected PurposedOperationFact(String factType) {
        super(factType);
    }

    @Override
    public Hint getOperationHint() {
        Util.raiseError("Unimplemented method getOperationHint(); GeneralOperationFact.");
        return null;
    }

    @Override
    public byte[] toBytes() {
        Util.raiseError("Unimplemented method getOperationHint(); GeneralOperationFact.");
        return null;
    }

    @Override
    public HashMap<String, Object> toDict() {
        Util.raiseError("Unimplemented method getOperationHint(); GeneralOperationFact.");
        return null;
    }
}
