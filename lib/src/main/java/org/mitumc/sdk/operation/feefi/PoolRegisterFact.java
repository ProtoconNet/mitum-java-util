package org.mitumc.sdk.operation.feefi;

import java.util.Base64;
import java.util.HashMap;

import org.mitumc.sdk.Constant;
import org.mitumc.sdk.key.Address;
import org.mitumc.sdk.operation.base.PurposedOperationFact;
import org.mitumc.sdk.operation.currency.base.Amount;
import org.mitumc.sdk.util.Hint;
import org.mitumc.sdk.util.Util;

public class PoolRegisterFact extends PurposedOperationFact {
    private Address sender;
    private Address target;
    private Amount initialFee;
    private String incomingCid;
    private String outgoingCid;
    private String currencyId;

    PoolRegisterFact(String sender, String target, Amount initialFee, String incomingCid, String outgoingCid, String currencyId) {
        super(Constant.MF_POOL_REGISTER_OPERATION_FACT);
        this.sender = Address.get(sender);
        this.target = Address.get(target);
        this.initialFee = initialFee;
        this.incomingCid = incomingCid;
        this.outgoingCid = outgoingCid;
        this.currencyId = currencyId;

        this.generateHash();
    }

    @Override
    public Hint getOperationHint() {
        return Hint.get(Constant.MF_POOL_REGISTER_OPERATION);
    }

    @Override
    public byte[] toBytes() {
        byte[] btoken = this.token.getISO().getBytes();
        byte[] bsender = this.sender.toBytes();
        byte[] btarget = this.target.toBytes();
        byte[] bfee = this.initialFee.toBytes();
        byte[] bincome = this.incomingCid.getBytes();
        byte[] boutgo = this.outgoingCid.getBytes();
        byte[] bcurrencyId = this.currencyId.getBytes();

        return Util.concatByteArray(btoken, bsender, btarget, bfee, bincome, boutgo, bcurrencyId);
    }

    @Override
    public HashMap<String, Object> toDict() {
        HashMap<String, Object> hashMap = new HashMap<>();

        hashMap.put("_hint", this.hint.getHint());
        hashMap.put("hash", this.hash.getSha3Hash());
        hashMap.put("token", Base64.getEncoder().encodeToString(this.token.getISO().getBytes()));
        hashMap.put("sender", this.sender.getAddress());
        hashMap.put("target", this.target.getAddress());
        hashMap.put("initialfee", this.initialFee.toDict());
        hashMap.put("incomingcid", this.incomingCid);
        hashMap.put("outgoingcid", this.outgoingCid);
        hashMap.put("currency", this.currencyId);

        return hashMap;
    }
}
