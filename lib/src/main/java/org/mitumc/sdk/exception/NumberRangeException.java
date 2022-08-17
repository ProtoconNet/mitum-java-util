package org.mitumc.sdk.exception;

import org.w3c.dom.ranges.RangeException;

public class NumberRangeException extends RangeException {
    public NumberRangeException(String msg) {
        super(RangeException.BAD_BOUNDARYPOINTS_ERR, msg);
    }
}
