package org.mitumc.sdk;

import java.util.ArrayList;
import java.util.HashMap;

import org.bitcoinj.core.Base58;

import org.mitumc.sdk.key.KeyManager;
import org.mitumc.sdk.key.BTCKeypair;
import org.mitumc.sdk.key.ETHERKeypair;
import org.mitumc.sdk.key.STELLARKeypair;
import org.mitumc.sdk.key.BaseKeypair;
import org.mitumc.sdk.key.KeyTypeFixable;
import org.mitumc.sdk.operation.Operation;
import org.mitumc.sdk.util.Hash;
import org.mitumc.sdk.util.Hint;
import org.mitumc.sdk.util.TimeStamp;
import org.mitumc.sdk.util.Util;

public class SealManager {
    public static HashMap<String, Object> newSeal(String signKey, Operation[] operations, String networkId) {
        Object keypair = KeyManager.getKeypairFromPrivateKey(signKey);
        String keypairType = ((KeyTypeFixable)keypair).getKeypairType();
        TimeStamp signedAt = Util.getDateTimeStamp();
        byte[] bsignedAt = signedAt.getUTC().getBytes();
        String signer;
        byte[] bsigner;

        switch(keypairType) {
            case BaseKeypair.KEYPAIR_TYPE_BTC:
                Util.raiseError("Not Support BTC Keypair yet.");
                signer = ((BTCKeypair) keypair).getPublicKey();
                bsigner = signer.getBytes();
                break;
            case BaseKeypair.KEYPAIR_TYPE_ETHER:
                Util.raiseError("Not Support ETHER Keypair yet.");
                signer = ((ETHERKeypair) keypair).getPublicKey();
                bsigner = signer.getBytes();
                break;
            case BaseKeypair.KEYPAIR_TYPE_STELLAR:
                signer = ((STELLARKeypair) keypair).getPublicKey();
                bsigner = signer.getBytes();
                break;
            default:
                Util.raiseError("Invalid keypair type for newSeal.");
                return null;
        }
        
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
        switch(keypairType) {
            case BaseKeypair.KEYPAIR_TYPE_BTC:
                Util.raiseError("Not Support BTC Keypair yet.");
                signature = ((BTCKeypair)keypair).sign(
                    Util.concatByteArray(
                        bodyHash.getSha3Digest(),
                        networkId.getBytes())
                );
                break;
            case BaseKeypair.KEYPAIR_TYPE_ETHER:
                Util.raiseError("Not Support ETHER Keypair yet.");
                signature = ((ETHERKeypair)keypair).sign(
                    Util.concatByteArray(
                        bodyHash.getSha3Digest(),
                        networkId.getBytes())
                );
                break;
            case BaseKeypair.KEYPAIR_TYPE_STELLAR:
                signature = ((STELLARKeypair)keypair).sign(
                    Util.concatByteArray(
                        bodyHash.getSha3Digest(),
                        networkId.getBytes())
                );
                break;
            default:
                Util.raiseError("Invalid keypair type for newSeal.");
                return null;
        }

        Hash hash = new Hash(Util.concatByteArray(bodyHash.getSha3Digest(), signature));

        HashMap<String, Object> hashMap = new HashMap<>();

        hashMap.put("_hint", new Hint(Constant.SEAL).getHint());
        hashMap.put("hash", hash.getSha3Hash());
        hashMap.put("body_hash", bodyHash.getSha3Hash());
        hashMap.put("signer", signer);
        hashMap.put("signature", Base58.encode(signature));
        hashMap.put("signed_at", signedAt.getISO());

        ArrayList<Object> arr = new ArrayList<>();
        for(Operation oper : operations) {
            arr.add(oper.toDict());
        }
        hashMap.put("operations", arr);

        return hashMap;
    }

    public static HashMap<String, Object> newSeal(String signKey, Operation[] operations) {
        return newSeal(signKey, operations, Constant.NETWORK_ID);
    }
}