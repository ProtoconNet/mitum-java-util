package org.mitumc.sdk.operation.document;

import org.mitumc.sdk.Constant;
import org.mitumc.sdk.operation.document.base.GeneralDocumentsItem;


public class CreateDocumentsItem extends GeneralDocumentsItem {
    CreateDocumentsItem(Document document, String currencyId) {
        super(Constant.MD_CREATE_DOCUMENTS_ITEM, document, currencyId);
    }
}
