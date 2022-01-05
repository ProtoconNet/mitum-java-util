package org.mitumc.sdk.operation.blocksign;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import org.mitumc.sdk.util.BigInt;
import org.mitumc.sdk.util.Util;
import org.mitumc.sdk.key.Address;

public class CreateDocumentsItem extends BlockSignItem {
    private String fileHash;
    private BigInt documentId;
    private String signcode;
    private String title;
    private BigInt size;
    private String currencyId;
    private ArrayList<Address> signers;
    private ArrayList<String> signcodes;

    CreateDocumentsItem(String fileHash, int documentId, String signcode, String title, int size, String currencyId, String[] signers, String[] signcodes) {
        super(ITEM_TYPE_CREATE_DOCUMENTS);
        this.fileHash = fileHash;
        this.documentId = new BigInt(Integer.toString(documentId));
        this.signcode = signcode;
        this.title = title;
        this.size = new BigInt(Integer.toString(size));
        this.currencyId = currencyId;
        this.signers = new ArrayList<Address>(Arrays.stream(signers).map(item -> new Address(item)).toList());
        this.signcodes = new ArrayList<String>(Arrays.stream(signcodes).map(item -> item).toList());
    }

    @Override
    public byte[] toBytes() {
        byte[] bfileHash = this.fileHash.getBytes();
        byte[] bdocumentId = this.documentId.toBytes(BigInt.LITTLE_ENDIAN, true);
        byte[] bscode = this.signcode.getBytes();
        byte[] btitle = this.title.getBytes();
        byte[] bsize = this.size.toBytes(BigInt.LITTLE_ENDIAN, true);
        byte[] bcurrencyId = this.currencyId.getBytes();

        byte[] bsigners = Util.<Address>concatItemArray(this.signers);
        
        ArrayList<byte[]> arr = new ArrayList<>();
        int byteLen = 0;
        for (String scode : this.signcodes) {
            byte[] temp = scode.getBytes();
            arr.add(temp);
            byteLen += temp.length;
        }
        byte[] bscodes = new byte[byteLen];
        int tempLen = 0;
        for (byte[] bt : arr) {
            System.arraycopy(bt, 0, bscodes, tempLen, bt.length);
            tempLen += bt.length;
        }

        return Util.concatByteArray(bfileHash, bdocumentId, bscode, btitle, bsize, bcurrencyId, bsigners, bscodes);
    }

    @Override
    public HashMap<String, Object> toDict() {
        HashMap<String, Object> hashMap = new HashMap<>();

        hashMap.put("_hint", this.hint.getHint());
        hashMap.put("filehash", this.fileHash);
        hashMap.put("documentid", this.documentId.getValue());
        hashMap.put("signcode", this.signcode);
        hashMap.put("title", this.title);
        hashMap.put("size", this.size.getValue());

        ArrayList<String> arr = new ArrayList<>();
        for(Address signer : this.signers) {
            arr.add(signer.getAddress());
        }
        hashMap.put("signers", arr);

        hashMap.put("signcodes", this.signcodes);
        hashMap.put("currency", this.currencyId);

        return hashMap;
    }
}
