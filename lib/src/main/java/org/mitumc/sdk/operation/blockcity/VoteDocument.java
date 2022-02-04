package org.mitumc.sdk.operation.blockcity;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;

import org.mitumc.sdk.Constant;
import org.mitumc.sdk.util.BigInt;
import org.mitumc.sdk.util.Util;

public class VoteDocument extends Document {
    private BigInt round;
    private ArrayList<Candidate> candidates;

    VoteDocument(Info info, String owner, int round, Candidate[] candidates) {
        super(Constant.MBC_DOCTYPE_VOTE_DATA, info, owner);

        this.round = new BigInt("" + round);
        this.candidates = new ArrayList<Candidate>();

        for(Candidate candidate : candidates) {
            this.candidates.add(candidate);
        }
    }
    
    @Override
    public byte[] toBytes() {
        this.candidates.sort(new CandidateComparator());

        byte[] binfo = this.info.toBytes();
        byte[] bowner = this.owner.toBytes();
        byte[] bround = this.round.toBytes();
        byte[] bcandidates = Util.<Candidate>concatItemArray(this.candidates);

        return Util.concatByteArray(binfo, bowner, bround, bcandidates);
    }

    @Override
    public HashMap<String, Object> toDict() {
        HashMap<String, Object> hashMap = new HashMap<>();

        hashMap.put("_hint", this.hint.getHint());
        hashMap.put("info", this.info.toDict());
        hashMap.put("owner", this.owner.getAddress());
        hashMap.put("round", Integer.parseInt(this.round.getValue()));
        
        ArrayList<Object> arr = new ArrayList<>();
        for(Candidate candidate : this.candidates) {
            arr.add(candidate.toDict());
        }
        hashMap.put("candidates", arr);

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