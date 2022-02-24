package org.mitumc.sdk.operation.document.blocksign;

import org.mitumc.sdk.Constant;
import org.mitumc.sdk.operation.document.base.GeneralDocumentsFact;
import org.mitumc.sdk.util.Hint;

public class SignDocumentsFact extends GeneralDocumentsFact {
    SignDocumentsFact(String sender, SignDocumentsItem[] items) {
        super(Constant.MBS_SIGN_DOCUMENTS_OPERATION_FACT, sender, items);
    }

    @Override
    public Hint getOperationHint() {
        return new Hint(Constant.MBS_SIGN_DOCUMENTS_OPERATION);
    }
}
