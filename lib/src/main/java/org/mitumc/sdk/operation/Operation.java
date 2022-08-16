package org.mitumc.sdk.operation;

import java.util.ArrayList;
import java.util.HashMap;

import org.bitcoinj.core.Base58;

import org.mitumc.sdk.interfaces.BytesConvertible;
import org.mitumc.sdk.interfaces.HashMapConvertible;
import org.mitumc.sdk.operation.base.OperationFact;
import org.mitumc.sdk.sign.FactSign;
import org.mitumc.sdk.util.Hash;
import org.mitumc.sdk.util.Hint;
import org.mitumc.sdk.util.Util;

public class Operation implements BytesConvertible, HashMapConvertible {
    private Hint hint;
    private String memo;
    private OperationFact fact;
    private ArrayList<FactSign> factSigns;
    private Hash hash;

    private String networkId;

    private Operation(OperationFact fact, String memo) throws Exception {
        this.memo = memo;
        this.fact = fact;
        this.factSigns = new ArrayList<FactSign>();
        this.hint = fact.getOperationHint();
    }

    private Operation(OperationFact fact, String memo, String networkId) throws Exception {
        this(fact, memo);
        this.networkId = networkId;
    }

    private void generateHash() throws Exception {
        try {
            this.hash = Hash.fromBytes(toBytes());
        } catch (Exception e) {
            throw new Exception(
                    Util.linkErrMsgs(
                            Util.errMsg("failed to generate hash", Util.getName()),
                            e.getMessage()));
        }
    }

    public static Operation get(OperationFact fact, String memo, String networkId) throws Exception {
        try {
            return new Operation(fact, memo, networkId);
        } catch (Exception e) {
            throw new Exception(
                    Util.linkErrMsgs(
                            Util.errMsg("failed to create operation", Util.getName()),
                            e.getMessage()));
        }
    }

    @Deprecated
    public void addSign(String signKey) throws Exception {
        sign(signKey);
    }

    public void sign(String signKey) throws Exception {
        try {
            this.factSigns.add(
                    FactSign.get(
                            Util.concatByteArray(
                                    this.fact.getHash().getSha3Digest(),
                                    this.networkId.getBytes()),
                            signKey));
            generateHash();
        } catch (Exception e) {
            throw new Exception(
                    Util.linkErrMsgs(
                            Util.errMsg("failed to sign operation", Util.getName()),
                            e.getMessage()));
        }
    }

    public Hash getHash() {
        if (this.hash != null) {
            return this.hash;
        }
        return null;
    }

    @Override
    public byte[] toBytes() throws Exception {
        if (this.factSigns.size() < 1) {
            throw new Exception(Util.errMsg("empty fact signs", Util.getName()));
        }

        byte[] bfactHash = this.fact.getHash().getSha3Digest();
        byte[] bfactSigns = Util.<FactSign>concatItemArray(this.factSigns);
        byte[] bmemo = this.memo.getBytes();

        return Util.concatByteArray(bfactHash, bfactSigns, bmemo);
    }

    @Override
    public HashMap<String, Object> toDict() throws Exception {
        if (this.factSigns.size() < 1) {
            throw new Exception(
                    Util.errMsg("empty fact signs", Util.getName()));
        }

        HashMap<String, Object> hashMap = new HashMap<>();

        hashMap.put("memo", this.memo);
        hashMap.put("_hint", this.hint.getHint());
        hashMap.put("fact", this.fact.toDict());
        hashMap.put("hash", Base58.encode(this.hash.getSha3Digest()));

        ArrayList<Object> arr = new ArrayList<>();
        for (FactSign factSign : this.factSigns) {
            arr.add(factSign.toDict());
        }
        hashMap.put("fact_signs", arr);

        return hashMap;
    }
}