package org.mitumc.sdk.operation.feefi;

import java.util.Base64;
import java.util.HashMap;

import org.mitumc.sdk.Constant;
import org.mitumc.sdk.key.Address;
import org.mitumc.sdk.operation.Amount;
import org.mitumc.sdk.operation.CurrencyID;
import org.mitumc.sdk.operation.base.PurposedOperationFact;
import org.mitumc.sdk.util.Hint;
import org.mitumc.sdk.util.Util;

public class PoolRegisterFact extends PurposedOperationFact {
    private Address sender;
    private Address target;
    private Amount initialFee;
    private CurrencyID incomingCid;
    private CurrencyID outgoingCid;
    private CurrencyID currency;

    PoolRegisterFact(String sender, String target, Amount initialFee, String incomingCid, String outgoingCid,
            String currency) {
        super(Constant.MF_POOL_REGISTER_OPERATION_FACT);
        this.sender = Address.get(sender);
        this.target = Address.get(target);
        this.initialFee = initialFee;
        this.incomingCid = CurrencyID.get(incomingCid);
        this.outgoingCid = CurrencyID.get(outgoingCid);
        this.currency = CurrencyID.get(currency);

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
        byte[] bincome = this.incomingCid.toBytes();
        byte[] boutgo = this.outgoingCid.toBytes();
        byte[] bcurrencyId = this.currency.toBytes();

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
        hashMap.put("incomingcid", this.incomingCid.toString());
        hashMap.put("outgoingcid", this.outgoingCid.toString());
        hashMap.put("currency", this.currency.toString());

        return hashMap;
    }
}
