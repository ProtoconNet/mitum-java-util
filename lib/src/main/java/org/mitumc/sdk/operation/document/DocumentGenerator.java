package org.mitumc.sdk.operation.document;

import org.mitumc.sdk.operation.base.BaseGenerator;
import org.mitumc.sdk.operation.document.base.Document;
import org.mitumc.sdk.operation.document.blockcity.BlockCityGenerator;
import org.mitumc.sdk.operation.document.blocksign.BlockSignGenerator;

public class DocumentGenerator extends BaseGenerator {
    public static BlockSignGenerator blocksign = BlockSignGenerator.get();
    public static BlockCityGenerator blockcity = BlockCityGenerator.get();

    public static DocumentGenerator get() {
        return new DocumentGenerator();
    }

    public static CreateDocumentsItem getCreateDocumentsItem(Document document, String currencyId) {
        return new CreateDocumentsItem(document, currencyId);
    }

    public static UpdateDocumentsItem getUpdateDocumentsItem(Document document, String currencyId) {
        return new UpdateDocumentsItem(document, currencyId);
    }

    public static CreateDocumentsFact getCreateDocumentsFact(String sender, CreateDocumentsItem[] items) {
        return new CreateDocumentsFact(sender, items);
    }

    public static UpdateDocumentsFact getUpdateDocumentsFact(String sender, UpdateDocumentsItem[] items) {
        return new UpdateDocumentsFact(sender, items);
    }
}
