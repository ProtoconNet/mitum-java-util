package org.mitumc.sdk.operation.nft;

import java.util.ArrayList;
import java.util.HashMap;

import org.mitumc.sdk.Constant;
import org.mitumc.sdk.interfaces.BytesConvertible;
import org.mitumc.sdk.interfaces.HashMapConvertible;
import org.mitumc.sdk.key.Address;
import org.mitumc.sdk.util.BigInt;
import org.mitumc.sdk.util.Hint;
import org.mitumc.sdk.util.Util;

public class CollectionRegisterForm implements BytesConvertible, HashMapConvertible {
    private Hint hint;
    private Address target;
    private String symbol;
    private String name;
    private BigInt royalty;
    private String uri;
    private ArrayList<Address> whites;

    CollectionRegisterForm(String target, String symbol, String name, int royalty, String uri, String[] whites)
            throws Exception {
        this.hint = Hint.get(Constant.MNFT_COLLECTION_REGISTER_FORM);
        this.target = Address.get(target);
        this.symbol = symbol;
        this.name = name;
        this.royalty = new BigInt(royalty + "");
        this.uri = uri;
        this.whites = new ArrayList<>();

        for (String w : whites) {
            this.whites.add(Address.get(w));
        }
    }

    @Override
    public byte[] toBytes() throws Exception {
        byte[] btarget = this.target.toBytes();
        byte[] bsymbol = this.symbol.getBytes();
        byte[] bname = this.name.getBytes();
        byte[] broyalty = this.royalty.toBytes();
        byte[] buri = this.uri.getBytes();
        byte[] bwhites = null;

        try {
            bwhites = Util.<Address>concatItemArray(this.whites);
        } catch (Exception e) {
            throw new Exception(
                    Util.linkErrMsgs(
                            Util.errMsg("failed to convert collection register form to bytes", Util.getName()),
                            e.getMessage()));
        }

        return Util.concatByteArray(btarget, bsymbol, bname, broyalty, buri, bwhites);
    }

    @Override
    public HashMap<String, Object> toDict() {
        HashMap<String, Object> map = new HashMap<>();

        map.put("_hint", this.hint.getHint());
        map.put("target", this.target.getAddress());
        map.put("symbol", this.symbol);
        map.put("name", this.name);
        map.put("royalty", Integer.parseInt(this.royalty.getValue()));
        map.put("uri", this.uri);

        ArrayList<Object> arr = new ArrayList<>();
        for (Address w : this.whites) {
            arr.add(w.getAddress());
        }
        map.put("whites", arr);

        return map;
    }
}
