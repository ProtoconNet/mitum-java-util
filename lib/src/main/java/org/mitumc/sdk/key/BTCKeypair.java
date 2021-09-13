/* Copyright 2021 wyuinche
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
import org.mitumc.sdk.util.Hint;

import com.wuin.ecdsakeyj.*;

public class BTCKeypair extends BaseKeypair {
    private BTCKeyPair keypair;

    BTCKeypair(String key) {
        super(key);
        generatePublicKey();
    }

    private BTCKeypair(String key, String type) {
        super(key, type);
        generatePublicKey();
    }

    public static BTCKeypair newKeypair() {
        BTCKeyPair kp = new BTCKeyPair();
        return new BTCKeypair(kp.getPrivateKey() + ":" +  new Hint(Constant.KEY_BTC_PRIVATE).getHint());
    }

    public Object getKeypair() {
        return this.keypair;
    }

    @Override
    void generatePublicKey() {
        this.keypair = new BTCKeyPair(this.privateKey.getRawKey());
        this.publicKey = new BaseKey(this.keypair.getPublicKey(), Constant.KEY_BTC_PUBLIC);
    }

    @Override
    public byte[] sign(byte[] target) {
        return this.keypair.sign(target);
    }
}