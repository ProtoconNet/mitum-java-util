package org.mitumc.sdk.operation;

public class OperationGenerator {
    private String id;
    
    protected OperationGenerator(String id) {
        this.id = id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
