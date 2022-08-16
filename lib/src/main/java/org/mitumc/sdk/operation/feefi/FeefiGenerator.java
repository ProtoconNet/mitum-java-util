package org.mitumc.sdk.operation.feefi;

import org.mitumc.sdk.operation.base.BaseGenerator;
import org.mitumc.sdk.operation.currency.base.Amount;
import org.mitumc.sdk.util.Util;

public class FeefiGenerator extends BaseGenerator {
    public static FeefiGenerator get() {
        return new FeefiGenerator();
    }

    public PoolRegisterFact getPoolRegisterFact(String sender, String target, Amount initialFee, String incomingCid,
            String outgoingCid, String currencyId) throws Exception {
        try {
            return new PoolRegisterFact(sender, target, initialFee, incomingCid, outgoingCid, currencyId);
        } catch (Exception e) {
            throw new Exception(
                    Util.linkErrMsgs(
                            Util.errMsg("failed to create pool register fact", Util.getName()),
                            e.getMessage()));
        }
    }

    public PoolPolicyUpdaterFact getPoolPolicyUpdaterFact(String sender, String target, Amount fee, String poolId,
            String currencyId) throws Exception {
        try {
            return new PoolPolicyUpdaterFact(sender, target, fee, poolId, currencyId);
        } catch (Exception e) {
            throw new Exception(
                    Util.linkErrMsgs(
                            Util.errMsg("failed to create pool policy updater fact", Util.getName()),
                            e.getMessage()));
        }
    }

    public PoolDepositsFact getPoolDepositsFact(String sender, String pool, String poolId, Amount amount)
            throws Exception {
        try {
            return new PoolDepositsFact(sender, pool, poolId, amount);
        } catch (Exception e) {
            throw new Exception(
                    Util.linkErrMsgs(
                            Util.errMsg("failed to create pool deposits fact", Util.getName()),
                            e.getMessage()));
        }
    }

    public PoolWithdrawFact getPoolWithdrawFact(String sender, String pool, String poolId, Amount[] amounts)
            throws Exception {
        try {
            return new PoolWithdrawFact(sender, pool, poolId, amounts);
        } catch (Exception e) {
            throw new Exception(
                    Util.linkErrMsgs(
                            Util.errMsg("failed to create pool withdraw fact", Util.getName()),
                            e.getMessage()));
        }
    }
}
