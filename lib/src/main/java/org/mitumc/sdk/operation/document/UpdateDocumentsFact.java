package org.mitumc.sdk.operation.document;

import org.mitumc.sdk.Constant;
import org.mitumc.sdk.operation.document.base.DocumentsItem;
import org.mitumc.sdk.operation.document.base.GeneralDocumentsFact;
import org.mitumc.sdk.util.Hint;

public class UpdateDocumentsFact extends GeneralDocumentsFact {
    UpdateDocumentsFact(String sender, DocumentsItem[] items) {
        super(Constant.MD_UPDATE_DOCUMENTS_OPERATION_FACT, sender, items);
    }

    @Override
    public Hint getOperationHint() {
        return new Hint(Constant.MD_UPDATE_DOCUMENTS_OPERATION);
    }
}
