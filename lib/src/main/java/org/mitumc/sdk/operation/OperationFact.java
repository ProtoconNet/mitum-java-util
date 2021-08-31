package org.mitumc.sdk.operation;

import java.util.HashMap;

import org.mitumc.sdk.BytesChangeable;
import org.mitumc.sdk.Dictionariable;
import org.mitumc.sdk.util.Hash;
import org.mitumc.sdk.util.Hint;
import org.mitumc.sdk.util.TimeStamp;
import org.mitumc.sdk.util.Util;

abstract class OperationFact implements BytesChangeable, Dictionariable {
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

    abstract public byte[] toBytes();
    abstract public HashMap<String, Object> toDict();
}