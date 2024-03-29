package org.mitumc.sdk.key;

import org.mitumc.sdk.interfaces.BytesConvertible;
import org.mitumc.sdk.util.RegExp;

public class Address implements BytesConvertible {
    private String address;

    private Address(String address) {
        RegExp.assertAddress(address);
        this.address = address;
    }

    public static Address get(String address) {
        return new Address(address);
    }

    public byte[] toBytes() {
        return this.address.getBytes();
    }

    public String getAddress() {
        return this.address;
    }
}