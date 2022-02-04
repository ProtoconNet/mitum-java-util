package org.mitumc.sdk.operation.currency;

import java.util.ArrayList;
import java.util.HashMap;

import org.mitumc.sdk.util.Util;
import org.mitumc.sdk.key.Address;

public class TransfersItem extends CurrencyItem {
    private Address receiver;

    TransfersItem(String receiver, Amount[] amounts) {
        super(ITEM_TYPE_C_TRANSFERS, amounts);
        this.receiver = new Address(receiver);
    }

    @Override
    public byte[] toBytes() {
        byte[] breceiver = this.receiver.toBytes();
        byte[] bamounts = Util.<Amount>concatItemArray(this.amounts);
        
        return Util.concatByteArray(breceiver, bamounts);
    }

    @Override
    public HashMap<String, Object> toDict() {
        HashMap<String, Object> hashMap = new HashMap<>();

        hashMap.put("_hint", this.hint.getHint());
        hashMap.put("receiver", this.receiver.getAddress());

        ArrayList<Object> arr = new ArrayList<>();
        for(Amount amount : this.amounts) {
            arr.add(amount.toDict());
        }
        hashMap.put("amounts", arr);

        return hashMap;
    }
}