package org.mitumc.sdk.util;

import org.mitumc.sdk.Constant;

public class Hint {
    private String type;
    private String version;

    public Hint(String type) {
        this(type, Constant.VERSION);
    }

    public Hint(String type, String version) {
        this.type = type;
        this.version = version;

        if(!isVersionValid()) {
            Util.raiseError("Invalid version for Hint.");
        }
    }

    private boolean isVersionValid() {
        if(!version.equals(Constant.VERSION)) {
            return false;
        }

        return true;
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