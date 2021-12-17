package org.mitumc.sdk.operation;

import org.mitumc.sdk.BytesChangeable;

public class Address implements BytesChangeable {
    private String address;

    Address(String address) {
        this.address = address;
    }

    public byte[] toBytes() {
        return this.address.getBytes();
    }

    public String getAddress() {
        return this.address;
    }
}