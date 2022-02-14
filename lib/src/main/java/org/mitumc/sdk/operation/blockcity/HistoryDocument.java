package org.mitumc.sdk.operation.blockcity;

import java.util.HashMap;

import org.mitumc.sdk.Constant;
import org.mitumc.sdk.key.Address;
import org.mitumc.sdk.util.Util;

public class HistoryDocument extends Document {
    private Info info;
    private Address owner;
    private String name;
    private Address account;
    private String date;
    private String usage;
    private String app;

    HistoryDocument(Info info, String owner, String name, String account, String date, String usage, String app) {
        super(Constant.MBC_DOCTYPE_HISTORY_DATA, info, owner);
        assertInfo(info);
        this.info = info;
        this.owner = new Address(owner);
        this.name = name;
        this.account = new Address(account);
        this.date = date;
        this.usage = usage;
        this.app = app;
    }

    private void assertInfo(Info info) {
        if(!info.getDocType().equals(DOCTYPE_HISTORY_DATA)) {
            Util.raiseError("Invalid docType of Info; HistoryDocument.");
        }
    }

    @Override
    public byte[] toBytes() {
        byte[] binfo = this.info.toBytes();
        byte[] bowner = this.owner.toBytes();
        byte[] bname = this.name.getBytes();
        byte[] baccount = this.account.toBytes();
        byte[] bdate = this.date.getBytes();
        byte[] busage = this.usage.getBytes();
        byte[] bapp = this.app.getBytes();

        return Util.concatByteArray(binfo, bowner, bname, baccount, bdate, busage, bapp);
    }

    @Override
    public HashMap<String, Object> toDict() {
        HashMap<String, Object> hashMap = new HashMap<>();

        hashMap.put("_hint", this.hint.getHint());
        hashMap.put("info", this.info.toDict());
        hashMap.put("owner", this.owner.getAddress());
        hashMap.put("name", this.name);
        hashMap.put("account", this.account.getAddress());
        hashMap.put("date", this.date);
        hashMap.put("usage", this.usage);
        hashMap.put("application", this.app);

        return hashMap;
    }
}