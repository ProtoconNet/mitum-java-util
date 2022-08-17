package org.mitumc.sdk.operation.nft;

import org.mitumc.sdk.Constant;
import org.mitumc.sdk.util.Hint;
import org.mitumc.sdk.util.Util;
import org.mitumc.sdk.operation.base.GeneralOperationFact;

public class ApproveFact extends GeneralOperationFact<ApproveItem> {
    ApproveFact(String sender, ApproveItem[] items) {
        super(Constant.MNFT_APPROVE_OPERATION_FACT, sender, items);
    }

    @Override
    public Hint getOperationHint() {
        return Hint.get(Constant.MNFT_APPROVE_OPERATION);
    }
}
