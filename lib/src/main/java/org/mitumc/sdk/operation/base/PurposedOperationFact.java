package org.mitumc.sdk.operation.base;

import java.util.HashMap;

import org.mitumc.sdk.util.Hint;
import org.mitumc.sdk.util.Util;

public class PurposedOperationFact extends OperationFact {
    protected PurposedOperationFact(String factType) throws Exception {
        super(factType);
    }

    @Override
    public Hint getOperationHint() throws Exception {
        throw new Exception(Util.errMsg("unimplemented method", Util.getName()));
    }

    @Override
    public byte[] toBytes() throws Exception {
        throw new Exception(Util.errMsg("unimplemented method", Util.getName()));
    }

    @Override
    public HashMap<String, Object> toDict() throws Exception {
        throw new Exception(Util.errMsg("unimplemented method", Util.getName()));
    }
}
