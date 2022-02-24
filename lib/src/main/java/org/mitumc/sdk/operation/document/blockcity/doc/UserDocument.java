package org.mitumc.sdk.operation.document.blockcity.doc;

import java.util.HashMap;

import org.mitumc.sdk.Constant;
import org.mitumc.sdk.operation.document.Document;
import org.mitumc.sdk.operation.document.base.Info;
import org.mitumc.sdk.operation.document.blockcity.UserStatistics;
import org.mitumc.sdk.operation.document.blockcity.info.UserInfo;
import org.mitumc.sdk.util.BigInt;
import org.mitumc.sdk.util.Util;

public class UserDocument extends Document {
    private BigInt gold;
    private BigInt bankGold;
    private UserStatistics statistics;

    public UserDocument(String documentId, String owner, int gold, int bankGold, UserStatistics statistics) {
        super(new UserInfo(documentId), owner);
        assertInfo(info);
        this.gold = new BigInt("" + gold);
        this.bankGold = new BigInt("" + bankGold);
        this.statistics = statistics;
    }

    private void assertInfo(Info info) {
        if(!info.getDocType().equals(Constant.MBC_DOCTYPE_USER_DATA)) {
            Util.raiseError("Invalid docType of Info; UserDocument.");
        }
    }

    @Override
    public byte[] toBytes() {
        byte[] binfo = this.info.toBytes();
        byte[] bowner = this.owner.toBytes();
        byte[] bgold = this.gold.toBytes();
        byte[] bbankGold = this.bankGold.toBytes();
        byte[] bstatistics = this.statistics.toBytes();
        return Util.concatByteArray(binfo, bowner, bgold, bbankGold, bstatistics);
    }

    @Override
    public HashMap<String, Object> toDict() {
        HashMap<String, Object> hashMap = new HashMap<>();

        hashMap.put("_hint", this.hint.getHint());
        hashMap.put("info", this.info.toDict());
        hashMap.put("owner", this.owner.getAddress());
        hashMap.put("gold", Integer.parseInt(this.gold.getValue())); 
        hashMap.put("bankgold", Integer.parseInt(this.bankGold.getValue()));
        hashMap.put("statistics", this.statistics.toDict());

        return hashMap;
    }
}
