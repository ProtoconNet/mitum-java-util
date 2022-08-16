package org.mitumc.sdk.util;

import org.mitumc.sdk.Constant;

public class Hint {
    private String type;
    private String version;

    private Hint(String type) throws Exception{
        this(type, Constant.VERSION);
    }

    private Hint(String type, String version) throws Exception{
        assertVersion(version);
        this.type = type;
        this.version = version;
    }

    public static Hint get(String type) throws Exception {
        try {
            return new Hint(type);
        } catch(Exception e) {
            throw new Exception(
                Util.linkErrMsgs(
                    Util.errMsg("failed to create hint", Util.getName()),
                    e.getMessage()
                )
            );
        }
    }

    public static Hint get(String type, String version) throws Exception {
        try {
            return new Hint(type, version);
        } catch(Exception e) {
            throw new Exception(
                Util.linkErrMsgs(
                    Util.errMsg("failed to create hint", Util.getName()),
                    e.getMessage()
                )
            );
        }
    }

    private void assertVersion(String version) throws Exception {
        if(!version.equals(Constant.VERSION)) {
            throw new Exception(
                Util.errMsg("invalid mitum version", Util.getName())
            );
        }
    }

    public String getHint() {
        return this.type + '-' + this.version;
    }

    public String getType() {
        return this.type;
    }

    public String getVersion() {
        return this.version;
    }
}