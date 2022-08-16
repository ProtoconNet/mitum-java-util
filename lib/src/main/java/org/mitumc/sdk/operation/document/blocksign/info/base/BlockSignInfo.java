package org.mitumc.sdk.operation.document.blocksign.info.base;

import org.mitumc.sdk.Constant;
import org.mitumc.sdk.operation.document.base.Info;
import org.mitumc.sdk.util.Hint;
import org.mitumc.sdk.util.Util;

public class BlockSignInfo extends Info {
    protected BlockSignInfo(String docType, String documentId) throws Exception {
        super(docType, documentId);
    }

    @Override
    public Hint getIdHint() throws Exception {
        try {
            switch (this.getDocType()) {
                case Constant.MBS_DOCTYPE_DOCUMENT_DATA:
                    return Hint.get(Constant.MD_DOCUMENT_ID);
                default:
                    throw new Exception(
                            Util.errMsg("invalid doctype", Util.getName()));
            }
        } catch (Exception e) {
            throw new Exception(
                    Util.linkErrMsgs(
                            Util.errMsg("failed to create hint from doctype", Util.getName()),
                            e.getMessage()));
        }
    }
}
