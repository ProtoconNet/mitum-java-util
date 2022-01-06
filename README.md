# mitum-java-util

'mitum-java-util' will introduce the usage of [mitum-currency](https://github.com/ProtoconNet/mitum-currency) and [mitum-data-blocksign](https://github.com/ProtoconNet/mitum-data-blocksign) for Java.

'mitum-java-util' now supports generating fact, operation and sign with btc wif based keypairs.

## Installation

Recommended requirements for 'mitum-java-util' are,

* Java - OpenJDK v16.0.1
* Javac - javac v16.0.1

Additionally, This project is using below external Java libraries.

* [bitcoinj v0.14.7](https://bitcoinj.org/)

And you must download and add [ecdsa-keygen-java v1.4](https://github.com/wyuinche/ecdsa-keygen-java) to the project to build.

```sh
$ java -version
openjdk version "16.0.1" 2021-04-20
OpenJDK Runtime Environment (build 16.0.1+9-Ubuntu-120.04)
OpenJDK 64-Bit Server VM (build 16.0.1+9-Ubuntu-120.04, mixed mode, sharing)

$ javac -version
javac 16.0.1
```
[Download jar file](release/) and include the package to your project.

The latest version is `mitum-java-util-1.3.0.jar`.

#### Gradle
```sh
implementation files('./lib/mitum-java-util-1.3.0.jar')
```

Replace `./lib/mitum-java-util-1.3.0.jar` with your file path.

## Generate New Operation

### Operations

'mitum-java-util' provides three operations of 'mitum-currency',

* `Create-Accounts` creates an account corresponding to any public key with a pre-registered account.
* `Key-Updater` updates the public key of the account to something else.
* `Transfers` transfers tokens from the account to another account.

'mitum-currency' supports various kinds of operations, but 'mitum-java-util' will provide these frequently used operations.

In addition, 'mitum-java-util' provides three operations of 'mitum-data-blocksign',

* `Create-Documents` creates an document with file hash.
* `Sign-Documents` signs the document.
* `Transfer-Documents` transfers documents from the account to another account.

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

### Generate Keypairs

`mitum-java-util` supports to generate keypair from private key and seed.

And you can just get a new keypair by `Keypair.create()`.

There are fixed type suffixes for private key, public key and address.

* private key -> mpr
* public key -> mpu
* account address -> mca

#### Keypair (org.mitumc.sdk.key.Keypair) - create / fromSeed / fromPrivateKey

`Keypair.create()` returns new keypair for mitum.

You can use `Keypair.fromPrivateKey(String key)` when you already have a private key.

If you have seed of your private key, just use `Keypair.fromSeed(String seed)`.

Or, you can use `Keypair.fromSeed(byte[] seed)`.

`new String(seed).length()` should be longer than or equal to 36.

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

### Generate Operation and Seal

`mitum-java-util` supports to generate operations of `mitum-currency` and `mitum-data-blocksign`.

#### Generator (org.mitumc.sdk.Generator)

You can use `Generator` by this library.

First of all, set network id by `Generator.get(id)`.

For `mitum-currency`, use `Generator.currency()`.

Or, for `mitum-data-blocksign`, use `Generator.blockSign()`.

```java
String id = 'mitum';
Generator generator = Generator.get(id);

CurrencyGenerator cgn = generator.currency(); // org.mitumc.sdk.operation.currency.CurrencyGenerator;
BlockSignGenerator bgn = generator.blockSign(); // org.mitumc.sdk.operation.blocksign.BlockSignGenerator;
```

#### CurrencyGenerator (org.mitumc.sdk.operation.currency.CurrencyGenerator)

Using `CurrencyGenerator`, below methods are available.

```java
Key newKey(String key, int weight);
Keys newKeys(int threshold);
Keys newKeys(Key[] keys, int threshold); 

Amount newAmount(String currency, String amount);

CreateAccountsItem newCreateAccountsItem(Keys keys, Amount[] amounts);
TransfersItem newTransfersItem(String receiver, Amount[] amounts);

CreateAccountsFact newCreateAccountsFact(String sender);
CreateAccountsFact newCreateAccountsFact(String sender, CreateAccountsItem[] items);

KeyUpdaterFact newKeyUpdaterFact(String target, String currencyId, Keys keys);

TransfersFact newTransfersFact(String sender);
TransfersFact newTransfersFact(String sender, TransfersItem[] items);
```

#### BlockSignGenerator (org.mitumc.sdk.operation.blocksign.BlockSignGenerator)

Using `BlockSignGenerator`, below methods are available.

```java
CreateDocumentsItem newCreateDocumentsItem(String fileHash, int documentId, String signcode, String title, int size, String currencyId, String[] signers, String[] signcodes);
SignDocumentsItem newSignDocumentsItem(String owner, int documentId, String currencyId);
TransferDocumentsItem newTransferDocumentsItem(String owner, String receiver, int documentId, String currencyId);

BlockSignFact newBlockSignFact(String sender, CreateDocumentsItem[] items);
BlockSignFact newBlockSignFact(String sender, SignDocumentsItem[] items);
BlockSignFact newBlockSignFact(String sender, TransferDocumentsItem[] items);
```

#### Generate Operation by Generator

`Generator` provides methods for generating operation and seal.

```java
Operation newOperation(OperationFact fact);
Operation newOperation(String memo, OperationFact fact);
HashMap<String, Object> newSeal(String signKey, Operation[] operations);
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
Generator generator = Generator.get('mitum');

Key key = generator.currency().newKey("24TbbrNYVngpPEdq6Zc5rD1PQSTGQpqwabB9nVmmonXjqmpu", 100);
Keys keys = generator.currency().newKeys(100);
keys.addKey(key);

Keys keys2 = newKeys(new Key[]{key}, 100);

String address = keys.getAddress(); // your address
```

Note that 'keys' and 'keys2' work same.

### Generate JSON File from Operation and Seal

#### JSONParser (org.mitumc.sdk.JSONParser)

You can create a json file of generated operation object without `JSONParser`. However, I recommend to use `JSONParser` for convenience.

Methods that `JSONParser` supports are,

```java
void createJSON(JsonObject target, String fpName);
void createJSON(HashMap target, String fpName);
```

A use-case of `JSONParser` will be introduced in the next part, too.

### Generate Create-Accounts 

For new account, `currency id` and `initial amount` must be set. With source account, you can create and register new account of target public key.

#### Usage

```java
/*
import org.mitumc.sdk.key.*;
import org.mitumc.sdk.Generator;
import org.mitumc.sdk.JSONParser;
import org.mitumc.sdk.operation.currency.*;
*/

String sourcePriv = "KzafpyGojcN44yme25UMGvZvKWdMuFv1SwEhsZn8iF8szUz16jskmpr";
String sourceAddr = "FcLfoPNCYjSMnxLPiQJQFGTV15ecHn3xY4J2HNCrqbCfmca";

String targetPub = "knW2wVXH399P9Xg8aVjAGuMkk3uTBZwcSpcy4aR3UjiAmpu";

Generator gn = Generator.get('mitum');

Key key = gn.currency().newKey(targetPub, 100);
Keys keys = gn.currency().newKeys(new Key[]{key}, 100);

Amount amount = gn.currency().newAmount("MCC", "1000");
CreateAccountsItem item = gn.currency().newCreateAccountsItem(keys, new Amount[]{amount});

CreateAccountsFact fact = gn.currency().newCreateAccountsFact(sourceAddr, new CreateAccountsItem[]{item});

Operation operation = gn.newOperation(fact);
operation.addSign(sourcePriv);

JSONParser.createJSON(operation.toDict(), "createaccounts.json");
```

You must add new fact signature by `addSign()` before creating operation json files.

If you would like to change 'network id' for the operation, use `newOperation(fact, networkId)` or `newOperation(memo, fact, networkId)` instead of `newOperation(fact)` and `newOperation(memo, fact)`

With `JSONParser.createJSON(target, fileName)`, the result format will be like [this](example/createaccounts.json). (Each value is up to input arguments and time)

### Generate Key-Updater

Key-Updater literally supports to update source public key to something else.

#### Usage

```java
/*
import org.mitumc.sdk.key.*;
import org.mitumc.sdk.Generator;
import org.mitumc.sdk.JSONParser;
import org.mitumc.sdk.operation.currency.*;
*/

Generator gn = Generator.get('mitum');

String sourcePriv = "KzafpyGojcN44yme25UMGvZvKWdMuFv1SwEhsZn8iF8szUz16jskmpr";
String sourceAddr = "FcLfoPNCYjSMnxLPiQJQFGTV15ecHn3xY4J2HNCrqbCfmca";

String targetPub = "27uxAwUpvdc9sbRgztW8LrNoHnBmwgKavGuU6KvWzCgnimpu";

Key key = gn.currency().newKey(targetPub, 100);
Keys keys = gn.currency().newKeys(new Key[]{key}, 100);

KeyUpdaterFact fact = gn.currency().newKeyUpdaterFact(sourceAddr, "MCC", keys);
Operation operation = gn.newOperation(fact);
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
import org.mitumc.sdk.operation.currency.*;
*/
String sourcePriv = "KzafpyGojcN44yme25UMGvZvKWdMuFv1SwEhsZn8iF8szUz16jskmpr";
String sourceAddr = "FcLfoPNCYjSMnxLPiQJQFGTV15ecHn3xY4J2HNCrqbCfmca";

String targetAddr = "77UNyuDQtxkYhRMLuKgyQCpWwGZzLoZ4E7S7qZd4Jbmpmca";

Generator gn = Generator.get('mitum');

Amount amount = gn.currency().newAmount("MCC", "1000");
TransfersItem item = gn.currency().newTransfersItem(targetAddr, new Amount[]{amount});

TransfersFact fact = gn.currency().newTransfersFact(sourceAddr, new TransfersItem[]{item});

Operation operation = OperationManager.newOperation(fact);
operation.addSign(sourcePriv);

JSONParser.createJSON(operation.toDict(), "transfers.json");
```

### Generate Create-Documents

To generate an operation, you must prepare file-hash. Create-Document supports to create documents with setting signers who must sign them.

#### Usage

```java
/*
import org.mitumc.sdk.Generator;
import org.mitumc.sdk.JSONParser;
import org.mitumc.sdk.operation.blocksign.*;
*/
String sourcePriv = "KzafpyGojcN44yme25UMGvZvKWdMuFv1SwEhsZn8iF8szUz16jskmpr";
String sourceAddr = "FcLfoPNCYjSMnxLPiQJQFGTV15ecHn3xY4J2HNCrqbCfmca";

Generator gn = Generator.get('mitum');

CreateDocumentsItem item = gn.blockSign().newCreateDocumentsItem("absscd:mbfh-v0.0.1", 300, "user03", "title300", 1234, "MCC", new String[0], new String[]{"user04"});
BlockSignFact<CreateDocumentsItem> fact = gn.blockSign().newBlockSignFact(sourceAddr, new CreateDocumentsItem[]{item});

Operation operation = gn.newOperation(fact);
operation.addSign(sourcePriv);

JSONParser.createJSON(operation.toDict(), "createdocuments.json");
```

### Generate Sign-Documents

To generate an operation, you must prepare owner and document id. Sign-Document supports to sign documents registered by 'mitum-data-blocksign'

#### Usage

```java
/*
import org.mitumc.sdk.Generator;
import org.mitumc.sdk.JSONParser;
import org.mitumc.sdk.operation.blocksign.*;
*/
String sourcePriv = "KzafpyGojcN44yme25UMGvZvKWdMuFv1SwEhsZn8iF8szUz16jskmpr";
String sourceAddr = "FcLfoPNCYjSMnxLPiQJQFGTV15ecHn3xY4J2HNCrqbCfmca";

Generator gn = Generator.get('mitum');

SignDocumentsItem item = gn.blockSign().newSignDocumentsItem(sourceAddr, 0, "MCC");
BlockSignFact<SignDocumentsItem> fact = gn.blockSign().newBlockSignFact(sourceAddr, new SignDocumentsItem[]{item});

Operation operation = gn.newOperation(fact);
operation.addSign(sourcePriv);

JSONParser.createJSON(operation.toDict(), "signdocuments.json");
```

### ~~Generate Transfer-Documents~~

__This operation is not supported anymore.__

~~To generate an operation, you must prepare owner and document id. Transfer-Document supports to transfer documents to other account.~~

#### Usage

```java
/*
import org.mitumc.sdk.Generator;
import org.mitumc.sdk.JSONParser;
import org.mitumc.sdk.operation.blocksign.*;
*/
String sourcePriv = "KzafpyGojcN44yme25UMGvZvKWdMuFv1SwEhsZn8iF8szUz16jskmpr";
String sourceAddr = "FcLfoPNCYjSMnxLPiQJQFGTV15ecHn3xY4J2HNCrqbCfmca";

String targetAddr = "Bz4LPnkSrvGaMwKediXjxTkB6JZkQdbqQHyQbLVcWHprmca";

Generator gn = Generator.get('mitum');

TransferDocumentsItem item = gn.blockSign().newTransferDocumentsItem(sourceAddr, targetAddr, 0, "MCC");
BlockSignFact<TransferDocumentsItem> fact = gn.blockSign().newBlockSignFact(sourceAddr, new TransferDocumentsItem[]{item});

Operation operation = gn.newOperation(fact);
operation.addSign(sourcePriv);

JSONParser.createJSON(operation.toDict(), "transferdocuments.json");
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

Generator gn = Generator('mitum');

String signKey = "KzafpyGojcN44yme25UMGvZvKWdMuFv1SwEhsZn8iF8szUz16jskmpr";

HashMap<String, Object> seal = gn.newSeal(signKey, new Operation[]{operations});
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

### Signer (org.mitumc.sdk.Signer)

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
String id = 'mitum';
String key = "KzafpyGojcN44yme25UMGvZvKWdMuFv1SwEhsZn8iF8szUz16jskmpr";

Signer signer = Signer.get(id, key);

HashMap<string, Object> signed = signer.addSignToOperation("operation.json");
JSONParser.createJSON(signed, "newOperation.json");
```

Signer class itself doesn't create json file of new operation.

Use `JSONParser` if you need.