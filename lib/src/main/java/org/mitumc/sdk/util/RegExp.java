package org.mitumc.sdk.util;

import java.util.regex.Pattern;

import org.mitumc.sdk.Constant;

public class RegExp {
    public static final String EXP_ADDRESS = Constant.MC_ADDRESS;
    public static final String EXP_PRIVATE_KEY = Constant.KEY_PRIVATE;
    public static final String EXP_PUBLIC_KEY = Constant.KEY_PUBLIC;
    public static final String EXP_USER_DATA = Constant.MBC_USER_DATA;
    public static final String EXP_LAND_DATA = Constant.MBC_LAND_DATA;
    public static final String EXP_VOTE_DATA = Constant.MBC_VOTE_DATA;
    public static final String EXP_HISTORY_DATA = Constant.MBC_HISTORY_DATA;
    public static final String EXP_BLOCKSIGN_DATA = Constant.MBS_DOCUMENT_DATA;

    public static void assertLongEnough(String target) {
        if(target.length() < 3) {
            Util.raiseError("Target string is too short; RegExp.assertLongEnough(String target).");
        }
    }

    public static void assertAddress(String address) {
        assertLongEnough(address);
        if(!address.substring(address.length() - 3).equals(EXP_ADDRESS)) {
            Util.raiseError("Invalid type suffix; RegExp.assertAddress(String address).");
        }
        if(!Pattern.matches("^[a-zA-Z0-9]*$", address.substring(0, address.length() - 3))) {
            Util.raiseError("Invalid format; RegExp.assertAddress(String address).");
        }
    }

    public static void assertKey(String key){
        assertLongEnough(key);
        String suffix = key.substring(key.length() - 3);
        if(!(suffix.equals(EXP_PRIVATE_KEY) || suffix.equals(EXP_PUBLIC_KEY))) {
            Util.raiseError("Invalid type suffix; RegExp.assertKey(String key).");
        }
        if(!Pattern.matches("^[a-zA-Z0-9]*$", key.substring(0, key.length() - 3))) {
            Util.raiseError("Invalid format; RegExp.assertKey(String key).");
        }
    }

    public static void assertPrivateKey(String key) {
        assertLongEnough(key);
        if(!key.substring(key.length() - 3).equals(EXP_PRIVATE_KEY)) {
            Util.raiseError("Invalid type suffix; RegExp.assertPrivateKey(String key).");
        }
        if(!Pattern.matches("^[a-zA-Z0-9]*$", key.substring(0, key.length() - 3))) {
            Util.raiseError("Invalid format; RegExp.assertPrivateKey(String key).");
        }
    }

    public static void assertPublicKey(String key) {
        assertLongEnough(key);
        if(!key.substring(key.length() - 3).equals(EXP_PUBLIC_KEY)) {
            Util.raiseError("Invalid type suffix; RegExp.assertPublicKey(String key).");
        }
        if(!Pattern.matches("^[a-zA-Z0-9]*$", key.substring(0, key.length() - 3))) {
            Util.raiseError("Invalid format; RegExp.assertPublicKey(String key).");
        }
    }

    public static void assertBlockSignDocumentId(String id) {
        assertLongEnough(id);
        String suffix = id.substring(id.length() - 3);
        if(!suffix.equals(EXP_BLOCKSIGN_DATA)) {
            Util.raiseError("Invalid type suffix; RegExp.assertBlockSignDocumentId(String id).");
        }
    }

    public static void assertBlockCityDocumentId(String id) {
        assertLongEnough(id);
        String suffix = id.substring(id.length() - 3);
        if(!(suffix.equals(EXP_USER_DATA) || suffix.equals(EXP_LAND_DATA) || suffix.equals(EXP_VOTE_DATA) || suffix.equals(EXP_HISTORY_DATA))) {
            Util.raiseError("Invalid type suffix; RegExp.assertBlockCityDocumentId(String id).");
        }
    }
    
    public static void assertUserData(String id) {
        assertLongEnough(id);
        if(!id.substring(id.length() - 3).equals(EXP_USER_DATA)) {
            Util.raiseError("Invalid type suffix; RegExp.assertUserData(String id).");
        }
    }

    public static void assertLandData(String id) {
        assertLongEnough(id);
        if(!id.substring(id.length() - 3).equals(EXP_LAND_DATA)) {
            Util.raiseError("Invalid type suffix; RegExp.assertLandData(String id).");
        }
    }

    public static void assertVoteData(String id) {
        assertLongEnough(id);
        if(!id.substring(id.length() - 3).equals(EXP_VOTE_DATA)) {
            Util.raiseError("Invalid type suffix; RegExp.assertVoteData(String id).");
        }
    }

    public static void assertHistoryData(String id) {
        assertLongEnough(id);
        if(!id.substring(id.length() - 3).equals(EXP_HISTORY_DATA)) {
            Util.raiseError("Invalid type suffix; RegExp.assertHistoryData(String id).");
        }
    }

    public static void assertNFTID(String id) {
        int dash = id.indexOf("-");
        if(dash < 0) {
            Util.raiseError("No divider(-) in target string; RegExp.assertNFTID(String id).");
        }
        
        assertLongEnough(id.substring(0, dash));
        try {
            Integer.parseInt(id.substring(dash + 1));
        } catch(Exception e) {
            Util.raiseError("Collection idx is not integer; RegExp.assertNFTID(String id).");
        }
    }
}