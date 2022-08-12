package org.mitumc.sdk.operation.document.blocksign.info.base;

import org.mitumc.sdk.Constant;
import org.mitumc.sdk.operation.document.base.Info;
import org.mitumc.sdk.util.Hint;
import org.mitumc.sdk.util.Util;

public class BlockSignInfo extends Info {
    protected BlockSignInfo(String docType, String documentId) {
        super(docType, documentId);
    }

    @Override
    public Hint getIdHint() {
        switch (this.getDocType()) {
            case Constant.MBS_DOCTYPE_DOCUMENT_DATA:
                return Hint.get(Constant.MD_DOCUMENT_ID);
            default:
                Util.raiseError("Invalid document type; BlockSignInfo");
        }
        return null;
    }
}
