package org.mitumc.sdk.operation.nft;

import java.util.HashMap;

import org.mitumc.sdk.Constant;
import org.mitumc.sdk.exception.EmptyStringException;
import org.mitumc.sdk.interfaces.BytesConvertible;
import org.mitumc.sdk.interfaces.HashMapConvertible;
import org.mitumc.sdk.util.Hint;
import org.mitumc.sdk.util.Util;

public class MintForm implements BytesConvertible, HashMapConvertible {
    private Hint hint;
    private String hash;
    private String uri;
    private NFTSigners creators;
    private NFTSigners copyrighters;

    MintForm(String hash, String uri, NFTSigners creators, NFTSigners copyrighters) {
        assertURINotEmpty(uri);
        this.hint = Hint.get(Constant.MNFT_MINT_FORM);
        this.hash = hash;
        this.uri = uri;
        this.creators = creators;
        this.copyrighters = copyrighters;
    }

    private static void assertURINotEmpty(String uri) {
        if (uri.equals("")) {
            throw new EmptyStringException(Util.errMsg("empty nft uri", Util.getName()));
        }
    }

    @Override
    public byte[] toBytes() {
        byte[] bhash = this.hash.getBytes();
        byte[] buri = this.uri.getBytes();
        byte[] bcreators = this.creators.toBytes();
        byte[] bcopyrighters = this.copyrighters.toBytes();

        return Util.concatByteArray(bhash, buri, bcreators, bcopyrighters);
    }

    @Override
    public HashMap<String, Object> toDict() {
        HashMap<String, Object> map = new HashMap<>();

        map.put("_hint", this.hint.getHint());
        map.put("hash", this.hash);
        map.put("uri", this.uri);
        map.put("creators", this.creators.toDict());
        map.put("copyrighters", this.copyrighters.toDict());

        return map;
    }
}
