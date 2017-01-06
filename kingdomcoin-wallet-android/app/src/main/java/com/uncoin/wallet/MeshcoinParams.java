package com.uncoin.wallet;

import com.google.bitcoin.core.Block;
import com.google.bitcoin.core.NetworkParameters;
import com.google.bitcoin.core.Sha256Hash;
import com.google.bitcoin.core.StoredBlock;
import com.google.bitcoin.core.Transaction;
import com.google.bitcoin.core.TransactionInput;
import com.google.bitcoin.core.TransactionOutput;
import com.google.bitcoin.core.Utils;
import com.google.bitcoin.script.Script;
import com.google.bitcoin.script.ScriptOpCodes;
import com.lambdaworks.crypto.SCrypt;

import org.litecoin.LitecoinParams;
import org.spongycastle.util.encoders.Hex;

import java.io.ByteArrayOutputStream;
import java.math.BigInteger;
import java.security.GeneralSecurityException;

import static com.google.common.base.Preconditions.checkState;

/**
 * Created by kun7788 on 2016. 10. 22..
 */

public class MeshcoinParams extends NetworkParameters {
    public MeshcoinParams() {
        super();
        id = "org.litecoin.production";
        proofOfWorkLimit = Utils.decodeCompactBits(0x1e0fffffL);
        addressHeader = 49;//48
        p2shHeader = 49;
        acceptableAddressCodes = new int[] { 49 };
        port = 8890;//8889;//9333
        packetMagic = 0xfbc0b6dbL;
        dumpedPrivateKeyHeader = 128 + addressHeader;

        targetTimespan = (int)(3.5 * 24 * 60 * 60);
        interval = targetTimespan/((int)(2.5 * 60));

        genesisBlock.setDifficultyTarget(0x1e0ffff0L);
        genesisBlock.setTime(1317972665L);
        genesisBlock.setNonce(2084524493L);
        genesisBlock.removeTransaction(0);
        Transaction t = new Transaction(this);
        try {
            // A script containing the difficulty bits and the following message:
            //
            //   "The Times 03/Jan/2009 Chancellor on brink of second bailout for banks"
            byte[] bytes = Hex.decode
                    ("04ffff001d0104404e592054696d65732030352f4f63742f32303131205374657665204a6f62732c204170706c65e280997320566973696f6e6172792c2044696573206174203536");
            t.addInput(new TransactionInput(this, t, bytes));
            ByteArrayOutputStream scriptPubKeyBytes = new ByteArrayOutputStream();
            Script.writeBytes(scriptPubKeyBytes, Hex.decode
                    ("040184710fa689ad5023690c80f3a49c8f13f8d45b8c857fbcbc8bc4a8e4d3eb4b10f4d4604fa08dce601aaf0f470216fe1b51850b4acf21b179c45070ac7b03a9"));
            scriptPubKeyBytes.write(ScriptOpCodes.OP_CHECKSIG);
            t.addOutput(new TransactionOutput(this, t, Utils.toNanoCoins(50, 0), scriptPubKeyBytes.toByteArray()));
        } catch (Exception e) {
            // Cannot happen.
            throw new RuntimeException(e);
        }
        genesisBlock.addTransaction(t);

        genesisBlock.setMerkleRoot(new Sha256Hash("7352e3e158571b3b4f17427b14c406429938fd1f02ab89973d786f7c636fb5ea"));
        genesisBlock.setHash(new Sha256Hash("0dc69fcbcf80e8b122479262524798ba8dd610e77da07d39f1cea0b8545bbc7b"));

        //genesisBlock(new Sha256Hash("7352e3e158571b3b4f17427b14c406429938fd1f02ab89973d786f7c636fb5ea"));


        String genesisHash = genesisBlock.getHashAsString();
        /*checkState(genesisHash.equals("12a765e31ffd4059bada1e25190f6e98c99d9714d334efa41a195a7e7e04bfe2"),
                genesisBlock);
*/
        subsidyDecreaseBlockCount = 832905;

        dnsSeeds = new String[] {
               /* "dnsseed.litecointools.com",
                "dnsseed.litecoinpool.org",
                "dnsseed.ltc.xurious.com",
                "dnsseed.koin-project.com",
                "dnsseed.weminemnc.com"*/
                "node.walletbuilders.com"
        };
    }

    private static BigInteger MAX_MONEY = Utils.COIN.multiply(BigInteger.valueOf(84000000));
    @Override
    public BigInteger getMaxMoney() { return MAX_MONEY; }

    /*private static org.litecoin.LitecoinParams instance;
    public static synchronized org.litecoin.LitecoinParams get() {
        if (instance == null) {
            instance = new org.litecoin.LitecoinParams();
        }
        return instance;
    }*/

    private static MeshcoinParams instance;
    public static synchronized MeshcoinParams get() {
        if (instance == null) {
            instance = new MeshcoinParams();
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
