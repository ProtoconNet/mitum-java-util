package org.mitumc.sdk.operation.document.blockcity.info;

import org.mitumc.sdk.Constant;
import org.mitumc.sdk.operation.document.blockcity.info.base.BlockCityInfo;

public class HistoryInfo extends BlockCityInfo {
    public HistoryInfo(String documentId) {
        super(Constant.MBC_DOCTYPE_HISTORY_DATA, documentId);
    }
}
