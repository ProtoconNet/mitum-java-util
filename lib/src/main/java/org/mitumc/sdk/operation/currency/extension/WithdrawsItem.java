package org.mitumc.sdk.operation.currency.extension;

import java.util.ArrayList;
import java.util.HashMap;

import org.mitumc.sdk.key.Address;
import org.mitumc.sdk.operation.Amount;
import org.mitumc.sdk.operation.currency.base.CurrencyItem;
import org.mitumc.sdk.util.Util;

public class WithdrawsItem extends CurrencyItem {
    private Address target;

    WithdrawsItem(String itemType, String target, Amount[] amounts) {
        super(itemType, amounts);
        this.target = Address.get(target);
    }

    @Override
    public byte[] toBytes() {
        byte[] btarget = this.target.toBytes();
        byte[] bamounts = Util.<Amount>concatItemArray(this.amounts);
        return Util.concatByteArray(btarget, bamounts);
    }

    @Override
    public HashMap<String, Object> toDict() {
        HashMap<String, Object> hashMap = new HashMap<>();

        hashMap.put("_hint", this.hint.getHint());
        hashMap.put("target", this.target.getAddress());

        ArrayList<Object> arr = new ArrayList<>();
        for (Amount amt : this.amounts) {
            arr.add(amt.toDict());
        }

        hashMap.put("amounts", arr);

        return hashMap;
    }
}
