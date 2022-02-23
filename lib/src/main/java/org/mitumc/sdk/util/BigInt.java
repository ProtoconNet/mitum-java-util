package org.mitumc.sdk.util;

import java.math.BigInteger;

import org.mitumc.sdk.interfaces.BytesConvertible;

public class BigInt implements BytesConvertible {
    final public static String BIG_ENDIAN = "big-endian";
    final public static String LITTLE_ENDIAN = "little-endian";

    final private int defaultByteLength = 8;
    final private String defaultEndian = BIG_ENDIAN;
    final private Boolean defaultTight = false;

    private BigInteger num;

    public BigInt(String num) {
        this.num = new BigInteger(num);
    }

    private byte[] reverse(byte[] bytes) {
        if (bytes == null) {
            return null;
        }
        int i = 0;
        int j = bytes.length - 1;
        byte tmp;
        while (j > i) {
            tmp = bytes[j];
            bytes[j] = bytes[i];
            bytes[i] = tmp;
            j--;
            i++;
        }

        return bytes;
    }

    public String getValue() {
        return this.num.toString();
    }

    public byte[] toBytes() {
        return toBytes(defaultByteLength, defaultEndian, defaultTight);
    }

    public byte[] toBytes(int byteLength) {
        return toBytes(byteLength, defaultEndian, defaultTight);
    }

    public byte[] toBytes(boolean isTight) {
        return toBytes(defaultByteLength, defaultEndian, isTight);
    }

    public byte[] toBytes(String endian) {
        return toBytes(defaultByteLength, endian, defaultTight);
    }

    public byte[] toBytes(int byteLength, boolean isTight) {
        return toBytes(byteLength, defaultEndian, isTight);
    }

    public byte[] toBytes(int byteLength, String endian) {
        return toBytes(byteLength, endian, defaultTight);
    }

    public byte[] toBytes(String endian, boolean isTight) {
        return toBytes(defaultByteLength, endian, isTight);
    }

    public byte[] toBytes(int byteLength, String endian, boolean isTight) {

        if (this.num.equals(new BigInteger("0"))) {
            if (isTight) {
                return new byte[0];
            } else {
                return new byte[byteLength];
            }
        }

        /* tight big-endian */
        byte[] bytes = reverse(this.num.toByteArray());

        /* byteLength is ignored */
        if (isTight) {
            if (endian.equals(BIG_ENDIAN)) {
                return bytes;
            } else if (endian.equals(LITTLE_ENDIAN)) {
                return reverse(bytes);
            }
        }

        if(endian.equals(LITTLE_ENDIAN)) {
            bytes = reverse(bytes);
        }

        byte[] result = new byte[byteLength];
        System.arraycopy(bytes, 0, result, 0, bytes.length);

        return result;
    }
}