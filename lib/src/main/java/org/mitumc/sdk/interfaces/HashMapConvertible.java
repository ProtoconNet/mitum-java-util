package org.mitumc.sdk.interfaces;

import java.util.HashMap;

import org.mitumc.sdk.exception.DummyMethodException;

public interface HashMapConvertible {
    public abstract HashMap<String, Object> toDict() throws DummyMethodException;
}
