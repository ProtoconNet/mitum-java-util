package org.mitumc.sdk.operation.document.base;

import java.util.HashMap;

import org.mitumc.sdk.Constant;
import org.mitumc.sdk.util.Hint;
import org.mitumc.sdk.util.RegExp;
import org.mitumc.sdk.util.Util;
import org.mitumc.sdk.interfaces.BytesConvertible;
import org.mitumc.sdk.interfaces.HashMapConvertible;
import org.mitumc.sdk.operation.document.DocumentId;


public abstract class Info implements BytesConvertible, HashMapConvertible {
    private Hint hint;
    private String docType;
    private DocumentId documentId;

    protected Info(String docType, String documentId) {
        assertInfo(documentId);
        this.hint = new Hint(Constant.MD_DOCUMENT_INFO);
        this.docType = docType;
        this.documentId = new DocumentId(documentId);
    }

    private void assertInfo(String documentId) {
        if(docType == Constant.MBS_DOCTYPE_DOCUMENT_DATA) {
            RegExp.assertBlockSignDocumentId(documentId);
        }
        else {
            switch(docType) {
                case Constant.MBC_DOCTYPE_USER_DATA:
                    RegExp.assertUserData(documentId);
                    break;
                case Constant.MBC_DOCTYPE_LAND_DATA:
                    RegExp.assertLandData(documentId);
                    break;
                case Constant.MBC_DOCTYPE_VOTE_DATA:
                    RegExp.assertVoteData(documentId);
                    break;
                case Constant.MBC_DOCTYPE_HISTORY_DATA:
                    RegExp.assertHistoryData(documentId);
                    break;
                default:
                    RegExp.assertBlockCityDocumentId(documentId);
                    Util.raiseError("Invalid document type; Info.");
            }
        }
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
        hashMap.put("docid", this.documentId.toDict());
        hashMap.put("doctype", this.docType);

        return hashMap;
    }

    abstract public Hint getIdHint();
}
