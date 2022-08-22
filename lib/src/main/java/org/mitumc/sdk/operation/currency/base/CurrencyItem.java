package org.mitumc.sdk.operation.currency.base;

import java.util.ArrayList;
import java.util.Arrays;

import org.mitumc.sdk.exception.EmptyElementException;
import org.mitumc.sdk.exception.NumberLimitExceededException;
import org.mitumc.sdk.operation.Amount;
import org.mitumc.sdk.operation.base.Item;
import org.mitumc.sdk.util.Util;

public abstract class CurrencyItem extends Item {
    protected ArrayList<Amount> amounts;

    protected CurrencyItem(String itemType, Amount[] amounts) {
        super(itemType);
        assertNumberOfAmountsValidRange(amounts);
        this.amounts = new ArrayList<Amount>(Arrays.asList(amounts));
    }

    private static void assertNumberOfAmountsValidRange(Amount[] amounts) {
        if (amounts.length <= 0) {
            throw new EmptyElementException(Util.errMsg("empty amounts", Util.getName()));
        }

        if (amounts.length > 10) {
            throw new NumberLimitExceededException(
                Util.errMsg("the number of amounts exceeds max - now, " + amounts.length, Util.getName()
            ));
        }
    }
}