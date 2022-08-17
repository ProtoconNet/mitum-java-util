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

    public static NFTSigner signer(String account, int share, boolean signed) {
        return NFTSigner.get(account, share, signed);
    }

    public static NFTSigners signers(int total, NFTSigner[] signers) {
        return NFTSigners.get(total, signers);
    }

    public static CollectionRegisterForm collectionRegisterForm(String target, String symbol, String name, int royalty,
            String uri, String[] whites) {
        return new CollectionRegisterForm(target, symbol, name, royalty, uri, whites);
    }

    public static CollectionPolicy collectionPolicy(String name, int royalty, String uri, String[] whites) {
        return CollectionPolicy.get(name, royalty, uri, whites);
    }

    public static MintForm mintForm(String hash, String uri, NFTSigners creators, NFTSigners copyrighters) {
        return new MintForm(hash, uri, creators, copyrighters);
    }

    public static MintItem getMintItem(String collection, MintForm form, String currencyId) {
        return new MintItem(collection, form, currencyId);
    }

    public static NFTTransferItem getTransferItem(String receiver, NFTID nid, String currencyId) {
        return new NFTTransferItem(receiver, nid, currencyId);
    }

    public static BurnItem getBurnItem(NFTID nid, String currencyId) {
        return new BurnItem(nid, currencyId);
    }

    public static NFTSignItem getSignItem(String qualification, NFTID nid, String currencyId) {
        return new NFTSignItem(qualification, nid, currencyId);
    }

    public static ApproveItem getApproveItem(String approved, NFTID nid, String currencyId) {
        return new ApproveItem(approved, nid, currencyId);
    }

    public static DelegateItem getDelegateItem(String collection, String agent, String mode, String currencyId) {
        return new DelegateItem(collection, agent, mode, currencyId);
    }

    public static CollectionRegisterFact getCollectionRegisterFact(String sender, CollectionRegisterForm form,
            String currencyId) {
        return new CollectionRegisterFact(sender, form, currencyId);
    }

    public static CollectionPolicyUpdaterFact getCollectionPolicyUpdaterFact(String sender, String collection,
            CollectionPolicy policy, String currencyId) {
        return new CollectionPolicyUpdaterFact(sender, collection, policy, currencyId);
    }

    public static MintFact getMintFact(String sender, MintItem[] items) {
        return new MintFact(sender, items);
    }

    public static NFTTransferFact getTransferFact(String sender, NFTTransferItem[] items) {
        return new NFTTransferFact(sender, items);
    }

    public static BurnFact getBurnFact(String sender, BurnItem[] items) {
        return new BurnFact(sender, items);
    }

    public static NFTSignFact getSignFact(String sender, NFTSignItem[] items) {
        return new NFTSignFact(sender, items);
    }

    public static ApproveFact getApproveFact(String sender, ApproveItem[] items) {
        return new ApproveFact(sender, items);
    }

    public static DelegateFact getDelegateFact(String sender, DelegateItem[] items) {
        return new DelegateFact(sender, items);
    }
}
