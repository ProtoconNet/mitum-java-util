package org.mitumc.sdk.operation.document.blockcity;

import org.mitumc.sdk.operation.base.BaseGenerator;
import org.mitumc.sdk.operation.document.base.Document;
import org.mitumc.sdk.operation.document.blockcity.doc.SingleDocument;
import org.mitumc.sdk.util.Util;

public class BlockCityGenerator extends BaseGenerator {
    public static BlockCityGenerator get() {
        return new BlockCityGenerator();
    }

    public static Candidate candidate(String address, String nickname, String manifest, int count) throws Exception {
        try {
            return new Candidate(address, nickname, manifest, count);
        } catch (Exception e) {
            throw new Exception(
                    Util.linkErrMsgs(
                            Util.errMsg("failed to create candidate", Util.getName()),
                            e.getMessage()));
        }
    }

    public static UserStatistics userStatistics(int hp, int strength, int agility, int dexterity, int charisma,
            int intelligence, int vital) throws Exception {
        try {
            return new UserStatistics(hp, strength, agility, dexterity, charisma, intelligence, vital);
        } catch (Exception e) {
            throw new Exception(
                    Util.linkErrMsgs(
                            Util.errMsg("failed to create user statistics", Util.getName()),
                            e.getMessage()));
        }
    }

    public static Document document(String documentId, String owner, int gold, int bankGold,
            UserStatistics statistics) throws Exception {
        try {
            return SingleDocument.user(documentId, owner, gold, bankGold, statistics);
        } catch (Exception e) {
            throw new Exception(
                    Util.linkErrMsgs(
                            Util.errMsg("failed to create user document", Util.getName()),
                            e.getMessage()));
        }
    }

    public static Document document(String documentId, String owner, String address, String area, String renter,
            String account, String rentDate, int period) throws Exception {
        try {
            return SingleDocument.land(documentId, owner, address, area, renter, account, rentDate, period);
        } catch (Exception e) {
            throw new Exception(
                    Util.linkErrMsgs(
                            Util.errMsg("failed to create land document", Util.getName()),
                            e.getMessage()));
        }
    }

    public static Document document(String documentId, String owner, int round, String endTime, Candidate[] candidates,
            String bossName, String account, String office) throws Exception {
        try {
            return SingleDocument.vote(documentId, owner, round, endTime, candidates, bossName, account, office);
        } catch (Exception e) {
            throw new Exception(
                    Util.linkErrMsgs(
                            Util.errMsg("failed to create vote document", Util.getName()),
                            e.getMessage()));
        }
    }

    public static Document document(String documentId, String owner, String name, String account, String date,
            String usage, String app) throws Exception {
        try {
            return SingleDocument.history(documentId, owner, name, account, date, usage, app);
        } catch (Exception e) {
            throw new Exception(
                    Util.linkErrMsgs(
                            Util.errMsg("failed to create history document", Util.getName()),
                            e.getMessage()));
        }
    }
}