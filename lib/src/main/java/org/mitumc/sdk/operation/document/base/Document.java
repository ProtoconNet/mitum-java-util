package org.mitumc.sdk.operation.document.base;

import org.mitumc.sdk.interfaces.BytesConvertible;
import org.mitumc.sdk.interfaces.HashMapConvertible;
import org.mitumc.sdk.key.Address;
import org.mitumc.sdk.util.Hint;

public abstract class Document implements BytesConvertible, HashMapConvertible {
    protected Hint hint;
    protected Info info;
    protected Address owner;

    protected Document(Info info, String owner) {
        this.info = info;
        this.hint = Hint.get(info.getDocType());
        this.owner = Address.get(owner);
    }

    public String getDocType() {
        return info.getDocType();
    }
}
