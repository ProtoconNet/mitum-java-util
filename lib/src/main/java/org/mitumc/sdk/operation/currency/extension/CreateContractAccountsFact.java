package org.mitumc.sdk.operation.currency.extension;

import org.mitumc.sdk.Constant;
import org.mitumc.sdk.operation.base.GeneralOperationFact;
import org.mitumc.sdk.util.Hint;

public class CreateContractAccountsFact extends GeneralOperationFact<CreateContractAccountsItem> {
    CreateContractAccountsFact(String sender, CreateContractAccountsItem[] items) {
        super(Constant.MC_EXT_CREATE_CONTRACT_ACCOUNTS_OPERATION_FACT, sender, items);
    }

    @Override
    public Hint getOperationHint() {
        return Hint.get(Constant.MC_EXT_CREATE_CONTRACT_ACCOUNTS_OPERATION);
    }
}