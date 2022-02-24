package org.mitumc.sdk.operation.base;

import java.util.HashMap;

import org.mitumc.sdk.interfaces.BytesConvertible;
import org.mitumc.sdk.interfaces.HashMapConvertible;
import org.mitumc.sdk.util.Hint;

abstract public class Item implements BytesConvertible, HashMapConvertible {
    protected Hint hint;

    protected Item(String itemType) {
        this.hint = new Hint(itemType);
    }

    abstract public byte[] toBytes();

    abstract public HashMap<String, Object> toDict();
}