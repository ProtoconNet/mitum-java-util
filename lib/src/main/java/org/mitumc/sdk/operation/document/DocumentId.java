package org.mitumc.sdk.operation.document;

import java.util.HashMap;

import org.mitumc.sdk.interfaces.BytesConvertible;
import org.mitumc.sdk.interfaces.HashMapConvertible;
import org.mitumc.sdk.util.Hint;
import org.mitumc.sdk.util.Util;
import org.mitumc.sdk.Constant;

public class DocumentId implements BytesConvertible, HashMapConvertible {
    private String id;
    private String type;

    private DocumentId(String documentId) throws Exception {
        HashMap<String, String> parsed = Util.parseDocumentId(documentId);
        this.id = parsed.get("id");
        this.type = parsed.get("suffix");
    }

    public static DocumentId get(String documentId) throws Exception {
        try {
            return new DocumentId(documentId);
        } catch (Exception e) {
            throw new Exception(
                    Util.linkErrMsgs(
                            Util.errMsg("failed to create document id", Util.getName()),
                            e.getMessage()));
        }
    }

    public String getDocumentId() {
        return this.id + this.type;
    }

    public String getId() {
        return this.id;
    }

    public String getType() {
        return this.type;
    }

    @Override
    public byte[] toBytes() {
        return this.getDocumentId().getBytes();
    }

    @Override
    public HashMap<String, Object> toDict() throws Exception {
        HashMap<String, Object> hashMap = new HashMap<>();

        try {
            if (getType().equals(Constant.MBS_DOCUMENT_DATA)) {
                hashMap.put("_hint", Hint.get(Constant.MD_DOCUMENT_ID).getHint());
            } else {
                switch (getType()) {
                    case Constant.MBC_USER_DATA:
                        hashMap.put("_hint", Hint.get(Constant.MBC_USER_DOCUMENT_ID).getHint());
                        break;
                    case Constant.MBC_LAND_DATA:
                        hashMap.put("_hint", Hint.get(Constant.MBC_LAND_DOCUMENT_ID).getHint());
                        break;
                    case Constant.MBC_VOTE_DATA:
                        hashMap.put("_hint", Hint.get(Constant.MBC_VOTE_DOCUMENT_ID).getHint());
                        break;
                    case Constant.MBC_HISTORY_DATA:
                        hashMap.put("_hint", Hint.get(Constant.MBC_HISTORY_DOCUMENT_ID).getHint());
                        break;
                    default:
                        throw new Exception(
                                Util.errMsg("invalid document id", Util.getName()));
                }
            }
        } catch (Exception e) {
            throw new Exception(
                    Util.linkErrMsgs(
                            Util.errMsg("failed to convert document id to hashmap", Util.getName()),
                            e.getMessage()));
        }

        hashMap.put("id", getDocumentId());

        return hashMap;
    }
}
