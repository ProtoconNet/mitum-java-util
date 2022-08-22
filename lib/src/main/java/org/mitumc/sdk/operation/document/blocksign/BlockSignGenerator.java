package org.mitumc.sdk.operation.document.blocksign;

import org.mitumc.sdk.operation.base.BaseGenerator;
import org.mitumc.sdk.operation.document.base.Document;
import org.mitumc.sdk.operation.document.blocksign.doc.BlockSignDocument;

public class BlockSignGenerator extends BaseGenerator {
    public static BlockSignGenerator get() {
        return new BlockSignGenerator();
    }

    public BlockSignUser user(String address, String signCode, boolean signed) {
        return new BlockSignUser(address, signCode, signed);
    }

    public Document document(String documentId, String owner, String fileHash, BlockSignUser creator,
            String title, String size, BlockSignUser[] signers) {
        return BlockSignDocument.get(documentId, owner, fileHash, creator, title, size, signers);
    }

    public SignDocumentsItem getSignDocumentsItem(String documentId, String owner, String currency) {
        return new SignDocumentsItem(documentId, owner, currency);
    }

    public SignDocumentsFact getSignDocumentsFact(String sender, SignDocumentsItem[] items) {
        return new SignDocumentsFact(sender, items);
    }
}