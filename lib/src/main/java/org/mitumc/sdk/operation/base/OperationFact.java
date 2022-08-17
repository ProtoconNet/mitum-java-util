package org.mitumc.sdk.operation.base;

import org.mitumc.sdk.exception.DummyMethodException;
import org.mitumc.sdk.interfaces.BytesConvertible;
import org.mitumc.sdk.interfaces.HashMapConvertible;
import org.mitumc.sdk.util.Hash;
import org.mitumc.sdk.util.Hint;
import org.mitumc.sdk.util.TimeStamp;
import org.mitumc.sdk.util.Util;

abstract public class OperationFact implements BytesConvertible, HashMapConvertible {
    protected Hint hint;
    protected TimeStamp token;
    protected Hash hash;

    protected OperationFact(String operationType) {
        this.token = Util.getDateTimeStamp();
        this.hint = Hint.get(operationType);
    }

    public String getType() {
        return this.hint.getType();
    }

    public Hash getHash() {
        return this.hash;
    }

    protected void generateHash() {
        try {
            this.hash = Hash.fromBytes(toBytes());
        } catch (DummyMethodException e) {
            Util.loggingAndExit(e);
        }
    }

    abstract public Hint getOperationHint() throws DummyMethodException;
}