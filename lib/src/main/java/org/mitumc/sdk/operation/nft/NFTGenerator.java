package org.mitumc.sdk.operation.nft;

import org.mitumc.sdk.operation.base.OperationGenerator;
import org.mitumc.sdk.operation.nft.base.CollectionPolicy;
import org.mitumc.sdk.operation.nft.base.NFTID;
import org.mitumc.sdk.operation.nft.base.NFTSigners;

public class NFTGenerator extends OperationGenerator {
    private NFTGenerator(String id) {
        super(id);
    }

    public static NFTGenerator get(String id) {
        return new NFTGenerator(id);
    }

    public CollectionRegisterForm getCollectionRegisterForm(String target, String symbol, String name, int royalty, String uri, String[] whites) {
        return new CollectionRegisterForm(target, symbol, name, royalty, uri, whites);
    }

    public CollectionPolicy getCollectionPolicy(String name, int royalty, String uri, String[] whites) {
        return new CollectionPolicy(name, royalty, uri, whites);
    }

    public MintForm getMintForm(String hash, String uri, NFTSigners creators, NFTSigners copyrighters) {
        return new MintForm(hash, uri, creators, copyrighters);
    }

    public MintItem getMintItem(String collection, MintForm form, String currencyId) {
        return new MintItem(collection, form, currencyId);
    }

    public NFTTransferItem getTransferItem(String receiver, NFTID nid, String currencyId) {
        return new NFTTransferItem(receiver, nid, currencyId);
    }

    public BurnItem getBurnItem(NFTID nid, String currencyId) {
        return new BurnItem(nid, currencyId);
    }

    public NFTSignItem getSignItem(String qualification, NFTID nid, String currencyId) {
        return new NFTSignItem(qualification, nid, currencyId);
    }

    public ApproveItem getApproveItem(String approved, NFTID nid, String currencyId) {
        return new ApproveItem(approved, nid, currencyId);
    }

    public DelegateItem getDelegateItem(String collection, String agent, String mode, String currencyId) {
        return new DelegateItem(collection, agent, mode, currencyId);
    }

    public CollectionRegisterFact getCollectionRegisterFact(String sender, CollectionRegisterForm form, String currencyId) {
        return new CollectionRegisterFact(sender, form, currencyId);
    }

    public CollectionPolicyUpdaterFact getCollectionPolicyUpdaterFact(String sender, String collection, CollectionPolicy policy, String currencyId) {
        return new CollectionPolicyUpdaterFact(sender, collection, policy, currencyId);
    }
    
    public MintFact getMintFact(String sender, MintItem[] items) {
        return new MintFact(sender, items);
    }

    public NFTTransferFact getTransferFact(String sender, NFTTransferItem[] items) {
        return new NFTTransferFact(sender, items);
    }

    public BurnFact getBurnFact(String sender, BurnItem[] items) {
        return new BurnFact(sender, items);
    }

    public NFTSignFact getSignFact(String sender, NFTSignItem[] items) {
        return new NFTSignFact(sender, items);
    }

    public ApproveFact getApproveFact(String sender, ApproveItem[] items) {
        return new ApproveFact(sender, items);
    }

    public DelegateFact getDelegateFact(String sender, DelegateItem[] items) {
        return new DelegateFact(sender, items);
    }
}
