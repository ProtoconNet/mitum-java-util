package org.mitumc.sdk.operation.document.blockcity.doc;

import java.util.HashMap;

import org.mitumc.sdk.Constant;
import org.mitumc.sdk.operation.document.base.Document;
import org.mitumc.sdk.operation.document.base.Info;
import org.mitumc.sdk.operation.document.blockcity.UserStatistics;
import org.mitumc.sdk.operation.document.blockcity.info.SingleInfo;
import org.mitumc.sdk.util.BigInt;
import org.mitumc.sdk.util.Util;

public class UserDocument extends Document {
    private BigInt gold;
    private BigInt bankGold;
    private UserStatistics statistics;

    UserDocument(String documentId, String owner, int gold, int bankGold, UserStatistics statistics) throws Exception {
        super(SingleInfo.user(documentId), owner);
        assertInfo(info);
        this.gold = new BigInt("" + gold);
        this.bankGold = new BigInt("" + bankGold);
        this.statistics = statistics;
    }

    private void assertInfo(Info info) throws Exception {
        if (!info.getDocType().equals(Constant.MBC_DOCTYPE_USER_DATA)) {
            throw new Exception(Util.errMsg("invalid doctype", Util.getName()));
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
    public HashMap<String, Object> toDict() throws Exception {
        HashMap<String, Object> hashMap = new HashMap<>();

        hashMap.put("_hint", this.hint.getHint());
        hashMap.put("owner", this.owner.getAddress());
        hashMap.put("gold", Integer.parseInt(this.gold.getValue()));
        hashMap.put("bankgold", Integer.parseInt(this.bankGold.getValue()));
        hashMap.put("statistics", this.statistics.toDict());

        try {
            hashMap.put("info", this.info.toDict());
        } catch (Exception e) {
            throw new Exception(
                    Util.linkErrMsgs(
                            Util.errMsg("failed to convert user document to hashmap", Util.getName()),
                            e.getMessage()));
        }

        return hashMap;
    }
}
