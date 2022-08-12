package org.mitumc.sdk.operation.document.blockcity.info;

import org.mitumc.sdk.Constant;
import org.mitumc.sdk.operation.document.blockcity.info.base.BlockCityInfo;

public class LandInfo extends BlockCityInfo {
    LandInfo(String documentId) {
        super(Constant.MBC_DOCTYPE_LAND_DATA, documentId);
    }
}
