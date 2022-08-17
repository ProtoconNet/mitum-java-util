package org.mitumc.sdk.operation.nft;

import org.mitumc.sdk.Constant;
import org.mitumc.sdk.operation.base.GeneralOperationFact;
import org.mitumc.sdk.util.Hint;

public class MintFact extends GeneralOperationFact<MintItem> {
    MintFact(String sender, MintItem[] items) {
        super(Constant.MNFT_MINT_OPERATION_FACT, sender, items);
    }

    @Override
    public Hint getOperationHint() {
        return Hint.get(Constant.MNFT_MINT_OPERATION);
    }
}
