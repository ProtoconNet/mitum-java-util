package org.mitumc.sdk.operation;

import org.mitumc.sdk.BytesChangeable;
import org.mitumc.sdk.Constant;
import org.mitumc.sdk.util.Hint;

public class Address implements BytesChangeable {
    private Hint hint;
    private String address;

    Address(String address) {
        this.hint = new Hint(Constant.MC_ADDRESS);
        this.address = address;
    }

    public byte[] toBytes() {
        return this.address.getBytes();
    }

    public String getAddress() {
        return this.address;
    }
}