package org.mitumc.sdk.interfaces;

import org.mitumc.sdk.exception.DummyMethodException;

public interface BytesConvertible {
    public abstract byte[] toBytes() throws DummyMethodException;
}
