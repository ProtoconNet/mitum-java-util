package org.mitumc.sdk.exception;

public class NumberLimitExceededException extends RuntimeException {
    public NumberLimitExceededException(String msg) {
        super(msg);
    }
}
