package org.mitumc.sdk.operation.document.blockcity;

import org.mitumc.sdk.operation.base.BaseGenerator;
import org.mitumc.sdk.operation.document.base.Document;
import org.mitumc.sdk.operation.document.blockcity.doc.SingleDocument;

public class BlockCityGenerator extends BaseGenerator {
    public static BlockCityGenerator get() {
        return new BlockCityGenerator();
    }

    public Candidate candidate(String address, String nickname, String manifest, int count) {
        return new Candidate(address, nickname, manifest, count);
    }

    public UserStatistics userStatistics(int hp, int strength, int agility, int dexterity, int charisma,
            int intelligence, int vital) {
        return new UserStatistics(hp, strength, agility, dexterity, charisma, intelligence, vital);
    }

    @Deprecated
    public Document document(String documentId, String owner, int gold, int bankGold,
            UserStatistics statistics) {
        return userDocument(documentId, owner, gold, bankGold, statistics);
    }

    @Deprecated
    public Document document(String documentId, String owner, String address, String area, String renter,
            String account, String rentDate, int period) {
        return landDocument(documentId, owner, address, area, renter, account, rentDate, period);
    }

    @Deprecated
    public Document document(String documentId, String owner, int round, String endTime, Candidate[] candidates,
            String bossName, String account, String office) {
        return voteDocument(documentId, owner, round, endTime, candidates, bossName, account, office);
    }

    @Deprecated
    public Document document(String documentId, String owner, String name, String account, String date,
            String usage, String app) {
        return historyDocument(documentId, owner, name, account, date, usage, app);
    }

    public Document userDocument(String documentId, String owner, int gold, int bankGold,
            UserStatistics statistics) {
        return SingleDocument.user(documentId, owner, gold, bankGold, statistics);
    }

    public Document landDocument(String documentId, String owner, String address, String area, String renter,
            String account, String rentDate, int period) {
        return SingleDocument.land(documentId, owner, address, area, renter, account, rentDate, period);
    }

    public Document voteDocument(String documentId, String owner, int round, String endTime, Candidate[] candidates,
            String bossName, String account, String office) {
        return SingleDocument.vote(documentId, owner, round, endTime, candidates, bossName, account, office);
    }

    public Document historyDocument(String documentId, String owner, String name, String account, String date,
            String usage, String app) {
        return SingleDocument.history(documentId, owner, name, account, date, usage, app);
    }
}