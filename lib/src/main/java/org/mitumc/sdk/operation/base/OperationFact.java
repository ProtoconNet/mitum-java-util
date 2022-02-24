package org.mitumc.sdk.operation.base;

import java.util.HashMap;

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
        this.hint = new Hint(operationType);
        this.token = Util.getDateTimeStamp();
    }

    public String getType() {
        return this.hint.getType();
    }

    public Hash getHash() {
        return this.hash;
    }

    protected void generateHash() {
        this.hash = new Hash(toBytes());
    }

    abstract public Hint getOperationHint();
    abstract public byte[] toBytes();
    abstract public HashMap<String, Object> toDict();
}