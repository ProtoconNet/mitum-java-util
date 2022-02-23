package org.mitumc.sdk.operation.document.blocksign;

import org.mitumc.sdk.operation.base.OperationGenerator;
import org.mitumc.sdk.operation.document.blocksign.doc.BlockSignDocument;


public class BlockSignGenerator extends OperationGenerator {

    private BlockSignGenerator(String id) {
        super(id);
    }

    public static BlockSignGenerator get(String id) {
        return new BlockSignGenerator(id);
    }

    public BlockSignUser user(String address, String signCode, boolean signed) {
        return new BlockSignUser(address, signCode, signed);
    }

    public BlockSignDocument document(String documentId, String owner, String fileHash, BlockSignUser creator, String title, String size, BlockSignUser[] signers) {
        return new BlockSignDocument(documentId, owner, fileHash, creator, title, size, signers);
    }
    
    public SignDocumentsItem getSignDocumentsItem(String documentId, String owner, String currencyId) {
        return new SignDocumentsItem(documentId, owner, currencyId);
    }

    public SignDocumentsFact getSignDocumentsFact(String sender, SignDocumentsItem[] items) {
        return new SignDocumentsFact(sender, items);
    }
}