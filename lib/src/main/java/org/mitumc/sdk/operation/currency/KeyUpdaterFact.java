package org.mitumc.sdk.operation.currency;

import java.util.Base64;
import java.util.HashMap;

import org.mitumc.sdk.Constant;
import org.mitumc.sdk.key.Keys;
import org.mitumc.sdk.operation.base.PurposedOperationFact;
import org.mitumc.sdk.key.Address;
import org.mitumc.sdk.util.Hint;
import org.mitumc.sdk.util.Util;


public class KeyUpdaterFact extends PurposedOperationFact {
    private Address target;
    private String currencyId;
    private Keys keys;

    KeyUpdaterFact(String target, String currencyId, Keys keys) {
        super(Constant.MC_KEY_UPDATER_OPERATION_FACT);
        this.target = new Address(target);
        this.currencyId = currencyId;
        this.keys = keys;

        generateHash();
    }

    @Override
    public Hint getOperationHint() {
        return new Hint(Constant.MC_KEY_UPDATER_OPERATION);
    }

    @Override
    public byte[] toBytes() {
        byte[] btoken = this.token.getISO().getBytes();
        byte[] btarget = this.target.toBytes();
        byte[] bkeys = this.keys.toBytes();
        byte[] bcurrencyId = this.currencyId.getBytes();

        return Util.concatByteArray(btoken, btarget, bkeys, bcurrencyId);
    }

    @Override
    public HashMap<String,Object> toDict() {
        HashMap<String, Object> hashMap = new HashMap<>();

        hashMap.put("_hint", this.hint.getHint());
        hashMap.put("hash", this.hash.getSha3Hash());
        hashMap.put("token", Base64.getEncoder().encodeToString(this.token.getISO().getBytes()));
        hashMap.put("target", this.target.getAddress());
        hashMap.put("keys", this.keys.toDict());
        hashMap.put("currency", this.currencyId);

        return hashMap;
    }
}