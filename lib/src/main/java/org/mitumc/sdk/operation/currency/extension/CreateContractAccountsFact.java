package org.mitumc.sdk.operation.currency.extension;

import org.mitumc.sdk.Constant;
import org.mitumc.sdk.operation.base.GeneralOperationFact;
import org.mitumc.sdk.util.Hint;
import org.mitumc.sdk.util.Util;

public class CreateContractAccountsFact extends GeneralOperationFact<CreateContractAccountsItem> {
    CreateContractAccountsFact(String sender, CreateContractAccountsItem[] items) throws Exception {
        super(Constant.MC_EXT_CREATE_CONTRACT_ACCOUNTS_OPERATION_FACT, sender, items);
    }

    @Override
    public Hint getOperationHint() throws Exception {
        try {
            return Hint.get(Constant.MC_EXT_CREATE_CONTRACT_ACCOUNTS_OPERATION);
        } catch (Exception e) {
            throw new Exception(
                    Util.linkErrMsgs(
                            Util.errMsg("failed to get operation hint", Util.getName()),
                            e.getMessage()));
        }
    }
}