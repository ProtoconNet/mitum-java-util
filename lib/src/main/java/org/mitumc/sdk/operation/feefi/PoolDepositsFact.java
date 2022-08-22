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

public class PoolDepositsFact extends PurposedOperationFact {
    private Address sender;
    private Address pool;
    private CurrencyID poolId;
    private Amount amount;

    PoolDepositsFact(String sender, String pool, String poolId, Amount amount) {
        super(Constant.MF_POOL_DEPOSITS_OPERATION_FACT);
        this.sender = Address.get(sender);
        this.pool = Address.get(pool);
        this.poolId = CurrencyID.get(poolId);
        this.amount = amount;

        this.generateHash();
    }

    @Override
    public Hint getOperationHint() {
        return Hint.get(Constant.MF_POOL_DEPOSITS_OPERATION);
    }

    @Override
    public byte[] toBytes() {
        byte[] btoken = this.token.getISO().getBytes();
        byte[] bsender = this.sender.toBytes();
        byte[] bpool = this.pool.toBytes();
        byte[] bpoolId = this.poolId.toBytes();
        byte[] bamount = this.amount.toBytes();
        return Util.concatByteArray(btoken, bsender, bpool, bpoolId, bamount);
    }

    @Override
    public HashMap<String, Object> toDict() {
        HashMap<String, Object> hashMap = new HashMap<>();

        hashMap.put("_hint", this.hint.getHint());
        hashMap.put("hash", this.hash.getSha3Hash());
        hashMap.put("token", Base64.getEncoder().encodeToString(this.token.getISO().getBytes()));
        hashMap.put("sender", this.sender.getAddress());
        hashMap.put("pool", this.pool.getAddress());
        hashMap.put("poolid", this.poolId.toString());
        hashMap.put("amount", this.amount.toDict());

        return hashMap;
    }
}
