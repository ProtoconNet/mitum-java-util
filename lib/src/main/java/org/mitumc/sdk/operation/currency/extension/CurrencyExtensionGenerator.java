package org.mitumc.sdk.operation.currency.extension;

import org.mitumc.sdk.Constant;
import org.mitumc.sdk.key.Address;
import org.mitumc.sdk.key.Key;
import org.mitumc.sdk.key.Keys;
import org.mitumc.sdk.operation.base.OperationGenerator;
import org.mitumc.sdk.operation.currency.Amount;
import org.mitumc.sdk.util.Util;

public class CurrencyExtensionGenerator extends OperationGenerator {
    
    private CurrencyExtensionGenerator(String id) {
        super(id);
    }

    public static CurrencyExtensionGenerator get(String id) {
        return new CurrencyExtensionGenerator(id);
    }

    public Key key(String key, int weight) {
        return new Key(key, weight);
    }

    public Keys keys(Key[] keys, int threshold) {
        return new Keys(keys, threshold);
    }

    public CreateContractAccountsItem getCreateContractAccountsItem(Keys keys, Amount[] amounts) {
        if(amounts.length >= 1) {
            return new CreateContractAccountsItem(Constant.MC_EXT_CREATE_CONTRACT_ACCOUNTS_MUL_AMOUNTS, keys, amounts);
        }
        // else if(amounts.length == 1){
        //     return new CreateContractAccountsItem(Constant.MC_EXT_CREATE_CONTRACT_ACCOUNTS_SIN_AMOUNT, keys, amounts);
        // }
        else {
            Util.raiseError("No element in amounts; CurrencyExtensionGenerator.");
            return null;
        }
    }

    public WithdrawsItem getWithdrawsItem(Address target, Amount[] amounts) {
        if(amounts.length >= 1) {
            return new WithdrawsItem(Constant.MC_EXT_WITHDRAWS_MUL_AMOUNTS, target, amounts);
        }
        // else if (amounts.length == 1) {
        //     return new WithdrawsItem(Constant.MC_EXT_WITHDRAWS_SIN_AMOUNT, target, amounts);
        // }
        else {
            Util.raiseError("No element in amounts; CurrencyExtensionGenerator.");
            return null;
        }
    }

    public CreateContractAccountsFact getCreateContractAccountsFact(String sender, CreateContractAccountsItem[] items) {
        return new CreateContractAccountsFact(sender, items);
    }
    public WithdrawsFact getWithdrawsFact(String sender, WithdrawsItem[] items) {
        return new WithdrawsFact(sender, items);
    }
}
