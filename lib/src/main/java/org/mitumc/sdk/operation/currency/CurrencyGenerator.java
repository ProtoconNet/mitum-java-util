package org.mitumc.sdk.operation.currency;

import org.mitumc.sdk.Constant;
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
    public Key key(String key, int weight) throws Exception {
        try {
            return Key.get(key, weight);
        } catch (Exception e) {
            throw new Exception(
                    Util.linkErrMsgs(
                            Util.errMsg("failed to create key", Util.getName()),
                            e.getMessage()));
        }
    }

    @Deprecated
    public Keys keys(Key[] keys, int threshold) throws Exception {
        try {
            return Keys.get(keys, threshold);
        } catch (Exception e) {
            throw new Exception(
                    Util.linkErrMsgs(
                            Util.errMsg("failed to create keys", Util.getName()),
                            e.getMessage()));
        }
    }

    @Deprecated
    public Amount amount(String currency, String amount) throws Exception {
        try {
            return Amount.get(currency, amount);
        } catch (Exception e) {
            throw new Exception(
                    Util.linkErrMsgs(
                            Util.errMsg("failed to create amount", Util.getName()),
                            e.getMessage()));
        }
    }

    public static CreateAccountsItem getCreateAccountsItem(Keys keys, Amount[] amounts) throws Exception {
        try {
            if (amounts.length > 1) {
                return new CreateAccountsItem(Constant.MC_CREATE_ACCOUNTS_MUL_AMOUNTS, keys, amounts);
            } else if (amounts.length == 1) {
                return new CreateAccountsItem(Constant.MC_CREATE_ACCOUNTS_SIN_AMOUNT, keys, amounts);
            } else {
                throw new Exception(Util.errMsg("empty amounts", Util.getName()));
            }
        } catch (Exception e) {
            throw new Exception(
                    Util.linkErrMsgs(
                            Util.errMsg("failed to create create accounts item", Util.getName()),
                            e.getMessage()));
        }
    }

    public static TransfersItem getTransfersItem(String receiver, Amount[] amounts) throws Exception {
        try {
            if (amounts.length > 1) {
                return new TransfersItem(Constant.MC_TRANSFERS_ITEM_MUL_AMOUNTS, receiver, amounts);
            } else if (amounts.length == 1) {
                return new TransfersItem(Constant.MC_TRANSFERS_ITEM_SIN_AMOUNT, receiver, amounts);
            } else {
                throw new Exception(Util.errMsg("empty amounts", Util.getName()));
            }
        } catch (Exception e) {
            throw new Exception(
                    Util.linkErrMsgs(
                            Util.errMsg("failed to create transfers item", Util.getName()),
                            e.getMessage()));
        }
    }

    public static CreateAccountsFact getCreateAccountsFact(String sender, CreateAccountsItem[] items) throws Exception {
        try {
            return new CreateAccountsFact(sender, items);
        } catch (Exception e) {
            throw new Exception(
                    Util.linkErrMsgs(
                            Util.errMsg("failed to create create accounts fact", Util.getName()),
                            e.getMessage()));
        }
    }

    public static KeyUpdaterFact getKeyUpdaterFact(String target, String currencyId, Keys keys) throws Exception {
        try {
            return new KeyUpdaterFact(target, currencyId, keys);
        } catch (Exception e) {
            throw new Exception(
                    Util.linkErrMsgs(
                            Util.errMsg("failed to create key updater fact", Util.getName()),
                            e.getMessage()));
        }
    }

    public static TransfersFact getTransfersFact(String sender, TransfersItem[] items) throws Exception {
        try {
            return new TransfersFact(sender, items);
        } catch (Exception e) {
            throw new Exception(
                    Util.linkErrMsgs(
                            Util.errMsg("failed to create tranfsers fact", Util.getName()),
                            e.getMessage()));
        }
    }
}
