package org.mitumc.sdk.operation.nft;

import org.mitumc.sdk.Constant;
import org.mitumc.sdk.operation.base.GeneralOperationFact;
import org.mitumc.sdk.util.Hint;

public class BurnFact extends GeneralOperationFact<BurnItem>{
    BurnFact(String sender, BurnItem[] items) {
        super(Constant.MNFT_BURN_OPERATION_FACT, sender, items);
    }

    @Override
    public Hint getOperationHint() {
        return Hint.get(Constant.MNFT_BURN_OPERATION);
    }
}
