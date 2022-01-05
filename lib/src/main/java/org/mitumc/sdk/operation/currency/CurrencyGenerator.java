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

    public static Key newKey(String key, int weight) {
        return new Key(key, weight);
    }

    public static Keys newKeys(int threshold) {
        return new Keys(threshold);
    }

    public static Keys newKeys(Key[] keys, int threshold) {
        return new Keys(keys, threshold);
    }

    public static Amount newAmount(String currency, String amount) {
        return new Amount(currency, amount);
    }

    public static CreateAccountsItem newCreateAccountsItem(Keys keys, Amount[] amounts) {
        return new CreateAccountsItem(keys, amounts);
    }

    public static TransfersItem newTransfersItem(String receiver, Amount[] amounts) {
        return new TransfersItem(receiver, amounts);
    }

    public static CreateAccountsFact newCreateAccountsFact(String sender) {
        return new CreateAccountsFact(sender);
    }

    public static CreateAccountsFact newCreateAccountsFact(String sender, CreateAccountsItem[] items) {
        return new CreateAccountsFact(sender, items);
    }

    public static KeyUpdaterFact newKeyUpdaterFact(String target, String currencyId, Keys keys) {
        return new KeyUpdaterFact(target, currencyId, keys); 
    }

    public static TransfersFact newTransfersFact(String sender) {
        return new TransfersFact(sender);
    }

    public static TransfersFact newTransfersFact(String sender, TransfersItem[] items) {
        return new TransfersFact(sender, items);
    }
}
