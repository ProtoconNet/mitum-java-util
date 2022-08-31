# mitum-java-util

__mitum-java-util__ is a Java tool that can create jobs for the models below.

- [mitum-currency](https://github.com/ProtoconNet/mitum-currency)
- [mitum-currency-extension](https://github.com/ProtoconNet/mitum-currency-extension)
- [mitum-document](https://github.com/ProtoconNet/mitum-document)
- [mitum-feefi](https://github.com/ProtoconNet/mitum-feefi)
- [mitum-nft](https://github.com/ProtoconNet/mitum-nft)

All addresses and keys are examples only. Never mind each value in the example.

Use the exact address and key that you can trust when using it. Do not trust all values in this document example.

With regard to the values given in all examples of this document, we are not responsible for the use of incorrect values.

## Installation

Recommended requirements for __mitum-java-util__ are,

* Java - OpenJDK v17.0.1
* Javac - javac v17.0.1
* Gradle - gradle v7.4

Additionally, This project is using below external Java libraries.

* [bitcoinj v0.14.7](https://bitcoinj.org/)

And you must download and add [ecdsa-keygen-java v1.4](https://github.com/wyuinche/ecdsa-keygen-java) to the project to build.

```sh
$ java -version
java 17.0.1 2021-10-19 LTS
...

$ javac -version
javac 17.0.1

$ gradlew --version

------------------------------------------------------------
Gradle 7.4
------------------------------------------------------------
...
```

[Download jar file](release/) and include the package to your project.

The latest version is `mitum-java-util-4.0.0-jdk17.jar`.

## Test

Run the following command to test the package:

```sh
$ gradle test

BUILD SUCCESSFUL in 288ms
3 actionable tasks: 3 up-to-date

$ ./gradlew test

BUILD SUCCESSFUL in 288ms
3 actionable tasks: 3 up-to-date
```

#### How to Use

Add the following line to your build.gradle file.

```sh
implementation files('./lib/mitum-java-util-4.0.0-jdk17.jar')
```

## Index

||Title|
|---|---|
|1|[Generate Keypairs](#generate-keypairs)|
|2|[How to Use Generator](#how-to-use-generator)|
|2-1|[Create Generator](#create-generator)|
|2-2|[Get Address from Keys](#get-address-from-keys)|
|2-3|[Generate JSON File from Operation and Seal](#generate-json-file-from-operation-and-seal)|
|3|[Currency Operations](#generate-currency-operation)|
|3-1|[Create Accounts](#generate-create-accounts)|
|3-2|[Key Updater](#generate-key-updater)|
|3-3|[Transfers](#generate-transfers)|
|3-4|[Create Contract Accounts](#generate-create-contract-accounts)|
|3-5|[Withdraws](#generate-withdraws)|
|4|[Document Operations](#generate-document-operation)|
|4-1|[BlockSign Documents](#generate-blocksign-documents)|
|4-2|[BlockCity Documents](#generate-blockcity-documents)|
|4-3|[Create Documents](#generate-create-documents)|
|4-4|[Update Documents](#generate-update-documents)|
|4-5|[BlockSign Sign Documents](#generate-blocksign-sign-documents)|
|5|[Feefi Operations](#feefi-operations)|
|5-1|[Pool Register](#pool-register)|
|5-2|[Pool Policy Updater](#pool-policy-updater)|
|5-3|[Pool Deposits](#pool-deposits)|
|5-4|[Pool Withdraw](#pool-withdraw)|
|6|[NFT Operations](#nft-operations)|
|6-1|[Collection Register](#collection-register)|
|6-2|[Collection Policy Updater](#collection-policy-updater)|
|6-3|[Approve](#approve)|
|6-4|[Delegate](#delegate)|
|6-5|[NFT Mint](#nft-mint)|
|6-6|[NFT Sign](#nft-sign)|
|6-7|[NFT Transfer](#nft-transfer)|
|6-8|[NFT Burn](#nft-burn)|
|7|[Generate New Seal](#generate-new-seal)|
|8|[Send Messages to Network](#send-messages-to-network)|
|9|[Sign Message](#sign-message)|
|10|[Add Fact Signature to Operation](#add-fact-signature-to-operation)|

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

`org.mitumc.sdk` supports keypair generation for mitum.

Each private key, public key, and mitum-currency address has its own suffix.

private key: mpr
public key: mpu
address: mca

### Keypair

* `org.mitumc.sdk.key.Keypair`

`Keypair.random()` returns new keypair for mitum.
~~Keypair.create() has been deprecated.~~

You can use `Keypair.fromPrivateKey(String key)` when you already have a private key.

If you have seed of your private key, just use `Keypair.fromSeed(String seed)`.

Or, you can use `Keypair.fromSeed(byte[] seed)`.

`new String(seed).length()` should be longer than or equal to 36.

```java
Keypair random();
Keypair fromSeed(seed);
Keypair fromPrivateKey(privKey);
```

#### Usage

```java
/*
import org.mitumc.sdk.key.Keypair;
*/
Keypair kp = Keypair.random();

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

This section describes how to use `Generator`. 

### Support Operations

__mitum-java-util__ provides three operations of __mitum-currency__.

* __create-accounts__ uses pre-registered accounts to create accounts from public keys.
* __key-updater__ replaces the public key of the account.
* __transfers__ transfers tokens from one account to another.

There are many different types of operations for __mitum-currency__, but __mitum-java-util__ provides only three of these frequently used operations.

__mitum-java-util__ provides two operations of __mitum-currency-extension__.

* __create-contract-accounts__ creates a contract account.
* __withdraws__ withdraws tokens from the contract account.

In addition, __mitum-java-util__ provides two operations of __mitum-document__.

* __create-documents__ creates an document.
* __update-documents__ updates the state of the document.

And currently, this sdk supports two models: __blocksign__ and __blockcity__ implemented based on __mitum-document__.

__mitum blocksign__ provides an additional operation called , __sign-documents__.

The following types of documents are available for each model:

* `blocksign` for __blocksign__.
* `user`, `land`, `vote`, and `history` for __blockcity__.

__mitum-java-util__ provides four operations of __mitum-feefi__.

* __pool-register__ registers 'pool' in the contract account.
* __pool-policy-updater__ updates 'policy' of the pool in the contract account.
* __pool-deposits__ deposits amounts in the pool.
* __pool-withdraw__ withdraws amounts from the pool.

Finally, __mitum-java-util__ provides seven operations of __mitum-nft__.

* __collection-register__ registers 'collection' in the contract account.
* __collection-policy-updater__ updates collection policy.
* __nft mint__ registers a new nft in 'collection'.
* __nft transfer__ changes ownership of nft.
* __nft burn__ burns nft.
* __approve__ delegates the authority to change ownership of a specific nft to a general account.
* __delegate__ delegates the authority to change ownership of all nfts owned by some general account for a specific 'collection'.
* Through __nft sign__, you can sign nft as a creator or copyrighter.

### Generator

* `org.mitumc.sdk.Generator`

You can use `Generator` by this library.

First of all, set network id by `Generator.get(id)`.

For __mitum-currency__, use `Generator.currency`.

For __mitum-document__, use `Generator.document`.

For __mitum-feefi__, use `Generator.feefi`.

For __mitum-nft__, use `Generator.nft`.

`Generator.currency` provides one more generator.

For __mitum-currency-extension__, use `Generator.currency.extension`.

`Generator.document` provides two more generators.

For __blocksign__, use `Generator.document.blocksign`.

For __blockcity__, use `Generator.document.blockcity`.

```java
String id = "mitum";
Generator generator = Generator.get(id);

generator.currency // for currency
generator.currency.extenion // for currency-extension
generator.document // for document
generator.document.blocksign // for blocksign
generator.document.blockcity // for blockcity
generator.feefi // for feefi
generator.nft // for nft
```

#### Currency Generator

Using `Generator.currency`, following methods are available.

```java
CreateAccountsItem getCreateAccountsItem(Keys keys, Amount[] amounts);
TransfersItem getTransfersItem(String receiver, Amount[] amounts);

CreateAccountsFact getCreateAccountsFact(String sender, CreateAccountsItem[] items);
KeyUpdaterFact getKeyUpdaterFact(String target, String currency, Keys keys);
TransfersFact getTransfersFact(String sender, TransfersItem[] items);
```

Using `Generator.currency.extension`, following methods are available.

```java
CreateContractAccountsItem getCreateContractAccountsItem(Keys keys, Amount[] amounts);
WithdrawsItem getWithdrawsItem(String target, Amount[] amounts);

CreateContractAccountsFact getCreateContractAccountsFact(String sender, CreateContractAccountsItem[] items);
WithdrawsFact getWithdrawsFact(String sender, WithdrawsItem[] items);            
```

#### Document Generator

Using `Generator.document`, following methods are available.

```java
CreateDocumentsItem getCreateDocumentsItem(Document document, String currency);
UpdateDocumentsItem getUpdateDocumentsItem(Document document, String currency);
CreateDocumentsFact getCreateDocumentsFact(String sender, CreateDocumentsItem[] items);
UpdateDocumentsFact getUpdateDocumentsFact(String sender, UpdateDocumentsItem[] items);
```

Note that create-documents and update-documents of __mitum-document__ are common operations of __blocksign__ and __blockcity__.

So `document` helps to generate `item` and `fact` of those operations simultaneously.

1. Using `Generator.document.blocksign` following methods are available.

```java
BlockSignUser user(String address, String signCode, boolean signed);
Document document(String documentId, String owner, String fileHash, BlockSignUser creator, String title, String size, BlockSignUser[] signers);

SignDocumentsItem getSignDocumentsItem(String documentId, String owner, String currency);
SignDocumentsFact getSignDocumentsFact(String sender, SignDocumentsItem[] items);
```

Note that __sign-documents__ is provided only for __blocksign__.

So what supports sign-documents is `Generator.document.blocksign` rather than `Generator.document`.

The output of `user` is served as 'creator' or 'signer' of __document__.

2. Using `Generator.document.blockcity`, following methods are available.

```java
Candidate candidate(String address, String nickname, String manifest, int count);
UserStatistics userStatistics(int hp, int strength, int agility, int dexterity, int charisma, int intelligence, int vital);

Document userDocument(String documentId, String owner, int gold, int bankGold, UserStatistics statistics);
Document landDocument(String documentId, String owner, String address, String area, String renter, String account, String rentDate, int period);
Document voteDocument(String documentId, String owner, int round, String endTime, Candidate[] candidates, String bossName, String account, String office);
Document historyDocument(String documentId, String owner, String name, String account, String date, String usage, String app);
```

#### Feefi Generator

Using `Generator.feefi`, following methods are available.

```java
PoolRegisterFact getPoolRegisterFact(String sender, String target, Amount initialFee, String incomingCid, String outgoingCid, String currency);
PoolPolicyUpdaterFact getPoolPolicyUpdaterFact(String sender, String target, Amount fee, String poolId, String currency);
PoolDepositsFact getPoolDepositsFact(String sender, String pool, String poolId, Amount amount);
PoolWithdrawFact getPoolWithdrawFact(String sender, String pool, String poolId, Amount[] amounts);
```

#### NFT Generator

Using `Generator.nft`, following methods are available.

```java
NFTSigner signer(String account, int share, boolean signed);
NFTSigners signers(int total, NFTSigner[] signers);
CollectionRegisterForm collectionRegisterForm(String target, String symbol, String name, int royalty, String uri, String[] whites);
CollectionPolicy collectionPolicy(String name, int royalty, String uri, String[] whites);
MintForm mintForm(String hash, String uri, NFTSigners creators, NFTSigners copyrighters);

MintItem getMintItem(String collection, MintForm form, String currency);
NFTTransferItem getTransferItem(String receiver, NFTID nid, String currency);
BurnItem getBurnItem(NFTID nid, String currency);
NFTSignItem getSignItem(String qualification, NFTID nid, String currency);
ApproveItem getApproveItem(String approved, NFTID nid, String currency);
DelegateItem getDelegateItem(String collection, String agent, String mode, String currency);

CollectionRegisterFact getCollectionRegisterFact(String sender, CollectionRegisterForm form, String currency);
CollectionPolicyUpdaterFact getCollectionPolicyUpdaterFact(String sender, String collection, CollectionPolicy policy, String currency);
MintFact getMintFact(String sender, MintItem[] items);
NFTTransferFact getTransferFact(String sender, NFTTransferItem[] items);
BurnFact getBurnFact(String sender, BurnItem[] items);
NFTSignFact getSignFact(String sender, NFTSignItem[] items);
ApproveFact getApproveFact(String sender, ApproveItem[] items);
DelegateFact getDelegateFact(String sender, DelegateItem[] items);
```

#### Generate Operation by Generator

`Generator` provides methods for generating __operation__ and __seal__.

```java
Operation getOperation(OperationFact fact);
Operation getOperation(OperationFact fact, String memo);
HashMap<String, Object> getSeal(String signKey, Operation[] operations);
HashMap<String, Object> getSeal(String signKey, JsonObject[] operations);
HashMap<String, Object> randomKeys();
HashMap<String, Object> randomKeys(int numOfKeys);
```

Use cases of `Generator` can be found in the next chapter.

### Get Address from Keys

First, you have to create `Keys`. Use `key` and `keys` of `Generator`.

Note that __1 <= threshold, weight <= 100__.

#### How to Get Address

```java
// import org.mitumc.sdk.key.Key;
// import org.mitumc.sdk.key.Keys;

Key key = Key.get("24TbbrNYVngpPEdq6Zc5rD1PQSTGQpqwabB9nVmmonXjqmpu", 100);
Keys keys = Keys.get(new Key[]{key}, 100);

String address = keys.getAddress(); // your address
```

#### Get Random Accounts by Using Generator

```java
// import java.util.HashMap;

// import org.mitumc.sdk.Generator;
// import org.mitumc.sdk.key.Keys;
// import org.mitumc.sdk.key.Keypair;

int numOfKeys = 10;
HashMap<String, Object> rkeys = Generator.randomKeys(numOfKeys);

Keys keys = (Keys) rkeys.get(Keys.ID);
HashMap<String, Keypair> kps = (HashMap<String, Keypair>) rkeys.get(Keypair.ID);

String rAccountAddress = keys.getAddress();

// to get the keypair corresponding a key in keys...
Keypair k = kps.get(keys.getKeys().get(0).getKey()); // first key in keys
```

### Generate JSON File from Operation and Seal

#### JSONParser

* `org.mitumc.sdk.JSONParser`

You can create a json file of generated operation object without `JSONParser`. However, I recommend to use `JSONParser` for convenience.

Methods that `JSONParser` supports are,

```java
JsonObject getObjectFromJsonFile(String fpName);
JsonObject getObjectFromHashMap(HashMap<String, Object> target);
void writeJsonFileFromJsonObject(JsonObject target, String fpName);
void writeJsonFileFromHashMap(HashMap target, String fpName);
HashMap<String, Object> mergeOperations(JsonObject[] operations);
HashMap<String, Object> mergeOperations(HashMap<String, Object>[] operations);
```

Note that you don't have to use `JSONParser` when you create a json file of operations without creating seals.

Just use `Operation.exportToJsonFile(String fp)`.

A use-case of `JSONParser` will be introduced in the next part, too.

## Currency Operations

This part explains how to generate currency operations by `Generator`.

Supported operations are

* create-accounts
* key-updater
* transfers

### Create Accounts 

For new account, `currency id` and `initial amount` must be set. With source account, you can create and register new account of target public key.

Note that source account must be already registered one.

When you use `Generator`, you must set `network id` before you create something.

#### Usage

```java
// import org.mitumc.sdk.key.*;
// import org.mitumc.sdk.Generator;
// import org.mitumc.sdk.JSONParser;
// import org.mitumc.sdk.Amount;
// import org.mitumc.sdk.operation.Operation;
// import org.mitumc.sdk.operation.currency.*;

String senderPriv = "KzafpyGojcN44yme25UMGvZvKWdMuFv1SwEhsZn8iF8szUz16jskmpr";
String senderAddr = "FcLfoPNCYjSMnxLPiQJQFGTV15ecHn3xY4J2HNCrqbCfmca";

String targetPub = "knW2wVXH399P9Xg8aVjAGuMkk3uTBZwcSpcy4aR3UjiAmpu";

Generator gn = Generator.get("mitum");

Key key = Key.get(targetPub, 100);
Keys keys = Keys.get(new Key[] { keys }, 100);
Amount amount = Amount.get("PEN", "100");

CreateAccountsItem item = gn.currency.getCreateAccountsItem(keys, new Amount[] { amount });
CreateAccountsFact fact = gn.currency.getCreateAccountsFact(senderAddr, new CreateAccountsItem[] { item });

Operation operation = gn.getOperation(fact);
operation.sign(senderPriv);

// use json parser
JSONParser.writeJsonFileFromHashMap(operation.toDict(), "createaccounts.json");

// or...
operation.exportToJsonFile("createaccounts.json");
```

You must add new fact signature by `sign()` before creating operation json files.

With `JSONParser.writeJsonFileFromHashMap(target, filePath)`, the result format will be like [this](example/createaccounts.json). (Each value is up to input arguments and time)

### Key Updater

__key-updater__ literally supports to update source public key to something else.

#### Usage

```java
// import org.mitumc.sdk.key.*;
// import org.mitumc.sdk.Generator;
// import org.mitumc.sdk.operation.Operation;
// import org.mitumc.sdk.operation.currency.*;

Generator gn = Generator.get("mitum");

String senderPriv = "KzafpyGojcN44yme25UMGvZvKWdMuFv1SwEhsZn8iF8szUz16jskmpr";
String senderAddr = "FcLfoPNCYjSMnxLPiQJQFGTV15ecHn3xY4J2HNCrqbCfmca";

String targetPub = "27uxAwUpvdc9sbRgztW8LrNoHnBmwgKavGuU6KvWzCgnimpu";

Key key = Key.get(targetPub, 100);
Keys keys = Keys.get(new Key[]{key}, 100);

KeyUpdaterFact fact = gn.currency.getKeyUpdaterFact(senderAddr, "MCC", keys);
Operation operation = gn.getOperation(fact);
operation.sign(senderPriv);

operation.exportToJsonFile("keyupdater.json");
```

Then keys of the source account will be replaced with target keys.

### Transfers

To generate an operation, you must prepare target address, not public key. __transfers__ supports to send tokens to another account.

#### Usage

```java
// import org.mitumc.sdk.Generator;
// import org.mitumc.sdk.operation.Operation;
// import org.mitumc.sdk.operation.currency.*;

String senderPriv = "KzafpyGojcN44yme25UMGvZvKWdMuFv1SwEhsZn8iF8szUz16jskmpr";
String senderAddr = "FcLfoPNCYjSMnxLPiQJQFGTV15ecHn3xY4J2HNCrqbCfmca";

String targetAddr = "77UNyuDQtxkYhRMLuKgyQCpWwGZzLoZ4E7S7qZd4Jbmpmca";

Generator gn = Generator.get("mitum");

Amount amount = Amount.get("MCC", "1000");
TransfersItem item = gn.currency.getTransfersItem(targetAddr, new Amount[]{ amount });

TransfersFact fact = gn.currency.getTransfersFact(senderAddr, new TransfersItem[]{ item });

Operation operation = gn.getOperation(fact);
operation.sign(senderPriv);

operation.exportToJsonFile("transfers.json");
```

### Create Contract Accounts

For a new contract account, you need to set `current id` and `initial amount`. You can use a pre-registered account to create and register new contract accounts from the target public keys.

#### Usage

```java
// import org.mitumc.sdk.key.*;
// import org.mitumc.sdk.Generator;
// import org.mitumc.sdk.Amount;
// import org.mitumc.sdk.operation.Operation;
// import org.mitumc.sdk.operation.currency.*;
// import org.mitumc.sdk.operation.currency.extension.*;

String senderPriv = "KzafpyGojcN44yme25UMGvZvKWdMuFv1SwEhsZn8iF8szUz16jskmpr";
String senderAddr = "FcLfoPNCYjSMnxLPiQJQFGTV15ecHn3xY4J2HNCrqbCfmca";

String targetPub = "knW2wVXH399P9Xg8aVjAGuMkk3uTBZwcSpcy4aR3UjiAmpu";

Generator gn = Generator.get("mitum");

Key key = Key.get(targetPub, 100);
Keys keys = Keys.get(new Key[] { keys }, 100);
Amount amount = Amount.get("PEN", "100");

CreateContractAccountsItem item = gn.currency.extension.getCreateContractAccountsItem(keys, new Amount[] { amount });
CreateContractAccountsFact fact = gn.currency.extension.getCreateContractAccountsFact(senderAddr, new CreateContractAccountsItem[] { item });

Operation operation = gn.getOperation(fact);
operation.sign(senderPriv);

operation.exportToJsonFile("createcontractaccounts.json");
```

### Withdraws

__withdraws__ is an operation for withdrawing tokens from a contract account.

#### Usage

```java
// import org.mitumc.sdk.key.*;
// import org.mitumc.sdk.Generator;
// import org.mitumc.sdk.Amount;
// import org.mitumc.sdk.operation.Operation;
// import org.mitumc.sdk.operation.currency.*;
// import org.mitumc.sdk.operation.currency.extension.*;

String senderPriv = "KzafpyGojcN44yme25UMGvZvKWdMuFv1SwEhsZn8iF8szUz16jskmpr";
String senderAddr = "FcLfoPNCYjSMnxLPiQJQFGTV15ecHn3xY4J2HNCrqbCfmca";

String targetAddr = "77UNyuDQtxkYhRMLuKgyQCpWwGZzLoZ4E7S7qZd4Jbmpmca";

Generator gn = Generator.get("mitum");

Amount amount = Amount.get("PEN", "100");

WithdrawsItem item = gn.currency.extension.getWithdrawsItem(targetAddr, new Amount[] { amount });
WithdrawsFact fact = gn.currency.extension.getWithdrawsFact(senderAddr, new WithdrawsItem[] { item });

Operation operation = gn.getOperation(fact);
operation.sign(senderPriv);

operation.exportToJsonFile("withdraws.json");
```

## Document Operations

To create or update documents, you must prepare available document objects for each type of operation item.

For example, __blocksign__ supports blocksign document, a type of __document__ with the hint `mitum-blocksign-document-data`.

However, __blockcity__ supports four types of __document__, including __user/land/vote/history documents__, and their hints are different from __blocksign__.

That is, you must create a document that corresponds to the type of document you want.

So let's start with how to create documents for each type.

### BlockSign Documents

As mentioned, __blocksign__ uses only one document type, __blocksign document__.

First, you must prepare a creator and signers.

For convenience, call each of them `user`.

A `user` can be generated by `Generator.document.blocksign.user(address, signCode, signed)`

What you have to prepare to generate document are...:

* document id
* owner
* file hash
* creator - from `user`
* title
* file size
* a signer list - signers from `user`

Note that every document ids of __blocksign__ are followed by the type suffix `sdi`.

#### Usage

```java
// import org.mitumc.sdk.Generator;
// import org.mitumc.sdk.operation.document.Document;
// import org.mitumc.sdk.operation.document.blocksign.BlockSignUser;

String signer1 = "FcLfoPNCYjSMnxLPiQJQFGTV15ecHn3xY4J2HNCrqbCfmca";
String signer2 = "77UNyuDQtxkYhRMLuKgyQCpWwGZzLoZ4E7S7qZd4Jbmpmca";

Generator gn = Generator.get("mitum");

BlockSignUser creator = gn.document.blocksign.user(signer1, "signcode01", true);
BlockSignUser user1 = gn.document.blocksign.user(signer1, "signcode01", true);
BlockSignUser user2 = gn.document.blocksign.user(signer2, "signcode02", false);

Document document = gn.document.blocksign.document("4000sdi", signer1, "test-hs:01", creator, "test-doc-01", "12345", new BlockSignUser[] { user1, user2 });
```

### BlockCity Documents

Supported document types of __blockcity__ are...:

* User Data
* Land Data
* Voting Data
* History Data

Note a document id for each document type has a unique suffix.

* user data: cui
* land data: cli
* vote data: cvi
* history data: chi

Those documents are used only by __blockcity__.

If you wonder what each argument means, see [Generator](#generator).

#### User Document

What you must prepare before generate a user document are...:

* document id
* Each value in a user statistics
* document owner
* user's gold and bank gold

#### Usage

```java

// import org.mitumc.sdk.Generator;
// import org.mitumc.sdk.operation.document.Document;
// import org.mitumc.sdk.operation.document.blockcity.UserStatistics;

Generator gn = Generator.get("mitum");

UserStatistics statistics = gn.document.blockcity.userStatistics(1, 1, 1, 1, 1, 1, 1);
Document document = gn.document.blockcity.userDocument("user01cui", "FcLfoPNCYjSMnxLPiQJQFGTV15ecHn3xY4J2HNCrqbCfmca", 1, 1, statistics);      
```

#### Land Document

What you must prepare are...:

* document id
* document owner
* address to rent
* area to rent
* renter who rent
* account who rent
* rent date and period

#### Usage

```java
// import org.mitumc.sdk.Generator;
// import org.mitumc.sdk.operation.document.Document;

Generator gn = Generator.get("mitum");

Document document = gn.document.blockcity.landDocument("land01cli", "FcLfoPNCYjSMnxLPiQJQFGTV15ecHn3xY4J2HNCrqbCfmca", "addr-01", "area-01", "renter01", "77UNyuDQtxkYhRMLuKgyQCpWwGZzLoZ4E7S7qZd4Jbmpmca", "2022-02-02", 5);
```

#### Vote Document

What you must prepare are...:

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
// import org.mitumc.sdk.Generator;
// import org.mitumc.sdk.operation.document.Document;

Generator gn = Generator.get("mitum");

Candidate c1 = gn.document.blockcity.candidate("FcLfoPNCYjSMnxLPiQJQFGTV15ecHn3xY4J2HNCrqbCfmca", "nickname-01", "HI", 1);
Candidate c2 = gn.document.blockcity.candidate("77UNyuDQtxkYhRMLuKgyQCpWwGZzLoZ4E7S7qZd4Jbmpmca", "nickname-02", "HELLO", 2);   
Document document = gn.document.blockcity.voteDocument("vote01cvi", "FcLfoPNCYjSMnxLPiQJQFGTV15ecHn3xY4J2HNCrqbCfmca", 1, "2022-02-28T02:20:34.333Z", new Candidate[] { c1, c2 }, "boss01", "FcLfoPNCYjSMnxLPiQJQFGTV15ecHn3xY4J2HNCrqbCfmca", "5");
```

#### History Document

What you must prepare are...:

* document id
* document owner
* name
* account
* date
* usage
* application

#### Usage

```java
// import org.mitumc.sdk.Generator;
// import org.mitumc.sdk.operation.document.Document;

Generator gn = Generator.get("mitum");

Document document = gn.document.blockcity.historyDocument("hist01chi", "77UNyuDQtxkYhRMLuKgyQCpWwGZzLoZ4E7S7qZd4Jbmpmca", "hello", "FcLfoPNCYjSMnxLPiQJQFGTV15ecHn3xY4J2HNCrqbCfmca", "2022-02-25T08:03:22.234Z", "usage090", "app090");
```

### Create Documents

All models based on __mitum-document__ are played with operations,  __create-documents__ and __update-documents__.

So in this section, we will introduce how to generate __create-documents__ and __update-documents__ operation with documents you prepared.

About generating documents, go to the previous section.

To generate __create-documents__ operation, you have to prepare,

* currency id for fees
* document
* sender's address and private key

#### Usage

```java
// import org.mitumc.sdk.Generator;
// import org.mitumc.sdk.operation.document.*;
// import org.mitumc.sdk.operation.Operation;

Generator gn = Generator.get("mitum");

String sender = "FcLfoPNCYjSMnxLPiQJQFGTV15ecHn3xY4J2HNCrqbCfmca";
String priv = "KzafpyGojcN44yme25UMGvZvKWdMuFv1SwEhsZn8iF8szUz16jskmpr";
// document: Document object you made

CreateDocumentsItem item = gn.document.getCreateDocumentsItem(document, "PEN");
CreateDocumentsFact fact = gn.document.getCreateDocumentsFact(sender, new CreateDocumentsItem[] { item });

Operation operation = gn.getOperation(fact);
operation.sign(priv);

operation.exportToJsonFile("createdocuments.json");
```

See the start of [Generate Document Operations](#generate-document-operations) for `Document`.

See [Generator](#generator) for details.

### Update Documents

To generate __create-documents__ operation, you have to prepare,

* currency id for fees
* document object generated along the above instructions.
* sender's address and private key

#### Usage

```java
// import org.mitumc.sdk.Generator;
// import org.mitumc.sdk.operation.document.*;
// import org.mitumc.sdk.operation.Operation;

Generator gn = Generator.get("mitum");

String sender = "FcLfoPNCYjSMnxLPiQJQFGTV15ecHn3xY4J2HNCrqbCfmca";
String priv = "KzafpyGojcN44yme25UMGvZvKWdMuFv1SwEhsZn8iF8szUz16jskmpr";
// document: Document object you made

UpdateDocumentsItem item = gn.document.getUpdateDocumentsItem(document, "PEN");
UpdateDocumentsFact fact = gn.document.getUpdateDocumentsFact(sender, new UpdateDocumentsItem[] { item });

Operation operation = gn.getOperation(fact);
operation.sign(priv);

operation.exportToJsonFile("updatedocuments.json");
```

See the start of [Generate Document Operations](#generate-document-operations) for `Document`.

See [Generator](#generator) for details.

### BlockSign Sign Documents

As mentioned, __sign-documents__ operation is used only for __blocksign__.

So you must use _blocksign specific generator_, `Generator.document.blocksign` to generate items and facts of __sign-documents__.

To generate items of __sign-document__, you must prepare...:

* document id
* owner's address
* currency id for fee

Note that you don't have to prepare document for __sign-documents__. Only document id is needed.

#### Usage

```java
// import org.mitumc.sdk.Generator;
// import org.mitumc.sdk.operation.document.blocksign.*;

Generator gn = Generator.get("mitum");

SignDocumentsItem item = gn.document.blocksign.getSignDocumentsItem("4000sdi", "FcLfoPNCYjSMnxLPiQJQFGTV15ecHn3xY4J2HNCrqbCfmca", "PEN");
SignDocumentsFact fact = gn.document.blocksign.getSignDocumentsFact("FcLfoPNCYjSMnxLPiQJQFGTV15ecHn3xY4J2HNCrqbCfmca", new SignDocumentsItem[] { item });

Operation operation = gn.getOperation(fact);
operation.sign("KzafpyGojcN44yme25UMGvZvKWdMuFv1SwEhsZn8iF8szUz16jskmpr");

operation.exportToJsonFile("signdocuments.json");
```

## Feefi Operations

This part shows how to generate operations for the feefi model.

### Pool Register

__pool-register__ supports the registration of `pool` in the contract account.

#### Usage

```java
// import org.mitumc.sdk.Generator;
// import org.mitumc.sdk.operation.Amount;
// import org.mitumc.sdk.operation.Operation;
// import org.mitumc.sdk.operation.feefi.*;

String senderPriv = "KzafpyGojcN44yme25UMGvZvKWdMuFv1SwEhsZn8iF8szUz16jskmpr";
String senderAddr = "FcLfoPNCYjSMnxLPiQJQFGTV15ecHn3xY4J2HNCrqbCfmca";

String targetAddr = "77UNyuDQtxkYhRMLuKgyQCpWwGZzLoZ4E7S7qZd4Jbmpmca";

Generator gn = Generator.get("mitum");

Amount fee = Amount.get("PEN", "1000");

PoolRegisterFact fact = gn.feefi.getPoolRegisterFact(senderAddr, targetAddr, fee, "PEN", "MCC", "MCC");

Operation op = Generator.get("mitum").getOperation(fact);
op.sign(senderPriv);

op.exportToJsonFile("poolregister.json");
```

### Pool Policy Updater

__pool-policy-updater__ supports to update a registered pool policy.

#### Usage

```java
// import org.mitumc.sdk.Generator;
// import org.mitumc.sdk.operation.Amount;
// import org.mitumc.sdk.operation.Operation;
// import org.mitumc.sdk.operation.feefi.*;

String senderPriv = "KzafpyGojcN44yme25UMGvZvKWdMuFv1SwEhsZn8iF8szUz16jskmpr";
String senderAddr = "FcLfoPNCYjSMnxLPiQJQFGTV15ecHn3xY4J2HNCrqbCfmca";

String targetAddr = "77UNyuDQtxkYhRMLuKgyQCpWwGZzLoZ4E7S7qZd4Jbmpmca";

Generator gn = Generator.get("mitum");

Amount amount = Amount.get("100", "MCC");

PoolPolicyUpdaterFact fact = gn.feefi.getPoolPolicyUpdaterFact(senderAddr, targetAddr, amount, "ABC", "MCC"); // sender, target, amount, pool id, currency id

PoolPolicyUpdater operation = gn.getOperation(fact, "");
operation.sign(senderPriv);

operation.exportToJsonFile("poolpolicyupdater.json");
```

### Pool Deposits

__pool-deposits__ supports depositing amounts into the pool.

#### Usage

```java
// import org.mitumc.sdk.Generator;
// import org.mitumc.sdk.operation.Amount;
// import org.mitumc.sdk.operation.Operation;
// import org.mitumc.sdk.operation.feefi.*;

String senderPriv = "KzafpyGojcN44yme25UMGvZvKWdMuFv1SwEhsZn8iF8szUz16jskmpr";
String senderAddr = "FcLfoPNCYjSMnxLPiQJQFGTV15ecHn3xY4J2HNCrqbCfmca";

String targetAddr = "77UNyuDQtxkYhRMLuKgyQCpWwGZzLoZ4E7S7qZd4Jbmpmca";

Generator gn = Generator.get("mitum");

Amount amount = Amount.get("100", "MCC");

PoolDepositsFact fact = gn.feefi.getPoolDepositsFact(senderAddr, targetAddr, "ABC", amount); // sender, pool, pool id, amount

PoolDeposits operation = gn.getOperation(fact, "");
operation.sign(senderPriv);

operation.exportToJsonFile("pooldeposits.json);
```

### Pool Withdraw

__pool-withdraw__ supports withdrawing amounts from the pool.

#### Usage

```java
// import org.mitumc.sdk.Generator;
// import org.mitumc.sdk.operation.Amount;
// import org.mitumc.sdk.operation.Operation;
// import org.mitumc.sdk.operation.feefi.*;

String senderPriv = "KzafpyGojcN44yme25UMGvZvKWdMuFv1SwEhsZn8iF8szUz16jskmpr";
String senderAddr = "FcLfoPNCYjSMnxLPiQJQFGTV15ecHn3xY4J2HNCrqbCfmca";

String targetAddr = "77UNyuDQtxkYhRMLuKgyQCpWwGZzLoZ4E7S7qZd4Jbmpmca";

Generator gn = Generator.get("mitum");

Amount amount = Amount.get("100", "MCC");

PoolWithdrawFact fact = gn.feefi.getPoolWithdrawFact(senderAddr, targetAddr, "ABC", new Amount[]{ amount }); // sender, pool, pool id, amounts

PoolWithdraw operation = gn.getOperation(fact, "");
operation.sign(senderPriv);

operation.exportToJsonFile("poolwithdraw.json);
```

## NFT Operations

This part shows how to generate operations of the nft model.

### Collection Register

__collection-register__ supports the registration of `collection` in the contract account.

#### Usage

```java
// import org.mitumc.sdk.Generator;
// import org.mitumc.sdk.operation.Operation;
// import org.mitumc.sdk.operation.nft.*;

String senderPriv = "KzafpyGojcN44yme25UMGvZvKWdMuFv1SwEhsZn8iF8szUz16jskmpr";
String senderAddr = "FcLfoPNCYjSMnxLPiQJQFGTV15ecHn3xY4J2HNCrqbCfmca";

String targetAddr = "77UNyuDQtxkYhRMLuKgyQCpWwGZzLoZ4E7S7qZd4Jbmpmca";

Generator gn = Generator.get("mitum");

CollectionRegisterForm form = gn.nft.collectionRegisterForm(
    targetAddr,
    "COL",
    "Collection",
    0,
    "https://localhost:5000",
    new String[] { white0Addr, white1Addr }
);
CollectionRegisterFact fact = gn.nft.getCollectionRegisterFact(
    senderAddr,
    form,
    "PEN"
);

Operation operation = gn.getOperation(fact);
operation.sign(senderPriv);

operation.exportToJsonFile("collectionregister.json");
```

### Collection Policy Updater

__collection-policy-updater__ supports updating collection policies.

#### Usage

```java
// import org.mitumc.sdk.Generator;
// import org.mitumc.sdk.operation.Operation;
// import org.mitumc.sdk.operation.nft.*;

String senderPriv = "KzafpyGojcN44yme25UMGvZvKWdMuFv1SwEhsZn8iF8szUz16jskmpr";
String senderAddr = "FcLfoPNCYjSMnxLPiQJQFGTV15ecHn3xY4J2HNCrqbCfmca";

String whiteAddr = "77UNyuDQtxkYhRMLuKgyQCpWwGZzLoZ4E7S7qZd4Jbmpmca";

Generator gn = Generator.get("mitum");

CollectionPolicy policy = gn.nft.collectionPolicy("Collection", 1, "https://localhost:5000", new String[] { whiteAddr });
CollectionPolicyUpdaterFact fact = gn.nft.getCollectionPolicyUpdaterFact(senderAddr, "COL", policy, "PEN");

Operation operation = gn.getOperation(fact);
operation.sign(senderPriv);

operation.exportToJsonFile("collectionpolicyupdater.json");
```

### Approve

__approve__ supports delegation of authority for specific nft ownership changes.

#### Usage

```java
// import org.mitumc.sdk.Generator;
// import org.mitumc.sdk.operation.Operation;
// import org.mitumc.sdk.operation.nft.*;

String senderPriv = "KzafpyGojcN44yme25UMGvZvKWdMuFv1SwEhsZn8iF8szUz16jskmpr";
String senderAddr = "FcLfoPNCYjSMnxLPiQJQFGTV15ecHn3xY4J2HNCrqbCfmca";

String approvedAddr = "77UNyuDQtxkYhRMLuKgyQCpWwGZzLoZ4E7S7qZd4Jbmpmca";

Generator gn = Generator.get("mitum");

ApproveItem item = gn.nft.getApproveItem(approvedAddr, NFTID.get("COL-00001"), "PEN");
ApproveFact fact = gn.nft.getApproveFact(senderAddr, new ApproveItem[] { item });

Operation operation = gn.getOperation(fact);
operation.sign(senderPriv);

operation.exportToJsonFile("approve.json");
```

### Delegate

__delegate__ supports delegating the authority to change ownership of all nfts held by one general account for a collection.

#### Usage

```java
// import org.mitumc.sdk.Generator;
// import org.mitumc.sdk.operation.Operation;
// import org.mitumc.sdk.operation.nft.*;

String senderPriv = "KzafpyGojcN44yme25UMGvZvKWdMuFv1SwEhsZn8iF8szUz16jskmpr";
String senderAddr = "FcLfoPNCYjSMnxLPiQJQFGTV15ecHn3xY4J2HNCrqbCfmca";

String agentAddr = "77UNyuDQtxkYhRMLuKgyQCpWwGZzLoZ4E7S7qZd4Jbmpmca";

Generator gn = Generator.get("mitum");

DelegateItem item = gn.nft.getDelegateItem("COL", agentAddr, DelegateItem.ALLOW,  "PEN"); // DelegateItem.ALLOW: "allow", DelegateItem.CANCEL: "cancel"
DelegateFact fact = gn.nft.getDelegateFact(senderAddr, new DelegateItem[] { item });

Operation operation = gn.getOperation(fact);
operation.sign(senderPriv);

operation.exportToJsonFile("delegate.json");
```

### NFT Mint

__nft mint__ supports the registration of a new nft in the collection.

#### Usage

This example shows how to create an operation when both the creator and copyrighter are the same account as minting nft.
Actually, any general account can be a creator and a copyrighter.

```java
// import org.mitumc.sdk.Generator;
// import org.mitumc.sdk.operation.Operation;
// import org.mitumc.sdk.operation.nft.*;

String senderPriv = "KzafpyGojcN44yme25UMGvZvKWdMuFv1SwEhsZn8iF8szUz16jskmpr";
String senderAddr = "FcLfoPNCYjSMnxLPiQJQFGTV15ecHn3xY4J2HNCrqbCfmca";

String signer0Addr = "77UNyuDQtxkYhRMLuKgyQCpWwGZzLoZ4E7S7qZd4Jbmpmca";
String signer1Addr = "EMkLBPd51JNBKvAJxWqYVXUVX7bN5b1nnc8h4XLWWwf1mca";

Generator gn = Generator.get("mitum");

NFTSigner signer0 = gn.nft.signer(signer0Addr, 50, false);
NFTSigner signer1 = gn.nft.signer(signer1Addr, 50, false); 
NFTSigners signers = gn.nft.signers(100, new NFTSigner[]{ signer0, signer1 });

MintForm form = gn.nft.mintForm("nfthash", "https://localhost:5000", signers, signers);
MintItem item = gn.nft.getMintItem("COL", form, "PEN");

MintFact fact = gn.nft.getMintFact(senderAddr, new MintItem[]{ item });

Operation operation = gn.getOperation(fact);
operation.sign(senderPriv);

operation.exportToJsonFile("nft-mint.json");
```

### NFT Sign

__nft sign__ supports signing in nft as a creator or copyrighter.

#### Usage

```java
// import org.mitumc.sdk.Generator;
// import org.mitumc.sdk.operation.Operation;
// import org.mitumc.sdk.operation.nft.*;

String senderPriv = "KzafpyGojcN44yme25UMGvZvKWdMuFv1SwEhsZn8iF8szUz16jskmpr";
String senderAddr = "FcLfoPNCYjSMnxLPiQJQFGTV15ecHn3xY4J2HNCrqbCfmca";

Generator gn = Generator.get("mitum");

NFTSignItem item = gn.nft.getSignItem(NFTSignItem.CREATOR, NFTID.get("COL-00001"), "PEN");
NFTSignFact fact = gn.nft.getSignFact(senderAddr, new NFTSignItem[] { item });

Operation operation = gn.getOperation(fact);
operation.sign(senderPriv);

operation.exportToJsonFile("nft-sign.json");
```

### NFT Transfer

__nft transfer__ supports the transfer of nft.

#### Usage

```java
// import org.mitumc.sdk.Generator;
// import org.mitumc.sdk.operation.Operation;
// import org.mitumc.sdk.operation.nft.*;

String senderPriv = "KzafpyGojcN44yme25UMGvZvKWdMuFv1SwEhsZn8iF8szUz16jskmpr";
String senderAddr = "FcLfoPNCYjSMnxLPiQJQFGTV15ecHn3xY4J2HNCrqbCfmca";

String receiverAddr = "77UNyuDQtxkYhRMLuKgyQCpWwGZzLoZ4E7S7qZd4Jbmpmca";

Generator gn = Generator.get("mitum");

NFTTransferItem item = gn.nft.getTransferItem(receiverAddr, NFTID.get("COL-00001"), "PEN");
NFTTransferFact fact = gn.nft.getTransferFact(senderAddr, new NFTTransferItem[] { item });

Operation operation = gn.getOperation(fact);
operation.sign(senderPriv);

operation.exportToJsonFile("nft-transfer.json");
```

### NFT Burn

__nft burn__ supports nft burning.

#### Usage

```java
// import org.mitumc.sdk.Generator;
// import org.mitumc.sdk.operation.Operation;
// import org.mitumc.sdk.operation.nft.*;

String senderPriv = "KzafpyGojcN44yme25UMGvZvKWdMuFv1SwEhsZn8iF8szUz16jskmpr";
String senderAddr = "FcLfoPNCYjSMnxLPiQJQFGTV15ecHn3xY4J2HNCrqbCfmca";

Generator gn = Generator.get("mitum");

BurnItem item = gn.nft.getBurnItem(NFTID.get("COL-00001"), "PEN");
BurnFact fact = gn.nft.getBurnFact(senderAddr, new BurnItem[] { item });

Operation operation = gn.getOperation(fact);
operation.sign(senderPriv);

operation.exportToJsonFile("nft-burn.json");
```

## Generate New Seal

__mitum-java-util__ supports you to generate a seal json file such that the seal is able to consist of several operations. Those operations can be any type __mitum-java-util__ provides.

### Prerequisite

To generate a seal, __mitum-java-util__ requires,

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
JSONParser.writeJsonFileFromHashMap(seal, "seal.json");
```

Then the result format of `writeJsonFileFromHashMap(seal, fileName)` will be like [this](example/seal.json). (Each value is up to input arguments and time)

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

Use `Signer.addSignToOperation(key, targetOperation)` to add new fact signature to `fact_signs`.

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
JSONParser.writeJsonFileFromHashMap(signed, "newOperation.json");
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