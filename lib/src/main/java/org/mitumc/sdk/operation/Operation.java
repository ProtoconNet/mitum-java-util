/* Copyright 2021 BitcoinJ
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 */
package org.mitumc.sdk.operation;

import java.util.ArrayList;
import java.util.HashMap;

import org.bitcoinj.core.Base58;
import org.mitumc.sdk.BytesChangeable;
import org.mitumc.sdk.Constant;
import org.mitumc.sdk.Dictionariable;
import org.mitumc.sdk.sign.FactSign;
import org.mitumc.sdk.sign.SignManager;
import org.mitumc.sdk.util.Hash;
import org.mitumc.sdk.util.Hint;
import org.mitumc.sdk.util.Util;

public class Operation implements BytesChangeable, Dictionariable {
    private Hint hint;
    private String memo;
    private OperationFact fact;
    private ArrayList<FactSign> factSigns;
    private Hash hash;

    private String networkId;

    Operation(OperationFact fact) {
        this("", fact);
    }

    Operation(String memo, OperationFact fact) {
        this.memo = memo;
        this.fact = fact;
        this.factSigns = new ArrayList<FactSign>();
        setHint();
        // generateHash();
    }

    Operation(OperationFact fact, String NetworkId) {
        this("", fact, NetworkId);
    }

    Operation(String memo, OperationFact fact, String networkId) {
        this(memo, fact);
        this.networkId = networkId;
    }

    private void setHint() {
        String type = this.fact.getType();
        if(type.equals(Constant.MC_CREATE_ACCOUNTS_OPERATION_FACT)) {
            this.hint = new Hint(Constant.MC_CRAETE_ACCOUNTS_OPERATION);
        }
        else if(type.equals(Constant.MC_KEY_UPDATER_OPERATION_FACT)) {
            this.hint = new Hint(Constant.MC_KEY_UPDATER_OPERATION);
        }
        else if(type.equals(Constant.MC_TRANSFERS_OPERATION_FACT)) {
            this.hint = new Hint(Constant.MC_TRANSFERS_OPERATION);
        }
        else if(type.equals(Constant.MBS_CREATE_DOCUMENTS_OPERATION_FACT)) {
            this.hint = new Hint(Constant.MBS_CREATE_DOCUMENTS_OPERATION);
        }
        else if(type.equals(Constant.MBS_SIGN_DOCUMENTS_OPERATION_FACT)) {
            this.hint = new Hint(Constant.MBS_SIGN_DOCUMENTS_OPERATION);
        }
        else if(type.equals(Constant.MBS_TRANSFER_DOCUMENTS_OPERATION_FACT)) {
            this.hint = new Hint(Constant.MBS_TRANSFER_DOCUMENTS_OPERATION);
        }
        else {
            Util.raiseError("Invalid fact type for Operation.");
        }
    }

    private void generateHash() {
        this.hash = new Hash(toBytes());
    }

    public void addSign(String signKey) {
        this.factSigns.add(
            SignManager.getFactSignWithSignKey(
                Util.concatByteArray(
                    this.fact.hash.getSha3Digest(), 
                    this.networkId != null ? this.networkId.getBytes() : Constant.NETWORK_ID.getBytes()), 
                signKey));
        generateHash();
    }

    public Hash getHash() {
        if(this.hash != null){
            return this.hash;
        }

        return null;
    }

    @Override
    public byte[] toBytes() {
        if(this.factSigns.size() < 1) {
            Util.raiseError("Fact Sign is empty.");
        }
        
        byte[] bfactHash = this.fact.hash.getSha3Digest();
        byte[] bfactSigns = Util.<FactSign>concatItemArray(this.factSigns);
        byte[] bmemo = this.memo.getBytes();

        return Util.concatByteArray(bfactHash, bfactSigns, bmemo);
    }

    @Override
    public HashMap<String,Object> toDict() {
        if(this.factSigns.size() < 1) {
            Util.raiseError("Fact Sign is empty.");
        }

        HashMap<String, Object> hashMap = new HashMap<>();

        hashMap.put("memo", this.memo);
        hashMap.put("_hint", this.hint.getHint());
        hashMap.put("fact", this.fact.toDict());
        hashMap.put("hash", Base58.encode(this.hash.getSha3Digest()));

        ArrayList<Object> arr = new ArrayList<>();
        for(FactSign factSign : this.factSigns) {
            arr.add(factSign.toDict());
        }
        hashMap.put("fact_signs", arr);

        return hashMap;
    }
}