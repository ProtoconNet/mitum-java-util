# mitum-java-util

'mitum-java-util' will introduce the usage of [mitum-currency](https://github.com/ProtoconNet/mitum-currency) and [mitum-data-blocksign](https://github.com/ProtoconNet/mitum-data-blocksign) for Java.

'mitum-java-util' now supports generating fact, operation and sign with Stellar keypair.

Using BTC, ETHER keypair, and adding fact signature to operation json files will be available later.

## Installation

Recommended requirements for 'mitum-java-util' are,

* Java - OpenJDK v16.0.1
* Javac - javac v16.0.1

Additionally, This project is using below external Java libraries.

* [Java Stellar SDK v0.26.0](https://github.com/stellar/java-stellar-sdk)
* [Bouncy Castle v1.46 for JDK 16](https://mvnrepository.com/artifact/org.bouncycastle/bcprov-jdk16/1.46)
* [bitcoinj v0.14.7](https://bitcoinj.org/)

```sh
$ java -version
openjdk version "16.0.1" 2021-04-20
OpenJDK Runtime Environment (build 16.0.1+9-Ubuntu-120.04)
OpenJDK 64-Bit Server VM (build 16.0.1+9-Ubuntu-120.04, mixed mode, sharing)

$ javac -version
javac 16.0.1
```
[Download jar file](release/) and include the package to your project.

#### Gradle
```sh
implementation files('./lib/mitum-java-util.jar')
```

Replace './lib/mitum-java-util.jar' with your own file path.

## Generate New Operation

### Operations

'mitum-java-util' provides three operations of 'mitum-currency',

* 'Create-Accounts' creates an account corresponding to any public key with a pre-registered account.
* 'Key-Updater' updates the public key of the account to something else.
* 'Transfers' transfers tokens from the account to another account.

'mitum-currency' supports various kinds of operations, but 'mitum-java-util' will provide these frequently used operations.

In addition, 'mitum-java-util' provides three operations of 'mitum-data-blocksign',

* 'Create-Documents' creates an document with file hash.
* 'Sign-Documents' signs the document.
* 'Transfer-Documents' transfers documents from the account to another account.

### Prerequisite

Before generating new operation, you should check below for 'mitum-currency',

* 'private key' of source account to generate signatures (a.k.a signing key)
* 'public address' of source account
* 'public key' of target account
* 'network id'

Additionally, you should check below for 'mitum-data-blocksign',

* 'filehash' for Create-Documents
* 'owner' and 'documentid' for Sign-Documents and Transfer-Documents

Note that the package root of 'mitum-java-util' is 'org.mitumc.sdk'.

* Every key, address, and keypair must be that of mitum-currency.

### KeyManager (org.mitumc.sdk.key.KeyManager);

The package provides 'KeyManager' class to manage classes about key.

Methods that 'KeyManager' supports are,

```java
>>> Key newKey(String key, int weight);
>>> Keys newKeys(int threshold);
>>> Keys newKeys(Key[] keys, int threshold); 
>>> Object getNewKeypair(String keypairType);
>>> Object getKeypairFromPrivateKey(String hintedKey);
>>> Object getKeypairFromPrivateKey(String rawKey, String keyType);
```

#### KeyManager - newKey / newKeys

```java
>>> import org.mitumc.sdk.key.KeyManager.newKey;
>>> import org.mitumc.sdk.key.KeyManager.newKeys;

>>> Key key = newKey("SAA7UAG4BYG6PZCPQZ5BPBA5OMOJVH5BIKZQFIZRM6TPFJ5TQW3QAHE3:stellar-priv-v0.0.1", 100);
>>> Keys keys = newKeys(100);
>>> keys.addKey(key);

>>> Keys keys2 = newKeys(new Key[]{key}, 100);
```

Note that 'keys' and 'keys2' work same.

The usage of KeyManager.getNewKeypair and KeyManager.getKeyPairFromPrivateKey will be introduces in the last section.

### OperationManager (org.mitumc.sdk.operation.OperationManager)

The package provides 'OperationManager' class to manage classes about operation.

Methods that 'OperationManager' supports are,

```java
>>> Amount newAmount(String currency, long amount);

>>> CreateAccountsItem newCreateAccountsItem(Keys keys);
>>> CreateAccountsItem newCreateAccountsItem(Keys keys, Amount[] amounts);

>>> TransfersItem newTransfersItem(String receiver);
>>> TransfersItem newTransfersItem(String receiver, Amount[] amounts);

>>> CreateDocumentsItem newCreateDocumentsItem(String fileHash, String currencyId);
>>> CreateDocumentsItem newCreateDocumentsItem(String fileHash, String[] signers, String currencyId);

>>> SignDocumentsItem newSignDocumentsItem(String owner, int documentId, String currencyId);

>>> TransferDocumentsItem newTransferDocumentsItem(String owner, String receiver, int documentId, String currencyId);

>>> CreateAccountsFact newCreateAccountsFact(String sender);
>>> CreateAccountsFact newCreateAccountsFact(String sender, CreateAccountsItem[] items);

>>> KeyUpdaterFact newKeyUpdaterFact(String target, String currencyId, Keys keys);

>>> TransfersFact newTransfersFact(String sender);
>>> TransfersFact newTransfersFact(String sender, TransfersItem[] items);

>>> BlockSignFact newBlockSignFact(String sender, CreateDocumentsItem[] items);
>>> BlockSignFact newBlockSignFact(String sender, SignDocumentsItem[] items);
>>> BlockSignFact newBlockSignFact(String sender, TransferDocumentsItem[] items);

>>> Operation newOperation(OperationFact fact);
>>> Operation newOperation(String memo, OperationFact fact);
>>> Operation newOperation(OperationFact fact, String networkId);
>>> Operation newOperation(String memo, OperationFact fact, String networkId);
```

The usage of OperationManager will be introduces in the next section.

### JSONParser (org.mitumc.sdk.JSONParser)

You can create a json file of generated operation object without 'JSONParser'. However, I recommend to use 'JSONParser' for convenience.

Methods that 'JSONParser' supports are,

```java
>>> void createJSON(shadow.com.google.gson.JsonObject target, String fpName);
>>> void createJSON(HashMap target, String fpName);
```

A use-case of 'JSONParser' will be introduced in the next part, too.

### Generate Create-Accounts 

For new account, 'currency id' and 'initial amount' must be set. With source account, you can create and register new account of target public key.

#### Usage

```java
>>> import org.mitumc.sdk.key.*;
>>> import org.mitumc.sdk.operation.*;

>>> String sourcePriv = "SAA7UAG4BYG6PZCPQZ5BPBA5OMOJVH5BIKZQFIZRM6TPFJ5TQW3QAHE3:stellar-priv-v0.0.1";
>>> String sourceAddr = "PGKUST2YyoS1ujCKpXKUgVcn1Vk323cFP3pN5Z2Gf7k:mca-v0.0.1";

>>> String targetPub = "kQxEqq6t32EEegAvV4oqAGcwQJojertuU7EdsvZYAB9X:btc-pub-v0.0.1";

>>> Key key = KeyManager.newKey(targetPub, 100);
>>> Keys keys = KeyManager.newKeys(new Key[]{key}, 100);

>>> Amount amount = OperationManager.newAmount("MCC", 1000);
>>> CreateAccountsItem item = OperationManager.newCreateAccountsItem(keys, new Amount[]{amount});

>>> CreateAccountsFact fact = OperationManager.newCreateAccountsFact(sourceAddr, new CreateAccountsItem[]{item});

>>> Operation operation = OperationManager.newOperation(fact);
>>> operation.addSign(sourcePriv);

>>> JSONParser.createJSON(operation.toDict(), "createaccounts.json");
```

You must add new fact signature by addFactSign before creating operation json files.

If you would like to change 'network id' for the operation, use 'newOperation(fact, networkId)' or 'newOperation(memo, fact, networkId)' instead of 'newOperation(fact)' and 'newOperation(memo, fact)'

With 'JSONParser.createJSON', the result format will be like [this](example/createaccounts.json). (Each value is up to input arguments and time)

### Generate Key-Updater

Key-Updater literally supports to update source public key to something else.

#### Usage

```java
>>> import org.mitumc.sdk.key.*;
>>> import org.mitumc.sdk.operation.*;

>>> String sourcePriv = "SAA7UAG4BYG6PZCPQZ5BPBA5OMOJVH5BIKZQFIZRM6TPFJ5TQW3QAHE3:stellar-priv-v0.0.1";
>>> String sourceAddr = "PGKUST2YyoS1ujCKpXKUgVcn1Vk323cFP3pN5Z2Gf7k:mca-v0.0.1";

>>> String targetPub = "kQxEqq6t32EEegAvV4oqAGcwQJojertuU7EdsvZYAB9X:btc-pub-v0.0.1";

>>> Key key = KeyManager.newKey(targetPub, 100);
>>> Keys keys = KeyManager.newKeys(new Key[]{key}, 100);

>>> KeyUpdaterFact fact = OperationManager.newKeyUpdaterFact(sourceAddr, "MCC", keys);
>>> Operation operation = OperationManager.newOperation(fact);
>>> operation.addSign(sourcePriv);

>>> JSONParser.createJSON(operation.toDict(), "keyupdater.json");
```

Then keys of the source account will be replaced with target keys.

### Generate Transfers

To generate an operation, you must prepare target address, not public key. Transfers supports to send tokens to another account.

#### Usage

```java
>>> import org.mitumc.sdk.key.*;
>>> import org.mitumc.sdk.operation.*;

>>> String sourcePriv = "SAA7UAG4BYG6PZCPQZ5BPBA5OMOJVH5BIKZQFIZRM6TPFJ5TQW3QAHE3:stellar-priv-v0.0.1";
>>> String sourceAddr = "PGKUST2YyoS1ujCKpXKUgVcn1Vk323cFP3pN5Z2Gf7k:mca-v0.0.1";

>>> String targetAddr = "2mFPBWgASRvtuiJ6P9DuVF6f4kmnJxn7p4khjDLXBTmP:mca-v0.0.1";

>>> Amount amount = OperationManager.newAmount("MCC", 1000);
>>> TransfersItem item = OperationManager.newTransfersItem(targetAddr, new Amount[]{amount});

>>> TransfersFact fact = OperationManager.newTransfersFact(sourceAddr, new TransfersItem[]{item});

>>> Operation operation = OperationManager.newOperation(fact);
>>> operation.addSign(sourcePriv);

>>> JSONParser.createJSON(operation.toDict(), "transfers.json");
```

### Generate Create-Documents

To generate an operation, you must prepare file-hash. Create-Document supports to create documents with setting signers who must sign them.

#### Usage

```java
>>> import org.mitumc.sdk.key.*;
>>> import org.mitumc.sdk.operation.*;

>>> String sourcePriv = "SAA7UAG4BYG6PZCPQZ5BPBA5OMOJVH5BIKZQFIZRM6TPFJ5TQW3QAHE3:stellar-priv-v0.0.1";
>>> String sourceAddr = "PGKUST2YyoS1ujCKpXKUgVcn1Vk323cFP3pN5Z2Gf7k:mca-v0.0.1";

>>> CreateDocumentsItem item = OperationManager.newCreateDocumentsItem("abcd:mbfh-v0.0.1", "MCC");
>>> BlockSignFact fact = OperationManager.newBlockSignFact(sourceAddr, new CreateDocumentsItem[]{item});

>>> Operation operation = OperationManager.newOperation(fact);
>>> operation.addSign(sourcePriv);

>>> JSONParser.createJSON(operation.toDict(), "createdocuments.json");
```

### Generate Sign-Documents

To generate an operation, you must prepare owner and document id. Sign-Document supports to sign documents registered by 'mitum-data-blocksign'

#### Usage

```java
>>> import org.mitumc.sdk.key.*;
>>> import org.mitumc.sdk.operation.*;

>>> String sourcePriv = "SAA7UAG4BYG6PZCPQZ5BPBA5OMOJVH5BIKZQFIZRM6TPFJ5TQW3QAHE3:stellar-priv-v0.0.1";
>>> String sourceAddr = "PGKUST2YyoS1ujCKpXKUgVcn1Vk323cFP3pN5Z2Gf7k:mca-v0.0.1";

>>> SignDocumentsItem item = OperationManager.newSignDocumentsItem(sourceAddr, 0, "MCC");
>>> BlockSignFact fact = OperationManager.newBlockSignFact(sourceAddr, new SignDocumentsItem[]{item});

>>> Operation operation = OperationManager.newOperation(fact);
>>> operation.addSign(sourcePriv);

>>> JSONParser.createJSON(operation.toDict(), "signdocuments.json");
```

### Generate Transfer-Documents

To generate an operation, you must prepare owner and document id. Transfer-Document supports to transfer documents to other account.

#### Usage

```java
>>> import org.mitumc.sdk.key.*;
>>> import org.mitumc.sdk.operation.*;

>>> String sourcePriv = "SAA7UAG4BYG6PZCPQZ5BPBA5OMOJVH5BIKZQFIZRM6TPFJ5TQW3QAHE3:stellar-priv-v0.0.1";
>>> String sourceAddr = "PGKUST2YyoS1ujCKpXKUgVcn1Vk323cFP3pN5Z2Gf7k:mca-v0.0.1";

>>> String targetAddr = "2mFPBWgASRvtuiJ6P9DuVF6f4kmnJxn7p4khjDLXBTmP:mca-v0.0.1";

>>> TransferDocumentsItem item = OperationManager.newTransferDocumentsItem(sourceAddr, targetAddr, 0, "MCC");
>>> BlockSignFact fact = OperationManager.newBlockSignFact(sourceAddr, new TransferDocumentsItem[]{item});

>>> Operation operation = OperationManager.newOperation(fact);
>>> operation.addSign(sourcePriv);

>>> JSONParser.createJSON(operation.toDict(), "transferdocuments.json");
```

## Generate New Seal

Supports you to generate a seal json file such that the seal is able to consist of several operations. Those operations can be any type 'mitum-py-util' provides.

### Prerequisite

To generate a seal, 'mitum-java-util' requires,

* 'signing key'
* 'a list of pre-constructed operations' not empty

Registration of 'signing key' is not necessary.

### SealManager (org.mitumc.sdk.SealManager)

'SealManager' class supports to generate Seal Object as HashMap.

If you would like to use your own network id, use 'newSeal(signKey, operations, networkId)' instead of 'newSeal(signKey, operations)'.

```java
>>> HashMap<String, Object> newSeal(String signKey, Operation[] operations); // default network id: 'mitum'
>>> HashMap<String, Object> newSeal(String signKey, Operation[] operations, String networkId);
```

### Usage

First of all, suppose that every operation is that generated by 'OperationManager'. (createAccounts, keyUpdater, Transfers and etc)

```java
>>> import org.mitumc.sdk.SealManager;
>>> import org.mitumc.sdk.JSONParser;

... omitted
''' Create each operation [createAccounts, keyUpdater, transfers, etc] with OperationManager. See above sections.
'''
...

>>> String signKey = "SAA7UAG4BYG6PZCPQZ5BPBA5OMOJVH5BIKZQFIZRM6TPFJ5TQW3QAHE3:stellar-priv-v0.0.1";

>>> HashMap<String, Object> seal = SealManager.newSeal(signKey, new Operation[]{operation}, "mitum");
>>> JSONParser.createJSON(seal, "seal.json");
```

Then the result format of generateFile() will be like [this](example/seal.json). (Each value is up to input arguments and time)

## Send Messages to Network

Send created json files to the network by 'mitum-data-blocksign'.

See [mitum-data-blocksign](https://github.com/ProtoconNet/mitum-data-blocksign).

## Sign Message

Sign message with btc, ether, stellar keypair.

Now only stellar keypair is available. 'mitum-java-util' will supports BTC and ETHER soon.

### Usage

#### Generate Keypair

```java
>>> import org.mitumc.sdk.key.KeyManager;

>>> STELLARKeypair keypair = (STELLARKeypair) KeyManager.getNewKeypair(BaseKeypair.KEYPAIR_TYPE_STELLAR);

>>> keypair.getPrivateKey(); // returns private key of the keypair
>>> keypair.getPublicKey(); // returns public key of the keypair

>>> String key = "SAA7UAG4BYG6PZCPQZ5BPBA5OMOJVH5BIKZQFIZRM6TPFJ5TQW3QAHE3:stellar-priv-v0.0.1";
>>> STELLARKeypair derivedKeypair = (STELLARKeypair) KeyManager.getKeypairFromPrivateKey(key);

// keypair and derivedKeypair works same
```

#### Sign Message

Each keypair supports 'sign' method that generates bytes format signature by signing bytes format message.

If you want to get signature for 'mitum-currency', use 'base58' to encode the signature.

```java
// Omit steps for generating keypair
>>> String message = "mitum";
>>> byte[] signedMessage = keypair.sign(message.getBytes());
```

## Add Fact Signature to Operation

Use 'Signer.addSignToOperation' to add new fact signature to "fact_signs".

After adding a fact signature, operation hash is always changed.

### Signer (org.mitumc.sdk.Signer)

```java
>>> HashMap<String, Object> addSignToOperation(String signKey, JsonObject operation, String networkId);
>>> HashMap<String, Object> addSignToOperation(String signKey, JsonObject operation);
>>> HashMap<String, Object> addSignToOperation(String signKey, String operationPath, String networkId);
>>> HashMap<String, Object> addSignToOperation(String signKey, String operationPath);
```

```java
import org.mitumc.sdk.Signer;
import org.mitumc.sdk.JSONParser;

>>> String key = "SAA7UAG4BYG6PZCPQZ5BPBA5OMOJVH5BIKZQFIZRM6TPFJ5TQW3QAHE3:stellar-priv-v0.0.1";

>>> HashMap<string, Object> newOper = Signer.addSignToOperation(key, "operation.json", "mitum");
>>> JSONParser.createJSON(newOper, "newOperation.json");
```

Signer class doesn't create json file of new operation.

Use 'JSONParser' if you need.