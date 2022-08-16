package org.mitumc.sdk.operation.document.blocksign;

import java.util.HashMap;

import org.mitumc.sdk.Constant;
import org.mitumc.sdk.interfaces.BytesConvertible;
import org.mitumc.sdk.interfaces.HashMapConvertible;
import org.mitumc.sdk.key.Address;
import org.mitumc.sdk.util.Hint;
import org.mitumc.sdk.util.Util;

public class BlockSignUser implements BytesConvertible, HashMapConvertible {
    private Hint hint;
    private Address address;
    private String signCode;
    private boolean signed;

    BlockSignUser(String address, String signCode, boolean signed) throws Exception {
        this.hint = Hint.get(Constant.MBS_USER);
        this.address = Address.get(address);
        this.signCode = signCode;
        this.signed = signed;
    }

    public Address getAddress() {
        return this.address;
    }

    @Override
    public byte[] toBytes() {
        byte[] baddress = this.address.toBytes();
        byte[] bsignCode = this.signCode.getBytes();
        byte[] bsigned = this.signed ? new byte[] { 1 } : new byte[] { 0 };
        return Util.concatByteArray(baddress, bsignCode, bsigned);
    }

    @Override
    public HashMap<String, Object> toDict() {
        HashMap<String, Object> hashMap = new HashMap<>();

        hashMap.put("_hint", this.hint.getHint());
        hashMap.put("address", this.address.getAddress());
        hashMap.put("signcode", this.signCode);
        hashMap.put("signed", this.signed);

        return hashMap;
    }
}
