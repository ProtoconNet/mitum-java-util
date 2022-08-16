package org.mitumc.sdk.operation.document.base;

import java.util.HashMap;

import org.mitumc.sdk.util.Util;

public class PurposedDocumentsItem extends DocumentsItem {
    protected PurposedDocumentsItem(String itemType) throws Exception {
        super(itemType);
    }

    @Override
    public byte[] toBytes() throws Exception {
        throw new Exception(Util.errMsg("unimplemented method", Util.getName()));
    }

    @Override
    public HashMap<String, Object> toDict() throws Exception {
        throw new Exception(Util.errMsg("unimplemented method", Util.getName()));
    }
}
