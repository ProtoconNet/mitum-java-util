package org.mitumc.sdk.operation.document;

import org.mitumc.sdk.operation.base.BaseGenerator;
import org.mitumc.sdk.operation.document.base.Document;
import org.mitumc.sdk.operation.document.blockcity.BlockCityGenerator;
import org.mitumc.sdk.operation.document.blocksign.BlockSignGenerator;

public class DocumentGenerator extends BaseGenerator {
    public BlockSignGenerator blocksign = BlockSignGenerator.get();
    public BlockCityGenerator blockcity = BlockCityGenerator.get();

    public static DocumentGenerator get() {
        return new DocumentGenerator();
    }

    public CreateDocumentsItem getCreateDocumentsItem(Document document, String currency) {
        return new CreateDocumentsItem(document, currency);
    }

    public UpdateDocumentsItem getUpdateDocumentsItem(Document document, String currency) {
        return new UpdateDocumentsItem(document, currency);
    }

    public CreateDocumentsFact getCreateDocumentsFact(String sender, CreateDocumentsItem[] items) {
        return new CreateDocumentsFact(sender, items);
    }

    public UpdateDocumentsFact getUpdateDocumentsFact(String sender, UpdateDocumentsItem[] items) {
        return new UpdateDocumentsFact(sender, items);
    }
}
