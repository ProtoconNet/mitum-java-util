package org.mitumc.sdk.operation.document.blockcity.info;

import org.mitumc.sdk.util.Util;

public class SingleInfo {
    public static UserInfo user(String documentId) throws Exception {
        try {
            return new UserInfo(documentId);
        } catch (Exception e) {
            throw new Exception(
                    Util.linkErrMsgs(
                            Util.errMsg("failed to create user info", Util.getName()),
                            e.getMessage()));
        }
    }

    public static LandInfo land(String documentId) throws Exception {
        try {
            return new LandInfo(documentId);
        } catch (Exception e) {
            throw new Exception(
                    Util.linkErrMsgs(
                            Util.errMsg("failed to create land info", Util.getName()),
                            e.getMessage()));
        }
    }

    public static VoteInfo vote(String documentId) throws Exception {
        try {
            return new VoteInfo(documentId);
        } catch (Exception e) {
            throw new Exception(
                    Util.linkErrMsgs(
                            Util.errMsg("failed to create vote info", Util.getName()),
                            e.getMessage()));
        }
    }

    public static HistoryInfo history(String documentId) throws Exception {
        try {
            return new HistoryInfo(documentId);
        } catch (Exception e) {
            throw new Exception(
                    Util.linkErrMsgs(
                            Util.errMsg("failed to create history info", Util.getName()),
                            e.getMessage()));
        }
    }
}
