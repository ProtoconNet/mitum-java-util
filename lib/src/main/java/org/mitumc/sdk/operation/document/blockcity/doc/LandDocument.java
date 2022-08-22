package org.mitumc.sdk.operation.document.blockcity.doc;

import java.util.HashMap;

import org.mitumc.sdk.Constant;
import org.mitumc.sdk.exception.NumberRangeException;
import org.mitumc.sdk.exception.StringFormatException;
import org.mitumc.sdk.key.Address;
import org.mitumc.sdk.operation.document.base.Document;
import org.mitumc.sdk.operation.document.base.Info;
import org.mitumc.sdk.operation.document.blockcity.info.SingleInfo;
import org.mitumc.sdk.util.BigInt;
import org.mitumc.sdk.util.Util;

public class LandDocument extends Document {
    private String address;
    private String area;
    private String renter;
    private Address account;
    private String rentDate;
    private BigInt period;

    LandDocument(String documentId, String owner, String address, String area, String renter, String account,
            String rentDate, int period) {
        super(SingleInfo.land(documentId), owner);
        assertInfoValid(info);
        assertPeriodValidRange(period);
        this.address = address;
        this.area = area;
        this.renter = renter;
        this.account = Address.get(account);
        this.rentDate = rentDate;
        this.period = BigInt.fromInt(period);
    }

    private static void assertInfoValid(Info info) {
        if (!info.getDocType().equals(Constant.MBC_DOCTYPE_LAND_DATA)) {
            throw new StringFormatException(Util.errMsg("invalid doctype", Util.getName()));
        }
    }

    private static void assertPeriodValidRange(int period) {
        if (period < 0) {
            throw new NumberRangeException(Util.errMsg("invalid period - now, " + period, Util.getName()));
        }
    }

    @Override
    public byte[] toBytes() {
        byte[] binfo = this.info.toBytes();
        byte[] bowner = this.owner.toBytes();
        byte[] baddress = this.address.getBytes();
        byte[] barea = this.area.getBytes();
        byte[] brenter = this.renter.getBytes();
        byte[] baccount = this.account.toBytes();
        byte[] brentDate = this.rentDate.getBytes();
        byte[] bperiod = this.period.toBytes();

        return Util.concatByteArray(binfo, bowner, baddress, barea, brenter, baccount, brentDate, bperiod);
    }

    @Override
    public HashMap<String, Object> toDict() {
        HashMap<String, Object> hashMap = new HashMap<>();

        hashMap.put("_hint", this.hint.getHint());
        hashMap.put("owner", this.owner.getAddress());
        hashMap.put("address", this.address);
        hashMap.put("area", this.area);
        hashMap.put("renter", this.renter);
        hashMap.put("account", this.account.getAddress());
        hashMap.put("rentdate", this.rentDate);
        hashMap.put("periodday", Integer.parseInt(this.period.getValue()));
        hashMap.put("info", this.info.toDict());

        return hashMap;
    }
}