package org.mitumc.sdk.operation.document.blockcity.doc;

import org.mitumc.sdk.operation.document.blockcity.Candidate;
import org.mitumc.sdk.operation.document.blockcity.UserStatistics;

public class SingleDocument {
    public static UserDocument user(String documentId, String owner, int gold, int bankGold,
            UserStatistics statistics) {
        return new UserDocument(documentId, owner, gold, bankGold, statistics);
    }

    public static LandDocument land(String documentId, String owner, String address, String area, String renter,
            String account, String rentDate, int period) {
        return new LandDocument(documentId, owner, address, area, renter, account, rentDate, period);
    }

    public static VoteDocument vote(String documentId, String owner, int round, String endTime, Candidate[] candidates,
            String bossName, String account, String office) {
        return new VoteDocument(documentId, owner, round, endTime, candidates, bossName, account, office);
    }

    public static HistoryDocument history(String documentId, String owner, String name, String account, String date,
            String usage, String app) {
        return new HistoryDocument(documentId, owner, name, account, date, usage, app);
    }
}
