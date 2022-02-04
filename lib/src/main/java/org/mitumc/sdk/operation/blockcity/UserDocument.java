package org.mitumc.sdk.operation.blockcity;

import java.util.HashMap;

import org.mitumc.sdk.Constant;
import org.mitumc.sdk.util.BigInt;

public class UserDocument extends Document {
    private BigInt gold;
    private BigInt bankGold;
    private UserStatistics statistics;

    UserDocument(Info info, String owner, String gold, String bankGold, UserStatistics statistics) {
        super(Constant.MBC_DOCTYPE_USER_DATA, info, owner);
        
        this.gold = new BigInt(gold);
        this.bankGold = new BigInt(bankGold);
        this.statistics = statistics;
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
