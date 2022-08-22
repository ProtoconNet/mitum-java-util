package org.mitumc.sdk.operation;

import org.mitumc.sdk.operation.base.ID;

public class ContractID extends ID {
    private ContractID(String id) {
        super(id);
    }

    public static ContractID get(String id) {
        return new ContractID(id);
    }
}
