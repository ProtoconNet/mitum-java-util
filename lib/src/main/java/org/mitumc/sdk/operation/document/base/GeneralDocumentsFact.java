package org.mitumc.sdk.operation.document.base;

import org.mitumc.sdk.operation.base.GeneralOperationFact;

public class GeneralDocumentsFact extends GeneralOperationFact<DocumentsItem> {
    protected GeneralDocumentsFact(String factType, String sender, DocumentsItem[] items) throws Exception {
        super(factType, sender, items);
    }
}
