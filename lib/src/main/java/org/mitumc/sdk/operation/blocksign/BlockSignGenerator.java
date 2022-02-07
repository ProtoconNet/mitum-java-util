package org.mitumc.sdk.operation.blocksign;

import org.mitumc.sdk.Constant;
import org.mitumc.sdk.operation.OperationGenerator;

public class BlockSignGenerator extends OperationGenerator {

    private BlockSignGenerator(String id) {
        super(id);
    }

    public static BlockSignGenerator get(String id) {
        return new BlockSignGenerator(id);
    }

    public CreateDocumentsItem getCreateDocumentsItem(String fileHash, int documentId, String signcode, String title, int size, String currencyId, String[] signers, String[] signcodes) {
        return new CreateDocumentsItem(fileHash, documentId, signcode, title, size, currencyId, signers, signcodes);
    }

    public SignDocumentsItem getSignDocumentsItem(String owner, int documentId, String currencyId) {
        return new SignDocumentsItem(owner, documentId, currencyId);
    }

    public BlockSignFact<CreateDocumentsItem> getBlockSignFact(String sender, CreateDocumentsItem[] items) {
        return new BlockSignFact<CreateDocumentsItem>(Constant.MBS_CREATE_DOCUMENTS_OPERATION_FACT, sender, items);
    }

    public BlockSignFact<SignDocumentsItem> getBlockSignFact(String sender, SignDocumentsItem[] items) {
        return new BlockSignFact<SignDocumentsItem>(Constant.MBS_SIGN_DOCUMENTS_OPERATION_FACT, sender, items);
    }
}