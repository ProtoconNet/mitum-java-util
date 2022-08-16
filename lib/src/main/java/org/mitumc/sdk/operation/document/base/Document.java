package org.mitumc.sdk.operation.document.base;

import org.mitumc.sdk.interfaces.BytesConvertible;
import org.mitumc.sdk.interfaces.HashMapConvertible;
import org.mitumc.sdk.key.Address;
import org.mitumc.sdk.util.Hint;
import org.mitumc.sdk.util.Util;

public abstract class Document implements BytesConvertible, HashMapConvertible {
    protected Hint hint;
    protected Info info;
    protected Address owner;

    protected Document(Info info, String owner) throws Exception {
        this.info = info;
        try {
            this.hint = Hint.get(info.getDocType());
            this.owner = Address.get(owner);
        } catch (Exception e) {
            throw new Exception(
                    Util.linkErrMsgs(
                            Util.errMsg("failed to create document", Util.getName()),
                            e.getMessage()));
        }
    }

    public String getDocType() {
        return info.getDocType();
    }
}
