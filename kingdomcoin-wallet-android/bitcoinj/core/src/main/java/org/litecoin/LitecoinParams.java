/*
 * Copyright 2013 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.litecoin;

import com.google.bitcoin.core.*;
import com.google.bitcoin.script.Script;
import com.google.bitcoin.script.ScriptOpCodes;
import com.lambdaworks.crypto.SCrypt;
import org.spongycastle.util.encoders.Hex;

import java.io.ByteArrayOutputStream;
import java.math.BigInteger;
import java.security.GeneralSecurityException;

import static com.google.common.base.Preconditions.checkState;

/**
 * Parameters for the testnet, a separate public instance of Bitcoin that has relaxed rules suitable for development
 * and testing of applications and new Bitcoin versions.
 */
public class LitecoinParams extends NetworkParameters {
    public LitecoinParams() {
        super();
        id = "org.litecoin.production";
        proofOfWorkLimit = Utils.decodeCompactBits(0x1e0fffffL);
        addressHeader = 45;
        acceptableAddressCodes = new int[] { addressHeader };
        port = 9381;
        packetMagic = 0x7880d2abL;//0xfbc0b6dbL;
        dumpedPrivateKeyHeader = 128 + addressHeader;

        //interval = INTERVAL;
        //targetTimespan = TARGET_TIMESPAN;
        targetTimespan = 1;//(int)(3.5 * 24 * 60 * 60);
        interval = 1;//targetTimespan/((int)(2.5 * 60));
        genesisBlock.setDifficultyTarget(0x1e0ffff0L);
        genesisBlock.setTime(1481402709L);
        genesisBlock.setNonce(398559L);

        genesisBlock.removeTransaction(0);
        Transaction t = new Transaction(this);
        try {

            // A script containing the difficulty bits and the following message:
            //
            //   "The Times 03/Jan/2009 Chancellor on brink of second bailout for banks"
            byte[] bytes = Hex.decode
                    ("04ffff001d01040432303136");
            t.addInput(new TransactionInput(this, t, bytes));
            ByteArrayOutputStream scriptPubKeyBytes = new ByteArrayOutputStream();
            Script.writeBytes(scriptPubKeyBytes, Hex.decode
                    ("04e421bab90567ccbe9ab34bdb27d9fc87f1efcad5ba76754c75c25d434fa57de5aaa4e5095371d9bd120fec48a16e41d88e3039b750e30a02fdad50e7e5dd2c13"));
            scriptPubKeyBytes.write(ScriptOpCodes.OP_CHECKSIG);
            t.addOutput(new TransactionOutput(this, t, Utils.toNanoCoins(1125, 0), scriptPubKeyBytes.toByteArray()));
        } catch (Exception e) {
            // Cannot happen.
            throw new RuntimeException(e);
        }
        genesisBlock.addTransaction(t);

        String genesisHash = genesisBlock.getHashAsString();
        System.out.println("genesisHash ========= " + genesisHash);
       /* checkState(genesisHash.equals("536c0e7ea569c228e892e86f2a02b2b9021f050682edd7683a00a982fc5791a5"),
                genesisBlock);
*/
        System.out.println("========= " + genesisHash);
        subsidyDecreaseBlockCount = 840000;

        dnsSeeds = new String[] {
                "115.68.73.67"
                ,"115.68.73.68"
                ,"115.68.73.69"
        };
    }

    private static BigInteger MAX_MONEY = Utils.COIN.multiply(BigInteger.valueOf(2100000000));
    @Override
    public BigInteger getMaxMoney() { return MAX_MONEY; }

    private static LitecoinParams instance;
    public static synchronized LitecoinParams get() {
        if (instance == null) {
            instance = new LitecoinParams();
        }
        return instance;
    }

    /** The number of previous blocks to look at when calculating the next Block's difficulty */
    @Override
    public int getRetargetBlockCount(StoredBlock cursor) {
        if (cursor.getHeight() + 1 != getInterval()) {
            //Logger.getLogger("wallet_ltc").info("Normal LTC retarget");
            return getInterval();
        } else {
            //Logger.getLogger("wallet_ltc").info("Genesis LTC retarget");
            return getInterval() - 1;
        }
    }

    @Override public String getURIScheme() { return "kingdomcoin:"; }

    /** Gets the hash of the given block for the purpose of checking its PoW */
    public Sha256Hash calculateBlockPoWHash(Block b) {
        byte[] blockHeader = b.cloneAsHeader().bitcoinSerialize();
        try {
            return new Sha256Hash(Utils.reverseBytes(SCrypt.scrypt(blockHeader, blockHeader, 1024, 1, 1, 32)));
        } catch (GeneralSecurityException e) {
            throw new RuntimeException(e);
        }
    }

    static {
        NetworkParameters.registerParams(get());
        NetworkParameters.PROTOCOL_VERSION = 70002;
    }
}