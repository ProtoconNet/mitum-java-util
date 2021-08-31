package org.mitumc.sdk.operation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import org.mitumc.sdk.util.Util;

public class CreateDocumentsItem extends BlockSignItem {
    private String fileHash;
    private ArrayList<Address> signers;
    private String currencyId;

    CreateDocumentsItem(String fileHash, String currencyId) {
        super(Item.ITEM_TYPE_CREATE_DOCUMENTS);
        this.fileHash = fileHash;
        this.currencyId = currencyId;

        this.signers = new ArrayList<Address>();
    }

    CreateDocumentsItem(String fileHash, String[] signers, String currencyId) {
        super(Item.ITEM_TYPE_CREATE_DOCUMENTS);
        this.fileHash = fileHash;
        this.currencyId = currencyId;
        this.signers = new ArrayList<Address>(Arrays.stream(signers).map(item -> new Address(item)).toList());
    }

    public void addSigner(String signer) {
        this.signers.add(new Address(signer));
    }

    @Override
    public byte[] toBytes() {
        byte[] bfileHash = this.fileHash.getBytes();
        byte[] bcurrencyId = this.currencyId.getBytes();

        return Util.concatByteArray(bfileHash, bcurrencyId);
    }

    @Override
    public HashMap<String, Object> toDict() {
        HashMap<String, Object> hashMap = new HashMap<>();

        hashMap.put("_hint", this.hint.getHint());
        hashMap.put("filehash", this.fileHash);

        ArrayList<String> arr = new ArrayList<>();
        for(Address signer : this.signers) {
            arr.add(signer.getAddress());
        }
        hashMap.put("signers", arr);
        
        hashMap.put("currency", this.currencyId);

        return hashMap;
    }
}
