package org.mitumc.sdk.operation.nft;

import org.mitumc.sdk.Constant;
import org.mitumc.sdk.operation.base.GeneralOperationFact;
import org.mitumc.sdk.util.Hint;
import org.mitumc.sdk.util.Util;

public class NFTSignFact extends GeneralOperationFact<NFTSignItem> {
    NFTSignFact(String sender, NFTSignItem[] items) throws Exception {
        super(Constant.MNFT_SIGN_OPERATION_FACT, sender, items);
    }

    @Override
    public Hint getOperationHint() throws Exception {
        try {
            return Hint.get(Constant.MNFT_SIGN_OPERATION);
        } catch (Exception e) {
            throw new Exception(
                    Util.linkErrMsgs(
                            Util.errMsg("failed to get operation hint", Util.getName()),
                            e.getMessage()));
        }
    }
}
