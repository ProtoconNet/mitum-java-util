package org.mitumc.sdk.operation.document;

import org.mitumc.sdk.Constant;
import org.mitumc.sdk.operation.document.base.Document;
import org.mitumc.sdk.operation.document.base.GeneralDocumentsItem;

public class UpdateDocumentsItem extends GeneralDocumentsItem {
    UpdateDocumentsItem(Document document, String currencyId) throws Exception {
        super(Constant.MD_UPDATE_DOCUMENTS_ITEM, document, currencyId);
    }
}
