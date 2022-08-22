package org.mitumc.sdk.operation;

import java.util.HashMap;

import org.mitumc.sdk.interfaces.BytesConvertible;
import org.mitumc.sdk.interfaces.HashMapConvertible;
import org.mitumc.sdk.Constant;
import org.mitumc.sdk.exception.NumberRangeException;
import org.mitumc.sdk.util.BigInt;
import org.mitumc.sdk.util.Hint;
import org.mitumc.sdk.util.Util;

public class Amount implements BytesConvertible, HashMapConvertible {
    private Hint hint;
    private CurrencyID currency;
    private BigInt amount;

    private Amount(String currency, String amount) {
        assertAmountNotZero(amount);
        this.hint = Hint.get(Constant.MC_AMOUNT);
        this.currency = CurrencyID.get(currency);
        this.amount = BigInt.fromString(amount);
    }

    public static Amount get(String currency, String amount) {
        return new Amount(currency, amount);
    }

    private static void assertAmountNotZero(String amount) {
        BigInt big = BigInt.fromString(amount);
        if (big.signum() <= 0) {
            throw new NumberRangeException(Util.errMsg("zero big", Util.getName()));
        }
    }

    public byte[] toBytes() {
        byte[] bamount = this.amount.toBytes(BigInt.LITTLE_ENDIAN, true);
        byte[] bcurrency = this.currency.toBytes();

        if (bamount.length < 1) {
            return bcurrency;
        }

        return Util.concatByteArray(bamount, bcurrency);
    }

    @Override
    public HashMap<String, Object> toDict() {
        HashMap<String, Object> hashMap = new HashMap<>();

        hashMap.put("_hint", this.hint.getHint());
        hashMap.put("amount", this.amount.getValue());
        hashMap.put("currency", this.currency.toString());

        return hashMap;
    }
}
