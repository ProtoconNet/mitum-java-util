package org.mitumc.sdk.operation.document.blocksign.doc;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;

import org.mitumc.sdk.operation.document.Document;
import org.mitumc.sdk.operation.document.blocksign.BlockSignUser;
import org.mitumc.sdk.operation.document.blocksign.info.BlockSignGeneralInfo;
import org.mitumc.sdk.util.BigInt;
import org.mitumc.sdk.util.Util;

public class BlockSignDocument extends Document {
    private String fileHash;
    private BlockSignUser creator;
    private String title;
    private BigInt size;
    private ArrayList<BlockSignUser> signers;

    public BlockSignDocument(String documentId, String owner, String fileHash, BlockSignUser creator, String title, String size, BlockSignUser[] signers) {
        super(new BlockSignGeneralInfo(documentId), owner);
    
        this.fileHash = fileHash;
        this.creator = creator;
        this.title = title;
        this.size = new BigInt(size);
        
        this.signers = new ArrayList<>();
        for(BlockSignUser user : signers) {
            this.signers.add(user);
        }
    }
    
    @Override
    public byte[] toBytes() {
        this.signers.sort(new SignerComparator());

        byte[] bInfo = this.info.toBytes();
        byte[] bOwner = this.owner.toBytes();
        byte[] bFileHash = this.fileHash.getBytes();
        byte[] bCreator = this.creator.toBytes();
        byte[] bTitle = this.title.getBytes();
        byte[] bSize = this.size.toBytes(BigInt.LITTLE_ENDIAN, true);
        byte[] bSigners = Util.<BlockSignUser>concatItemArray(this.signers);

        return Util.concatByteArray(bInfo, bOwner, bFileHash, bCreator, bTitle, bSize, bSigners);
    }

    @Override
    public HashMap<String, Object> toDict() {
        HashMap<String, Object> hashMap = new HashMap<>();

        hashMap.put("_hint", this.hint.getHint());
        hashMap.put("info", this.info.toDict());
        hashMap.put("owner", this.owner.getAddress());
        hashMap.put("filehash", this.fileHash);
        hashMap.put("creator", this.creator.toDict());
        hashMap.put("title", this.title);
        hashMap.put("size", this.size.getValue());

        ArrayList<Object> arr = new ArrayList<>();
        for(BlockSignUser user : this.signers) {
            arr.add(user.toDict());
        }
        hashMap.put("signers", arr);

        return hashMap;
    }

    public static class SignerComparator implements Comparator<BlockSignUser> {

        @Override
        public int compare(BlockSignUser u1, BlockSignUser u2) {
            ByteBuffer b1 = ByteBuffer.wrap(u1.getAddress().toBytes());
            ByteBuffer b2 = ByteBuffer.wrap(u2.getAddress().toBytes());

            return b1.compareTo(b2);
        }
    }
}
