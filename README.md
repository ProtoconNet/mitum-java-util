# mitum-java-util

'mitum-java-util' will introduce the usage of [mitum-currency](https://github.com/ProtoconNet/mitum-currency), [mitum-data-blocksign](https://github.com/ProtoconNet/mitum-data-blocksign) and [mitum-blockcity](https://github.com/ProtoconNet/mitum-blockcity) for Java.

'mitum-java-util' now supports generating fact, operation and sign with btc wif based keypairs.

## Installation

Recommended requirements for 'mitum-java-util' are,

* Java - OpenJDK v17.0.1
* Javac - javac v17.0.1

Additionally, This project is using below external Java libraries.

* [bitcoinj v0.14.7](https://bitcoinj.org/)

And you must download and add [ecdsa-keygen-java v1.4](https://github.com/wyuinche/ecdsa-keygen-java) to the project to build.

```sh
$ java -version
java 17.0.1 2021-10-19 LTS
Java(TM) SE Runtime Environment (build 17.0.1+12-LTS-39)
Java HotSpot(TM) 64-Bit Server VM (build 17.0.1+12-LTS-39, mixed mode, sharing)

$ javac -version
javac 17.0.1
```
[Download jar file](release/) and include the package to your project.

The latest version is `mitum-java-util-2.2.2-jdk17.jar`.

#### Gradle
```sh
implementation files('./lib/mitum-java-util-2.2.2-jdk17.jar')
```

Replace `./lib/mitum-java-util-2.2.2-jdk17.jar` with your file path.

## Index

||Title|
|---|---|
|1|[Generate Keypairs](#generate-keypairs)|
|2|[How to Use Generator](#how-to-use-generator)|
|2-1|[Create Generator](#create-generator)|
|2-2|[Get Address from Keys](#get-address-from-keys)|
|2-3|[Generate JSON File from Operation and Seal](#generate-json-file-from-operation-and-seal)|
|3|[Generate Currency Operations](#generate-currency-operations)|
|3-1|[Generate Create-Accounts](#generate-create-accounts)|
|3-2|[Generate Key-Updater](#generate-key-updater)|
|3-3|[Generate Transfers](#generate-transfers)|
|4|[Generate BlockSign Operations](#generate-blocksign-operations)|
|4-1|[Generate BlockSign Create-Documents](#generate-blocksign-create-documents)|
|4-2|[Generate BlockSign Sign-Documents](#generate-blocksign-sign-documents)|
|5|[Generate BlockCity Operations](#generate-blockcity-operations)|
|5-1|[Generate User Document](#generate-user-document)|
|5-2|[Generate Land Document](#generate-land-document)|
|5-3|[Generate Vote Document](#generate-vote-document)|
|5-4|[Generate History Document](#generate-history-document)|
|5-5|[Generate BlockCity Create-Documents](#generate-blockcity-create-documents)|
|5-6|[Generate BlockCity Update-Documents](#generate-blockcity-update-documents)|
|6|[Generate New Seal](#generate-new-seal)|
|7|[Send Messages to Network](#send-messages-to-network)|
|8|[Sign Message](#sign-message)|
|9|[Add Fact Signature to Operation](#add-fact-signature-to-operation)|

<br />

|Class|
|---|
|[Keypair](#keypair)|
|[Generator](#generator)|
|[CurrencyGenerator](#currencygenerator)|
|[BlockSignGenerator](#blocksigngenerator)|
|[JSONParser](#jsonparser)|
|[Signer](#signer)|

<br />

|Appendix|
|---|
|[About Time Stamp](#about-time-stamp)|

## Generate Keypairs

`mitum-java-util` supports to generate keypair from private key and seed.

And you can just get a new keypair by `Keypair.create()`.

There are fixed type suffixes for private key, public key and address.

* private key -> mpr
* public key -> mpu
* account address -> mca

### Keypair

* `org.mitumc.sdk.key.Keypair`

`Keypair.create()` returns new keypair for mitum.

You can use `Keypair.fromPrivateKey(String key)` when you already have a private key.

If you have seed of your private key, just use `Keypair.fromSeed(String seed)`.

Or, you can use `Keypair.fromSeed(byte[] seed)`.

`new String(seed).length()` should be longer than or equal to 36.

```java
Keypair create();
Keypair fromSeed(seed);
Keypair fromPrivateKey(privKey);
```

#### Usage

```java
/*
import org.mitumc.sdk.key.Keypair;
*/
Keypair kp = Keypair.create();

kp.getPrivateKey(); // returns private key of the keypair
kp.getPublicKey(); // returns public key of the keypair

String key = "KzafpyGojcN44yme25UMGvZvKWdMuFv1SwEhsZn8iF8szUz16jskmpr";
Keypair pkp = Keypair.fromPrivateKey(key);

String seed =  "This is a seed for the example; Keypair.fromSeed()";
Keypair skp = Keypair.fromSeed(seed);

byte[] bseed = seed.getBytes();
Keypair skp = Keypair.fromSeed(bseed);
```

## How to Use Generator

### Operations

'mitum-java-util' provides three operations of 'mitum-currency',

* `Create-Accounts` creates an account corresponding to any public key with a pre-registered account.
* `Key-Updater` updates the public key of the account to something else.
* `Transfers` transfers tokens from the account to another account.

'mitum-currency' supports various kinds of operations, but 'mitum-java-util' will provide these frequently used operations.

In addition, 'mitum-java-util' provides two operations of 'mitum-data-blocksign',

* `Create-Documents` creates an document with file hash.
* `Sign-Documents` signs the document.

Also, it supports to create three types of blockcity document and two types of operation.

* `Create-Documents` create an document with document id.
* `Update-Documents` update the state of the document.

### Prerequisite

Before generating new operation, you should check below for 'mitum-currency',

* `private key` of source account to generate signatures (a.k.a signing key)
* `public address` of source account
* `public key` of target account
* `network id`

Additionally, you should check below for 'mitum-data-blocksign',

* `filehash` for Create-Documents
* `owner` and 'documentid' for Sign-Documents and Transfer-Documents

Note that the package root of 'mitum-java-util' is `org.mitumc.sdk`.

* Every key, address, and keypair must be that of mitum-currency.

### Create Generator

`mitum-java-util` supports to generate operations of `mitum-currency`, `mitum-data-blocksign` and `mitum-blockcity`.

#### Generator

* `org.mitumc.sdk.Generator`

You can use `Generator` by this library.

First of all, set network id by `Generator.get(id)`.

For `mitum-currency`, use `Generator.currency()`.

For `mitum-data-blocksign`, use `Generator.blockSign()`.

For `mitum-blockcity`, use `Generator.blockCity()`.

```java
String id = "mitum";
Generator generator = Generator.get(id);

CurrencyGenerator cgn = generator.currency(); // org.mitumc.sdk.operation.currency.CurrencyGenerator;
BlockSignGenerator bsgn = generator.blockSign(); // org.mitumc.sdk.operation.blocksign.BlockSignGenerator;
BlockCityGenerator bcgn = generator.blockCity(); // org.mitumc.sdk.operation.blockcity.BlockCityGenerator;
```

#### CurrencyGenerator

* `org.mitumc.sdk.operation.currency.CurrencyGenerator`

Using `CurrencyGenerator`, below methods are available.

```java
Key key(String key, int weight);
Keys keys(Key[] keys, int threshold); 

Amount amount(String currency, String amount);

CreateAccountsItem getCreateAccountsItem(Keys keys, Amount[] amounts);
TransfersItem getTransfersItem(String receiver, Amount[] amounts);

CreateAccountsFact getCreateAccountsFact(String sender, CreateAccountsItem[] items);
KeyUpdaterFact getKeyUpdaterFact(String target, String currencyId, Keys keys);
TransfersFact getTransfersFact(String sender, TransfersItem[] items);
```

#### BlockSignGenerator

* `org.mitumc.sdk.operation.blocksign.BlockSignGenerator`

Using `BlockSignGenerator`, below methods are available.

```java
CreateDocumentsItem getCreateDocumentsItem(String fileHash, int documentId, String signcode, String title, int size, String currencyId, String[] signers, String[] signcodes);
SignDocumentsItem getSignDocumentsItem(String owner, int documentId, String currencyId);

BlockSignFact getBlockSignFact(String sender, CreateDocumentsItem[] items);
BlockSignFact getBlockSignFact(String sender, SignDocumentsItem[] items);
```

#### BlockCityGenerator

* `org.mitumc.sdk.operation.blockcity.BlockCityGenerator`

Using `BlockCityGenerator`, below methods are available.

```java
@Deprecated Candidate candidate(String address, String nickname, String manifest);
Candidate candidate(String address, String nickname String manifest, int count);
Info info(String docType, String documentId);
UserStatistics userStatistics(int hp, int strength, int agility, int dexterity, int charisma, int intelligence, int vital);

Document document(Info info, String owner, int gold, int bankGold, UserStatistics statistics);
Document document(Info info, String owner, String address, String area, String renter, String account, String rentDate, int period);
Document document(Info info, String owner, int round, String endTime, Candidate[] candidates, String bossName, String account, String office);
Document document(Info info, String owner, String name, String account, String date, String usage, String app);

BlockCityItem getCreateDocumentsItem(T document, String currencyId);
BlockCityItem getUpdateDocumentsItem(T document, String currencyId);

BlockCityFact getCreateDocumentsFact(String sender, BlockCityItem<T>[] items);
BlockCityFact getUpdateDocumentsFact(String sender, BlockCityItem<T>[] items);
```

#### Generate Operation by Generator

`Generator` provides methods for generating operation and seal.

```java
Operation getOperation(OperationFact fact);
Operation getOperation(String memo, OperationFact fact);
HashMap<String, Object> getSeal(String signKey, Operation[] operations);
```

The usage of those methods will be introduced in the next section.

### Get Address from Keys

You can calculate the address of the account by its keys.

Each key in the account has a corresponding weight(1 <= weight <= 100).

And, the account has threshold(1 <= threshold <= 100) which should be smaller than or equal to the sum of all weights of account keys.

To get address, use `CurrencyGenerator`.

#### How to Get Address

```java
/*
import org.mitumc.sdk.Generator
import org.mitumc.key.Key
import org.mitumc.key.Keys
*/
Generator generator = Generator.get("mitum");

Key key = generator.currency().key("24TbbrNYVngpPEdq6Zc5rD1PQSTGQpqwabB9nVmmonXjqmpu", 100);
Keys keys = generator.currency().keys(100);
keys.addKey(key);

Keys keys2 = generator.currency().keys(new Key[]{key}, 100);

String address = keys.getAddress(); // your address
```

Note that 'keys' and 'keys2' work same.

### Generate JSON File from Operation and Seal

#### JSONParser

* `org.mitumc.sdk.JSONParser`

You can create a json file of generated operation object without `JSONParser`. However, I recommend to use `JSONParser` for convenience.

Methods that `JSONParser` supports are,

```java
JsonObject getObjectFromJSONFile(String fpName);
JsonObject getObjectFromMap(HashMap<String, Object> target, String fpName);
void createJSON(JsonObject target, String fpName);
void createJSON(HashMap target, String fpName);
```

A use-case of `JSONParser` will be introduced in the next part, too.

## Generate Currency Operations

This part explains how to generate currency operations by `Generator`.

Supported operations are

* Create-Accounts
* Key-Updater
* Transfers

### Generate Create-Accounts 

For new account, `currency id` and `initial amount` must be set. With source account, you can create and register new account of target public key.

#### Usage

```java
/*
import org.mitumc.sdk.key.*;
import org.mitumc.sdk.Generator;
import org.mitumc.sdk.JSONParser;
import org.mitumc.sdk.operation.Operation;
import org.mitumc.sdk.operation.currency.*;
*/

String sourcePriv = "KzafpyGojcN44yme25UMGvZvKWdMuFv1SwEhsZn8iF8szUz16jskmpr";
String sourceAddr = "FcLfoPNCYjSMnxLPiQJQFGTV15ecHn3xY4J2HNCrqbCfmca";

String targetPub = "knW2wVXH399P9Xg8aVjAGuMkk3uTBZwcSpcy4aR3UjiAmpu";

Generator gn = Generator.get("mitum");

Key key = gn.currency().key(targetPub, 100);
Keys keys = gn.currency().keys(new Key[]{key}, 100);

Amount amount = gn.currency().amount("MCC", "1000");
CreateAccountsItem item = gn.currency().getCreateAccountsItem(keys, new Amount[]{amount});

CreateAccountsFact fact = gn.currency().getCreateAccountsFact(sourceAddr, new CreateAccountsItem[]{item});

Operation operation = gn.getOperation(fact);
operation.addSign(sourcePriv);

JSONParser.createJSON(operation.toDict(), "createaccounts.json");
```

You must add new fact signature by `addSign()` before creating operation json files.

If you would like to change 'network id' for the operation, use `getOperation(fact, networkId)` or `getOperation(memo, fact, networkId)` instead of `getOperation(fact)` and `getOperation(memo, fact)`

With `JSONParser.createJSON(target, fileName)`, the result format will be like [this](example/createaccounts.json). (Each value is up to input arguments and time)

### Generate Key-Updater

Key-Updater literally supports to update source public key to something else.

#### Usage

```java
/*
import org.mitumc.sdk.key.*;
import org.mitumc.sdk.Generator;
import org.mitumc.sdk.JSONParser;
import org.mitumc.sdk.operation.Operation;
import org.mitumc.sdk.operation.currency.*;
*/
Generator gn = Generator.get("mitum");

String sourcePriv = "KzafpyGojcN44yme25UMGvZvKWdMuFv1SwEhsZn8iF8szUz16jskmpr";
String sourceAddr = "FcLfoPNCYjSMnxLPiQJQFGTV15ecHn3xY4J2HNCrqbCfmca";

String targetPub = "27uxAwUpvdc9sbRgztW8LrNoHnBmwgKavGuU6KvWzCgnimpu";

Key key = gn.currency().key(targetPub, 100);
Keys keys = gn.currency().keys(new Key[]{key}, 100);

KeyUpdaterFact fact = gn.currency().getKeyUpdaterFact(sourceAddr, "MCC", keys);
Operation operation = gn.getOperation(fact);
operation.addSign(sourcePriv);

JSONParser.createJSON(operation.toDict(), "keyupdater.json");
```

Then keys of the source account will be replaced with target keys.

### Generate Transfers

To generate an operation, you must prepare target address, not public key. Transfers supports to send tokens to another account.

#### Usage

```java
/*
import org.mitumc.sdk.Generator;
import org.mitumc.sdk.JSONParser;
import org.mitumc.sdk.operation.Operation;
import org.mitumc.sdk.operation.currency.*;
*/
String sourcePriv = "KzafpyGojcN44yme25UMGvZvKWdMuFv1SwEhsZn8iF8szUz16jskmpr";
String sourceAddr = "FcLfoPNCYjSMnxLPiQJQFGTV15ecHn3xY4J2HNCrqbCfmca";

String targetAddr = "77UNyuDQtxkYhRMLuKgyQCpWwGZzLoZ4E7S7qZd4Jbmpmca";

Generator gn = Generator.get("mitum");

Amount amount = gn.currency().getAmount("MCC", "1000");
TransfersItem item = gn.currency().getTransfersItem(targetAddr, new Amount[]{amount});

TransfersFact fact = gn.currency().getTransfersFact(sourceAddr, new TransfersItem[]{item});

Operation operation = gn.getOperation(fact);
operation.addSign(sourcePriv);

JSONParser.createJSON(operation.toDict(), "transfers.json");
```

## Generate BlockSign Operations

This part explains how to generate blocksign operations by `Generator`.

Supported operations are

* Create-Documents
* Sign-Documents

### Generate BlockSign Create-Documents

To generate an operation, you must prepare file-hash. Create-Document supports to create documents with setting signers who must sign them.

#### Usage

```java
/*
import org.mitumc.sdk.Generator;
import org.mitumc.sdk.JSONParser;
import org.mitumc.sdk.operation.Operation;
import org.mitumc.sdk.operation.blocksign.*;
*/
String sourcePriv = "KzafpyGojcN44yme25UMGvZvKWdMuFv1SwEhsZn8iF8szUz16jskmpr";
String sourceAddr = "FcLfoPNCYjSMnxLPiQJQFGTV15ecHn3xY4J2HNCrqbCfmca";

Generator gn = Generator.get("mitum");

CreateDocumentsItem item = gn.blockSign().getCreateDocumentsItem("absscd:mbfh-v0.0.1", 300, "user03", "title300", 1234, "MCC", new String[0], new String[]{"user04"});
BlockSignFact<CreateDocumentsItem> fact = gn.blockSign().getBlockSignFact(sourceAddr, new CreateDocumentsItem[]{item});

Operation operation = gn.getOperation(fact);
operation.addSign(sourcePriv);

JSONParser.createJSON(operation.toDict(), "createdocuments.json");
```

### Generate BlockSign Sign-Documents

To generate an operation, you must prepare owner and document id. Sign-Document supports to sign documents registered by 'mitum-data-blocksign'

#### Usage

```java
/*
import org.mitumc.sdk.Generator;
import org.mitumc.sdk.JSONParser;
import org.mitumc.sdk.operation.Operation;
import org.mitumc.sdk.operation.blocksign.*;
*/
String sourcePriv = "KzafpyGojcN44yme25UMGvZvKWdMuFv1SwEhsZn8iF8szUz16jskmpr";
String sourceAddr = "FcLfoPNCYjSMnxLPiQJQFGTV15ecHn3xY4J2HNCrqbCfmca";

Generator gn = Generator.get("mitum");

SignDocumentsItem item = gn.blockSign().getSignDocumentsItem(sourceAddr, 0, "MCC");
BlockSignFact<SignDocumentsItem> fact = gn.blockSign().getBlockSignFact(sourceAddr, new SignDocumentsItem[]{item});

Operation operation = gn.getOperation(fact);
operation.addSign(sourcePriv);

JSONParser.createJSON(operation.toDict(), "signdocuments.json");
```

## Generate BlockCity Operations

This part shows how to genereate blockcity operations with `Generator`.

Supported operations are

* Create-Documents
* Sign-Documents

Supported document types are

* User Data
* Land Data
* Voting Data
* History Data

Note a document id for each document type has a unique suffix.

* user data: cui
* land data: cli
* vote data: cvi
* history data: chi

### Generate User Document

What you must prepare before generate a user document are,

* document id
* Each value in a user statistics
* document owner
* user's gold and bank gold

#### Usage

```java
/*
import org.mitumc.sdk.operation.blockcity.*;
*/
Info info = generator.blockCity().info(Document.DOCTYPE_USER_DATA, "4cui");
UserStatistics userStatic = generator.blockCity().userStatistics(1, 1, 1, 1, 1, 1, 1);
    
Document document = generator.blockCity().document(info, "5KGBDDsmNXCa69kVAgRxDovu7JWxdsUxtAz7GncKxRfqmca", 10, 10, userStatic);       
```

If you wonder what value needs for each parameter, see [Generator](#generator).

### Generate Land Document

What you must prepare are,

* document id
* document owner
* address to rent
* area to rent
* renter
* account who rents
* rent date and period

#### Usage

```java
/*
import org.mitumc.sdk.operation.blockcity.*;
*/
Info info = generator.blockCity().info(Document.DOCTYPE_LAND_DATA, "4cli");
Document document = generator.blockCity().document(info, "5KGBDDsmNXCa69kVAgRxDovu7JWxdsUxtAz7GncKxRfqmca", "abcd", "city1", "foo", "8sXvbEaGh1vfpSWSib7qiJQQeqxVJ5YQRPpceaa5rd9Ymca", "2021-10-10", 10);
```

If you wonder what value needs for each parameter, see [Generator](#generator).

### Generate Vote Document

What you must prepare are,

* voting round
* candidates and their manifests
* document id
* document owner
* end date for voting
* boss name
* account
* termofoffice

#### Usage

```java
/*
import org.mitumc.sdk.operation.blockcity.*;
*/
Info info = generator.blockCity().info(Document.DOCTYPE_VOTE_DATA, "5cvi");
Candidate c1 = generator.blockCity().candidate("8sXvbEaGh1vfpSWSib7qiJQQeqxVJ5YQRPpceaa5rd9Ymca", "foo", "", 1);
Candidate c2 = generator.blockCity().candidate("Gu5xHjhos5WkjGo9jKmYMY7dwWWzbEGdQCs11QkyAhh8mca", "foo2", "", 2);

Document document = generator.blockCity().document(info, "5KGBDDsmNXCa69kVAgRxDovu7JWxdsUxtAz7GncKxRfqmca", 1, "2022-01-02", new Candidate[]{ c1, c2 }, "foo", "Gu5xHjhos5WkjGo9jKmYMY7dwWWzbEGdQCs11QkyAhh8mca", "2022");
```

If you wonder what value needs for each parameter, see [Generator](#generator).

### Generate History Document

What you must prepare are,

* document id
* document owner
* name
* account
* date
* usage
* application

#### Usage

```java
/*
import org.mitumc.sdk.operation.blockcity.*;
*/
Info info = generator.blockCity().info(Document.DOCTYPE_HISTORY_DATA, "1000chi");
Document document = generator.blockCity().document(info, "8iRVFAPiHKaeznfN3CmNjtFtjYSPMPKLuL6qkaJz8RLumca", "abcd", "8iRVFAPiHKaeznfN3CmNjtFtjYSPMPKLuL6qkaJz8RLumca", "2022-02-01T00:00:00.000+09:00", "bob", "foo");
```

If you wonder what value needs for each parameter, see [Generator](#generator).

### Generate BlockCity Create-Documents

To generate create-documents operation, you have to prepare,

* currency id for fees
* document object generated along the above instructions.
* sender's address and private key

#### Usage

```java
/*
import org.mitumc.sdk.operation.blockcity.*;
import org.mitumc.sdk.operation.Operation;
import org.mitumc.sdk.JSONParser;
*/
// type of document: org.mitumc.sdk.operation.blockcity.Document
BlockCityItem item = generator.blockCity().getCreateDocumentsItem(document, "PEN");
BlockCityFact fact = generator.blockCity().getCreateDocumentsFact("5KGBDDsmNXCa69kVAgRxDovu7JWxdsUxtAz7GncKxRfqmca", new BlockCityItem[]{ item });

Operation operation = generator.getOperation(fact);
operation.addSign("Kz5gif6kskQA8HD6GeEjPse1LuqF8d3WFEauTSAuCwD1h94vboyAmpr");

JSONParser.createJSON(operation.toDict(), "create_documents.json");
```

See the start of [Generate BlockCity Operations](#generate-blockcity-operations) for `Document`.

See [Generator](#generator) for details.

### Generate BlockCity Update-Documents

To generate create-documents operation, you have to prepare,

* currency id for fees
* document object generated along the above instructions.
* sender's address and private key

#### Usage

```java
/*
import org.mitumc.sdk.operation.blockcity.*;
import org.mitumc.sdk.operation.Operation;
import org.mitumc.sdk.JSONParser;
*/
// type of document: org.mitumc.sdk.operation.blockcity.Document
BlockCityItem item = generator.blockCity().getUpdateDocumentsItem(document, "PEN");
BlockCityFact fact = generator.blockCity().getUpdateDocumentsFact("5KGBDDsmNXCa69kVAgRxDovu7JWxdsUxtAz7GncKxRfqmca", new BlockCityItem[]{ item });

Operation operation = generator.getOperation(fact);
operation.addSign("Kz5gif6kskQA8HD6GeEjPse1LuqF8d3WFEauTSAuCwD1h94vboyAmpr");

JSONParser.createJSON(operation.toDict(), "update_documents.json");
```

See the start of [Generate BlockCity Operations](#generate-blockcity-operations) for `Document`.

See [Generator](#generator) for details.

## Generate New Seal

`mitum-java-util` supports you to generate a seal json file such that the seal is able to consist of several operations. Those operations can be any type 'mitum-java-util' provides.

### Prerequisite

To generate a seal, 'mitum-java-util' requires,

* `signing key`
* `a list of pre-constructed operations` not empty

Registration of `signing key` to the network is not necessary.

#### Usage

First of all, suppose that every operation is that generated by `Generator`. (createAccounts, keyUpdater, Transfers and etc)

```java
/*
import org.mitumc.sdk.Generator;
import org.mitumc.sdk.JSONParser;
*/
... omitted
''' Create each operation [createAccounts, keyUpdater, transfers, etc] with Generator. See above sections.
'''
...

Generator gn = Generator.get("mitum");

String signKey = "KzafpyGojcN44yme25UMGvZvKWdMuFv1SwEhsZn8iF8szUz16jskmpr";

HashMap<String, Object> seal = gn.getSeal(signKey, new Operation[]{/*operations*/});
JSONParser.createJSON(seal, "seal.json");
```

Then the result format of `createJSON(seal, fileName)` will be like [this](example/seal.json). (Each value is up to input arguments and time)

## Send Messages to Network

Send created json files to the network by 'mitum-currency' and 'mitum-data-blocksign'.

See [mitum-currency](https://github.com/ProtoconNet/mitum-currency) and [mitum-data-blocksign](https://github.com/ProtoconNet/mitum-data-blocksign).

## Sign Message

Sign message with keypairs.

### Usage

#### Sign Message

Each keypair supports `sign` method that generates bytes format signature by signing bytes format message.

If you want to get signature for 'mitum-currency', use `Base58` to encode the signature.

```java
// Omit steps for generating keypair
String message = "mitum";
byte[] signedMessage = keypair.sign(message.getBytes());
```

## Add Fact Signature to Operation

Use `Signer.addSignToOperation(key, targetOperation)` to add new fact signature to "fact_signs".

After adding a fact signature, operation hash is always changed.

### Signer

* `org.mitumc.sdk.Signer`

```java
HashMap<String, Object> addSignToOperation(JsonObject operation);
HashMap<String, Object> addSignToOperation(String operationPath);
```

### Usage

```java
/*
import org.mitumc.sdk.Signer;
import org.mitumc.sdk.JSONParser;
*/
String id = "mitum";
String key = "KzafpyGojcN44yme25UMGvZvKWdMuFv1SwEhsZn8iF8szUz16jskmpr";

Signer signer = Signer.get(id, key);

HashMap<String, Object> signed = signer.addSignToOperation("operation.json");
JSONParser.createJSON(signed, "newOperation.json");
```

Signer class itself doesn't create json file of new operation.

Use `JSONParser` if you need.

## Appendix

### __About Time Stamp__

#### __Expression of Time Stamp__

For blocks, seals, signatures and etc, mitum uses `yyyy-MM-dd HH:mm:ss.* +0000 UTC` expression and `yyyy-MM-ddTHH:mm:ss.*Z` as standard.

All other timezones are not allowed! You must use only +0000 timezone for mitum.

For example,

1. When converting timestamp to byte format for generating block/seal/fact_sign hash
    - converting the string `2021-11-16 01:53:30.518 +0000 UTC` to bytes format

2. When putting timestamp in block, seal, fact_sign or etc
    - converting the timestamp to `2021-11-16T01:53:30.518Z` and put it in json

To generate operation hash, mitum concatenates byte arrays of network id, fact hash and byte arrays of fact_signs.

And to generate the byte array of a fact_sign, mitum concatenates byte arrays of signer, signature digest and signed_at.

Be careful that the format of `signed_at` after converted to bytes is like `yyyy-MM-dd HH:mm:ss.* +0000 UTC` but it will be expressed as `yyyy-MM-ddTHH:mm:ss.*Z` when putted in json.

#### __How many decimal places to be expressed?__

There is one more thing to note.

First at all, you don't have to care about decimal points of second(ss.*) in timestamp.## Appendix

### __About Time Stamp__

#### __Expression of Time Stamp__

For blocks, seals, signatures and etc, mitum uses `yyyy-MM-dd HH:mm:ss.* +0000 UTC` expression and `yyyy-MM-ddTHH:mm:ss.*Z` as standard.

All other timezones are not allowed! You must use only +0000 timezone for mitum.

For example,

1. When converting timestamp to byte format for generating block/seal/fact_sign hash
    - converting the string `2021-11-16 01:53:30.518 +0000 UTC` to bytes format

2. When putting timestamp in block, seal, fact_sign or etc
    - converting the timestamp to `2021-11-16T01:53:30.518Z` and put it in json

To generate operation hash, mitum concatenates byte arrays of network id, fact hash and byte arrays of fact_signs.

And to generate the byte array of a fact_sign, mitum concatenates byte arrays of signer, signature digest and signed_at.

Be careful that the format of `signed_at` after converted to bytes is like `yyyy-MM-dd HH:mm:ss.* +0000 UTC` but it will be expressed as `yyyy-MM-ddTHH:mm:ss.*Z` when putted in json.

#### __How many decimal places to be expressed?__

There is one more thing to note.

First at all, you don't have to care about decimal points of second(ss.*) in timestamp.

Moreover, you can write timestamp without `.` and any number under `.`.

However, you should not put any unnecessary zeros(0) in the float expression of second(ss.*) when converting timestamp to bytes format.

For example,

1. `2021-11-16T01:53:30.518Z` is converted to `2021-11-16 01:53:30.518 +0000 UTC` without any change of the time itself.

2. `2021-11-16T01:53:30.510Z` must be converted to `2021-11-16 01:53:30.51 +0000 UTC` when generating hash.

3. `2021-11-16T01:53:30.000Z` must be converted to `2021-11-16T01:53:30 +0000 UTC` when generating hash.

Any timestamp with some unnecessary zeros putted in json doesn't affect to effectiveness of the block, seal, or operation. Just pay attention when convert the format.

However, you should not put any unnecessary zeros(0) in the float expression of second(ss.*) when converting timestamp to bytes format.

For example,

1. `2021-11-16T01:53:30.518Z` is converted to `2021-11-16 01:53:30.518 +0000 UTC` without any change of the time itself.

2. `2021-11-16T01:53:30.510Z` must be converted to `2021-11-16 01:53:30.51 +0000 UTC` when generating hash.

3. `2021-11-16T01:53:30.000Z` must be converted to `2021-11-16T01:53:30 +0000 UTC` when generating hash.

Any timestamp with some unnecessary zeros putted in json doesn't affect to effectiveness of the block, seal, or operation. Just pay attention when convert the format.