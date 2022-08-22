package org.mitumc.sdk.operation.feefi;

import org.mitumc.sdk.operation.Amount;
import org.mitumc.sdk.operation.base.BaseGenerator;

public class FeefiGenerator extends BaseGenerator {
    public static FeefiGenerator get() {
        return new FeefiGenerator();
    }

    public PoolRegisterFact getPoolRegisterFact(String sender, String target, Amount initialFee, String incomingCid,
            String outgoingCid, String currency) {
        return new PoolRegisterFact(sender, target, initialFee, incomingCid, outgoingCid, currency);
    }

    public PoolPolicyUpdaterFact getPoolPolicyUpdaterFact(String sender, String target, Amount fee, String poolId,
            String currency) {
        return new PoolPolicyUpdaterFact(sender, target, fee, poolId, currency);
    }

    public PoolDepositsFact getPoolDepositsFact(String sender, String pool, String poolId, Amount amount) {
        return new PoolDepositsFact(sender, pool, poolId, amount);
    }

    public PoolWithdrawFact getPoolWithdrawFact(String sender, String pool, String poolId, Amount[] amounts) {
        return new PoolWithdrawFact(sender, pool, poolId, amounts);
    }
}
