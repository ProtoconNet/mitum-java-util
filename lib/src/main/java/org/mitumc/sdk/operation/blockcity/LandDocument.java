package org.mitumc.sdk.operation.blockcity;

import java.util.HashMap;

import org.mitumc.sdk.Constant;
import org.mitumc.sdk.key.Address;
import org.mitumc.sdk.util.BigInt;
import org.mitumc.sdk.util.Util;

public class LandDocument extends Document {
    private Address lender;
    private String startTime;
    private BigInt period;

    LandDocument(Info info, String owner, String lender, String startTime, int period) {
        super(Constant.MBC_DOCTYPE_LAND_DATA, info, owner);

        this.lender = new Address(lender);
        this.startTime = startTime;
        this.period = new BigInt("" + period);
    }

    @Override
    public byte[] toBytes() {
        byte[] binfo = this.info.toBytes();
        byte[] bowner = this.owner.toBytes();
        byte[] blender = this.lender.toBytes();
        byte[] bstartTime = this.startTime.getBytes();
        byte[] bperiod = this.period.toBytes();
        return Util.concatByteArray(binfo, bowner, blender, bstartTime, bperiod);
    }

    @Override
    public HashMap<String, Object> toDict() {
        HashMap<String, Object> hashMap = new HashMap<>();

        hashMap.put("_hint", this.hint.getHint());
        hashMap.put("info", this.info.toDict());
        hashMap.put("owner", this.owner.getAddress());
        hashMap.put("lender", this.lender.getAddress());
        hashMap.put("starttime", this.startTime);
        hashMap.put("periodday", Integer.parseInt(this.period.getValue()));

        return hashMap;
    }
}