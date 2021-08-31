package org.mitumc.sdk;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

import org.mitumc.sdk.util.Util;

public class Constant {
    public static Properties configProps;
    public static Properties hintProps;

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

    public static final String KEY_BTC_PRIVATE = "btc-priv";
    public static final String KEY_BTC_PUBLIC = "btc-pub";
    public static final String KEY_ETHER_PRIVATE = "ether-priv";
    public static final String KEY_ETHER_PUBLIC = "ether-pub";
    public static final String KEY_STELLAR_PRIVATE = "stellar-priv";
    public static final String KEY_STELLAR_PUBLIC = "stellar-pub";

    public static final String BASE_FACT_SIGN = "base-fact-sign";
    public static final String SEAL = "seal";

    public static final String MBS_CREATE_DOCUMENTS_SINGLE_FILE = "mitum-blocksign-create-documents-single-file";
    public static final String MBS_CREATE_DOCUMENTS_OPERATION_FACT = "mitum-blocksign-create-documents-operation-fact";
    public static final String MBS_CREATE_DOCUMENTS_OPERATION = "mitum-blocksign-create-documents-operation";
    public static final String MBS_TRANSFER_ITEM_SINGLE_DOCUMENT = "mitum-blocksign-transfer-item-single-document";
    public static final String MBS_TRANSFER_DOCUMENTS_OPERATION_FACT = "mitum-blocksign-transfer-documents-operation-fact";
    public static final String MBS_TRANSFER_DOCUMENTS_OPERATION = "mitum-blocksign-transfer-documents-operation";
    public static final String MBS_SIGN_ITEM_SINGLE_DOCUMENT = "mitum-blocksign-sign-item-single-document";
    public static final String MBS_SIGN_DOCUMENTS_OPERATION_FACT = "mitum-blocksign-sign-documents-operation-fact";
    public static final String MBS_SIGN_DOCUMENTS_OPERATION = "mitum-blocksign-sign-documents-operation";

    public Constant() {
        try {
            this.configProps = loadConfig();
            this.hintProps = loadHint();
        } catch(Exception e) {
            Util.raiseError("Fail to load properties.");
        }
    }

    private Properties loadConfig() throws IOException, FileNotFoundException {
        Properties prop = new Properties();
        prop.load(new FileReader("properties/config.properties"));
        return prop;
    }

    private Properties loadHint() throws IOException, FileNotFoundException {
        Properties prop = new Properties();
        prop.load(new FileReader("properties/hint.properties"));
        return prop;
    }
}