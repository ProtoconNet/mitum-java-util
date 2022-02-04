package org.mitumc.sdk.operation.blockcity;

import java.util.HashMap;

import org.mitumc.sdk.Constant;
import org.mitumc.sdk.interfaces.BytesChangeable;
import org.mitumc.sdk.interfaces.Dictionariable;
import org.mitumc.sdk.util.Hint;

public class Info implements BytesChangeable, Dictionariable {
    private Hint hint;
    private Hint docType;
    private String documentId;

    Info(String docType, String documentId) {
        this.hint = new Hint(Constant.MBC_DOCUMENT_INFO);
        this.docType = new Hint(docType);
        this.documentId = documentId;
    }

    public byte[] toBytes() {
        return null;
    }

    public HashMap<String, Object> toDict() {
        return null;
    }
}
