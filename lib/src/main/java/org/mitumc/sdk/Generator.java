package org.mitumc.sdk;

import java.util.ArrayList;
import java.util.HashMap;

import org.bitcoinj.core.Base58;
import org.mitumc.sdk.interfaces.IdSettable;
import org.mitumc.sdk.key.Key;
import org.mitumc.sdk.key.Keypair;
import org.mitumc.sdk.key.Keys;
import org.mitumc.sdk.util.Hash;
import org.mitumc.sdk.util.Hint;
import org.mitumc.sdk.util.TimeStamp;
import org.mitumc.sdk.util.Util;

import com.google.gson.JsonObject;

import org.mitumc.sdk.operation.Operation;
import org.mitumc.sdk.operation.base.OperationFact;
import org.mitumc.sdk.operation.currency.CurrencyGenerator;
import org.mitumc.sdk.operation.document.DocumentGenerator;
import org.mitumc.sdk.operation.feefi.FeefiGenerator;
import org.mitumc.sdk.operation.nft.NFTGenerator;

public class Generator implements IdSettable {
    private String id;
    public CurrencyGenerator currency = CurrencyGenerator.get();
    public DocumentGenerator document = DocumentGenerator.get();
    public FeefiGenerator feefi = FeefiGenerator.get();
    public NFTGenerator nft = NFTGenerator.get();

    private Generator() {
    }

    private Generator(String id) {
        this.id = id;
    }

    public static Generator get(String id) {
        return new Generator(id);
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String getId() {
        return this.id;
    }

    public Operation getOperation(OperationFact fact) {
        return Operation.get(fact, "", this.id);
    }

    public Operation getOperation(OperationFact fact, String memo) {
        return Operation.get(fact, memo, this.id);
    }

    public HashMap<String, Object> getSeal(String signKey, Operation[] operations) {
        Keypair keypair = Keypair.fromPrivateKey(signKey);

        TimeStamp signedAt = Util.getDateTimeStamp();
        byte[] bsignedAt = signedAt.getUTC().getBytes();

        String signer;
        byte[] bsigner;

        signer = keypair.getPublicKey();
        bsigner = signer.getBytes();

        ArrayList<byte[]> operationHashes = new ArrayList<>();
        int opHashesByteLen = 0;
        for (Operation oper : operations) {
            byte[] bHash = oper.getHash().getSha3Digest();
            operationHashes.add(bHash);
            opHashesByteLen += bHash.length;
        }
        byte[] boperations = new byte[opHashesByteLen];
        int totalByteLen = 0;
        for (byte[] bHash : operationHashes) {
            System.arraycopy(bHash, 0, boperations, totalByteLen, bHash.length);
            totalByteLen += bHash.length;
        }

        Hash bodyHash = Hash.fromBytes(Util.concatByteArray(bsigner, bsignedAt, boperations));

        byte[] signature = keypair.sign(
                Util.concatByteArray(
                        bodyHash.getSha3Digest(),
                        this.id.getBytes()));

        Hash hash = Hash.fromBytes(Util.concatByteArray(bodyHash.getSha3Digest(), signature));

        HashMap<String, Object> hashMap = new HashMap<>();

        hashMap.put("_hint", Hint.get(Constant.SEAL).getHint());
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

    public HashMap<String, Object> getSeal(String signKey, JsonObject[] operations) {
        Keypair keypair = Keypair.fromPrivateKey(signKey);

        TimeStamp signedAt = Util.getDateTimeStamp();
        byte[] bsignedAt = signedAt.getUTC().getBytes();

        String signer;
        byte[] bsigner;

        signer = keypair.getPublicKey();
        bsigner = signer.getBytes();

        ArrayList<byte[]> operationHashes = new ArrayList<>();
        int opHashesByteLen = 0;
        for (JsonObject oper : operations) {
            byte[] bHash =  Base58.decode(oper.getAsJsonObject().get("hash").getAsString());
            operationHashes.add(bHash);
            opHashesByteLen += bHash.length;
        }
        byte[] boperations = new byte[opHashesByteLen];
        int totalByteLen = 0;
        for (byte[] bHash : operationHashes) {
            System.arraycopy(bHash, 0, boperations, totalByteLen, bHash.length);
            totalByteLen += bHash.length;
        }

        Hash bodyHash = Hash.fromBytes(Util.concatByteArray(bsigner, bsignedAt, boperations));

        byte[] signature = keypair.sign(
                Util.concatByteArray(
                        bodyHash.getSha3Digest(),
                        this.id.getBytes()));

        Hash hash = Hash.fromBytes(Util.concatByteArray(bodyHash.getSha3Digest(), signature));

        HashMap<String, Object> hashMap = new HashMap<>();

        hashMap.put("_hint", Hint.get(Constant.SEAL).getHint());
        hashMap.put("hash", hash.getSha3Hash());
        hashMap.put("body_hash", bodyHash.getSha3Hash());
        hashMap.put("signer", signer);
        hashMap.put("signature", Base58.encode(signature));
        hashMap.put("signed_at", signedAt.getISO());

        ArrayList<Object> arr = new ArrayList<>();
        for (JsonObject oper : operations) {
            arr.add(oper);
        }
        hashMap.put("operations", arr);

        return hashMap;
    }

    public static HashMap<String, Object> randomKeys() {
        return Generator.randomKeys(1);
    }

    public static HashMap<String, Object> randomKeys(int numOfKeys) {
        int weight = 100 / numOfKeys;
        if (100 % numOfKeys != 0) {
            weight++;
        }

        HashMap<String, Keypair> kps = new HashMap<>();
        Key[] ks = new Key[numOfKeys];
        Keys keys = null;

        for (int i = 0; i < numOfKeys; i++) {
            Keypair kp = Keypair.random();
            kps.put(kp.getPublicKey(), kp);
            ks[i] = Key.get(kp.getPublicKey(), weight);
        }
        keys = Keys.get(ks, 100);

        HashMap<String, Object> map = new HashMap<>();
        map.put(Keypair.ID, kps);
        map.put(Keys.ID, keys);

        return map;
    }
}
