package org.mitumc.sdk.operation.document;

import java.util.HashMap;

import org.mitumc.sdk.interfaces.BytesConvertible;
import org.mitumc.sdk.interfaces.HashMapConvertible;
import org.mitumc.sdk.util.Hint;
import org.mitumc.sdk.util.Util;
import org.mitumc.sdk.Constant;
import org.mitumc.sdk.exception.StringFormatException;

public class DocumentID implements BytesConvertible, HashMapConvertible {
    private Hint hint;
    private String id;
    private String type;

    private DocumentID(String documentId) {
        HashMap<String, String> parsed = Util.parseDocumentID(documentId);
        this.id = parsed.get("id");
        this.type = parsed.get("suffix");
        setHint();
    }

    public static DocumentID get(String documentId) {
        return new DocumentID(documentId);
    }

    private void setHint() {
        switch (getType()) {
            case Constant.MBS_DOCUMENT_DATA:
                this.hint = Hint.get(Constant.MD_DOCUMENT_ID);
                break;
            case Constant.MBC_USER_DATA:
                this.hint = Hint.get(Constant.MBC_USER_DOCUMENT_ID);
                break;
            case Constant.MBC_LAND_DATA:
                this.hint = Hint.get(Constant.MBC_LAND_DOCUMENT_ID);
                break;
            case Constant.MBC_VOTE_DATA:
                this.hint = Hint.get(Constant.MBC_VOTE_DOCUMENT_ID);
                break;
            case Constant.MBC_HISTORY_DATA:
                this.hint = Hint.get(Constant.MBC_HISTORY_DOCUMENT_ID);
                break;
            default:
                throw new StringFormatException(
                        Util.errMsg("invalid document id", Util.getName()));
        }
    }

    public String getDocumentID() {
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
        return this.getDocumentID().getBytes();
    }

    @Override
    public HashMap<String, Object> toDict() {
        HashMap<String, Object> hashMap = new HashMap<>();

        hashMap.put("_hint", this.hint.getHint());
        hashMap.put("id", getDocumentID());

        return hashMap;
    }
}
