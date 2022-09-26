package org.mitumc.sdk.operation.feefi;

import org.mitumc.sdk.operation.Amount;
import org.mitumc.sdk.operation.base.BaseGenerator;

public class FeefiGenerator extends BaseGenerator {
	public static FeefiGenerator get() {
		return new FeefiGenerator();
	}

	public PoolRegisterFact getPoolRegisterFact(
		String sender,
		String target,
		String initialFee,
		String incomeCid,
		String outlayCid,
		String currency
	) {
		return new PoolRegisterFact(
			sender,
			target,
			initialFee,
			incomeCid,
			outlayCid,
			currency
			);
	}

	public PoolPolicyUpdaterFact getPoolPolicyUpdaterFact(
		String sender,
		String target,
		String fee,
		String incomeCid,
		String outlayCid,
		String currency
	) {
		return new PoolPolicyUpdaterFact(
			sender,
			target,
			fee,
			incomeCid,
			outlayCid,
			currency
		);
	}

	public PoolDepositsFact getPoolDepositsFact(
		String sender,
		String pool,
		String incomeCid,
		String outlayCid,
		String amount
	) {
		return new PoolDepositsFact(sender, pool, incomeCid, outlayCid, amount);
	}

	public PoolWithdrawFact getPoolWithdrawFact(
		String sender,
		String pool,
		String incomeCid,
		String outlayCid,
		Amount[] amounts
	) {
		return new PoolWithdrawFact(sender, pool, incomeCid, outlayCid, amounts);
	}
}
