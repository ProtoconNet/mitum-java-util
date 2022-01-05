package org.mitumc.sdk;

import java.util.ArrayList;
import java.util.HashMap;

import org.bitcoinj.core.Base58;

import org.mitumc.sdk.key.Keypair;
import org.mitumc.sdk.util.Hash;
import org.mitumc.sdk.util.Hint;
import org.mitumc.sdk.util.TimeStamp;
import org.mitumc.sdk.util.Util;
import org.mitumc.sdk.operation.Operation;
import org.mitumc.sdk.operation.OperationFact;
import org.mitumc.sdk.operation.currency.CurrencyGenerator;
import org.mitumc.sdk.operation.blocksign.BlockSignGenerator;


public class Generator {
    private String id;
    private CurrencyGenerator currencyGenerator;
    private BlockSignGenerator blockSignGenerator;

    private Generator(String id) {
        this.id = id;
        this.currencyGenerator = CurrencyGenerator.get(id);
        this.blockSignGenerator = BlockSignGenerator.get(id);
    }

    public static Generator get(String id) {
        return new Generator(id);
    }

    public void setId(String id) {
        this.id = id;
        this.currencyGenerator = CurrencyGenerator.get(id);
        this.blockSignGenerator = BlockSignGenerator.get(id);
    }

    public CurrencyGenerator currency() {
        return this.currencyGenerator;
    }

    public BlockSignGenerator blockSign() {
        return this.blockSignGenerator;
    }

    public Operation newOperation(OperationFact fact) {
        return new Operation(fact);
    }

    public Operation newOperation(String memo, OperationFact fact) {
        return new Operation(memo, fact);
    }

    public HashMap<String, Object> newSeal(String signKey, Operation[] operations) {
        Keypair keypair = Keypair.fromPrivateKey(signKey);

        TimeStamp signedAt = Util.getDateTimeStamp();
        byte[] bsignedAt = signedAt.getUTC().getBytes();

        String signer;
        byte[] bsigner;

        signer = keypair.getPublicKey();
        bsigner = signer.getBytes();

        ArrayList<byte[]> tempArr = new ArrayList<>();
        int byteLen = 0;
        for (Operation oper : operations) {
            byte[] temp = oper.getHash().getSha3Digest();
            tempArr.add(temp);
            byteLen += temp.length;
        }
        byte[] boperations = new byte[byteLen];
        int tempLen = 0;
        for (byte[] bt : tempArr) {
            System.arraycopy(bt, 0, boperations, tempLen, bt.length);
            tempLen += bt.length;
        }

        Hash bodyHash = new Hash(Util.concatByteArray(bsigner, bsignedAt, boperations));

        byte[] signature;
        signature = keypair.sign(
                Util.concatByteArray(
                        bodyHash.getSha3Digest(),
                        this.id.getBytes()));

        Hash hash = new Hash(Util.concatByteArray(bodyHash.getSha3Digest(), signature));

        HashMap<String, Object> hashMap = new HashMap<>();

        hashMap.put("_hint", new Hint(Constant.SEAL).getHint());
        hashMap.put("hash", hash.getSha3Hash());
        hashMap.put("body_hash", bodyHash.getSha3Hash());
        hashMap.put("signer", signer);
        hashMap.put("signature", Base58.encode(signature));
        hashMap.put("signed_at", signedAt.getISO());

        ArrayList<Object> arr = new ArrayList<>();
        for (Operation oper : operations) {
            arr.add(oper.toDict());
        }
        hashMap.put("operations", arr);

        return hashMap;
    }
}