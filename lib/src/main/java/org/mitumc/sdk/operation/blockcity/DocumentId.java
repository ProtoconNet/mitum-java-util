package org.mitumc.sdk.operation.blockcity;

import java.util.HashMap;

import org.mitumc.sdk.interfaces.BytesChangeable;
import org.mitumc.sdk.util.RegExp;
import org.mitumc.sdk.util.Util;

public class DocumentId implements BytesChangeable {
    private String id;
    private String type;

    DocumentId(String documentId) {
        RegExp.assertBlockCityDocumentId(documentId);

        HashMap<String, String> parsed = Util.parseDocumentId(documentId);
        this.id = parsed.get("id");
        this.type = parsed.get("suffix");
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
}
