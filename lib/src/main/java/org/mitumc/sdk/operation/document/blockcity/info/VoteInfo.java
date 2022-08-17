package org.mitumc.sdk.operation.document.blockcity.info;

import org.mitumc.sdk.Constant;
import org.mitumc.sdk.operation.document.blockcity.info.base.BlockCityInfo;

public class VoteInfo extends BlockCityInfo {
    VoteInfo(String documentId) {
        super(Constant.MBC_DOCTYPE_VOTE_DATA, documentId);
    }
}
