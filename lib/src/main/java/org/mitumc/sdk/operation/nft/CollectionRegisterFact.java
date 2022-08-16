package org.mitumc.sdk.operation.nft;

import java.util.HashMap;
import java.util.Base64;

import org.mitumc.sdk.Constant;
import org.mitumc.sdk.key.Address;
import org.mitumc.sdk.operation.base.PurposedOperationFact;
import org.mitumc.sdk.util.Hint;
import org.mitumc.sdk.util.Util;

public class CollectionRegisterFact extends PurposedOperationFact {
    private Address sender;
    private CollectionRegisterForm form;
    private String currencyId;

    CollectionRegisterFact(String sender, CollectionRegisterForm form, String currencyId) throws Exception {
        super(Constant.MNFT_COLLECTION_REGISTER_OPERATION_FACT);
        this.sender = Address.get(sender);
        this.form = form;
        this.currencyId = currencyId;

        this.generateHash();
    }

    @Override
    public Hint getOperationHint() throws Exception {
        try {
            return Hint.get(Constant.MNFT_COLLECTION_REGISTER_OPERATION);
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
        byte[] bsender = this.sender.toBytes();
        byte[] bform = null;
        byte[] bcurrencyId = this.currencyId.getBytes();

        try {
            bform = this.form.toBytes();
        } catch (Exception e) {
            throw new Exception(
                    Util.linkErrMsgs(
                            Util.errMsg("failed to convert collection register fact to bytes", Util.getName()),
                            e.getMessage()));
        }

        return Util.concatByteArray(btoken, bsender, bform, bcurrencyId);
    }

    @Override
    public HashMap<String, Object> toDict() {
        HashMap<String, Object> map = new HashMap<>();

        map.put("_hint", this.hint.getHint());
        map.put("hash", this.hash.getSha3Hash());
        map.put("token", Base64.getEncoder().encodeToString(this.token.getISO().getBytes()));
        map.put("sender", this.sender.getAddress());
        map.put("form", this.form.toDict());
        map.put("currency", this.currencyId);

        return map;
    }
}
