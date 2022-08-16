package org.mitumc.sdk.operation.nft;

import org.mitumc.sdk.Constant;
import org.mitumc.sdk.operation.base.GeneralOperationFact;
import org.mitumc.sdk.util.Hint;
import org.mitumc.sdk.util.Util;

public class BurnFact extends GeneralOperationFact<BurnItem> {
    BurnFact(String sender, BurnItem[] items) throws Exception {
        super(Constant.MNFT_BURN_OPERATION_FACT, sender, items);
    }

    @Override
    public Hint getOperationHint() throws Exception {
        try {
            return Hint.get(Constant.MNFT_BURN_OPERATION);
        } catch (Exception e) {
            throw new Exception(
                    Util.linkErrMsgs(
                            Util.errMsg("failed to get operation hint", Util.getName()),
                            e.getMessage()));
        }
    }
}
