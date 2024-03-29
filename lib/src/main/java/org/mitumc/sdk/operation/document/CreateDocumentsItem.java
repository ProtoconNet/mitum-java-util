package org.mitumc.sdk.operation.document;

import org.mitumc.sdk.Constant;
import org.mitumc.sdk.operation.document.base.Document;
import org.mitumc.sdk.operation.document.base.GeneralDocumentsItem;

public class CreateDocumentsItem extends GeneralDocumentsItem {
    CreateDocumentsItem(Document document, String currency) {
        super(Constant.MD_CREATE_DOCUMENTS_ITEM, document, currency);
    }
}
