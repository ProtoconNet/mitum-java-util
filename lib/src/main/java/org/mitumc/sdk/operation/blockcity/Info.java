package org.mitumc.sdk.operation.blockcity;

import java.util.HashMap;

import org.mitumc.sdk.Constant;
import org.mitumc.sdk.interfaces.BytesChangeable;
import org.mitumc.sdk.interfaces.Dictionariable;
import org.mitumc.sdk.util.BigInt;
import org.mitumc.sdk.util.Hint;
import org.mitumc.sdk.util.Util;

public class Info implements BytesChangeable, Dictionariable {
    private Hint hint;
    private String docType;
    private BigInt documentId;

    Info(String docType, String documentId) {
        this.hint = new Hint(Constant.MBC_DOCUMENT_INFO);
        this.docType = docType;

        HashMap<String, String> parsed = Util.parseDocumentId(documentId);
        this.documentId = new BigInt(parsed.get("id"));
    }

    public String getDocType() {
        return docType;
    }

    public byte[] toBytes() {
        byte[] bdocumentId = this.documentId.toBytes(BigInt.LITTLE_ENDIAN, true);
        byte[] bdocType =  this.docType.getBytes();
        return Util.concatByteArray(bdocumentId, bdocType);
    }

    public HashMap<String, Object> toDict() {
        HashMap<String, Object> hashMap = new HashMap<>();

        hashMap.put("_hint", this.hint.getHint());
        
        HashMap<String, String> docid = new HashMap<>();
        switch(this.docType) {
            case Constant.MBC_DOCTYPE_USER_DATA:
                docid.put("_hint", Constant.MBC_USER_DOCUMENT_ID);
                break;
            case Constant.MBC_DOCTYPE_LAND_DATA:
                docid.put("_hint", Constant.MBC_LAND_DOCUMENT_ID);
                break;
            case Constant.MBC_DOCTYPE_VOTE_DATA:
                docid.put("_hint", Constant.MBC_VOTE_DOCUMENT_ID);
                break;
            default:
                Util.raiseError("Wrong document type for Info.toDict()");
        }

        hashMap.put("docid", docid);
        hashMap.put("doctype", this.docType);

        return hashMap;
    }
}
