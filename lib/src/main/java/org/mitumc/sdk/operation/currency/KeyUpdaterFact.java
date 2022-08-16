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

    KeyUpdaterFact(String target, String currencyId, Keys keys) throws Exception {
        super(Constant.MC_KEY_UPDATER_OPERATION_FACT);
        this.currencyId = currencyId;
        this.keys = keys;

        try {
            this.target = Address.get(target);
            generateHash();
        } catch (Exception e) {
            throw new Exception(
                    Util.linkErrMsgs(
                            Util.errMsg("failed to create key updater fact", Util.getName()),
                            e.getMessage()));
        }
    }

    @Override
    public Hint getOperationHint() throws Exception {
        try {
            return Hint.get(Constant.MC_KEY_UPDATER_OPERATION);
        } catch (Exception e) {
            throw new Exception(
                    Util.linkErrMsgs(
                            Util.errMsg("failed to get operation hint", Util.getName()),
                            e.getMessage()));
        }
    }

    @Override
    public byte[] toBytes() throws Exception {
        byte[] btoken = this.token.getISO().getBytes();
        byte[] btarget = this.target.toBytes();
        byte[] bkeys = null;
        byte[] bcurrencyId = this.currencyId.getBytes();

        try {
            bkeys = this.keys.toBytes();
        } catch (Exception e) {
            throw new Exception(
                    Util.linkErrMsgs(
                            Util.errMsg("failed to convert key updater fact to bytes", Util.getName()),
                            e.getMessage()));
        }

        return Util.concatByteArray(btoken, btarget, bkeys, bcurrencyId);
    }

    @Override
    public HashMap<String, Object> toDict() {
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