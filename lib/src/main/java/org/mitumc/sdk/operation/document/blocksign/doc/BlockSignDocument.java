package org.mitumc.sdk.operation.document.blocksign.doc;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;

import org.mitumc.sdk.operation.document.base.Document;
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

    private BlockSignDocument(String documentId, String owner, String fileHash, BlockSignUser creator, String title,
            String size, BlockSignUser[] signers) throws Exception {
        super(BlockSignGeneralInfo.get(documentId), owner);

        this.fileHash = fileHash;
        this.creator = creator;
        this.title = title;
        this.size = new BigInt(size);

        this.signers = new ArrayList<>();
        for (BlockSignUser user : signers) {
            this.signers.add(user);
        }
    }

    public static BlockSignDocument get(String documentId, String owner, String fileHash, BlockSignUser creator,
            String title, String size, BlockSignUser[] signers) throws Exception {
        try {
            return new BlockSignDocument(documentId, owner, fileHash, creator, title, size, signers);
        } catch (Exception e) {
            throw new Exception(
                    Util.linkErrMsgs(
                            Util.errMsg("failed to create block sign document", Util.getName()),
                            e.getMessage()));
        }
    }

    @Override
    public byte[] toBytes() throws Exception {
        this.signers.sort(new SignerComparator());

        byte[] binfo = this.info.toBytes();
        byte[] bowner = this.owner.toBytes();
        byte[] bfileHash = this.fileHash.getBytes();
        byte[] bcreator = this.creator.toBytes();
        byte[] btitle = this.title.getBytes();
        byte[] bsize = this.size.toBytes(BigInt.LITTLE_ENDIAN, true);
        byte[] bsigners = null;

        try {
            bsigners = Util.<BlockSignUser>concatItemArray(this.signers);
        } catch (Exception e) {
            throw new Exception(
                    Util.linkErrMsgs(
                            Util.errMsg("failed to convert block sign document to bytes", Util.getName()),
                            e.getMessage()));
        }

        return Util.concatByteArray(binfo, bowner, bfileHash, bcreator, btitle, bsize, bsigners);
    }

    @Override
    public HashMap<String, Object> toDict() throws Exception {
        HashMap<String, Object> hashMap = new HashMap<>();

        hashMap.put("_hint", this.hint.getHint());
        hashMap.put("owner", this.owner.getAddress());
        hashMap.put("filehash", this.fileHash);
        hashMap.put("creator", this.creator.toDict());
        hashMap.put("title", this.title);
        hashMap.put("size", this.size.getValue());

        try {
            hashMap.put("info", this.info.toDict());
        } catch (Exception e) {
            throw new Exception(
                    Util.linkErrMsgs(
                            Util.errMsg("failed to convert block sign document to hashmap", Util.getName()),
                            e.getMessage()));
        }

        ArrayList<Object> arr = new ArrayList<>();
        for (BlockSignUser user : this.signers) {
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
