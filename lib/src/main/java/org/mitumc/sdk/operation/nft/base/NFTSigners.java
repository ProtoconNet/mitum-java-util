package org.mitumc.sdk.operation.nft.base;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import org.mitumc.sdk.Constant;
import org.mitumc.sdk.exception.NotEnoughtSumException;
import org.mitumc.sdk.exception.NumberLimitExceededException;
import org.mitumc.sdk.exception.NumberRangeException;
import org.mitumc.sdk.interfaces.BytesConvertible;
import org.mitumc.sdk.interfaces.HashMapConvertible;
import org.mitumc.sdk.util.BigInt;
import org.mitumc.sdk.util.Hint;
import org.mitumc.sdk.util.Util;

public class NFTSigners implements BytesConvertible, HashMapConvertible {

    private Hint hint;
    private BigInt total;
    private ArrayList<NFTSigner> signers;

    private NFTSigners(int total, NFTSigner[] signers) {
        assertNumberOfSignersValidRange(signers);
        assertTotalValidRange(total);
        assertShareSumEnough(total, signers);
        this.hint = Hint.get(Constant.MNFT_SIGNERS);
        this.total = BigInt.fromInt(total);
        this.signers = new ArrayList<NFTSigner>(Arrays.asList(signers));
    }

    public static NFTSigners get(int total, NFTSigner[] signers) {
        return new NFTSigners(total, signers);
    }

    private void assertNumberOfSignersValidRange(NFTSigner[] signers) {
        if (signers.length > 10) {
            throw new NumberLimitExceededException(
                Util.errMsg(
                    "the number of signers exceeds max - now, " + signers.length,
                    Util.getName()
                )
            );
        }
    }

    private static void assertTotalValidRange(int total) {
        if (total < 0 || total > 100) {
            throw new NumberRangeException(
                Util.errMsg("invalid total share - now, " + total, Util.getName())
            );
        }
    }

    private static void assertShareSumEnough(int total, NFTSigner[] signers) {
        int sum = 0;

        for (NFTSigner s : signers) {
            sum += s.getShare();
        }

        if (sum != total) {
            throw new NotEnoughtSumException(
                Util.errMsg(
                    "share sum != total - now, sum(" + sum + ") != total(" + total + ")",
                    Util.getName()
                )
            );
        }
    }

    @Override
    public byte[] toBytes() {
        byte[] btotal = this.total.toBytes();
        byte[] bsigners = Util.<NFTSigner>concatItemArray(this.signers);
        return Util.concatByteArray(btotal, bsigners);
    }

    @Override
    public HashMap<String, Object> toDict() {
        HashMap<String, Object> map = new HashMap<>();

        map.put("_hint", this.hint.getHint());
        map.put("total", Integer.parseInt(this.total.getValue()));

        ArrayList<Object> arr = new ArrayList<>();
        for (NFTSigner s : this.signers) {
        arr.add(s.toDict());
        }
        map.put("signers", arr);

        return map;
    }
}
