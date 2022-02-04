package org.mitumc.sdk.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import org.mitumc.sdk.interfaces.BytesChangeable;
import org.mitumc.sdk.Constant;

public class Util {

    public static HashMap<String, String> parseHint(String hinted) {
        int idx = hinted.indexOf('~');
        if (idx == -1) {
            raiseError("Invalid hinted string for parseHint.");
        }

        HashMap<String, String> map = new HashMap<String, String>();
        map.put("raw", hinted.substring(0, idx));
        map.put("hint", hinted.substring(idx + 1));

        return map;
    }

    public static HashMap<String, String> parseType(String typed) {
        if (typed.length() < 3) {
            raiseError("Invalid typed string for parseType");
        }

        HashMap<String, String> map = new HashMap<String, String>();
        map.put("raw", typed.substring(0, typed.length() - 3));
        map.put("type", typed.substring(typed.length() - 3));

        return map;
    }

    public static HashMap<String, String> parseDocumentId(String documentId) {
        if (documentId.length() < 3) {
            raiseError("Invalid suffix string for parseDocumentId");
        }

        HashMap<String, String> map = new HashMap<>();
        map.put("id", documentId.substring(0, documentId.length() - 3));
        map.put("suffix", documentId.substring(documentId.length() - 3));
        
        return map;
    }

    public static Hint getHintFromString(String hint) {
        int idx = hint.indexOf("-" + Constant.VERSION);

        if (idx == -1) {
            raiseError("Invalid hint for getHintFromString.");
        }

        return new Hint(hint.substring(0, idx));
    }

    public static Boolean isTypeValid(String type) {
        switch (type) {
            case Constant.KEY_PRIVATE:
            case Constant.KEY_PUBLIC:
            case Constant.MC_ADDRESS:
                return true;
            default:
                return false;
        }
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
        int len = s.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4) + Character.digit(s.charAt(i + 1), 16));
        }
        return data;
    }

    public static String bytesToHexString(byte[] bytes) {
        StringBuilder stringBuilder = new StringBuilder();

        for (byte b : bytes) {
            stringBuilder.append(String.format("%02X", b & 0xff));
        }

        return stringBuilder.toString();
    }

    public static void printBytes(byte[] bytes) {
        if (bytes == null) {
            return;
        }
        for (byte b : bytes) {
            System.out.print("" + b + " ");
        }
        System.out.println();
    }
}