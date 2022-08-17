package org.mitumc.sdk.operation.currency;

import org.mitumc.sdk.Constant;
import org.mitumc.sdk.exception.EmptyElementException;
import org.mitumc.sdk.key.Key;
import org.mitumc.sdk.key.Keys;
import org.mitumc.sdk.operation.base.BaseGenerator;
import org.mitumc.sdk.operation.currency.base.Amount;
import org.mitumc.sdk.operation.currency.extension.CurrencyExtensionGenerator;
import org.mitumc.sdk.util.Util;

public class CurrencyGenerator extends BaseGenerator {
    public static CurrencyExtensionGenerator extension = CurrencyExtensionGenerator.get();

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

    public static CreateAccountsItem getCreateAccountsItem(Keys keys, Amount[] amounts) {
        if (amounts.length > 1) {
            return new CreateAccountsItem(Constant.MC_CREATE_ACCOUNTS_MUL_AMOUNTS, keys, amounts);
        } else if (amounts.length == 1) {
            return new CreateAccountsItem(Constant.MC_CREATE_ACCOUNTS_SIN_AMOUNT, keys, amounts);
        } else {
            throw new EmptyElementException(Util.errMsg("empty amounts", Util.getName()));
        }
    }

    public static TransfersItem getTransfersItem(String receiver, Amount[] amounts) {
        if (amounts.length > 1) {
            return new TransfersItem(Constant.MC_TRANSFERS_ITEM_MUL_AMOUNTS, receiver, amounts);
        } else if (amounts.length == 1) {
            return new TransfersItem(Constant.MC_TRANSFERS_ITEM_SIN_AMOUNT, receiver, amounts);
        } else {
            throw new EmptyElementException(Util.errMsg("empty amounts", Util.getName()));
        }
    }

    public static CreateAccountsFact getCreateAccountsFact(String sender, CreateAccountsItem[] items) {
        return new CreateAccountsFact(sender, items);
    }

    public static KeyUpdaterFact getKeyUpdaterFact(String target, String currencyId, Keys keys) {
        return new KeyUpdaterFact(target, currencyId, keys);
    }

    public static TransfersFact getTransfersFact(String sender, TransfersItem[] items) {
        return new TransfersFact(sender, items);
    }
}
