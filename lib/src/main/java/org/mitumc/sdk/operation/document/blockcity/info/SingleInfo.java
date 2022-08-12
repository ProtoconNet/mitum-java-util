package org.mitumc.sdk.operation.document.blockcity.info;

public class SingleInfo {
    public static UserInfo user(String documentId) {
        return new UserInfo(documentId);
    }
    public static LandInfo land(String documentId) {
        return new LandInfo(documentId);
    }
    public static VoteInfo vote(String documentId) {
        return new VoteInfo(documentId);
    }
    public static HistoryInfo history(String documentId) {
        return new HistoryInfo(documentId);
    }
}
