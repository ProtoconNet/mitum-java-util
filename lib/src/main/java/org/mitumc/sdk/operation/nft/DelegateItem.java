package org.mitumc.sdk.operation.nft;

import java.util.HashMap;

import org.mitumc.sdk.Constant;
import org.mitumc.sdk.exception.NoSuchOptionException;
import org.mitumc.sdk.key.Address;
import org.mitumc.sdk.operation.nft.base.NFTItem;
import org.mitumc.sdk.util.Util;

public class DelegateItem extends NFTItem {
    public static final String ALLOW = "allow";
    public static final String CANCEL = "cancel";
    private String collection;
    private Address agent;
    private String mode;

    DelegateItem(String collection, String agent, String mode, String currency) {
        super(Constant.MNFT_DELEGATE_ITEM, currency);
        assertModeValid(mode);
        this.collection = collection;
        this.agent = Address.get(agent);
        this.mode = mode;
    }

    private static void assertModeValid(String mode) {
        if (!(mode.equals(ALLOW) || mode.equals(CANCEL))) {
            throw new NoSuchOptionException(Util.errMsg("invalid delegate mode - use 'allow', 'cancel'", Util.getName()));
        }
    }

    @Override
    public byte[] toBytes() {
        byte[] bcollection = this.collection.getBytes();
        byte[] bagent = this.agent.toBytes();
        byte[] bmode = this.mode.getBytes();
        byte[] bcurrencyId = this.currency.toBytes();
        return Util.concatByteArray(bcollection, bagent, bmode, bcurrencyId);
    }

    @Override
    public HashMap<String, Object> toDict() {
        HashMap<String, Object> map = new HashMap<>();

        map.put("_hint", this.hint.getHint());
        map.put("collection", this.collection);
        map.put("agent", this.agent.getAddress());
        map.put("mode", this.mode);
        map.put("currency", this.currency.toString());

        return map;
    }
}
