package org.mitumc.sdk.operation.currency;

import org.mitumc.sdk.key.Key;
import org.mitumc.sdk.key.Keys;
import org.mitumc.sdk.operation.OperationGenerator;

public class CurrencyGenerator extends OperationGenerator {
    
    private CurrencyGenerator(String id) {
        super(id);
    }

    public static CurrencyGenerator get(String id) {
        return new CurrencyGenerator(id);
    }

    public Key key(String key, int weight) {
        return new Key(key, weight);
    }

    @Deprecated
    public Keys keys(int threshold) {
        return new Keys(threshold);
    }

    public Keys keys(Key[] keys, int threshold) {
        return new Keys(keys, threshold);
    }

    public Amount amount(String currency, String amount) {
        return new Amount(currency, amount);
    }

    public CreateAccountsItem getCreateAccountsItem(Keys keys, Amount[] amounts) {
        return new CreateAccountsItem(keys, amounts);
    }

    public TransfersItem getTransfersItem(String receiver, Amount[] amounts) {
        return new TransfersItem(receiver, amounts);
    }

    @Deprecated
    public CreateAccountsFact getCreateAccountsFact(String sender) {
        return new CreateAccountsFact(sender);
    }

    public CreateAccountsFact getCreateAccountsFact(String sender, CreateAccountsItem[] items) {
        return new CreateAccountsFact(sender, items);
    }

    public KeyUpdaterFact getKeyUpdaterFact(String target, String currencyId, Keys keys) {
        return new KeyUpdaterFact(target, currencyId, keys); 
    }

    @Deprecated
    public TransfersFact getTransfersFact(String sender) {
        return new TransfersFact(sender);
    }

    public TransfersFact getTransfersFact(String sender, TransfersItem[] items) {
        return new TransfersFact(sender, items);
    }
}
