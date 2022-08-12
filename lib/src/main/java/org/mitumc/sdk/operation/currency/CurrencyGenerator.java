package org.mitumc.sdk.operation.currency;

import org.mitumc.sdk.Constant;
import org.mitumc.sdk.key.Key;
import org.mitumc.sdk.key.Keys;
import org.mitumc.sdk.operation.base.OperationGenerator;
import org.mitumc.sdk.operation.currency.base.Amount;
import org.mitumc.sdk.operation.currency.extension.CurrencyExtensionGenerator;
import org.mitumc.sdk.util.Util;

public class CurrencyGenerator extends OperationGenerator {
    private CurrencyExtensionGenerator extension;
    
    private CurrencyGenerator(String id) {
        super(id);
        this.extension = CurrencyExtensionGenerator.get(id);
    }

    public static CurrencyGenerator get(String id) {
        return new CurrencyGenerator(id);
    }

    public CurrencyExtensionGenerator extension() {
        return this.extension;
    }

    @Override
    public void setId(String id) {
        super.setId(id);
        this.extension = CurrencyExtensionGenerator.get(id);
    }
    
    public Key key(String key, int weight) {
        return new Key(key, weight);
    }

    public Keys keys(Key[] keys, int threshold) {
        return new Keys(keys, threshold);
    }

    public Amount amount(String currency, String amount) {
        return new Amount(currency, amount);
    }

    public CreateAccountsItem getCreateAccountsItem(Keys keys, Amount[] amounts) {
        if(amounts.length > 1) {
            return new CreateAccountsItem(Constant.MC_CREATE_ACCOUNTS_MUL_AMOUNTS, keys, amounts);
        }
        else if(amounts.length == 1){
            return new CreateAccountsItem(Constant.MC_CREATE_ACCOUNTS_SIN_AMOUNT, keys, amounts);
        }
        else {
            Util.raiseError("No element in amounts; CurrencyGenerator.");
            return null;
        }
    }

    public TransfersItem getTransfersItem(String receiver, Amount[] amounts) {
        if(amounts.length > 1) {
            return new TransfersItem(Constant.MC_TRANSFERS_ITEM_MUL_AMOUNTS, receiver, amounts);
        }
        else if(amounts.length == 1) {
            return new TransfersItem(Constant.MC_TRANSFERS_ITEM_SIN_AMOUNT, receiver, amounts);
        }
        else {
            Util.raiseError("No element in amounts; CurrencyGenerator.");
            return null;
        }
    }

    public CreateAccountsFact getCreateAccountsFact(String sender, CreateAccountsItem[] items) {
        return new CreateAccountsFact(sender, items);
    }

    public KeyUpdaterFact getKeyUpdaterFact(String target, String currencyId, Keys keys) {
        return new KeyUpdaterFact(target, currencyId, keys); 
    }

    public TransfersFact getTransfersFact(String sender, TransfersItem[] items) {
        return new TransfersFact(sender, items);
    }
}
