package org.mitumc.sdk.operation.document;

import org.mitumc.sdk.operation.base.OperationGenerator;
import org.mitumc.sdk.operation.document.blockcity.BlockCityGenerator;
import org.mitumc.sdk.operation.document.blocksign.BlockSignGenerator;

public class DocumentGenerator extends OperationGenerator {
    private BlockSignGenerator bs;
    private BlockCityGenerator bc;
    
    private DocumentGenerator(String id) {
        super(id);
        
        this.bs = BlockSignGenerator.get(id);
        this.bc = BlockCityGenerator.get(id);
    }

    public static DocumentGenerator get(String id) {
        return new DocumentGenerator(id);
    }

    public BlockSignGenerator bs() {
        return this.bs;
    }

    public BlockCityGenerator bc() {
        return this.bc;
    }

    @Override
    public void setId(String id) {
        super.setId(id);

        this.bs = BlockSignGenerator.get(id);
        this.bc = BlockCityGenerator.get(id);
    }

    @Override
    public String getId() {
        // TODO Auto-generated method stub
        return super.getId();
    }

    public CreateDocumentsItem getCreateDocumentsItem(Document document, String currencyId) {
        return new CreateDocumentsItem(document, currencyId);
    }

    public UpdateDocumentsItem getUpdateDocumentsItem(Document document, String currencyId) {
        return new UpdateDocumentsItem(document, currencyId);
    }

    public CreateDocumentsFact getCreateDocumentsFact(String sender, CreateDocumentsItem[] items) {
        return new CreateDocumentsFact(sender, items);
    }

    public UpdateDocumentsFact getUpdateDocumentsFact(String sender, UpdateDocumentsItem[] items) {
        return new UpdateDocumentsFact(sender, items);
    }
}
