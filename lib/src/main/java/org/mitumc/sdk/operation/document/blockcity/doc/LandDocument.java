package org.mitumc.sdk.operation.document.blockcity.doc;

import java.util.HashMap;

import org.mitumc.sdk.Constant;
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
            String rentDate, int period) throws Exception {
        super(SingleInfo.land(documentId), owner);
        assertInfo(info);
        this.address = address;
        this.area = area;
        this.renter = renter;
        this.account = Address.get(account);
        this.rentDate = rentDate;
        this.period = new BigInt("" + period);
    }

    private void assertInfo(Info info) throws Exception {
        if (!info.getDocType().equals(Constant.MBC_DOCTYPE_LAND_DATA)) {
            throw new Exception(Util.errMsg("invalid doctype", Util.getName()));
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
    public HashMap<String, Object> toDict() throws Exception {
        HashMap<String, Object> hashMap = new HashMap<>();

        hashMap.put("_hint", this.hint.getHint());
        hashMap.put("owner", this.owner.getAddress());
        hashMap.put("address", this.address);
        hashMap.put("area", this.area);
        hashMap.put("renter", this.renter);
        hashMap.put("account", this.account.getAddress());
        hashMap.put("rentdate", this.rentDate);
        hashMap.put("periodday", Integer.parseInt(this.period.getValue()));

        try {
            hashMap.put("info", this.info.toDict());
        } catch (Exception e) {
            throw new Exception(
                    Util.linkErrMsgs(
                            Util.errMsg("failed to convert land document to hashmap", Util.getName()),
                            e.getMessage()));
        }

        return hashMap;
    }
}