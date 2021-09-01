package org.mitumc.sdk.operation;

import java.util.ArrayList;

import org.mitumc.sdk.Constant;
import org.mitumc.sdk.key.Keys;

public class OperationManager {
    public static Amount newAmount(String currency, long amount) {
        return new Amount(currency, amount);
    }

    public static CreateAccountsItem newCreateAccountsItem(Keys keys) {
        return new CreateAccountsItem(keys);
    }

    public static CreateAccountsItem newCreateAccountsItem(Keys keys, Amount[] amounts) {
        return new CreateAccountsItem(keys, amounts);
    }

    public static TransfersItem newTransfersItem(String receiver) throws Exception {
        return new TransfersItem(receiver);
    }

    public static TransfersItem newTransfersItem(String receiver, Amount[] amounts) {
        return new TransfersItem(receiver, amounts);
    }

    public static CreateDocumentsItem newCreateDocumentsItem(String fileHash, String currencyId) {
        return new CreateDocumentsItem(fileHash, currencyId);
    }

    public static CreateDocumentsItem newCreateDocumentsItem(String fileHash, String[] signers, String currencyId) {
        return new CreateDocumentsItem(fileHash, signers, currencyId);
    }

    public static SignDocumentsItem newSignDocumentsItem(String owner, int documentId, String currencyId) {
        return new SignDocumentsItem(owner, documentId, currencyId);
    }

    public static TransferDocumentsItem newTransferDocumentsItem(String owner, String receiver, int documentId, String currencyId) {
        return new TransferDocumentsItem(owner, receiver, documentId, currencyId);
    }

    public static CreateAccountsFact newCreateAccountsFact(String sender) {
        return new CreateAccountsFact(sender);
       
    }

    public static CreateAccountsFact newCreateAccountsFact(String sender, CreateAccountsItem[] items) {
        return new CreateAccountsFact(sender, items);
    }

    public static KeyUpdaterFact newKeyUpdaterFact(String target, String currencyId, Keys keys) {
        return new KeyUpdaterFact(target, currencyId, keys); 
    }

    public static TransfersFact newTransfersFact(String sender) {
        return new TransfersFact(sender);
    }

    public static TransfersFact newTransfersFact(String sender, TransfersItem[] items) {
        return new TransfersFact(sender, items);
    }

    public static BlockSignFact<CreateDocumentsItem> newBlockSignFact(String sender, CreateDocumentsItem[] items) {
        return new BlockSignFact<CreateDocumentsItem>(Constant.MBS_CREATE_DOCUMENTS_OPERATION_FACT, sender, items);
    }

    public static BlockSignFact<SignDocumentsItem> newBlockSignFact(String sender, SignDocumentsItem[] items) {
        return new BlockSignFact<SignDocumentsItem>(Constant.MBS_SIGN_DOCUMENTS_OPERATION_FACT, sender, items);
    }

    public static BlockSignFact<TransferDocumentsItem> newBlockSignFact(String sender, TransferDocumentsItem[] items) {
        return new BlockSignFact<TransferDocumentsItem>(Constant.MBS_TRANSFER_DOCUMENTS_OPERATION_FACT, sender, items);
    }

    public static Operation newOperation(OperationFact fact) {
        return new Operation(fact);
    }

    public static Operation newOperation(String memo, OperationFact fact) {
        return new Operation(memo, fact);
    }
}
