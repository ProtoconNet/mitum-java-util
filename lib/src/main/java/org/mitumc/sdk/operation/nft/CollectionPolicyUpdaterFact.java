package org.mitumc.sdk.operation.nft;

import java.util.Base64;
import java.util.HashMap;

import org.mitumc.sdk.Constant;
import org.mitumc.sdk.key.Address;
import org.mitumc.sdk.operation.base.PurposedOperationFact;
import org.mitumc.sdk.operation.nft.base.CollectionPolicy;
import org.mitumc.sdk.util.Hint;
import org.mitumc.sdk.util.Util;

public class CollectionPolicyUpdaterFact extends PurposedOperationFact {
    private Address sender;
    private String collection;
    private CollectionPolicy policy;
    private String currencyId;

    CollectionPolicyUpdaterFact(String sender, String collection, CollectionPolicy policy, String currencyId)
            throws Exception {
        super(Constant.MNFT_COLLECTION_POLICY_UPDATER_OPERATION_FACT);
        this.sender = Address.get(sender);
        this.collection = collection;
        this.policy = policy;
        this.currencyId = currencyId;

        this.generateHash();
    }

    @Override
    public Hint getOperationHint() throws Exception {
        try {
            return Hint.get(Constant.MNFT_COLLECTION_POLICY_UPDATER_OPERATION);
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
        byte[] bcollection = this.collection.getBytes();
        byte[] bpolicy = null;
        byte[] bcurrencyId = this.currencyId.getBytes();

        try {
            bpolicy = this.policy.toBytes();
        } catch (Exception e) {
            throw new Exception(
                    Util.linkErrMsgs(
                            Util.errMsg("failed to convert collection policy updater fact to bytes", Util.getName()),
                            e.getMessage()));
        }

        return Util.concatByteArray(btoken, bsender, bcollection, bpolicy, bcurrencyId);
    }

    @Override
    public HashMap<String, Object> toDict() {
        HashMap<String, Object> map = new HashMap<>();

        map.put("_hint", this.hint.getHint());
        map.put("hash", this.hash.getSha3Hash());
        map.put("token", Base64.getEncoder().encodeToString(this.token.getISO().getBytes()));
        map.put("sender", this.sender.getAddress());
        map.put("collection", this.collection);
        map.put("policy", this.policy.toDict());
        map.put("currency", this.currencyId);

        return map;
    }
}
