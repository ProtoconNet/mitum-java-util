package org.mitumc.sdk.operation.base;

import org.mitumc.sdk.util.Util;

public abstract class BaseGenerator {
    public static BaseGenerator get() throws Exception {
        throw new Exception(Util.errMsg("unimplemented method", Util.getName()));
    }
}
