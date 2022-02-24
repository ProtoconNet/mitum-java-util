# mitum-java-util

'mitum-java-util' will introduce the usage of [mitum-currency](https://github.com/ProtoconNet/mitum-currency), [mitum-document](https://github.com/ProtoconNet/mitum-document) for Java.

'mitum-java-util' now supports generating fact, operation and sign with btc wif based keypairs.

Note that every address and key is just an example. Don't care about each value. Sometimes signer or owner can be written in practices.

Use accurate and correct addresses and keys when you use. Do not trust all values in this document.

__With all practices in this document, we are not responsible for using wrong or invalid values.__

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

The latest version is `mitum-java-util-3.0.0-jdk17.jar`.

#### Gradle
```sh
implementation files('./lib/mitum-java-util-3.0.0-jdk17.jar')
```

Replace `./lib/mitum-java-util-3.0.0-jdk17.jar` with your file path.

## Index

||Title|
|---|---|
|1|[Generate Keypairs](#generate-keypairs)|
|2|[How to Use Generator](#how-to-use-generator)|
|2-1|[Create Generator](#create-generator)|
|2-2|[Get Address from Keys](#get-address-from-keys)|
|2-3|[Generate JSON File from Operation and Seal](#generate-json-file-from-operation-and-seal)|
|3|[Generate Currency Operation](#generate-currency-operation)|
|3-1|[Generate Create-Accounts](#generate-create-accounts)|
|3-2|[Generate Key-Updater](#generate-key-updater)|
|3-3|[Generate Transfers](#generate-transfers)|
|4|[Generate Document Operation](#generate-document-operation)|
|4-1|[Generate BlockSign Documents](#generate-blocksign-documents)|
|4-2|[Generate BlockCity Documents](#generate-blockcity-documents)|
|4-3|[Generate Create-Documents](#generate-create-documents)|
|4-4|[Generate Update-Documents](#generate-update-documents)|
|4-5|[Generate BlockSign Sign-Documents](#generate-blocksign-sign-documents)|
|5|[Generate New Seal](#generate-new-seal)|
|6|[Send Messages to Network](#send-messages-to-network)|
|7|[Sign Message](#sign-message)|
|8|[Add Fact Signature to Operation](#add-fact-signature-to-operation)|

<br />

|Class|
|---|
|[Keypair](#keypair)|
|[Generator](#generator)|
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

In addition, 'mitum-java-util' provides two operations of 'mitum-document'.

* `Create-Documents` creates an document.
* `Update-Documents` update the state of the document.

And now, this sdk supports two models implemented based on 'mitum-document', `mitum blocksign` and `mitum blockcity`.

'mitum blocksign' provides one more additional operation, `Sign-Documents`.

Available document types for each model are like below.

* Use only one document type, 'blocksign' document for 'mitum blocksign'.
* Use four document types, 'user, 'land', 'vote', and 'history' for 'mitum blockcity'.

### Create Generator

`mitum-java-util` supports to generate operations of `mitum-currency`, and `mitum-document`.

#### Generator

* `org.mitumc.sdk.Generator`

You can use `Generator` by this library.

First of all, set network id by `Generator.get(id)`.

For `mitum-currency`, use `Generator.mc()`.

For `mitum-document`, use `Generator.md()`.

Additionally, 'mitum-document' provides two more generators.

For `blocksign`, use `Generator.md().bs()`.

For `blockcity`, use `Generator.md().bc()`.

```java
String id = "mitum";
Generator generator = Generator.get(id);

generator.mc() // for currency
generator.md() // for document
generator.md().bs() // for blocksign
generator.md().bc() // for blockcity
```

#### Currency Generator

Using `Generator.mc()`, below methods are available.

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

#### Document Generator

Using `Generator.md()`, below methods are available.

```java
CreateDocumentsItem getCreateDocumentsItem(Document document, String currencyId);
UpdateDocumentsItem getUpdateDocumentsItem(Document document, String currencyId);
CreateDocumentsFact getCreateDocumentsFact(String sender, CreateDocumentsItem[] items);
UpdateDocumentsFact getUpdateDocumentsFact(String sender, UpdateDocumentsItem[] items);
```

Note that create-documents and update-documents of `mitum-document` are common operations of `blocksign` and `blockcity`.

So `md` helps to generate `item` and `fact` of those operations simultaneously.

1. Using `Generator.md().bs()` below methods are available.

```java
BlockSignUser user(String address, String signCode, boolean signed);
Document document(String documentId, String owner, String fileHash, BlockSignUser creator, String title, String size, BlockSignUser[] signers);

SignDocumentsItem getSignDocumentsItem(String documentId, String owner, String currencyId);
SignDocumentsFact getSignDocumentsFact(String sender, SignDocumentsItem[] items);
```

Note that `sign-documents` is provided only for `blocksign`.

So what supports sign-documents is `Generator.md().bs()` rather than `Generator.md()`.

The output of `user` is served as 'creator' or 'signer' of `document`.

2. Using `Generator.md().bc()`, below methods are available.

```java
Candidate candidate(String address, String nickname, String manifest, int count);
UserStatistics userStatistics(int hp, int strength, int agility, int dexterity, int charisma, int intelligence, int vital);

Document document(String documentId, String owner, int gold, int bankGold, UserStatistics statistics);
Document document(String documentId, String owner, String address, String area, String renter, String account, String rentDate, int period);
Document document(String documentId, String owner, int round, String endTime, Candidate[] candidates, String bossName, String account, String office);
Document document(String documentId, String owner, String name, String account, String date, String usage, String app);
```

#### Generate Operation by Generator

`Generator` provides methods for generating operation and seal.

```java
Operation getOperation(OperationFact fact);
Operation getOperation(OperationFact fact, String memo);
HashMap<String, Object> getSeal(String signKey, Operation[] operations);
```

The usage of those methods will be introduced in the next section.

### Get Address from Keys

You can calculate the address of the account by its keys.

Each key in the account has a corresponding weight(1 <= weight <= 100).

And, the account has threshold(1 <= threshold <= 100) which should be smaller than or equal to the sum of all weights of account keys.

To get address, use `Generator.mc()`.

#### How to Get Address

```java
/*
import org.mitumc.sdk.Generator
import org.mitumc.key.Key
import org.mitumc.key.Keys
*/
Generator generator = Generator.get("mitum");

Key key = generator.mc().key("24TbbrNYVngpPEdq6Zc5rD1PQSTGQpqwabB9nVmmonXjqmpu", 100);
Keys keys = generator.mc().keys(new Key[]{key}, 100);

String address = keys.getAddress(); // your address
```

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

## Generate Currency Operation

This part explains how to generate currency operations by `Generator`.

Supported operations are

* Create-Accounts
* Key-Updater
* Transfers

### Generate Create-Accounts 

For new account, `currency id` and `initial amount` must be set. With source account, you can create and register new account of target public key.

Note that source account must be already registered one.

When you use `Generator`, you must set `network id` before you create something.

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

Key key = gn.mc().key(targetPub, 100);
Keys keys = gn.mc().keys(new Key[] { keys }, 100);
Amount amount = gn.mc().amount("PEN", "100");

CreateAccountsItem item = gn.mc().getCreateAccountsItem(keys, new Amount[] { amount });
CreateAccountsFact fact = gn.mc().getCreateAccountsFact(sourceAddr, new CreateAccountsItem[] { item });

Operation operation = gn.getOperation(fact);
operation.addSign(sourcePriv);

JSONParser.createJSON(operation.toDict(), "createaccounts.json");
```

You must add new fact signature by `addSign()` before creating operation json files.

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

Key key = gn.mc().key(targetPub, 100);
Keys keys = gn.mc().keys(new Key[]{key}, 100);

KeyUpdaterFact fact = gn.mc().getKeyUpdaterFact(sourceAddr, "MCC", keys);
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

Amount amount = gn.mc().getAmount("MCC", "1000");
TransfersItem item = gn.mc().getTransfersItem(targetAddr, new Amount[]{ amount });

TransfersFact fact = gn.mc().getTransfersFact(sourceAddr, new TransfersItem[]{ item });

Operation operation = gn.getOperation(fact);
operation.addSign(sourcePriv);

JSONParser.createJSON(operation.toDict(), "transfers.json");
```

## Generate Document Operation

To create or update documents, you must prepare available document object for each operation item.

For example, 'blocksign' supports one type of 'document', blocksign document, which hint is `mitum-blocksign-document-data`.

However, 'blockcity' supports four types of 'document', user/land/vote/history document, with hints different with blocksign.

That means you must generate a document corresponding to the document type you want.

So first, we will introduce how to generate a document for each type.

### Generate BlockSign Documents

As mentioned, blocksign uses only one document type, blocksign document.

First, you must prepare a creator and signers.

For convenience, call each of them `user`.

A `user` can be generated by `Generator.md().bs().user(address, signCode, signed)`

What you have to prepare to generate document are

* document id
* owner
* file hash
* creator - from `user`
* title
* file size
* a signer list - signers from `user`

Note that every document ids of blocksign are followed by the type suffix `sdi`.

#### Usage

```java
/*
import org.mitumc.sdk.Generator;
import org.mitumc.sdk.operation.document.Document;
import org.mitumc.sdk.operation.document.blocksign.BlockSignUser;
*/
String signer1 = "FcLfoPNCYjSMnxLPiQJQFGTV15ecHn3xY4J2HNCrqbCfmca";
String signer2 = "77UNyuDQtxkYhRMLuKgyQCpWwGZzLoZ4E7S7qZd4Jbmpmca";

Generator gn = Generator.get("mitum");

BlockSignUser creator = gn.md().bs().user(signer1, "signcode01", true);
BlockSignUser user1 = gn.md().bs().user(signer1, "signcode01", true);
BlockSignUser user2 = gn.md().bs().user(signer2, "signcode02", false);

Document document = gn.md().bs().document("4000sdi", signer1, "test-hs:01", creator, "test-doc-01", "12345", new BlockSignUser[] { user1, user2 });
```

### Generate BlockCity Documents

Supported document types of blockcity are

* User Data
* Land Data
* Voting Data
* History Data

Note a document id for each document type has a unique suffix.

* user data: cui
* land data: cli
* vote data: cvi
* history data: chi

Those documents are used only by blockcity.

If you wonder what each argument means, see [Generator](#generator).

#### User Document

What you must prepare before generate a user document are,

* document id
* Each value in a user statistics
* document owner
* user's gold and bank gold

#### Usage

```java
/*
import org.mitumc.sdk.Generator;
import org.mitumc.sdk.operation.document.Document;
import org.mitumc.sdk.operation.document.blockcity.UserStatistics;
*/
Generator gn = Generator.get("mitum");

UserStatistics statistics = gn.md().bc().userStatistics(1, 1, 1, 1, 1, 1, 1);
Document document = gn.md().bc().document("user01cui", "FcLfoPNCYjSMnxLPiQJQFGTV15ecHn3xY4J2HNCrqbCfmca", 1, 1, statistics);      
```

#### Land Document

What you must prepare are,

* document id
* document owner
* address to rent
* area to rent
* renter who rent
* account who rent
* rent date and period

#### Usage

```java
/*
import org.mitumc.sdk.Generator;
import org.mitumc.sdk.operation.document.Document;
*/
Generator gn = Generator.get("mitum");

Document document = gn.md().bc().document("land01cli", "FcLfoPNCYjSMnxLPiQJQFGTV15ecHn3xY4J2HNCrqbCfmca", "addr-01", "area-01", "renter01", "77UNyuDQtxkYhRMLuKgyQCpWwGZzLoZ4E7S7qZd4Jbmpmca", "2022-02-02", 5);
```

#### Vote Document

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
import org.mitumc.sdk.Generator;
import org.mitumc.sdk.operation.document.Document;
*/
Generator gn = Generator.get("mitum");

Candidate c1 = gn.md().bc().candidate("FcLfoPNCYjSMnxLPiQJQFGTV15ecHn3xY4J2HNCrqbCfmca", "nickname-01", "HI", 1);
Candidate c2 = gn.md().bc().candidate("77UNyuDQtxkYhRMLuKgyQCpWwGZzLoZ4E7S7qZd4Jbmpmca", "nickname-02", "HELLO", 2);   
Document document = gn.md().bc().document("vote01cvi", "FcLfoPNCYjSMnxLPiQJQFGTV15ecHn3xY4J2HNCrqbCfmca", 1, "2022-02-28T02:20:34.333Z", new Candidate[] { c1, c2 }, "boss01", "FcLfoPNCYjSMnxLPiQJQFGTV15ecHn3xY4J2HNCrqbCfmca", "5");
```

#### History Document

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
import org.mitumc.sdk.Generator;
import org.mitumc.sdk.operation.document.Document;
*/
Generator gn = Generator.get("mitum");

Document document = gn.md().bc().document("hist01chi", "77UNyuDQtxkYhRMLuKgyQCpWwGZzLoZ4E7S7qZd4Jbmpmca", "hello", "FcLfoPNCYjSMnxLPiQJQFGTV15ecHn3xY4J2HNCrqbCfmca", "2022-02-25T08:03:22.234Z", "usage090", "app090");
```

### Generate Create-Documents

All models based on 'mitum-document' are played with operations,  'create-documents' and 'update-documents'.

So in this section, we will introduce how to generate create-documents and update-documents operation with documents you prepared.

About generating documents, go to the previous section.

To generate create-documents operation, you have to prepare,

* currency id for fees
* document
* sender's address and private key

#### Usage

```java
/*
import org.mitumc.sdk.Generator;
import org.mitumc.sdk.JSONParser;
import org.mitumc.sdk.operation.document.*;
import org.mitumc.sdk.operation.Operation;
*/
Generator gn = Generator.get("mitum");

String sender = "FcLfoPNCYjSMnxLPiQJQFGTV15ecHn3xY4J2HNCrqbCfmca";
String priv = "KzafpyGojcN44yme25UMGvZvKWdMuFv1SwEhsZn8iF8szUz16jskmpr";
// document: Document object you made

CreateDocumentsItem item = gn.md().getCreateDocumentsItem(document, "PEN");
CreateDocumentsFact fact = gn.md().getCreateDocumentsFact(sender, new CreateDocumentsItem[] { item });

Operation operation = gn.getOperation(fact);
operation.addSign(priv);

JSONParser.createJSON(operation.toDict(), "createdocuments.json");
```

See the start of [Generate Document Operations](#generate-document-operations) for `Document`.

See [Generator](#generator) for details.

### Generate Update-Documents

To generate create-documents operation, you have to prepare,

* currency id for fees
* document object generated along the above instructions.
* sender's address and private key

#### Usage

```java
/*
import org.mitumc.sdk.Generator;
import org.mitumc.sdk.JSONParser;
import org.mitumc.sdk.operation.document.*;
import org.mitumc.sdk.operation.Operation;
*/
Generator gn = Generator.get("mitum");

String sender = "FcLfoPNCYjSMnxLPiQJQFGTV15ecHn3xY4J2HNCrqbCfmca";
String priv = "KzafpyGojcN44yme25UMGvZvKWdMuFv1SwEhsZn8iF8szUz16jskmpr";
// document: Document object you made

UpdateDocumentsItem item = gn.md().getUpdateDocumentsItem(document, "PEN");
UpdateDocumentsFact fact = gn.md().getUpdateDocumentsFact(sender, new UpdateDocumentsItem[] { item });

Operation operation = gn.getOperation(fact);
operation.addSign(priv);

JSONParser.createJSON(operation.toDict(), "updatedocuments.json");
```

See the start of [Generate Document Operations](#generate-document-operations) for `Document`.

See [Generator](#generator) for details.

### Generate BlockSign Sign-Documents

As mentioned, `sign-documents` operation is used only for 'blocksign'.

So you must use blocksign specific generator, `Generator.md().bs()` to generate items and facts of sign-documents.

To generate a sign-document's item, you must prepare

* document id
* owner's address
* currency id for fee

Note that you don't have to prepare document for 'sign-documents'. Only document id is needed.

#### Usage

```java
/*
import org.mitumc.sdk.Generator;
import org.mitumc.sdk.JSONParser;
import org.mitumc.sdk.operation.document.blocksign.*;
*/
Generator gn = Generator.get("mitum");

SignDocumentsItem item = gn.md().bs().getSignDocumentsItem("4000sdi", "FcLfoPNCYjSMnxLPiQJQFGTV15ecHn3xY4J2HNCrqbCfmca", "PEN");
SignDocumentsFact fact = gn.md().bs().getSignDocumentsFact("FcLfoPNCYjSMnxLPiQJQFGTV15ecHn3xY4J2HNCrqbCfmca", new SignDocumentsItem[] { item });

Operation operation = gn.getOperation(fact);
operation.addSign("KzafpyGojcN44yme25UMGvZvKWdMuFv1SwEhsZn8iF8szUz16jskmpr");

JSONParser.createJSON(operation.toDict(), "signdocuments.json");
```

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

Use `curl` to broadcast your operations.

```shell
~$ curl -X POST -H "Content-Type: application/json" -d @seal.json https://{mitum network address}/builder/send
```

* seal.json is your seal file.

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