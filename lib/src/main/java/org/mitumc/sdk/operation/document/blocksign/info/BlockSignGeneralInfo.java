package org.mitumc.sdk.operation.document.blocksign.info;

import org.mitumc.sdk.Constant;
import org.mitumc.sdk.operation.document.blocksign.info.base.BlockSignInfo;

public class BlockSignGeneralInfo extends BlockSignInfo {
    private BlockSignGeneralInfo(String documentId) {
        super(Constant.MBS_DOCTYPE_DOCUMENT_DATA, documentId);
    }

    public static BlockSignGeneralInfo get(String documentId) {
        return new BlockSignGeneralInfo(documentId);
    }
}
