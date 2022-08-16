package org.mitumc.sdk.operation.document.blockcity.doc;

import org.mitumc.sdk.operation.document.blockcity.Candidate;
import org.mitumc.sdk.operation.document.blockcity.UserStatistics;
import org.mitumc.sdk.util.Util;

public class SingleDocument {
    public static UserDocument user(String documentId, String owner, int gold, int bankGold, UserStatistics statistics)
            throws Exception {
        try {
            return new UserDocument(documentId, owner, gold, bankGold, statistics);
        } catch (Exception e) {
            throw new Exception(
                    Util.linkErrMsgs(
                            Util.errMsg("failed to create user document", Util.getName()),
                            e.getMessage()));
        }
    }

    public static LandDocument land(String documentId, String owner, String address, String area, String renter,
            String account, String rentDate, int period) throws Exception {
        try {
            return new LandDocument(documentId, owner, address, area, renter, account, rentDate, period);
        } catch (Exception e) {
            throw new Exception(
                    Util.linkErrMsgs(
                            Util.errMsg("failed to create land document", Util.getName()),
                            e.getMessage()));
        }
    }

    public static VoteDocument vote(String documentId, String owner, int round, String endTime, Candidate[] candidates,
            String bossName, String account, String office) throws Exception {
        try {
            return new VoteDocument(documentId, owner, round, endTime, candidates, bossName, account, office);
        } catch (Exception e) {
            throw new Exception(
                    Util.linkErrMsgs(
                            Util.errMsg("failed to create vote document", Util.getName()),
                            e.getMessage()));
        }

    }

    public static HistoryDocument history(String documentId, String owner, String name, String account, String date,
            String usage, String app) throws Exception {
        try {
            return new HistoryDocument(documentId, owner, name, account, date, usage, app);
        } catch (Exception e) {
            throw new Exception(
                    Util.linkErrMsgs(
                            Util.errMsg("failed to create history document", Util.getName()),
                            e.getMessage()));
        }
    }
}
