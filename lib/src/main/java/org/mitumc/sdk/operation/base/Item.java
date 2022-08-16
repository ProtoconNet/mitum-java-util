package org.mitumc.sdk.operation.base;

import org.mitumc.sdk.interfaces.BytesConvertible;
import org.mitumc.sdk.interfaces.HashMapConvertible;
import org.mitumc.sdk.util.Hint;
import org.mitumc.sdk.util.Util;

abstract public class Item implements BytesConvertible, HashMapConvertible {
    protected Hint hint;

    protected Item(String itemType) throws Exception {
        try {
            this.hint = Hint.get(itemType);
        } catch (Exception e) {
            throw new Exception(
                    Util.linkErrMsgs(
                            Util.errMsg("failed to create item", Util.getName()),
                            e.getMessage()));
        }
    }
}