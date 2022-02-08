package org.mitumc.sdk.operation.blockcity;

import java.util.HashMap;

import org.mitumc.sdk.Constant;
import org.mitumc.sdk.key.Address;
import org.mitumc.sdk.util.BigInt;
import org.mitumc.sdk.util.Util;

public class LandDocument extends Document {
    private String address;
    private String area;
    private String renter;
    private Address account;
    private String rentDate;
    private BigInt period;

    LandDocument(Info info, String owner, String address, String area, String renter, String account, String rentDate, int period) {
        super(Constant.MBC_DOCTYPE_LAND_DATA, info, owner);

        this.address = address;
        this.area = area;
        this.renter = renter;
        this.account = new Address(account);
        this.rentDate = rentDate;
        this.period = new BigInt("" + period);
    }

    @Override
    public byte[] toBytes() {
        byte[] bInfo = this.info.toBytes();
        byte[] bOwner = this.owner.toBytes();
        byte[] bAddress = this.address.getBytes();
        byte[] bArea = this.area.getBytes();
        byte[] bRenter = this.renter.getBytes();
        byte[] bAccount = this.account.toBytes();
        byte[] bRentDate = this.rentDate.getBytes();
        byte[] bPeriod = this.period.toBytes();

        return Util.concatByteArray(bInfo, bOwner, bAddress, bArea, bRenter, bAccount, bRentDate, bPeriod);
    }

    @Override
    public HashMap<String, Object> toDict() {
        HashMap<String, Object> hashMap = new HashMap<>();

        hashMap.put("_hint", this.hint.getHint());
        hashMap.put("info", this.info.toDict());
        hashMap.put("owner", this.owner.getAddress());
        hashMap.put("address", this.address);
        hashMap.put("area", this.area);
        hashMap.put("renter", this.renter);
        hashMap.put("account", this.account.getAddress());
        hashMap.put("rentdate", this.rentDate);
        hashMap.put("periodday", Integer.parseInt(this.period.getValue()));

        return hashMap;
    }
}