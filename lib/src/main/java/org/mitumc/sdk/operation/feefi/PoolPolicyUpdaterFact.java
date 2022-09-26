package org.mitumc.sdk.operation.feefi;

import java.util.Base64;
import java.util.HashMap;
import org.mitumc.sdk.Constant;
import org.mitumc.sdk.key.Address;
import org.mitumc.sdk.operation.CurrencyID;
import org.mitumc.sdk.operation.base.PurposedOperationFact;
import org.mitumc.sdk.util.BigInt;
import org.mitumc.sdk.util.Hint;
import org.mitumc.sdk.util.Util;

public class PoolPolicyUpdaterFact extends PurposedOperationFact {
    private Address sender;
    private Address target;
    private BigInt fee;
    private CurrencyID incomeCid;
    private CurrencyID outlayCid;
    private CurrencyID currency;

    PoolPolicyUpdaterFact(
        String sender,
        String target,
        String fee,
        String incomeCid,
        String outlayCid,
        String currency
    ) {
        super(Constant.MF_POOL_POLICY_UPDATER_OPERATION_FACT);
        this.sender = Address.get(sender);
        this.target = Address.get(target);
        this.fee = BigInt.fromString(fee);
        this.incomeCid = CurrencyID.get(incomeCid);
        this.outlayCid = CurrencyID.get(outlayCid);
        this.currency = CurrencyID.get(currency);

        this.generateHash();
    }

    @Override
    public Hint getOperationHint() {
        return Hint.get(Constant.MF_POOL_POLICY_UPDATER_OPERATION);
    }

    @Override
    public byte[] toBytes() {
        byte[] btoken = this.token.getISO().getBytes();
        byte[] bsender = this.sender.toBytes();
        byte[] btarget = this.target.toBytes();
        byte[] bfee = this.fee.toBytes(BigInt.LITTLE_ENDIAN, true);
        byte[] bincomeCid = this.incomeCid.toBytes();
        byte[] boutlayCid = this.outlayCid.toBytes();
        byte[] bcurrencyId = this.currency.toBytes();
        return Util.concatByteArray(
            btoken,
            bsender,
            btarget,
            bfee,
            bincomeCid,
            boutlayCid,
            bcurrencyId
        );
    }

    @Override
    public HashMap<String, Object> toDict() {
        HashMap<String, Object> hashMap = new HashMap<>();

        hashMap.put("_hint", this.hint.getHint());
        hashMap.put("hash", this.hash.getSha3Hash());
        hashMap.put(
            "token",
            Base64.getEncoder().encodeToString(this.token.getISO().getBytes())
        );
        hashMap.put("sender", this.sender.getAddress());
        hashMap.put("target", this.target.getAddress());
        hashMap.put("fee", this.fee.getValue());
        hashMap.put("incomecid", this.incomeCid.toString());
        hashMap.put("outlaycid", this.outlayCid.toString());
        hashMap.put("currency", this.currency.toString());

        return hashMap;
    }
}
