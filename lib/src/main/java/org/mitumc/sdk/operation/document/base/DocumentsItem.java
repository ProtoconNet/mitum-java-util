package org.mitumc.sdk.operation.document.base;

import java.util.HashMap;

import org.mitumc.sdk.operation.base.Item;
import org.mitumc.sdk.util.Util;

public class DocumentsItem extends Item {
    protected DocumentsItem(String itemType) {
        super(itemType);
    }

    @Override
    public byte[] toBytes() {
        Util.raiseError("Unimplemented method toBytes(); DocumentsItem.");
        return null;
    }

    @Override
    public HashMap<String, Object> toDict() {
        Util.raiseError("Unimplemented method toDict(); DocumentsItem.");
        return null;
    }
}
