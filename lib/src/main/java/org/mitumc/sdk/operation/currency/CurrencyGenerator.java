package org.mitumc.sdk.operation.currency;

import org.mitumc.sdk.Constant;
import org.mitumc.sdk.key.Key;
import org.mitumc.sdk.key.Keys;
import org.mitumc.sdk.operation.Amount;
import org.mitumc.sdk.operation.base.BaseGenerator;
import org.mitumc.sdk.operation.currency.extension.CurrencyExtensionGenerator;

public class CurrencyGenerator extends BaseGenerator {
    public CurrencyExtensionGenerator extension = CurrencyExtensionGenerator.get();

    public static CurrencyGenerator get() {
        return new CurrencyGenerator();
    }

    @Deprecated
    public Key key(String key, int weight) {
        return Key.get(key, weight);
    }

    @Deprecated
    public Keys keys(Key[] keys, int threshold) {
        return Keys.get(keys, threshold);
    }

    @Deprecated
    public Amount amount(String currency, String amount) {
        return Amount.get(currency, amount);
    }

    public CreateAccountsItem getCreateAccountsItem(Keys keys, Amount[] amounts) {
        if (amounts.length > 1) {
            return new CreateAccountsItem(Constant.MC_CREATE_ACCOUNTS_MUL_AMOUNTS, keys, amounts);
        }
        return new CreateAccountsItem(Constant.MC_CREATE_ACCOUNTS_SIN_AMOUNT, keys, amounts);
    }

    public TransfersItem getTransfersItem(String receiver, Amount[] amounts) {
        if (amounts.length > 1) {
            return new TransfersItem(Constant.MC_TRANSFERS_ITEM_MUL_AMOUNTS, receiver, amounts);
        } 
        return new TransfersItem(Constant.MC_TRANSFERS_ITEM_SIN_AMOUNT, receiver, amounts);
    }

    public CreateAccountsFact getCreateAccountsFact(String sender, CreateAccountsItem[] items) {
        return new CreateAccountsFact(sender, items);
    }

    public KeyUpdaterFact getKeyUpdaterFact(String target, String currency, Keys keys) {
        return new KeyUpdaterFact(target, currency, keys);
    }

    public TransfersFact getTransfersFact(String sender, TransfersItem[] items) {
        return new TransfersFact(sender, items);
    }
}
