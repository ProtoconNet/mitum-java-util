package org.mitumc.sdk.util;

import org.mitumc.sdk.Constant;
import org.mitumc.sdk.exception.MitumVersionException;
import org.mitumc.sdk.exception.StringFormatException;

public class Hint {
    private String type;
    private String version;

    private Hint(String type) throws StringFormatException {
        this(type, Constant.VERSION);
    }

    private Hint(String type, String version) throws StringFormatException {
        assertVersion(version);
        this.type = type;
        this.version = version;
    }

    public static Hint get(String type) throws StringFormatException {
        return new Hint(type);
    }

    public static Hint get(String type, String version) throws StringFormatException {
        return new Hint(type, version);
    }

    private void assertVersion(String version) throws MitumVersionException {
        if(!version.equals(Constant.VERSION)) {
            throw new MitumVersionException(
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