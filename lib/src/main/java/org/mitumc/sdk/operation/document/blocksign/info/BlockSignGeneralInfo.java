package org.mitumc.sdk.operation.document.blocksign.info;

import org.mitumc.sdk.Constant;
import org.mitumc.sdk.operation.document.blocksign.info.base.BlockSignInfo;

public class BlockSignGeneralInfo extends BlockSignInfo {
    public BlockSignGeneralInfo(String documentId) {
        super(Constant.MBS_DOCTYPE_DOCUMENT_DATA, documentId);
    }
}
