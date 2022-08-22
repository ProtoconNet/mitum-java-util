package org.mitumc.sdk.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.logging.Logger;

import org.mitumc.sdk.interfaces.BytesConvertible;
import org.mitumc.sdk.Constant;
import org.mitumc.sdk.exception.DummyMethodException;
import org.mitumc.sdk.exception.StringFormatException;

public class Util {
    public static String getClassName() {
        return Thread.currentThread().getStackTrace()[2].getClassName();
    }

    public static String getMethodName() {
        return Thread.currentThread().getStackTrace()[2].getMethodName();
    }

    public static String getName() {
        return getClassName() + "." + getMethodName();
    }

    public static String errMsg(String msg, String name) {
        return "error: " + msg + "; at " + name;
    }

    public static String linkErrMsgs(String... msgs) {
        String msg = "";
        for (String m : msgs) {
            msg += m + "\n";
        }
        return msg;
    }

    public static void loggingAndExit(Exception e) {
        Logger logger = Logger.getGlobal();
        logger.severe(e.getMessage());
        System.exit(1);
    }

    public static HashMap<String, String> parseHint(String hinted) throws StringFormatException {
        int idx = hinted.indexOf('~');
        if (idx == -1) {
            throw new StringFormatException(errMsg("invalid hinted string", getName()));
        }

        HashMap<String, String> map = new HashMap<String, String>();
        map.put("raw", hinted.substring(0, idx));
        map.put("hint", hinted.substring(idx + 1));

        return map;
    }

    public static HashMap<String, String> parseType(String typed) throws StringFormatException {
        RegExp.assertLongEnough(typed);

        HashMap<String, String> map = new HashMap<String, String>();
        map.put("raw", typed.substring(0, typed.length() - 3));
        map.put("type", typed.substring(typed.length() - 3));

        return map;
    }

    public static HashMap<String, String> parseDocumentID(String documentId) throws StringFormatException {
        RegExp.assertLongEnough(documentId);

        HashMap<String, String> map = new HashMap<>();
        map.put("id", documentId.substring(0, documentId.length() - 3));
        map.put("suffix", documentId.substring(documentId.length() - 3));

        return map;
    }

    public static HashMap<String, String> parseNFTID(String nid) throws StringFormatException {
        RegExp.assertNFTID(nid);

        int dash = nid.indexOf("-");

        HashMap<String, String> map = new HashMap<>();
        map.put("collection", nid.substring(0, dash));
        map.put("idx", nid.substring(dash + 1));

        return map;
    }

    public static Hint getHintFromString(String hint) throws StringFormatException {
        int idx = hint.indexOf("-" + Constant.VERSION);
        if (idx == -1) {
            throw new StringFormatException(Util.errMsg("no version with divider in target string", Util.getName()));
        }
        return Hint.get(hint.substring(0, idx));
    }

    public static TimeStamp getDateTimeStamp() {
        return TimeStamp.now();
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
        try {
            for (T obj : objs) {
                byte[] temp = ((BytesConvertible) obj).toBytes();
                arr.add(temp);
                byteLen += temp.length;
            }
        } catch (DummyMethodException e) {
            loggingAndExit(e);
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