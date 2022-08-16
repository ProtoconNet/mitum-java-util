package org.mitumc.sdk.operation.document.blocksign;

import org.mitumc.sdk.operation.base.BaseGenerator;
import org.mitumc.sdk.operation.document.base.Document;
import org.mitumc.sdk.operation.document.blocksign.doc.BlockSignDocument;
import org.mitumc.sdk.util.Util;

public class BlockSignGenerator extends BaseGenerator {
    public static BlockSignGenerator get() {
        return new BlockSignGenerator();
    }

    public static BlockSignUser user(String address, String signCode, boolean signed) throws Exception {
        try {
            return new BlockSignUser(address, signCode, signed);
        } catch (Exception e) {
            throw new Exception(
                    Util.linkErrMsgs(
                            Util.errMsg("failed to create block sign user", Util.getName()),
                            e.getMessage()));
        }
    }

    public static Document document(String documentId, String owner, String fileHash, BlockSignUser creator,
            String title, String size, BlockSignUser[] signers) throws Exception {
        try {
            return BlockSignDocument.get(documentId, owner, fileHash, creator, title, size, signers);
        } catch (Exception e) {
            throw new Exception(
                    Util.linkErrMsgs(
                            Util.errMsg("failed to create block sign documents", Util.getName()),
                            e.getMessage()));
        }
    }

    public static SignDocumentsItem getSignDocumentsItem(String documentId, String owner, String currencyId)
            throws Exception {
        try {
            return new SignDocumentsItem(documentId, owner, currencyId);
        } catch (Exception e) {
            throw new Exception(
                    Util.linkErrMsgs(
                            Util.errMsg("failed to create sign documents item", Util.getName()),
                            e.getMessage()));
        }
    }

    public static SignDocumentsFact getSignDocumentsFact(String sender, SignDocumentsItem[] items) throws Exception {
        try {
            return new SignDocumentsFact(sender, items);
        } catch (Exception e) {
            throw new Exception(
                    Util.linkErrMsgs(
                            Util.errMsg("failed to create sign documents fact", Util.getName()),
                            e.getMessage()));
        }
    }
}