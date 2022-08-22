package org.mitumc.sdk.operation.nft;

import java.util.Base64;
import java.util.HashMap;

import org.mitumc.sdk.Constant;
import org.mitumc.sdk.key.Address;
import org.mitumc.sdk.operation.ContractID;
import org.mitumc.sdk.operation.CurrencyID;
import org.mitumc.sdk.operation.base.PurposedOperationFact;
import org.mitumc.sdk.operation.nft.base.CollectionPolicy;
import org.mitumc.sdk.util.Hint;
import org.mitumc.sdk.util.Util;

public class CollectionPolicyUpdaterFact extends PurposedOperationFact {
    private Address sender;
    private ContractID collection;
    private CollectionPolicy policy;
    private CurrencyID currency;

    CollectionPolicyUpdaterFact(String sender, String collection, CollectionPolicy policy, String currency) {
        super(Constant.MNFT_COLLECTION_POLICY_UPDATER_OPERATION_FACT);
        this.sender = Address.get(sender);
        this.collection = ContractID.get(collection);
        this.policy = policy;
        this.currency = CurrencyID.get(currency);

        this.generateHash();
    }

    @Override
    public Hint getOperationHint() {
        return Hint.get(Constant.MNFT_COLLECTION_POLICY_UPDATER_OPERATION);
    }

    @Override
    public byte[] toBytes() {
        byte[] btoken = this.token.getISO().getBytes();
        byte[] bsender = this.sender.toBytes();
        byte[] bcollection = this.collection.toBytes();
        byte[] bpolicy = this.policy.toBytes();
        byte[] bcurrencyId = this.currency.toBytes();
        return Util.concatByteArray(btoken, bsender, bcollection, bpolicy, bcurrencyId);
    }

    @Override
    public HashMap<String, Object> toDict() {
        HashMap<String, Object> map = new HashMap<>();

        map.put("_hint", this.hint.getHint());
        map.put("hash", this.hash.getSha3Hash());
        map.put("token", Base64.getEncoder().encodeToString(this.token.getISO().getBytes()));
        map.put("sender", this.sender.getAddress());
        map.put("collection", this.collection.toString());
        map.put("policy", this.policy.toDict());
        map.put("currency", this.currency.toString());

        return map;
    }
}
