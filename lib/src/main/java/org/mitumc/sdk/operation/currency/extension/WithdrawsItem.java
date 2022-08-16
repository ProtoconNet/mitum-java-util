package org.mitumc.sdk.operation.currency.extension;

import java.util.ArrayList;
import java.util.HashMap;

import org.mitumc.sdk.key.Address;
import org.mitumc.sdk.operation.currency.base.Amount;
import org.mitumc.sdk.operation.currency.base.CurrencyItem;
import org.mitumc.sdk.util.Util;

public class WithdrawsItem extends CurrencyItem {
    private Address target;

    WithdrawsItem(String itemType, String target, Amount[] amounts) throws Exception {
        super(itemType, amounts);
        try {
            this.target = Address.get(target);
        } catch (Exception e) {
            throw new Exception(
                    Util.linkErrMsgs(
                            Util.errMsg("failed to create withdraws item", Util.getName()),
                            e.getMessage()));
        }
    }

    @Override
    public byte[] toBytes() throws Exception {
        byte[] btarget = this.target.toBytes();
        byte[] bamounts = null;

        try {
            bamounts = Util.<Amount>concatItemArray(this.amounts);
        } catch (Exception e) {
            throw new Exception(
                    Util.linkErrMsgs(
                            Util.errMsg("failed to convert withdraws item to bytes", Util.getName()),
                            e.getMessage()));
        }

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
