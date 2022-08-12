package org.mitumc.sdk.operation.nft;

import org.mitumc.sdk.Constant;
import org.mitumc.sdk.operation.base.GeneralOperationFact;
import org.mitumc.sdk.util.Hint;

public class NFTSignFact extends GeneralOperationFact<NFTSignItem>{
    NFTSignFact(String sender, NFTSignItem[] items) {
        super(Constant.MNFT_SIGN_OPERATION_FACT, sender, items);
    }

    @Override
    public Hint getOperationHint() {
        return new Hint(Constant.MNFT_SIGN_OPERATION);
    }
}
