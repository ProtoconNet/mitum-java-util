package org.mitumc.sdk.operation.document.base;

import java.util.HashMap;

import org.mitumc.sdk.exception.DummyMethodException;
import org.mitumc.sdk.util.Util;

public class PurposedDocumentsItem extends DocumentsItem {
    protected PurposedDocumentsItem(String itemType) {
        super(itemType);
    }

    @Override
    public byte[] toBytes() throws DummyMethodException {
        throw new DummyMethodException(Util.getName());
    }

    @Override
    public HashMap<String, Object> toDict() throws DummyMethodException {
        throw new DummyMethodException(Util.getName());
    }
}
