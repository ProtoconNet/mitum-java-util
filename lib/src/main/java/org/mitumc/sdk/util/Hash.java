package org.mitumc.sdk.util;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import org.bitcoinj.core.Base58;

public class Hash {
    private String msg;
    private byte[] target;
    private byte[] sha256Digest;
    private String sha256Hash;
    private byte[] sha3Digest;
    private String sha3Hash;

    private Hash() throws Exception {
        this("");
    }

    private Hash(byte[] target) throws Exception {
        setter(target);
    }

    private Hash(String msg) throws Exception {
        setter(target);
    }

    public static Hash fromBytes(byte[] target) throws Exception {
        try {
            return new Hash(target);
        } catch(Exception e) {
            throw new Exception(
                Util.linkErrMsgs(
                    Util.errMsg("failed to create hash from bytes", Util.getName()),
                    e.getMessage()
                )
            );
        }
    }

    public static Hash fromString(String msg) throws Exception {
        try {
            return new Hash(msg);
        } catch(Exception e) {
            throw new Exception(
                Util.linkErrMsgs(
                    Util.errMsg("failed to create hash from string", Util.getName()),
                    e.getMessage()
                )
            );
        }
    }

    private void sha256() throws Exception {
        try{
            MessageDigest hasher = MessageDigest.getInstance("SHA-256");
            this.sha256Digest = hasher.digest(this.target);
            this.sha256Hash = Base58.encode(this.sha256Digest);
        } catch(NoSuchAlgorithmException e) {
            throw new Exception(Util.errMsg("no such algorithm - sha-256", Util.getName()));
        }
    }

    private void sha3() throws Exception {
        try {
            MessageDigest hasher = MessageDigest.getInstance("SHA3-256");
            this.sha3Digest = hasher.digest(this.target);
            this.sha3Hash = Base58.encode(this.sha3Digest);
        } catch(NoSuchAlgorithmException e) {
            throw new Exception(Util.errMsg("no such algorithm - sha3-256", Util.getName()));
        }
    }

    public void setter(String msg) throws Exception {
        this.msg = msg;
        this.target = this.msg.getBytes(StandardCharsets.UTF_8);
        try {
            sha256();
            sha3();
        } catch(Exception e) {
            throw new Exception(
                Util.linkErrMsgs(
                    Util.errMsg("failed to hash target", Util.getName()),
                    e.getMessage()
                )
            );
        }
    }

    public void setter(byte[] target) throws Exception {
        this.target = target;
        this.msg = new String(this.target);
        try {
            sha256();
            sha3();
        } catch(Exception e) {
            throw new Exception(
                Util.linkErrMsgs(
                    Util.errMsg("failed to hash target", Util.getName()),
                    e.getMessage()
                )
            );
        }
    }

    public String getMessage() {
        return this.msg;
    }

    public byte[] getTarget() {
        return this.target;
    }

    public byte[] getSha256Digest() {
        return this.sha256Digest;
    }

    public byte[] getSha3Digest() {
        return this.sha3Digest;
    }

    public String getSha256Hash() {
        return this.sha256Hash;
    }

    public String getSha3Hash() {
        return this.sha3Hash;
    }
}