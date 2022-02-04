package org.mitumc.sdk.operation.blockcity;

import java.util.HashMap;

import org.mitumc.sdk.Constant;
import org.mitumc.sdk.interfaces.BytesChangeable;
import org.mitumc.sdk.interfaces.Dictionariable;
import org.mitumc.sdk.util.BigInt;
import org.mitumc.sdk.util.Hint;

public class UserStatistics implements BytesChangeable, Dictionariable {
    private Hint hint;
    private BigInt hp;
    private BigInt str;
    private BigInt agi;
    private BigInt dex;
    private BigInt cha;
    private BigInt intel;
    private BigInt vital;

    UserStatistics(int hp, int str, int agi, int dex, int cha, int intel, int vital) {
        this.hint = new Hint(Constant.MBC_USER_STATISTICS);
        
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
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public HashMap<String, Object> toDict() {
        // TODO Auto-generated method stub
        return null;
    }
}
