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

    private int byteLength(byte[] bytes, boolean isBig) {
        if(isBig) {
            for(int i = 0; i < bytes.length; i++){
                if(bytes[i] != 0) {
                    return bytes.length - i;
                }
            }
        }
        else {
            for(int i = bytes.length - 1; i > -1; i--) {
                if(bytes[i] != 0) {
                    return i + 1;
                }
            }
        }

        return -1;
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

        boolean isBig = endian.equals(BIG_ENDIAN);

        byte[] bytes = ByteBuffer.allocate(byteLength).order(
            isBig ? ByteOrder.BIG_ENDIAN : ByteOrder.LITTLE_ENDIAN).putLong(this.abs).array();

        if(isTight) {
            int len = byteLength(bytes, isBig);
            byte[] result = new byte[len];
            
            if(isBig) {
                System.arraycopy(bytes, bytes.length - len, result, 0, len);
            }
            else {
                System.arraycopy(bytes, 0, result, 0, len);
            }
        
            return result;
        }
        
        return bytes;
    }
}