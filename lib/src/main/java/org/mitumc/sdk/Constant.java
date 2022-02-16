package org.mitumc.sdk;

// import java.io.FileNotFoundException;
// import java.io.FileReader;
// import java.io.IOException;
// import java.util.Properties;

// import org.mitumc.sdk.util.Util;

public class Constant {
    // public static Properties configProps;
    // public static Properties hintProps;

    public static final String VERSION = "v0.0.1";
    public static final String NETWORK_ID = "mitum";

    public static final String MC_ADDRESS = "mca";
    public static final String MC_TRANSFERS_OPERATION_FACT = "mitum-currency-transfers-operation-fact";
    public static final String MC_TRANSFERS_OPERATION = "mitum-currency-transfers-operation";
    public static final String MC_KEY = "mitum-currency-key";
    public static final String MC_KEYS = "mitum-currency-keys";
    public static final String MC_CREATE_ACCOUNTS_OPERATION_FACT = "mitum-currency-create-accounts-operation-fact";
    public static final String MC_CRAETE_ACCOUNTS_OPERATION = "mitum-currency-create-accounts-operation";
    public static final String MC_KEY_UPDATER_OPERATION_FACT = "mitum-currency-keyupdater-operation-fact";
    public static final String MC_KEY_UPDATER_OPERATION = "mitum-currency-keyupdater-operation";
    public static final String MC_ACCOUNT = "mitum-currency-account";
    public static final String MC_AMOUNT = "mitum-currency-amount";
    public static final String MC_CREATE_ACCOUNTS_MUL_AMOUNTS = "mitum-currency-create-accounts-multiple-amounts";
    public static final String MC_CREATE_ACCOUNTS_SIN_AMOUNT = "mitum-currency-create-accounts-single-amount";
    public static final String MC_TRANSFERS_ITEM_MUL_AMOUNTS = "mitum-currency-transfers-item-multi-amounts";
    public static final String MC_TRANSFERS_ITEM_SIN_AMOUNT = "mitum-currency-transfers-item-single-amount";

    public static final String KEY_PRIVATE = "mpr";
    public static final String KEY_PUBLIC = "mpu";
    // public static final String KEY_BTC_PRIVATE = "btc-priv";
    // public static final String KEY_BTC_PUBLIC = "btc-pub";
    // public static final String KEY_ETHER_PRIVATE = "ether-priv";
    // public static final String KEY_ETHER_PUBLIC = "ether-pub";
    // public static final String KEY_STELLAR_PRIVATE = "stellar-priv";
    // public static final String KEY_STELLAR_PUBLIC = "stellar-pub";

    public static final String BASE_FACT_SIGN = "base-fact-sign";
    public static final String SEAL = "seal";

    public static final String MBS_CREATE_DOCUMENTS_SINGLE_FILE = "mitum-blocksign-create-documents-single-file";
    public static final String MBS_CREATE_DOCUMENTS_OPERATION_FACT = "mitum-blocksign-create-documents-operation-fact";
    public static final String MBS_CREATE_DOCUMENTS_OPERATION = "mitum-blocksign-create-documents-operation";
    public static final String MBS_SIGN_ITEM_SINGLE_DOCUMENT = "mitum-blocksign-sign-item-single-document";
    public static final String MBS_SIGN_DOCUMENTS_OPERATION_FACT = "mitum-blocksign-sign-documents-operation-fact";
    public static final String MBS_SIGN_DOCUMENTS_OPERATION = "mitum-blocksign-sign-documents-operation";

    public static final String MBC_USER_DATA = "cui";
    public static final String MBC_LAND_DATA = "cli";
    public static final String MBC_VOTE_DATA = "cvi";
    public static final String MBC_HISTORY_DATA = "chi";

    public static final String MBC_CREATE_DOCUMENTS_ITEM = "mitum-create-documents-item";
    public static final String MBC_CREATE_DOCUMENTS_OPERATION_FACT = "mitum-create-documents-operation-fact";
    public static final String MBC_CREATE_DOCUMENTS_OPERATION = "mitum-create-documents-operation";
    public static final String MBC_UPDATE_DOCUMENTS_ITEM = "mitum-update-documents-item";
    public static final String MBC_UPDATE_DOCUMENTS_OPERATION_FACT = "mitum-update-documents-operation-fact";
    public static final String MBC_UPDATE_DOCUMENTS_OPERATION = "mitum-update-documents-operation";

    public static final String MBC_DOCTYPE_USER_DATA = "mitum-blockcity-document-user-data";
    public static final String MBC_DOCTYPE_LAND_DATA = "mitum-blockcity-document-land-data";
    public static final String MBC_DOCTYPE_VOTE_DATA = "mitum-blockcity-document-voting-data";
    public static final String MBC_DOCTYPE_HISTORY_DATA = "mitum-blockcity-document-history-data";

    public static final String MBC_DOCUMENT_INFO = "mitum-document-info";

    public static final String MBC_USER_DOCUMENT_ID = "mitum-blockcity-user-document-id";
    public static final String MBC_LAND_DOCUMENT_ID = "mitum-blockcity-land-document-id";
    public static final String MBC_VOTE_DOCUMENT_ID = "mitum-blockcity-voting-document-id";
    public static final String MBC_HISTORY_DOCUMENT_ID = "mitum-blockcity-history-document-id";

    public static final String MBC_USER_STATISTICS = "mitum-blockcity-user-statistics";
    public static final String MBC_VOTING_CANDIDATE = "mitum-blockcity-voting-candidate";

    // public Constant() {
    //     // try {
    //     //     this.configProps = loadConfig();
    //     //     this.hintProps = loadHint();
    //     // } catch(Exception e) {
    //     //     Util.raiseError("Fail to load properties.");
    //     // }
    // }

    // private Properties loadConfig() throws IOException, FileNotFoundException {
    //     Properties prop = new Properties();
    //     prop.load(new FileReader("properties/config.properties"));
    //     return prop;
    // }

    // private Properties loadHint() throws IOException, FileNotFoundException {
    //     Properties prop = new Properties();
    //     prop.load(new FileReader("properties/hint.properties"));
    //     return prop;
    // }
}