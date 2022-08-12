package org.mitumc.sdk.operation.document.blockcity;

import java.util.HashMap;

import org.mitumc.sdk.Constant;
import org.mitumc.sdk.interfaces.BytesConvertible;
import org.mitumc.sdk.interfaces.HashMapConvertible;
import org.mitumc.sdk.util.BigInt;
import org.mitumc.sdk.util.Hint;
import org.mitumc.sdk.util.Util;

public class UserStatistics implements BytesConvertible, HashMapConvertible {
    private Hint hint;
    private BigInt hp;
    private BigInt str;
    private BigInt agi;
    private BigInt dex;
    private BigInt cha;
    private BigInt intel;
    private BigInt vital;

    UserStatistics(int hp, int str, int agi, int dex, int cha, int intel, int vital) {
        this.hint = Hint.get(Constant.MBC_USER_STATISTICS);
        
        this.hp = new BigInt("" + hp);
        this.str = new BigInt("" + str);
        this.agi = new BigInt("" + agi);
        this.dex = new BigInt("" + dex);
        this.cha = new BigInt("" + cha);
        this.intel = new BigInt("" + intel);
        this.vital = new BigInt("" + vital);
    }

    @Override
    public byte[] toBytes() {
        byte[] bhp = this.hp.toBytes();
        byte[] bstr = this.str.toBytes();
        byte[] bagi = this.agi.toBytes();
        byte[] bdex = this.dex.toBytes();
        byte[] bcha = this.cha.toBytes();
        byte[] bintel = this.intel.toBytes();
        byte[] bvital = this.vital.toBytes();

        return Util.concatByteArray(bhp, bstr, bagi, bdex, bcha, bintel, bvital);
    }

    @Override
    public HashMap<String, Object> toDict() {
        HashMap<String, Object> hashMap = new HashMap<>();

        hashMap.put("_hint", this.hint.getHint());
        hashMap.put("hp", Integer.parseInt(this.hp.getValue()));
        hashMap.put("strength", Integer.parseInt(this.str.getValue()));
        hashMap.put("agility", Integer.parseInt(this.agi.getValue()));
        hashMap.put("dexterity", Integer.parseInt(this.dex.getValue()));
        hashMap.put("charisma", Integer.parseInt(this.cha.getValue()));
        hashMap.put("intelligence", Integer.parseInt(this.intel.getValue()));
        hashMap.put("vital", Integer.parseInt(this.vital.getValue()));

        return hashMap;
    }
}