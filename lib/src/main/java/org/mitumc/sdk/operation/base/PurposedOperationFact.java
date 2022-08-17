package org.mitumc.sdk.operation.base;

import java.util.HashMap;

import org.mitumc.sdk.exception.DummyMethodException;
import org.mitumc.sdk.util.Hint;
import org.mitumc.sdk.util.Util;

public class PurposedOperationFact extends OperationFact {
    protected PurposedOperationFact(String factType) {
        super(factType);
    }

    @Override
    public Hint getOperationHint() throws DummyMethodException {
        throw new DummyMethodException(Util.getName());
    }

    @Override
    public byte[] toBytes() throws DummyMethodException {
        throw new DummyMethodException(Util.getName());
    }

    @Override
    public HashMap<String, Object> toDict() throws DummyMethodException {
        throw new DummyMethodException(Util.getName());
    }
}
