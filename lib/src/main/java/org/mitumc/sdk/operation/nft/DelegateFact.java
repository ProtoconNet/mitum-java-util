package org.mitumc.sdk.operation.nft;

import org.mitumc.sdk.Constant;
import org.mitumc.sdk.operation.base.GeneralOperationFact;
import org.mitumc.sdk.util.Hint;

public class DelegateFact extends GeneralOperationFact<DelegateItem>{
    DelegateFact(String sender, DelegateItem[] items) {
        super(Constant.MNFT_BURN_OPERATION_FACT, sender, items);
    }

    @Override
    public Hint getOperationHint() {
        return new Hint(Constant.MNFT_BURN_OPERATION);
    }
}
