package org.mitumc.sdk.operation.feefi;

import java.util.Base64;
import java.util.HashMap;

import org.mitumc.sdk.Constant;
import org.mitumc.sdk.key.Address;
import org.mitumc.sdk.operation.base.PurposedOperationFact;
import org.mitumc.sdk.operation.currency.base.Amount;
import org.mitumc.sdk.util.Hint;
import org.mitumc.sdk.util.Util;

public class PoolPolicyUpdaterFact extends PurposedOperationFact {
    private Address sender;
    private Address target;
    private Amount fee;
    private String poolId;
    private String currencyId;

    PoolPolicyUpdaterFact(String sender, String target, Amount fee, String poolId, String currencyId) throws Exception {
        super(Constant.MF_POOL_POLICY_UPDATER_OPERATION_FACT);
        this.sender = Address.get(sender);
        this.target = Address.get(target);
        this.fee = fee;
        this.poolId = poolId;
        this.currencyId = currencyId;

        this.generateHash();
    }

    @Override
    public Hint getOperationHint() throws Exception {
        try {
            return Hint.get(Constant.MF_POOL_POLICY_UPDATER_OPERATION);
        } catch (Exception e) {
            throw new Exception(
                    Util.linkErrMsgs(
                            Util.errMsg("failed to get operation hint", Util.getName()),
                            e.getMessage()));
        }
    }

    @Override
    public byte[] toBytes() {
        byte[] btoken = this.token.getISO().getBytes();
        byte[] bsender = this.sender.toBytes();
        byte[] btarget = this.target.toBytes();
        byte[] bfee = this.fee.toBytes();
        byte[] bpoolId = this.poolId.getBytes();
        byte[] bcurrencyId = this.currencyId.getBytes();

        return Util.concatByteArray(btoken, bsender, btarget, bfee, bpoolId, bcurrencyId);
    }

    @Override
    public HashMap<String, Object> toDict() {
        HashMap<String, Object> hashMap = new HashMap<>();

        hashMap.put("_hint", this.hint.getHint());
        hashMap.put("hash", this.hash.getSha3Hash());
        hashMap.put("token", Base64.getEncoder().encodeToString(this.token.getISO().getBytes()));
        hashMap.put("sender", this.sender.getAddress());
        hashMap.put("target", this.target.getAddress());
        hashMap.put("fee", this.fee.toDict());
        hashMap.put("poolid", this.poolId);
        hashMap.put("currency", this.currencyId);

        return hashMap;
    }
}
