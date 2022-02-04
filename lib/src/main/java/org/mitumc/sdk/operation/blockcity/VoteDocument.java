package org.mitumc.sdk.operation.blockcity;

import java.util.ArrayList;
import java.util.HashMap;

import org.mitumc.sdk.Constant;
import org.mitumc.sdk.util.BigInt;

public class VoteDocument extends Document {
    private BigInt round;
    private ArrayList<Candidate> candidates;

    VoteDocument(Info info, String owner, int round) {
        this(info, owner, round, new Candidate[0]);
    }

    VoteDocument(Info info, String owner, int round, Candidate[] candidates) {
        super(Constant.MBC_DOCTYPE_VOTE_DATA, info, owner);

        this.round = new BigInt("" + round);
        this.candidates = new ArrayList<Candidate>();

        for(Candidate candidate : candidates) {
            this.candidates.add(candidate);
        }
    }

    void addCandidate(String address, String manifest) {
        this.candidates.add(new Candidate(address, manifest));
    }

    @Override
    public byte[] toBytes() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public HashMap<String, Object> toDict() {
        // TODO Auto-generated method stub
        return null;
    }
}
