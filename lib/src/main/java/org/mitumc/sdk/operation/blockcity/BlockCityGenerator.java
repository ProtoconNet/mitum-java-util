package org.mitumc.sdk.operation.blockcity;

import org.mitumc.sdk.Constant;
import org.mitumc.sdk.operation.Item;
import org.mitumc.sdk.operation.OperationGenerator;

public class BlockCityGenerator extends OperationGenerator {
    private BlockCityGenerator(String id) {
        super(id);
    }

    public static BlockCityGenerator get(String id) {
        return new BlockCityGenerator(id);
    }

    public Candidate candidate(String address, String manifest) {
        return new Candidate(address, manifest);
    }

    public Info info(String docType, String documentId) {
        // Document.DOCTYPE_USER_DATA
        // Document.DOCTYPE_LANG_DATA
        // Document.DOCTYPE_VOTE_DATA
        return new Info(docType, documentId);
    }

    public UserStatistics userStatistics(int hp, int strength, int agility, int dexterity, int charisma, int intelligence, int vital) {
        return new UserStatistics(hp, strength, agility, dexterity, charisma, intelligence, vital);
    }

    public Document document(Info info, String owner, String gold, String bankGold, UserStatistics statistics) {
        return new UserDocument(info, owner, gold, bankGold, statistics);
    }

    public Document document(Info info, String owner, String lender, String startTime, int period) {
        return new LandDocument(info, owner, lender, startTime, period);
    }

    public Document document(Info info, String owner, int round, Candidate[] candidates) {
        return new VoteDocument(info, owner, round, candidates);
    }

    public <T extends Document> BlockCityItem<T> getCreateDocumentsItem(T document, String currencyId) {
        return new BlockCityItem<T>(Item.ITEM_TYPE_BC_CREATE_DOCUMENTS, document.getDocType(), document, currencyId);
    }

    public <T extends Document> BlockCityItem<T> getUpdateDocumentsItem(T document, String currencyId) {
        return new BlockCityItem<T>(Item.ITEM_TYPE_BC_UPDATE_DOCUMENTS, document.getDocType(), document, currencyId);
    }

    public <T extends Document> BlockCityFact<T> getCreateDocumentsFact(String sender, BlockCityItem<T>[] items) {
        return new BlockCityFact<T>(Constant.MBC_CREATE_DOCUMENTS_OPERATION_FACT, sender, items);
    }

    public <T extends Document> BlockCityFact<T> getUpdateDocumentsFact(String sender, BlockCityItem<T>[] items) {
        return new BlockCityFact<T>(Constant.MBC_UPDATE_DOCUMENTS_OPERATION_FACT, sender, items);
    }
}