package org.mitumc.sdk.operation.document.blockcity.info;

import org.mitumc.sdk.Constant;
import org.mitumc.sdk.operation.document.blockcity.info.base.BlockCityInfo;

public class UserInfo extends BlockCityInfo {
    public UserInfo(String documentId) {
        super(Constant.MBC_DOCTYPE_USER_DATA, documentId);
    }
}
