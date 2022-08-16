package org.mitumc.sdk.operation.nft;

import java.util.HashMap;

import org.mitumc.sdk.Constant;
import org.mitumc.sdk.interfaces.BytesConvertible;
import org.mitumc.sdk.interfaces.HashMapConvertible;
import org.mitumc.sdk.operation.nft.base.NFTSigners;
import org.mitumc.sdk.util.Hint;
import org.mitumc.sdk.util.Util;

public class MintForm implements BytesConvertible, HashMapConvertible {
    private Hint hint;
    private String hash;
    private String uri;
    private NFTSigners creators;
    private NFTSigners copyrighters;

    MintForm(String hash, String uri, NFTSigners creators, NFTSigners copyrighters) throws Exception {
        this.hint = Hint.get(Constant.MNFT_MINT_FORM);
        this.hash = hash;
        this.uri = uri;
        this.creators = creators;
        this.copyrighters = copyrighters;
    }

    @Override
    public byte[] toBytes() throws Exception {
        byte[] bhash = this.hash.getBytes();
        byte[] buri = this.uri.getBytes();
        byte[] bcreators = null;
        byte[] bcopyrighters = null;

        try {
            bcreators = this.creators.toBytes();
            bcopyrighters = this.copyrighters.toBytes();
        } catch (Exception e) {
            throw new Exception(
                    Util.linkErrMsgs(
                            Util.errMsg("failed to convert mint form to bytes", Util.getName()),
                            e.getMessage()));
        }

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
