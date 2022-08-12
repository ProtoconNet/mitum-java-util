package org.mitumc.sdk.operation.currency;

import org.mitumc.sdk.Constant;
import org.mitumc.sdk.util.Hint;
import org.mitumc.sdk.operation.base.GeneralOperationFact;


public class TransfersFact extends GeneralOperationFact<TransfersItem> {
    TransfersFact(String sender, TransfersItem[] items) {
        super(Constant.MC_TRANSFERS_OPERATION_FACT, sender, items);
    }

    @Override
    public Hint getOperationHint() {
        return Hint.get(Constant.MC_TRANSFERS_OPERATION);
    }
}