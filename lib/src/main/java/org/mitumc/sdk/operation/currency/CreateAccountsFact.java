package org.mitumc.sdk.operation.currency;

import org.mitumc.sdk.Constant;
import org.mitumc.sdk.operation.base.GeneralOperationFact;
import org.mitumc.sdk.util.Hint;

public class CreateAccountsFact extends GeneralOperationFact<CreateAccountsItem> {
    CreateAccountsFact(String sender, CreateAccountsItem[] items) {
        super(Constant.MC_CREATE_ACCOUNTS_OPERATION_FACT, sender, items);
        generateHash();
    }

    @Override
    public Hint getOperationHint() {
        return new Hint(Constant.MC_CRAETE_ACCOUNTS_OPERATION);
    }
}