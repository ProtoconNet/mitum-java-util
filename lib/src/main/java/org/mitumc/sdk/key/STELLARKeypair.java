/* Copyright 2021 Stellar Development Foundation
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
package org.mitumc.sdk.key;

import org.mitumc.sdk.Constant;
import org.stellar.sdk.KeyPair;

public class STELLARKeypair extends BaseKeypair {
    private KeyPair keypair;

    STELLARKeypair(String key) {
        super(key);
        generatePublicKey();
    }

    STELLARKeypair(String key, String type) {
        super(key, type);
        generatePublicKey();
    }

    public static STELLARKeypair newKeypair() {
        KeyPair stellarKeypair = KeyPair.random();
        return new STELLARKeypair(String.valueOf(stellarKeypair.getSecretSeed()), Constant.KEY_STELLAR_PRIVATE);
    }

    public Object getKeypair() {
        return this.keypair;
    }

    @Override
    void generatePublicKey() {
        this.keypair = KeyPair.fromSecretSeed(this.privateKey.getRawKey());
        this.publicKey = new BaseKey(keypair.getAccountId(), Constant.KEY_STELLAR_PUBLIC);
    }

    @Override
    public byte[] sign(byte[] target) {
        return this.keypair.sign(target);
    }
}