package org.mitumc.sdk.operation.feefi;

import org.mitumc.sdk.operation.base.OperationGenerator;
import org.mitumc.sdk.operation.currency.base.Amount;

public class FeefiGenerator extends OperationGenerator {
    
    private FeefiGenerator(String id) {
        super(id);
    }

    public static FeefiGenerator get(String id) {
        return new FeefiGenerator(id);
    }

    public PoolRegisterFact getPoolRegisterFact(String sender, String target, Amount initialFee, String incomingCid, String outgoingCid, String currencyId) {
        return new PoolRegisterFact(sender, target, initialFee, incomingCid, outgoingCid, currencyId);
    }

    public PoolPolicyUpdaterFact getPoolPolicyUpdaterFact(String sender, String target, Amount fee, String poolId, String currencyId) {
        return new PoolPolicyUpdaterFact(sender, target, fee, poolId, currencyId);
    }

    public PoolDepositsFact getPoolDepositsFact(String sender, String pool, String poolId, Amount amount) {
        return new PoolDepositsFact(sender, pool, poolId, amount);
    } 

    public PoolWithdrawFact getPoolWithdrawFact(String sender, String pool, String poolId, Amount[] amounts) {
        return new PoolWithdrawFact(sender, pool, poolId, amounts);
    } 
}
