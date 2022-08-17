package org.mitumc.sdk.operation.feefi;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.HashMap;

import org.mitumc.sdk.Constant;
import org.mitumc.sdk.key.Address;
import org.mitumc.sdk.operation.base.PurposedOperationFact;
import org.mitumc.sdk.operation.currency.base.Amount;
import org.mitumc.sdk.util.Hint;
import org.mitumc.sdk.util.Util;

public class PoolWithdrawFact extends PurposedOperationFact {
    private Address sender;
    private Address pool;
    private String poolId;
    private ArrayList<Amount> amounts;

    PoolWithdrawFact(String sender, String pool, String poolId, Amount[] amounts) {
        super(Constant.MF_POOL_WITHDRAW_OPERATION_FACT);
        this.sender = Address.get(sender);
        this.pool = Address.get(pool);
        this.poolId = poolId;
        this.amounts = new ArrayList<Amount>(Arrays.asList(amounts));

        this.generateHash();
    }

    @Override
    public Hint getOperationHint() {
        return Hint.get(Constant.MF_POOL_WITHDRAW_OPERATION);
    }

    @Override
    public byte[] toBytes() {
        byte[] btoken = this.token.getISO().getBytes();
        byte[] bsender = this.sender.toBytes();
        byte[] bpool = this.pool.toBytes();
        byte[] bpoolId = this.poolId.getBytes();
        byte[] bamounts = Util.<Amount>concatItemArray(this.amounts);
        return Util.concatByteArray(btoken, bsender, bpool, bpoolId, bamounts);
    }

    @Override
    public HashMap<String, Object> toDict() {
        HashMap<String, Object> hashMap = new HashMap<>();

        hashMap.put("_hint", this.hint.getHint());
        hashMap.put("hash", this.hash.getSha3Hash());
        hashMap.put("token", Base64.getEncoder().encodeToString(this.token.getISO().getBytes()));
        hashMap.put("sender", this.sender.getAddress());
        hashMap.put("pool", this.pool.getAddress());
        hashMap.put("poolid", this.poolId);

        ArrayList<Object> arr = new ArrayList<>();
        for (Amount amt : this.amounts) {
            arr.add(amt.toDict());
        }
        hashMap.put("amounts", arr);

        return hashMap;
    }
}
