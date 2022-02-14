package org.mitumc.sdk.util;

import org.mitumc.sdk.Constant;

public class Hint {
    private String type;
    private String version;

    public Hint(String type) {
        this(type, Constant.VERSION);
    }

    public Hint(String type, String version) {
        assertVersion(version);
        this.type = type;
        this.version = version;
    }

    private void assertVersion(String version) {
        if(!version.equals(Constant.VERSION)) {
            Util.raiseError("Invalid version; Hint.");
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