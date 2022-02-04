package org.mitumc.sdk.operation.blockcity;

import org.mitumc.sdk.Constant;
import org.mitumc.sdk.interfaces.BytesChangeable;
import org.mitumc.sdk.interfaces.Dictionariable;
import org.mitumc.sdk.key.Address;
import org.mitumc.sdk.util.Hint;

abstract public class Document implements BytesChangeable, Dictionariable {
    public final static String DOCTYPE_USER_DATA = Constant.MBC_DOCTYPE_USER_DATA;
    public final static String DOCTYPE_LAND_DATA = Constant.MBC_DOCTYPE_LAND_DATA;
    public final static String DOCTYPE_VOTE_DATA = Constant.MBC_DOCTYPE_VOTE_DATA;

    protected Hint hint;
    protected Info info;
    protected Address owner;
    
    protected Document(String docType, Info info, String owner) {
        this.hint = new Hint(docType);
        this.info = info;
        this.owner = new Address(owner);
    }

    public String getDocType() {
        return info.getDocType();
    }
}
