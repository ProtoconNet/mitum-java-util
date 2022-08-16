package org.mitumc.sdk.key;

import org.mitumc.sdk.interfaces.BytesConvertible;
import org.mitumc.sdk.util.RegExp;
import org.mitumc.sdk.util.Util;

public class Address implements BytesConvertible {
    private String address;

    private Address(String address) throws Exception {
        RegExp.assertAddress(address);
        this.address = address;
    }

    public static Address get(String address) throws Exception {
        try {
            return new Address(address);
        } catch (Exception e) {
            throw new Exception(Util.errMsg("failed to create address", Util.getName()));
        }
    }

    public byte[] toBytes() {
        return this.address.getBytes();
    }

    public String getAddress() {
        return this.address;
    }
}