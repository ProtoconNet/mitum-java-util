package org.mitumc.sdk.operation.currency.extension;

import java.util.ArrayList;
import java.util.HashMap;

import org.mitumc.sdk.key.Keys;
import org.mitumc.sdk.operation.currency.base.Amount;
import org.mitumc.sdk.operation.currency.base.CurrencyItem;
import org.mitumc.sdk.util.Util;

public class CreateContractAccountsItem extends CurrencyItem {
    private Keys keys;

    CreateContractAccountsItem(String itemType, Keys keys, Amount[] amounts) {
        super(itemType, amounts);
        this.keys = keys;
    }

    @Override
    public byte[] toBytes() {
        byte[] bkeys = this.keys.toBytes();
        byte[] bamounts = Util.<Amount>concatItemArray(this.amounts);
        return Util.concatByteArray(bkeys, bamounts);
    }

    @Override
    public HashMap<String, Object> toDict() {
        HashMap<String, Object> hashMap = new HashMap<>();

        hashMap.put("_hint", this.hint.getHint());
        hashMap.put("keys", this.keys.toDict());

        ArrayList<Object> arr = new ArrayList<>();
        for (Amount amt : this.amounts) {
            arr.add(amt.toDict());
        }

        hashMap.put("amounts", arr);

        return hashMap;
    }
}
