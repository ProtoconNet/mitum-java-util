package org.mitumc.sdk.operation.base;

import org.mitumc.sdk.interfaces.BytesConvertible;
import org.mitumc.sdk.interfaces.HashMapConvertible;
import org.mitumc.sdk.util.Hint;

abstract public class Item implements BytesConvertible, HashMapConvertible {
    protected Hint hint;

    protected Item(String itemType) {
        this.hint = Hint.get(itemType);
    }
}