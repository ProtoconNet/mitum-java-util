package org.mitumc.sdk.operation.nft;

import org.mitumc.sdk.operation.base.BaseGenerator;
import org.mitumc.sdk.operation.nft.base.CollectionPolicy;
import org.mitumc.sdk.operation.nft.base.NFTID;
import org.mitumc.sdk.operation.nft.base.NFTSigner;
import org.mitumc.sdk.operation.nft.base.NFTSigners;
import org.mitumc.sdk.util.Util;

public class NFTGenerator extends BaseGenerator {
    public static NFTGenerator get() {
        return new NFTGenerator();
    }

    public static NFTSigner signer(String account, int share, boolean signed) throws Exception {
        try {
            return NFTSigner.get(account, share, signed);
        } catch (Exception e) {
            throw new Exception(
                    Util.linkErrMsgs(
                            Util.errMsg("failed to create nft signer", Util.getName()),
                            e.getMessage()));
        }
    }

    public static NFTSigners signers(int total, NFTSigner[] signers) throws Exception {
        try {
            return NFTSigners.get(total, signers);
        } catch (Exception e) {
            throw new Exception(
                    Util.linkErrMsgs(
                            Util.errMsg("failed to create nft signers", Util.getName()),
                            e.getMessage()));
        }
    }

    public static CollectionRegisterForm collectionRegisterForm(String target, String symbol, String name, int royalty,
            String uri, String[] whites) throws Exception {
        try {
            return new CollectionRegisterForm(target, symbol, name, royalty, uri, whites);
        } catch (Exception e) {
            throw new Exception(
                    Util.linkErrMsgs(
                            Util.errMsg("failed to create collection register form", Util.getName()),
                            e.getMessage()));
        }
    }

    public static CollectionPolicy collectionPolicy(String name, int royalty, String uri, String[] whites)
            throws Exception {
        try {
            return CollectionPolicy.get(name, royalty, uri, whites);
        } catch (Exception e) {
            throw new Exception(
                    Util.linkErrMsgs(
                            Util.errMsg("failed to create collection policy", Util.getName()),
                            e.getMessage()));
        }
    }

    public static MintForm mintForm(String hash, String uri, NFTSigners creators, NFTSigners copyrighters)
            throws Exception {
        try {
            return new MintForm(hash, uri, creators, copyrighters);
        } catch (Exception e) {
            throw new Exception(
                    Util.linkErrMsgs(
                            Util.errMsg("failed to create mint form", Util.getName()),
                            e.getMessage()));
        }
    }

    public static MintItem getMintItem(String collection, MintForm form, String currencyId) throws Exception {
        try {
            return new MintItem(collection, form, currencyId);
        } catch (Exception e) {
            throw new Exception(
                    Util.linkErrMsgs(
                            Util.errMsg("failed to create mint item", Util.getName()),
                            e.getMessage()));
        }
    }

    public static NFTTransferItem getTransferItem(String receiver, NFTID nid, String currencyId) throws Exception {
        try {
            return new NFTTransferItem(receiver, nid, currencyId);
        } catch (Exception e) {
            throw new Exception(
                    Util.linkErrMsgs(
                            Util.errMsg("failed to create nft transfer item", Util.getName()),
                            e.getMessage()));
        }
    }

    public static BurnItem getBurnItem(NFTID nid, String currencyId) throws Exception {
        try {
            return new BurnItem(nid, currencyId);
        } catch (Exception e) {
            throw new Exception(
                    Util.linkErrMsgs(
                            Util.errMsg("failed to create burn item", Util.getName()),
                            e.getMessage()));
        }
    }

    public static NFTSignItem getSignItem(String qualification, NFTID nid, String currencyId) throws Exception {
        try {
            return new NFTSignItem(qualification, nid, currencyId);
        } catch (Exception e) {
            throw new Exception(
                    Util.linkErrMsgs(
                            Util.errMsg("failed to create nft sign item", Util.getName()),
                            e.getMessage()));
        }
    }

    public static ApproveItem getApproveItem(String approved, NFTID nid, String currencyId) throws Exception {
        try {
            return new ApproveItem(approved, nid, currencyId);
        } catch (Exception e) {
            throw new Exception(
                    Util.linkErrMsgs(
                            Util.errMsg("failed to create approve item", Util.getName()),
                            e.getMessage()));
        }
    }

    public static DelegateItem getDelegateItem(String collection, String agent, String mode, String currencyId)
            throws Exception {
        try {
            return new DelegateItem(collection, agent, mode, currencyId);
        } catch (Exception e) {
            throw new Exception(
                    Util.linkErrMsgs(
                            Util.errMsg("failed to create delegate item", Util.getName()),
                            e.getMessage()));
        }
    }

    public static CollectionRegisterFact getCollectionRegisterFact(String sender, CollectionRegisterForm form,
            String currencyId) throws Exception {
        try {
            return new CollectionRegisterFact(sender, form, currencyId);
        } catch (Exception e) {
            throw new Exception(
                    Util.linkErrMsgs(
                            Util.errMsg("failed to create collection register fact", Util.getName()),
                            e.getMessage()));
        }
    }

    public static CollectionPolicyUpdaterFact getCollectionPolicyUpdaterFact(String sender, String collection,
            CollectionPolicy policy, String currencyId) throws Exception {
        try {
            return new CollectionPolicyUpdaterFact(sender, collection, policy, currencyId);
        } catch (Exception e) {
            throw new Exception(
                    Util.linkErrMsgs(
                            Util.errMsg("failed to create collection policy updater fact", Util.getName()),
                            e.getMessage()));
        }
    }

    public static MintFact getMintFact(String sender, MintItem[] items) throws Exception {
        try {
            return new MintFact(sender, items);
        } catch (Exception e) {
            throw new Exception(
                    Util.linkErrMsgs(
                            Util.errMsg("failed to create mint fact", Util.getName()),
                            e.getMessage()));
        }
    }

    public static NFTTransferFact getTransferFact(String sender, NFTTransferItem[] items) throws Exception {
        try {
            return new NFTTransferFact(sender, items);
        } catch (Exception e) {
            throw new Exception(
                    Util.linkErrMsgs(
                            Util.errMsg("failed to create nft transfer fact", Util.getName()),
                            e.getMessage()));
        }
    }

    public static BurnFact getBurnFact(String sender, BurnItem[] items) throws Exception {
        try {
            return new BurnFact(sender, items);
        } catch (Exception e) {
            throw new Exception(
                    Util.linkErrMsgs(
                            Util.errMsg("failed to create burn fact", Util.getName()),
                            e.getMessage()));
        }
    }

    public static NFTSignFact getSignFact(String sender, NFTSignItem[] items) throws Exception {
        try {
            return new NFTSignFact(sender, items);
        } catch (Exception e) {
            throw new Exception(
                    Util.linkErrMsgs(
                            Util.errMsg("failed to create nft sign fact", Util.getName()),
                            e.getMessage()));
        }
    }

    public static ApproveFact getApproveFact(String sender, ApproveItem[] items) throws Exception {
        try {
            return new ApproveFact(sender, items);
        } catch (Exception e) {
            throw new Exception(
                    Util.linkErrMsgs(
                            Util.errMsg("failed to create approve fact", Util.getName()),
                            e.getMessage()));
        }
    }

    public static DelegateFact getDelegateFact(String sender, DelegateItem[] items) throws Exception {
        try {
            return new DelegateFact(sender, items);
        } catch (Exception e) {
            throw new Exception(
                    Util.linkErrMsgs(
                            Util.errMsg("failed to create delegate fact", Util.getName()),
                            e.getMessage()));
        }
    }
}
