package org.mitumc.sdk.util;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import org.mitumc.sdk.BytesChangeable;

public class BigInt implements BytesChangeable {
    final public static String BIG_ENDIAN = "big";
    final public static String LITTLE_ENDIAN = "little";

    final private int defaultByteLength = 8;
    final private String defaultEndian = BIG_ENDIAN;
    final private Boolean defaultTight = false;

    private long num;
    private long abs;

    public BigInt(int num) {
        this(Long.valueOf(num));
    }

    public BigInt(long num) {
        this.num = num;
        this.abs = Math.abs(num);
    }

    private int byteLength(byte[] bytes) {

        for(int i = 0; i < bytes.length; i++){
            if(bytes[i] != 0) {
                return bytes.length - i;
            }
        }
        
        return -1;
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

    public long getValue() {
        return this.num;
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
        if(!(endian.equals(BIG_ENDIAN) || endian.equals(LITTLE_ENDIAN))) {
            Util.raiseError("Invalid endian for BigInt.");
        }

        byte[] bytes = ByteBuffer.allocate(byteLength).order(ByteOrder.BIG_ENDIAN).putLong(this.abs).array();

        boolean isAllZero = true;
        for(byte b : bytes) {
            if(b != 0) {
                isAllZero = false;
            }
        }

        if(isTight) {
            int len = byteLength(bytes);

            if(len == -1) {
                if(isAllZero) {
                    return new byte[0];
                }

                Util.raiseError("Invalid value for BigInt.");
            }

            byte[] result = new byte[len];
            System.arraycopy(bytes, bytes.length - len, result, 0, len);

            return result;
        }

        return reverse(bytes);
    }
}