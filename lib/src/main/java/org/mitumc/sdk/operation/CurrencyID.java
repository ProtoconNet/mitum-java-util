package org.mitumc.sdk.operation;

import org.mitumc.sdk.operation.base.ID;

public class CurrencyID extends ID {
    private CurrencyID(String currency) {
        super(currency);
    }

    public static CurrencyID get(String id) {
        return new CurrencyID(id);
    }
}
