package org.mitumc.sdk.operation.document;

import org.mitumc.sdk.Constant;
import org.mitumc.sdk.operation.document.base.DocumentsItem;
import org.mitumc.sdk.operation.document.base.GeneralDocumentsFact;
import org.mitumc.sdk.util.Hint;
import org.mitumc.sdk.util.Util;

public class UpdateDocumentsFact extends GeneralDocumentsFact {
    UpdateDocumentsFact(String sender, DocumentsItem[] items) throws Exception {
        super(Constant.MD_UPDATE_DOCUMENTS_OPERATION_FACT, sender, items);
    }

    @Override
    public Hint getOperationHint() throws Exception {
        try {
            return Hint.get(Constant.MD_UPDATE_DOCUMENTS_OPERATION);
        } catch (Exception e) {
            throw new Exception(
                    Util.linkErrMsgs(
                            Util.errMsg("failed to get operation hint", Util.getName()),
                            e.getMessage()));
        }
    }
}
