package org.mitumc.sdk.operation.base;

import org.mitumc.sdk.exception.DummyMethodException;
import org.mitumc.sdk.util.Util;

public abstract class BaseGenerator {
    public static BaseGenerator get() throws DummyMethodException {
        throw new DummyMethodException(Util.getName());
    }
}
