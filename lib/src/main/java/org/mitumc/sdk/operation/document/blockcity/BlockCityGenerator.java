package org.mitumc.sdk.operation.document.blockcity;

import org.mitumc.sdk.operation.base.OperationGenerator;
import org.mitumc.sdk.operation.document.Document;
import org.mitumc.sdk.operation.document.blockcity.doc.HistoryDocument;
import org.mitumc.sdk.operation.document.blockcity.doc.LandDocument;
import org.mitumc.sdk.operation.document.blockcity.doc.UserDocument;
import org.mitumc.sdk.operation.document.blockcity.doc.VoteDocument;

public class BlockCityGenerator extends OperationGenerator {

    private BlockCityGenerator(String id) {
        super(id);
    }

    public static BlockCityGenerator get(String id) {
        return new BlockCityGenerator(id);
    }

    public Candidate candidate(String address, String nickname, String manifest, int count) {
        return new Candidate(address, nickname, manifest, count);
    }

    public UserStatistics userStatistics(int hp, int strength, int agility, int dexterity, int charisma, int intelligence, int vital) {
        return new UserStatistics(hp, strength, agility, dexterity, charisma, intelligence, vital);
    }

    public Document document(String documentId, String owner, int gold, int bankGold, UserStatistics statistics) {
        return new UserDocument(documentId, owner, gold, bankGold, statistics);
    }

    public Document document(String documentId, String owner, String address, String area, String renter, String account, String rentDate, int period) {
        return new LandDocument(documentId, owner, address, area, renter, account, rentDate, period);
    }

    public Document document(String documentId, String owner, int round, String endTime, Candidate[] candidates, String bossName, String account, String office) {
        return new VoteDocument(documentId, owner, round, endTime, candidates, bossName, account, office);
    }

    public Document document(String documentId, String owner, String name, String account, String date, String usage, String app) {
        return new HistoryDocument(documentId, owner, name, account, date, usage, app);
    }
}