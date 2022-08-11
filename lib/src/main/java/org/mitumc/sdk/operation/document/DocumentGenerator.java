package org.mitumc.sdk.operation.document;

import org.mitumc.sdk.operation.base.OperationGenerator;
import org.mitumc.sdk.operation.document.blockcity.BlockCityGenerator;
import org.mitumc.sdk.operation.document.blocksign.BlockSignGenerator;

public class DocumentGenerator extends OperationGenerator {
    private BlockSignGenerator blocksign;
    private BlockCityGenerator blockcity;
    
    private DocumentGenerator(String id) {
        super(id);
        
        this.blocksign = BlockSignGenerator.get(id);
        this.blockcity = BlockCityGenerator.get(id);
    }

    public static DocumentGenerator get(String id) {
        return new DocumentGenerator(id);
    }

    public BlockSignGenerator blocksign() {
        return this.blocksign;
    }

    public BlockCityGenerator blockcity() {
        return this.blockcity;
    }

    @Override
    public void setId(String id) {
        super.setId(id);

        this.blocksign = BlockSignGenerator.get(id);
        this.blockcity = BlockCityGenerator.get(id);
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
