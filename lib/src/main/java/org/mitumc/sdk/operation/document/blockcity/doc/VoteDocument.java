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
import org.mitumc.sdk.operation.document.blockcity.info.VoteInfo;
import org.mitumc.sdk.util.BigInt;
import org.mitumc.sdk.util.Util;

public class VoteDocument extends Document {
    private BigInt round;
    private String endTime;
    private ArrayList<Candidate> candidates;
    private String bossName;
    private Address account;
    private String office;

    public VoteDocument(String documentId, String owner, int round, String endTime, Candidate[] candidates, String bossName, String account, String office) {
        super(new VoteInfo(documentId), owner);
        assertInfo(info);
        this.round = new BigInt("" + round);
        this.endTime = endTime;
        this.bossName = bossName;
        this.account = new Address(account);
        this.office = office;

        this.candidates = new ArrayList<Candidate>();
        for(Candidate candidate : candidates) {
            this.candidates.add(candidate);
        }
    }
    
    private void assertInfo(Info info) {
        if(!info.getDocType().equals(Constant.MBC_DOCTYPE_VOTE_DATA)) {
            Util.raiseError("Invalid docType of Info; VoteDocument.");
        }
    }

    @Override
    public byte[] toBytes() {
        this.candidates.sort(new CandidateComparator());

        byte[] bInfo = this.info.toBytes();
        byte[] bOwner = this.owner.toBytes();
        byte[] bRound = this.round.toBytes();
        byte[] bEndTime = this.endTime.getBytes();
        byte[] bBossName = this.bossName.getBytes();
        byte[] bAccount = this.account.toBytes();
        byte[] bOffice = this.office.getBytes();
        byte[] bCandidates = Util.<Candidate>concatItemArray(this.candidates);
        
        return Util.concatByteArray(bInfo, bOwner, bRound, bEndTime, bBossName, bAccount, bOffice, bCandidates);
    }

    @Override
    public HashMap<String, Object> toDict() {
        HashMap<String, Object> hashMap = new HashMap<>();

        hashMap.put("_hint", this.hint.getHint());
        hashMap.put("info", this.info.toDict());
        hashMap.put("owner", this.owner.getAddress());
        hashMap.put("round", Integer.parseInt(this.round.getValue()));
        hashMap.put("endvotetime", this.endTime);

        ArrayList<Object> arr = new ArrayList<>();
        for(Candidate candidate : this.candidates) {
            arr.add(candidate.toDict());
        }
        hashMap.put("candidates", arr);

        hashMap.put("bossname", this.bossName);
        hashMap.put("account", this.account.getAddress());
        hashMap.put("termofoffice", this.office);

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