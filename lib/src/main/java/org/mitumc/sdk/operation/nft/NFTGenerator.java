package org.mitumc.sdk.operation.nft;

import org.mitumc.sdk.operation.base.BaseGenerator;
import org.mitumc.sdk.operation.nft.base.CollectionPolicy;
import org.mitumc.sdk.operation.nft.base.NFTID;
import org.mitumc.sdk.operation.nft.base.NFTSigner;
import org.mitumc.sdk.operation.nft.base.NFTSigners;

public class NFTGenerator extends BaseGenerator {
    public static NFTGenerator get() {
        return new NFTGenerator();
    }

    public NFTSigner signer(String account, int share, boolean signed) {
        return NFTSigner.get(account, share, signed);
    }

    public NFTSigners signers(int total, NFTSigner[] signers) {
        return NFTSigners.get(total, signers);
    }

    public CollectionRegisterForm collectionRegisterForm(String target, String symbol, String name, int royalty,
            String uri, String[] whites) {
        return new CollectionRegisterForm(target, symbol, name, royalty, uri, whites);
    }

    public CollectionPolicy collectionPolicy(String name, int royalty, String uri, String[] whites) {
        return CollectionPolicy.get(name, royalty, uri, whites);
    }

    public MintForm mintForm(String hash, String uri, NFTSigners creators, NFTSigners copyrighters) {
        return new MintForm(hash, uri, creators, copyrighters);
    }

    public MintItem getMintItem(String collection, MintForm form, String currency) {
        return new MintItem(collection, form, currency);
    }

    public NFTTransferItem getTransferItem(String receiver, NFTID nid, String currency) {
        return new NFTTransferItem(receiver, nid, currency);
    }

    public BurnItem getBurnItem(NFTID nid, String currency) {
        return new BurnItem(nid, currency);
    }

    public NFTSignItem getSignItem(String qualification, NFTID nid, String currency) {
        return new NFTSignItem(qualification, nid, currency);
    }

    public ApproveItem getApproveItem(String approved, NFTID nid, String currency) {
        return new ApproveItem(approved, nid, currency);
    }

    public DelegateItem getDelegateItem(String collection, String agent, String mode, String currency) {
        return new DelegateItem(collection, agent, mode, currency);
    }

    public CollectionRegisterFact getCollectionRegisterFact(String sender, CollectionRegisterForm form,
            String currency) {
        return new CollectionRegisterFact(sender, form, currency);
    }

    public CollectionPolicyUpdaterFact getCollectionPolicyUpdaterFact(String sender, String collection,
            CollectionPolicy policy, String currency) {
        return new CollectionPolicyUpdaterFact(sender, collection, policy, currency);
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
