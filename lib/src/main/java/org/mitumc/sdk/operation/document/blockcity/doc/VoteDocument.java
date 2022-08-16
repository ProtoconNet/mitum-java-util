package org.mitumc.sdk.operation.document.blockcity.doc;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;

import org.mitumc.sdk.Constant;
import org.mitumc.sdk.key.Address;
import org.mitumc.sdk.operation.document.base.Document;
import org.mitumc.sdk.operation.document.base.Info;
import org.mitumc.sdk.operation.document.blockcity.Candidate;
import org.mitumc.sdk.operation.document.blockcity.info.SingleInfo;
import org.mitumc.sdk.util.BigInt;
import org.mitumc.sdk.util.Util;

public class VoteDocument extends Document {
    private BigInt round;
    private String endTime;
    private ArrayList<Candidate> candidates;
    private String bossName;
    private Address account;
    private String office;

    VoteDocument(String documentId, String owner, int round, String endTime, Candidate[] candidates, String bossName,
            String account, String office) throws Exception {
        super(SingleInfo.vote(documentId), owner);
        assertInfo(info);
        this.round = new BigInt("" + round);
        this.endTime = endTime;
        this.bossName = bossName;
        this.account = Address.get(account);
        this.office = office;

        this.candidates = new ArrayList<Candidate>();
        for (Candidate candidate : candidates) {
            this.candidates.add(candidate);
        }
    }

    private void assertInfo(Info info) throws Exception {
        if (!info.getDocType().equals(Constant.MBC_DOCTYPE_VOTE_DATA)) {
            throw new Exception(Util.errMsg("invalid doctype", Util.getName()));
        }
    }

    @Override
    public byte[] toBytes() throws Exception {
        this.candidates.sort(new CandidateComparator());

        byte[] binfo = this.info.toBytes();
        byte[] bowner = this.owner.toBytes();
        byte[] bround = this.round.toBytes();
        byte[] bendTime = this.endTime.getBytes();
        byte[] bbossName = this.bossName.getBytes();
        byte[] baccount = this.account.toBytes();
        byte[] boffice = this.office.getBytes();
        byte[] bcandidates = null;

        try {
            bcandidates = Util.<Candidate>concatItemArray(this.candidates);
        } catch (Exception e) {
            throw new Exception(
                    Util.linkErrMsgs(
                            Util.errMsg("failed to convert vote document to bytes", Util.getName()),
                            e.getMessage()));
        }

        return Util.concatByteArray(binfo, bowner, bround, bendTime, bbossName, baccount, boffice, bcandidates);
    }

    @Override
    public HashMap<String, Object> toDict() throws Exception {
        HashMap<String, Object> hashMap = new HashMap<>();

        hashMap.put("_hint", this.hint.getHint());
        hashMap.put("owner", this.owner.getAddress());
        hashMap.put("round", Integer.parseInt(this.round.getValue()));
        hashMap.put("endvotetime", this.endTime);

        ArrayList<Object> arr = new ArrayList<>();
        for (Candidate candidate : this.candidates) {
            arr.add(candidate.toDict());
        }
        hashMap.put("candidates", arr);

        hashMap.put("bossname", this.bossName);
        hashMap.put("account", this.account.getAddress());
        hashMap.put("termofoffice", this.office);

        try {
            hashMap.put("info", this.info.toDict());
        } catch (Exception e) {
            throw new Exception(
                    Util.linkErrMsgs(
                            Util.errMsg("failed to convert vote document to hashmap", Util.getName()),
                            e.getMessage()));
        }

        return hashMap;
    }

    public static class CandidateComparator implements Comparator<Candidate> {
        @Override
        public int compare(Candidate c1, Candidate c2) {
            ByteBuffer b1 = ByteBuffer.wrap(c1.toBytes());
            ByteBuffer b2 = ByteBuffer.wrap(c2.toBytes());
            return b1.compareTo(b2);
        }
    }
}