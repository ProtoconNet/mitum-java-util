package org.mitumc.sdk.operation.nft;

import java.util.ArrayList;
import java.util.HashMap;

import org.mitumc.sdk.Constant;
import org.mitumc.sdk.exception.NumberLimitExceededException;
import org.mitumc.sdk.exception.NumberRangeException;
import org.mitumc.sdk.interfaces.BytesConvertible;
import org.mitumc.sdk.interfaces.HashMapConvertible;
import org.mitumc.sdk.key.Address;
import org.mitumc.sdk.operation.ContractID;
import org.mitumc.sdk.util.BigInt;
import org.mitumc.sdk.util.Hint;
import org.mitumc.sdk.util.Util;

public class CollectionRegisterForm implements BytesConvertible, HashMapConvertible {
    private Hint hint;
    private Address target;
    private ContractID symbol;
    private String name;
    private BigInt royalty;
    private String uri;
    private ArrayList<Address> whites;

    CollectionRegisterForm(String target, String symbol, String name, int royalty, String uri, String[] whites) {
        assertNumberOfWhitesValidRange(whites);
        assertRoyaltyValidRange(royalty);
        
        this.hint = Hint.get(Constant.MNFT_COLLECTION_REGISTER_FORM);
        this.target = Address.get(target);
        this.symbol = ContractID.get(symbol);
        this.name = name;
        this.royalty = BigInt.fromInt(royalty);
        this.uri = uri;
        this.whites = new ArrayList<>();

        for (String w : whites) {
            this.whites.add(Address.get(w));
        }
    }

    private static void assertNumberOfWhitesValidRange(String[] whites) {
        if (whites.length > 10) {
            throw new NumberLimitExceededException(Util.errMsg("the number of whites exceeds max - now, " + whites.length, Util.getName()));
        }
    }

    private static void assertRoyaltyValidRange(int royalty) {
        if (royalty < 0 || royalty >= 100) {
            throw new NumberRangeException(Util.errMsg("invalid royalty - now, " + royalty, Util.getName()));
        }
    }

    @Override
    public byte[] toBytes() {
        byte[] btarget = this.target.toBytes();
        byte[] bsymbol = this.symbol.toBytes();
        byte[] bname = this.name.getBytes();
        byte[] broyalty = this.royalty.toBytes();
        byte[] buri = this.uri.getBytes();
        byte[] bwhites = Util.<Address>concatItemArray(this.whites);
        return Util.concatByteArray(btarget, bsymbol, bname, broyalty, buri, bwhites);
    }

    @Override
    public HashMap<String, Object> toDict() {
        HashMap<String, Object> map = new HashMap<>();

        map.put("_hint", this.hint.getHint());
        map.put("target", this.target.getAddress());
        map.put("symbol", this.symbol.toString());
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
