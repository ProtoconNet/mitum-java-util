package org.mitumc.sdk.operation.blockcity;

import java.util.ArrayList;
import java.util.HashMap;

import org.mitumc.sdk.key.Address;
import org.mitumc.sdk.operation.OperationFact;

public class BlockCityFact extends OperationFact {
    private Address sender;
    private ArrayList<BlockCityItem> items;

    public BlockCityFact(String type, String sender) {
        this(type, sender, new BlockCityItem[0]);
    }

    public BlockCityFact(String type, String sender, BlockCityItem[] items) {
        super(type);

        this.sender = new Address(sender);
        this.items = new ArrayList<BlockCityItem>();

        for(BlockCityItem item : items) {
            this.items.add(item);
        }
    }

    public void addItem(BlockCityItem item) {
        this.items.add(item);
        this.generateHash();
    }

    private void generateHash() {

    }

    @Override
    public byte[] toBytes() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public HashMap<String, Object> toDict() {
        // TODO Auto-generated method stub
        return null;
    }
}
