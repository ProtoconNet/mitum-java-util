package org.mitumc.sdk.operation.document.blocksign;

import org.mitumc.sdk.Constant;
import org.mitumc.sdk.operation.document.base.GeneralDocumentsFact;

public class SignDocumentsFact extends GeneralDocumentsFact {
    SignDocumentsFact(String sender, SignDocumentsItem[] items) {
        super(Constant.MBS_SIGN_DOCUMENTS_OP_FACT, sender, items);
    }
}
