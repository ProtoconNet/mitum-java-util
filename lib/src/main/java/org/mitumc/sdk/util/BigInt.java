package org.mitumc.sdk.util;

import java.math.BigInteger;

import org.mitumc.sdk.exception.NumberRangeException;
import org.mitumc.sdk.interfaces.BytesConvertible;

public class BigInt implements BytesConvertible {
    final public static String BIG_ENDIAN = "big-endian";
    final public static String LITTLE_ENDIAN = "little-endian";

    final private int defaultByteLength = 8;
    final private String defaultEndian = BIG_ENDIAN;
    final private Boolean defaultTight = false;

    private BigInteger num;

    private BigInt(String num) {
        this.num = new BigInteger(num);
    }

    private BigInt(String num, boolean allowNegative) {
        this(num);
        if(!allowNegative && (this.num.signum() < 0)) {
            throw new NumberRangeException(Util.errMsg("negative big - only positive or zero bigs available", Util.getName()));
        }
    }

    public static BigInt fromString(String num) {
        return BigInt.fromString(num, false);
    }

    public static BigInt fromInt(int num) {
        return BigInt.fromString("" + num, false);
    }

    public static BigInt fromString(String num, boolean allowNegative) {
        return new BigInt(num, allowNegative);
    }

    public static BigInt fromInt(int num, boolean allowNegative) {
        return BigInt.fromString(num + "", allowNegative);
    }

    private byte[] reverse(byte[] bytes) {
        if (bytes == null) {
            return null;
        }

        byte[] reversed = new byte[bytes.length];
        for(int i = 0; i < bytes.length; i++) {
            reversed[i] = bytes[bytes.length - 1 - i];
        }

        return reversed;
    }

    private int bytesLen() {
        int quotient = (int) this.num.bitLength() / 8;
        int left = this.num.bitLength() % 8;
        
        if (left != 0) {
            return quotient + 1;
        }

        return quotient;
    }

    public int signum() {
        return this.num.signum();
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
        byte[] bytes = new byte[bytesLen()];
        System.arraycopy(reverse(this.num.toByteArray()), 0, bytes, 0, bytesLen());

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