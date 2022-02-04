package org.mitumc.sdk.operation.blockcity;

import java.util.HashMap;

import org.mitumc.sdk.Constant;
import org.mitumc.sdk.key.Address;
import org.mitumc.sdk.util.BigInt;

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
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public HashMap<String, Object> toDict() {
        // TODO Auto-generated method stub
        return null;
    }
}
