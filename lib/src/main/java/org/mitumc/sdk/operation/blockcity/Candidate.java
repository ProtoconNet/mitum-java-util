package org.mitumc.sdk.operation.blockcity;

import java.util.HashMap;

import org.mitumc.sdk.Constant;
import org.mitumc.sdk.interfaces.BytesChangeable;
import org.mitumc.sdk.interfaces.Dictionariable;
import org.mitumc.sdk.key.Address;
import org.mitumc.sdk.util.Hint;
import org.mitumc.sdk.util.Util;

public class Candidate implements BytesChangeable, Dictionariable {
    private Hint hint;
    private Address address;
    private String manifest;

    Candidate(String address, String manifest) {
        assertManifest(manifest);
        this.hint = new Hint(Constant.MBC_VOTING_CANDIDATE);
        this.address = new Address(address);
        this.manifest = manifest;
    }

    private void assertManifest(String manifest) {
        if (manifest.length() > 100) {
            Util.raiseError("manifest length is over 100! (manifest.length() <= 100; Candidate.");
        }
    }

    @Override
    public byte[] toBytes() {
        byte[] baddress = this.address.toBytes();
        byte[] bmanifest = this.manifest.getBytes();
        return Util.concatByteArray(baddress, bmanifest);
    }

    @Override
    public HashMap<String, Object> toDict() {
        HashMap<String, Object> hashMap = new HashMap<>();

        hashMap.put("_hint", this.hint.getHint());
        hashMap.put("address", this.address.getAddress());
        hashMap.put("manifest", this.manifest);

        return hashMap;
    }
}
