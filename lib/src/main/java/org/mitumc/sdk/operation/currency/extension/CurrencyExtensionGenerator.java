package org.mitumc.sdk.operation.currency.extension;

import org.mitumc.sdk.Constant;
import org.mitumc.sdk.key.Keys;
import org.mitumc.sdk.operation.base.BaseGenerator;
import org.mitumc.sdk.operation.currency.base.Amount;
import org.mitumc.sdk.util.Util;

public class CurrencyExtensionGenerator extends BaseGenerator {
    public static CurrencyExtensionGenerator get() {
        return new CurrencyExtensionGenerator();
    }

    public static CreateContractAccountsItem getCreateContractAccountsItem(Keys keys, Amount[] amounts) throws Exception {
        try {
            if(amounts.length >= 1) {
                return new CreateContractAccountsItem(Constant.MC_EXT_CREATE_CONTRACT_ACCOUNTS_MUL_AMOUNTS, keys, amounts);
            }
            // else if(amounts.length == 1){
            //     return new CreateContractAccountsItem(Constant.MC_EXT_CREATE_CONTRACT_ACCOUNTS_SIN_AMOUNT, keys, amounts);
            // }
            else {
                throw new Exception(
                    Util.errMsg("empty amounts", Util.getName())
                );
            }
        } catch(Exception e) {
            throw new Exception(
                Util.linkErrMsgs(
                    Util.errMsg("failed to create contract accounts item", Util.getName()),
                    e.getMessage()
                )
            );
        }
    }

    public static WithdrawsItem getWithdrawsItem(String target, Amount[] amounts) throws Exception {
        try {
            if(amounts.length >= 1) {
                return new WithdrawsItem(Constant.MC_EXT_WITHDRAWS_MUL_AMOUNTS, target, amounts);
            }
            // else if (amounts.length == 1) {
            //     return new WithdrawsItem(Constant.MC_EXT_WITHDRAWS_SIN_AMOUNT, target, amounts);
            // }
            else {
                throw new Exception(
                    Util.errMsg("empty amounts", Util.getName())
                );
            }
        } catch(Exception e) {
            throw new Exception(
                Util.linkErrMsgs(
                    Util.errMsg("failed to create withdraws item", Util.getName()),
                    e.getMessage()
                )
            );
        }
    }

    public static CreateContractAccountsFact getCreateContractAccountsFact(String sender, CreateContractAccountsItem[] items) throws Exception {
        try {
            return new CreateContractAccountsFact(sender, items);
        } catch(Exception e) {
            throw new Exception(
                Util.linkErrMsgs(
                    Util.errMsg("failed to create contract accounts fact", Util.getName()),
                    e.getMessage()
                )
            );
        }
    }
    public static WithdrawsFact getWithdrawsFact(String sender, WithdrawsItem[] items) throws Exception {
        try {
            return new WithdrawsFact(sender, items);
        } catch(Exception e) {
            throw new Exception(
                Util.linkErrMsgs(
                    Util.errMsg("failed to create withdraws fact", Util.getName()),
                    e.getMessage()
                )
            );
        }
    }
}
