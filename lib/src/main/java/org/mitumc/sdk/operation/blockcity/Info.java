package org.mitumc.sdk.operation.blockcity;

import java.util.HashMap;

import org.mitumc.sdk.Constant;
import org.mitumc.sdk.interfaces.BytesChangeable;
import org.mitumc.sdk.interfaces.Dictionariable;
import org.mitumc.sdk.util.Hint;
import org.mitumc.sdk.util.Util;

public class Info implements BytesChangeable, Dictionariable {
    private Hint hint;
    private String docType;
    private DocumentId documentId;

    Info(String docType, String documentId) {
        this.hint = new Hint(Constant.MBC_DOCUMENT_INFO);
        this.docType = docType;
        this.documentId = new DocumentId(documentId);
    }

    public String getDocType() {
        return docType;
    }

    public byte[] toBytes() {
        byte[] bdocumentId = this.documentId.toBytes();
        byte[] bdocType =  this.docType.getBytes();
        return Util.concatByteArray(bdocumentId, bdocType);
    }

    public HashMap<String, Object> toDict() {
        HashMap<String, Object> hashMap = new HashMap<>();

        hashMap.put("_hint", this.hint.getHint());
        
        HashMap<String, String> docid = new HashMap<>();
        switch(this.docType) {
            case Constant.MBC_DOCTYPE_USER_DATA:
                docid.put("_hint", new Hint(Constant.MBC_USER_DOCUMENT_ID).getHint());
                break;
            case Constant.MBC_DOCTYPE_LAND_DATA:
                docid.put("_hint", new Hint(Constant.MBC_LAND_DOCUMENT_ID).getHint());
                break;
            case Constant.MBC_DOCTYPE_VOTE_DATA:
                docid.put("_hint", new Hint(Constant.MBC_VOTE_DOCUMENT_ID).getHint());
                break;
            case Constant.MBC_DOCTYPE_HISTORY_DATA:
                docid.put("_hint", new Hint(Constant.MBC_HISTORY_DOCUMENT_ID).getHint());
                break;
            default:
                Util.raiseError("Wrong document type for Info.toDict()");
        }
        docid.put("id", this.documentId.getDocumentId());

        hashMap.put("docid", docid);
        hashMap.put("doctype", this.docType);

        return hashMap;
    }
}
