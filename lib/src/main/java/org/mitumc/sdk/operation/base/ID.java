package org.mitumc.sdk.operation.base;

import org.mitumc.sdk.exception.StringFormatException;
import org.mitumc.sdk.interfaces.BytesConvertible;
import org.mitumc.sdk.util.Util;

public class ID implements BytesConvertible {
    private String id;

    protected ID(String id) {
        assertIDNotEmpty(id);
        assertIDLengthInRange(id);
        this.id = id;
    }

    private static void assertIDNotEmpty(String id) {
        if (id.isEmpty()) {
            throw new StringFormatException(Util.errMsg("empty id", Util.getName()));
        }
    }

    private static void assertIDLengthInRange(String id) {
        if (id.length() < 3 || id.length() > 10) {
            throw new StringFormatException(Util.errMsg("invalid id length - now, " + id.length(), Util.getName()));
        }
    }

    @Override
    public byte[] toBytes() {
        return this.id.getBytes();
    }

    public String toString() {
        return this.id;
    }
}
