package org.mitumc.sdk.operation.base;

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

    protected OperationFact(String operationType) throws Exception {
        this.token = Util.getDateTimeStamp();
        try {
            this.hint = Hint.get(operationType);
        } catch (Exception e) {
            throw new Exception(
                    Util.linkErrMsgs(
                            Util.errMsg("failed to create operation fact", Util.getName()),
                            e.getMessage()));
        }
    }

    public String getType() {
        return this.hint.getType();
    }

    public Hash getHash() {
        return this.hash;
    }

    protected void generateHash() throws Exception {
        try {
            this.hash = Hash.fromBytes(toBytes());
        } catch (Exception e) {
            throw new Exception(
                    Util.linkErrMsgs(
                            Util.errMsg("failed to generate hash", Util.getName()),
                            e.getMessage()));
        }
    }

    abstract public Hint getOperationHint() throws Exception;
}