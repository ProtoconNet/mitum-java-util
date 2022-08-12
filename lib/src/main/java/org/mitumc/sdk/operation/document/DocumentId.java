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

    private DocumentId(String documentId) {
        HashMap<String, String> parsed = Util.parseDocumentId(documentId);
        this.id = parsed.get("id");
        this.type = parsed.get("suffix");
    }

    public static DocumentId get(String documentId) {
        return new DocumentId(documentId);
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
    public HashMap<String, Object> toDict() {
        HashMap<String, Object> hashMap = new HashMap<>();
        
        if(getType().equals(Constant.MBS_DOCUMENT_DATA)) {
            hashMap.put("_hint", Hint.get(Constant.MD_DOCUMENT_ID).getHint());
        }
        else {
            switch(getType()) {
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
                    Util.raiseError("Invalid document id; DocumentId");
            }
        }

        hashMap.put("id", getDocumentId());

        return hashMap;
    }
}
