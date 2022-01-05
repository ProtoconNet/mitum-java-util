package org.mitumc.sdk.key;

import org.mitumc.sdk.interfaces.BytesChangeable;

public class Address implements BytesChangeable {
    private String address;

    public Address(String address) {
        this.address = address;
    }

    public byte[] toBytes() {
        return this.address.getBytes();
    }

    public String getAddress() {
        return this.address;
    }
}