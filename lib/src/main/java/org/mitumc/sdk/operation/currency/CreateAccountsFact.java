package org.mitumc.sdk.operation.currency;

import org.mitumc.sdk.Constant;
import org.mitumc.sdk.operation.base.GeneralOperationFact;
import org.mitumc.sdk.util.Hint;
import org.mitumc.sdk.util.Util;

public class CreateAccountsFact extends GeneralOperationFact<CreateAccountsItem> {
    CreateAccountsFact(String sender, CreateAccountsItem[] items) throws Exception {
        super(Constant.MC_CREATE_ACCOUNTS_OPERATION_FACT, sender, items);
    }

    @Override
    public Hint getOperationHint() throws Exception {
        try {
            return Hint.get(Constant.MC_CRAETE_ACCOUNTS_OPERATION);
        } catch (Exception e) {
            throw new Exception(
                    Util.linkErrMsgs(
                            Util.errMsg("failed to get operation hint", Util.getName()),
                            e.getMessage()));
        }
    }
}