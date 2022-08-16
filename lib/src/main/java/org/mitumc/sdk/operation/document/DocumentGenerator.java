package org.mitumc.sdk.operation.document;

import org.mitumc.sdk.operation.base.BaseGenerator;
import org.mitumc.sdk.operation.document.base.Document;
import org.mitumc.sdk.operation.document.blockcity.BlockCityGenerator;
import org.mitumc.sdk.operation.document.blocksign.BlockSignGenerator;
import org.mitumc.sdk.util.Util;

public class DocumentGenerator extends BaseGenerator {
    public static BlockSignGenerator blocksign = BlockSignGenerator.get();
    public static BlockCityGenerator blockcity = BlockCityGenerator.get();

    public static DocumentGenerator get() {
        return new DocumentGenerator();
    }

    public static CreateDocumentsItem getCreateDocumentsItem(Document document, String currencyId) throws Exception {
        try {
            return new CreateDocumentsItem(document, currencyId);
        } catch (Exception e) {
            throw new Exception(
                    Util.linkErrMsgs(
                            Util.errMsg("failed to create create documents item", Util.getName()),
                            e.getMessage()));
        }
    }

    public static UpdateDocumentsItem getUpdateDocumentsItem(Document document, String currencyId) throws Exception {
        try {
            return new UpdateDocumentsItem(document, currencyId);
        } catch (Exception e) {
            throw new Exception(
                    Util.linkErrMsgs(
                            Util.errMsg("failed to create update documents item", Util.getName()),
                            e.getMessage()));
        }
    }

    public static CreateDocumentsFact getCreateDocumentsFact(String sender, CreateDocumentsItem[] items)
            throws Exception {
        try {
            return new CreateDocumentsFact(sender, items);
        } catch (Exception e) {
            throw new Exception(
                    Util.linkErrMsgs(
                            Util.errMsg("failed to create create documents fact", Util.getName()),
                            e.getMessage()));
        }
    }

    public static UpdateDocumentsFact getUpdateDocumentsFact(String sender, UpdateDocumentsItem[] items)
            throws Exception {
        try {
            return new UpdateDocumentsFact(sender, items);
        } catch (Exception e) {
            throw new Exception(
                    Util.linkErrMsgs(
                            Util.errMsg("failed to create update documents fact", Util.getName()),
                            e.getMessage()));
        }
    }
}
