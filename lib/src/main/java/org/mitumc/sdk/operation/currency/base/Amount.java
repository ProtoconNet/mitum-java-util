package org.mitumc.sdk.operation.currency.base;

import java.util.HashMap;

import org.mitumc.sdk.interfaces.BytesConvertible;
import org.mitumc.sdk.interfaces.HashMapConvertible;
import org.mitumc.sdk.Constant;
import org.mitumc.sdk.util.BigInt;
import org.mitumc.sdk.util.Hint;
import org.mitumc.sdk.util.Util;

public class Amount implements BytesConvertible, HashMapConvertible {
    private Hint hint;
    private String currency;
    private BigInt amount;

    private Amount(String currency, String amount) {
        this.hint = Hint.get(Constant.MC_AMOUNT);
        this.currency = currency;
        this.amount = new BigInt(amount);
    }

    public static Amount get(String currency, String amount) {
        return new Amount(currency, amount);
    }

    public byte[] toBytes() {
        byte[] bamount = this.amount.toBytes(BigInt.LITTLE_ENDIAN, true);
        byte[] bcurrency = this.currency.getBytes();

        if(bamount.length < 1) {
            return bcurrency;
        }

        return Util.concatByteArray(bamount, bcurrency);
    }

    @Override
    public HashMap<String, Object> toDict() {
        HashMap<String, Object> hashMap = new HashMap<>();

        hashMap.put("_hint", this.hint.getHint());
        hashMap.put("amount", this.amount.getValue());
        hashMap.put("currency", this.currency);

        return hashMap;
    }
}

