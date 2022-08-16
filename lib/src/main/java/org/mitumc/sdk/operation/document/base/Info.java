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

    protected Info(String docType, String documentId) throws Exception {
        assertInfo(docType, documentId);
        this.hint = Hint.get(Constant.MD_DOCUMENT_INFO);
        this.docType = docType;
        this.documentId = DocumentId.get(documentId);
    }

    private void assertInfo(String docType, String documentId) throws Exception {
        try {
            if (docType.equals(Constant.MBS_DOCTYPE_DOCUMENT_DATA)) {
                RegExp.assertBlockSignDocumentId(documentId);
            } else {
                switch (docType) {
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
                        throw new Exception(
                                Util.errMsg("invalid document type", Util.getName()));
                }
            }
        } catch (Exception e) {
            throw new Exception(
                    Util.linkErrMsgs(
                            Util.errMsg("failed to verify info", Util.getName()),
                            e.getMessage()));
        }
    }

    public String getDocType() {
        return docType;
    }

    @Override
    public byte[] toBytes() {
        byte[] bdocumentId = this.documentId.toBytes();
        byte[] bdocType = this.docType.getBytes();
        return Util.concatByteArray(bdocumentId, bdocType);
    }

    @Override
    public HashMap<String, Object> toDict() throws Exception {
        HashMap<String, Object> hashMap = new HashMap<>();

        hashMap.put("_hint", this.hint.getHint());
        hashMap.put("doctype", this.docType);

        try {
            hashMap.put("docid", this.documentId.toDict());
        } catch (Exception e) {
            throw new Exception(
                    Util.linkErrMsgs(
                            Util.errMsg("failed to convert document info to hashmap", Util.getName()),
                            e.getMessage()));
        }

        return hashMap;
    }

    abstract public Hint getIdHint() throws Exception;
}
