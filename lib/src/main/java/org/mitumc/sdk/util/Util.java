package org.mitumc.sdk.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import org.mitumc.sdk.BytesChangeable;
import org.mitumc.sdk.Constant;

public class Util {

    public static HashMap<String, String> parseHint(String hinted) {
        int idx = hinted.indexOf(':');
        if (idx == -1) {
            raiseError("Invalid hinted string for parseHint.");
        }

        HashMap<String, String> map = new HashMap<String, String>();
        map.put("raw", hinted.substring(0, idx));
        map.put("hint", hinted.substring(idx + 1));

        return map;
    }

    public static Hint getHintFromString(String hint) {
        int idx = hint.indexOf("-" + Constant.VERSION);

        if (idx == -1) {
            raiseError("Invalid hint for getHintFromString.");
        }

        return new Hint(hint.substring(0, idx));
    }

    public static TimeStamp getDateTimeStamp() {
        return new TimeStamp();
    }

    public static byte[] concatByteArray(byte[]... bs) {
        int arrLen = Arrays.stream(bs).map(item -> item.length).reduce(0, (a, b) -> a + b);

        int tempLen = 0;
        byte[] result = new byte[arrLen];
        for (byte[] arr : bs) {
            System.arraycopy(arr, 0, result, tempLen, arr.length);
            tempLen += arr.length;
        }

        return result;
    }

    public static <T> byte[] concatItemArray(ArrayList<T> objs) {
        ArrayList<byte[]> arr = new ArrayList<>();

        int byteLen = 0;
        for (T obj : objs) {
            byte[] temp = ((BytesChangeable) obj).toBytes();
            arr.add(temp);
            byteLen += temp.length;
        }

        byte[] result = new byte[byteLen];
        int tempLen = 0;
        for (byte[] bt : arr) {
            System.arraycopy(bt, 0, result, tempLen, bt.length);
            tempLen += bt.length;
        }

        return result;
    }

    public static byte[] copyByteArray(byte[] src) {
        byte[] result = new byte[src.length];
        System.arraycopy(src, 0, result, 0, src.length);
        return result;
    }

    public static void raiseError(String msg) {
        System.out.println(msg);
        System.exit(1);
    }

    public static void log(String msg) {
        System.out.println(msg);
    }

    public static byte[] hexStringToBytes(String s) {
        int len = s.length() / 2;
        byte[] data = new byte[len];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4) + Character.digit(s.charAt(i + 1), 16));
        }
        return data;
    }

    public static String bytesToHexString(byte[] bytes) {
        StringBuilder stringBuilder = new StringBuilder();

        for(byte b : bytes) {
            stringBuilder.append(String.format("%02X", b & 0xff));
        }

        return stringBuilder.toString();
    }
}