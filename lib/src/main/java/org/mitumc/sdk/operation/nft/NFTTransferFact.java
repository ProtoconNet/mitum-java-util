package org.mitumc.sdk.operation.nft;

import org.mitumc.sdk.Constant;
import org.mitumc.sdk.operation.base.GeneralOperationFact;
import org.mitumc.sdk.util.Hint;

public class NFTTransferFact extends GeneralOperationFact<NFTTransferItem>{
    NFTTransferFact(String sender, NFTTransferItem[] items) {
        super(Constant.MNFT_TRANSFER_OPERATION_FACT, sender, items);
    }

    @Override
    public Hint getOperationHint() {
        return Hint.get(Constant.MNFT_TRANSFER_OPERATION);
    }
}
