package org.mitumc.sdk.operation.blockcity;

import java.util.HashMap;

import org.mitumc.sdk.Constant;
import org.mitumc.sdk.util.BigInt;
import org.mitumc.sdk.util.Util;

public class UserDocument extends Document {
    private BigInt gold;
    private BigInt bankGold;
    private UserStatistics statistics;

    UserDocument(Info info, String owner, String gold, String bankGold, UserStatistics statistics) {
        super(Constant.MBC_DOCTYPE_USER_DATA, info, owner);
        assertnfo(info);
        this.gold = new BigInt(gold);
        this.bankGold = new BigInt(bankGold);
        this.statistics = statistics;
    }

    private void assertnfo(Info info) {
        if(!info.getDocType().equals(DOCTYPE_USER_DATA)) {
            Util.raiseError("Invalid docType of Info; UserDocument.");
        }
    }

    @Override
    public byte[] toBytes() {
        byte[] binfo = this.info.toBytes();
        byte[] bowner = this.owner.toBytes();
        byte[] bgold = this.gold.toBytes(BigInt.LITTLE_ENDIAN, true);;
        byte[] bbankGold = this.bankGold.toBytes(BigInt.LITTLE_ENDIAN, true);;
        byte[] bstatistics = this.statistics.toBytes();
        return Util.concatByteArray(binfo, bowner, bgold, bbankGold, bstatistics);
    }

    @Override
    public HashMap<String, Object> toDict() {
        HashMap<String, Object> hashMap = new HashMap<>();

        hashMap.put("_hint", this.hint.getHint());
        hashMap.put("info", this.info.toDict());
        hashMap.put("owner", this.owner.getAddress());
        hashMap.put("gold", this.gold.getValue());
        hashMap.put("bankgold", this.bankGold.getValue());
        hashMap.put("statistics", this.statistics.toDict());

        return hashMap;
    }
}
