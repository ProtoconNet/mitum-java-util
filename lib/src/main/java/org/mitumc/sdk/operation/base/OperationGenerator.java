package org.mitumc.sdk.operation.base;

import org.mitumc.sdk.interfaces.IdSettable;

public class OperationGenerator implements IdSettable {
    private String id;
    
    protected OperationGenerator(String id) {
        this.id = id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String getId() {
        return this.id;
    }
}
