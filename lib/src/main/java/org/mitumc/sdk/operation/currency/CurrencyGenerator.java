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

    public Key newKey(String key, int weight) {
        return new Key(key, weight);
    }

    public Keys newKeys(int threshold) {
        return new Keys(threshold);
    }

    public Keys newKeys(Key[] keys, int threshold) {
        return new Keys(keys, threshold);
    }

    public Amount newAmount(String currency, String amount) {
        return new Amount(currency, amount);
    }

    public CreateAccountsItem newCreateAccountsItem(Keys keys, Amount[] amounts) {
        return new CreateAccountsItem(keys, amounts);
    }

    public TransfersItem newTransfersItem(String receiver, Amount[] amounts) {
        return new TransfersItem(receiver, amounts);
    }

    public CreateAccountsFact newCreateAccountsFact(String sender) {
        return new CreateAccountsFact(sender);
    }

    public CreateAccountsFact newCreateAccountsFact(String sender, CreateAccountsItem[] items) {
        return new CreateAccountsFact(sender, items);
    }

    public KeyUpdaterFact newKeyUpdaterFact(String target, String currencyId, Keys keys) {
        return new KeyUpdaterFact(target, currencyId, keys); 
    }

    public TransfersFact newTransfersFact(String sender) {
        return new TransfersFact(sender);
    }

    public TransfersFact newTransfersFact(String sender, TransfersItem[] items) {
        return new TransfersFact(sender, items);
    }
}
