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

    public Candidate candidate(String address, String nickname, String manifest) {
        return new Candidate(address, nickname, manifest);
    }

    public Info info(String docType, String documentId) {
        // Document.DOCTYPE_USER_DATA
        // Document.DOCTYPE_LANG_DATA
        // Document.DOCTYPE_VOTE_DATA
        // Document.DOCTYPE_HISTORY_DATA
        return new Info(docType, documentId);
    }

    public UserStatistics userStatistics(int hp, int strength, int agility, int dexterity, int charisma, int intelligence, int vital) {
        return new UserStatistics(hp, strength, agility, dexterity, charisma, intelligence, vital);
    }

    public Document document(Info info, String owner, int gold, int bankGold, UserStatistics statistics) {
        return new UserDocument(info, owner, gold, bankGold, statistics);
    }

    public Document document(Info info, String owner, String address, String area, String renter, String account, String rentDate, int period) {
        return new LandDocument(info, owner, address, area, renter, account, rentDate, period);
    }

    public Document document(Info info, String owner, int round, String endTime, Candidate[] candidates, String bossName, String account, String office) {
        return new VoteDocument(info, owner, round, endTime, candidates, bossName, account, office);
    }

    public Document document(Info info, String owner, String name, String account, String date, String usage, String app) {
        return new HistoryDocument(info, owner, name, account, date, usage, app);
    }

    public BlockCityItem getCreateDocumentsItem(Document document, String currencyId) {
        return new BlockCityItem(Item.ITEM_TYPE_BC_CREATE_DOCUMENTS, document.getDocType(), document, currencyId);
    }

    public BlockCityItem getUpdateDocumentsItem(Document document, String currencyId) {
        return new BlockCityItem(Item.ITEM_TYPE_BC_UPDATE_DOCUMENTS, document.getDocType(), document, currencyId);
    }

    public BlockCityFact getCreateDocumentsFact(String sender, BlockCityItem[] items) {
        return new BlockCityFact(Constant.MBC_CREATE_DOCUMENTS_OPERATION_FACT, sender, items);
    }

    public BlockCityFact getUpdateDocumentsFact(String sender, BlockCityItem[] items) {
        return new BlockCityFact(Constant.MBC_UPDATE_DOCUMENTS_OPERATION_FACT, sender, items);
    }
}