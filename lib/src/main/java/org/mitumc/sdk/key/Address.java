package org.mitumc.sdk.key;

import org.mitumc.sdk.interfaces.BytesChangeable;
import org.mitumc.sdk.util.RegExp;

public class Address implements BytesChangeable {
    private String address;

    public Address(String address) {
        RegExp.assertAddress(address);
        this.address = address;
    }

    public byte[] toBytes() {
        return this.address.getBytes();
    }

    public String getAddress() {
        return this.address;
    }
}