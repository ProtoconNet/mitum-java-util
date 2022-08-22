package org.mitumc.sdk.operation.currency.extension;

import org.mitumc.sdk.Constant;
import org.mitumc.sdk.key.Keys;
import org.mitumc.sdk.operation.Amount;
import org.mitumc.sdk.operation.base.BaseGenerator;

public class CurrencyExtensionGenerator extends BaseGenerator {
    public static CurrencyExtensionGenerator get() {
        return new CurrencyExtensionGenerator();
    }

    public CreateContractAccountsItem getCreateContractAccountsItem(Keys keys, Amount[] amounts) {
        // if (amounts.length > 1) {
        //     return new CreateContractAccountsItem(Constant.MC_EXT_CREATE_CONTRACT_ACCOUNTS_MUL_AMOUNTS, keys, amounts);
        // } else if(amounts.length == 1){
        //     return new
        //     CreateContractAccountsItem(Constant.MC_EXT_CREATE_CONTRACT_ACCOUNTS_SIN_AMOUNT,
        //     keys, amounts);
        // }
        return new CreateContractAccountsItem(Constant.MC_EXT_CREATE_CONTRACT_ACCOUNTS_MUL_AMOUNTS, keys, amounts);
    }

    public WithdrawsItem getWithdrawsItem(String target, Amount[] amounts) {
        // if (amounts.length > 1) {
        //     return new WithdrawsItem(Constant.MC_EXT_WITHDRAWS_MUL_AMOUNTS, target, amounts);
        // } else if (amounts.length == 1) {
        // return new WithdrawsItem(Constant.MC_EXT_WITHDRAWS_SIN_AMOUNT, target,
        // amounts);
        // }
        return new WithdrawsItem(Constant.MC_EXT_WITHDRAWS_MUL_AMOUNTS, target, amounts);
    }

    public CreateContractAccountsFact getCreateContractAccountsFact(String sender,
            CreateContractAccountsItem[] items) {
        return new CreateContractAccountsFact(sender, items);
    }

    public WithdrawsFact getWithdrawsFact(String sender, WithdrawsItem[] items) {
        return new WithdrawsFact(sender, items);
    }
}
