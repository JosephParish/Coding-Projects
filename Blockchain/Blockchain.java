import java.util.*;
import java.math.BigInteger;
import java.security.*;
import javax.crypto.Cipher;
import java.util.Base64;

/**
 *  This is a Blockchain structure with the ability to create blocks and chain them through hashes.
 *  This has been created with further implementation in mind.
 *
 *  These are future improvements I would make:
 *      1.  implement a checking algorithm to reject/remove invalid blocks
 *      2.  implement an algorithm to check and handle forking
 */

// ----------------------------- Main.java -----------------------------

public class Main {
    public static void main(String[] args) throws Exception {
        Blockchain chain = new Blockchain(0x0d00ffffL);

        Block block1 = Block.createPoSBlock(
            chain.chain.get(chain.chain.size() - 1).getHash(),
            Arrays.stream(TransactionalData.T1).map(Transaction::toString).toArray(String[]::new),
            TransactionalData.S1,
            75
        );

        Block block2 = Block.createPoABlock(
            block1.getHash(),
            Arrays.stream(TransactionalData.T2).map(Transaction::toString).toArray(String[]::new),
            TransactionalData.S2,
            TransactionalData.AUTH_S2
        );

        chain.submitBlock(block1);
        chain.submitBlock(block2);
        chain.processPendingBlocks();
        chain.printChain();
    }
}

// ----------------------------- Blockchain.java -----------------------------

public class Blockchain {
    private final List<Block> chain = new ArrayList<>();
    private final Queue<Block> unverifiedBlocks = new LinkedList<>();
    private final BigInteger targetHash;

    public Blockchain(long compactDifficulty) {
        this.targetHash = generateTarget(compactDifficulty);
        Block genesis = Block.createPoWBlock(0, TransactionalData.GENESIS_T, TransactionalData.GENESIS_S, 0L);
        chain.add(genesis);
    }

    public void submitBlock(Block block) {
        unverifiedBlocks.add(block);
    }

    public void processPendingBlocks() {
        while (!unverifiedBlocks.isEmpty()) {
            Block block = unverifiedBlocks.poll();
            if (validate(block)) {
                chain.add(block);
                System.out.println("Block added: " + block.getHash());
            } else {
                System.out.println("Block rejected: " + block.getHash());
            }
        }
    }

    private boolean validate(Block block) { 
        if (!block.verifyTransactionSignatures()) {
            return false;
        } 
        
        switch (block.getType()) {
            case PoW -> return new BigInteger(1, block.getHashBytes()).compareTo(targetHash) < 0;
            case PoS -> return block.getVotes() > 50;
            case PoA -> return block.hasValidAuthoritySignature();
            default -> return false;
        }
    }

    private BigInteger generateTarget(long compact) {
        int exp = (int)(compact >> 24);
        int mantissa = (int)(compact & 0x007fffffL);
        if ((compact & 0x00800000L) != 0) mantissa |= 0xff800000;
        return BigInteger.valueOf(mantissa).shiftLeft(8 * (exp - 3));
    }

    public void printChain() {
        chain.forEach(b -> System.out.println(b));
    }
}

// ----------------------------- Block.java -----------------------------

enum TypeOfBlock { PoW, PoS, PoA }

class Block {
    private final TypeOfBlock type;
    private final long nonce;
    private final int votes;
    private final String authoritySignature;

    private final int previousHash;
    private final String[] transactions;
    private final String[] signatures;
    private final byte[] hashBytes;

    private final Map<String, PublicKey> pubKeyMap;

    private Block(TypeOfBlock type, int previousHash, String[] t, String[] s, long nonce, int votes, String authS, Map<String, PublicKey> keys) throws Exception {
        this.type = type;
        this.previousHash = previousHash;
        this.transactions = t;
        this.signatures = s;
        this.nonce = nonce;
        this.votes = votes;
        this.authoritySignature = authyS;
        this.pubKeyMap = keys;
        this.hashBytes = computeHashBytes();
    }

    public static Block createPoWBlock(int prevHash, String[] t, String[] s, long nonce) throws Exception {
        return new Block(TypeOfBlock.PoW, prevHash, t, s, nonce, 0, null, getAuthorityKeys());
    }

    public static Block createPoSBlock(int prevHash, String[] t, String[] s, int votes) throws Exception {
        return new Block(TypeOfBlock.PoS, prevHash, t, s, 0, votes, null, getAuthorityKeys());
    }

    public static Block createPoABlock(int prevHash, String[] t, String[] s, String authS) throws Exception {
        return new Block(TypeOfBlock.PoA, prevHash, t, s, 0, 0, authS, getAuthorityKeys());
    }

    static Map<String, PublicKey> getAuthorityKeys() {
        Map<String, PublicKey> map = new HashMap<>();
        for (EncryptionSignature e : TransactionalData.AUTHORITIES) {
            map.put(e.name, e.publicKey);
        }
        return map;
    }

    private byte[] computeHashBytes() throws Exception {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        digest.update(Integer.toString(previousHash).getBytes());
        for (String t : transactions) digest.update(t.getBytes());
        if (type == TypeOfBlock.PoW) digest.update(Long.toString(nonce).getBytes());
        return digest.digest();
    }

    public boolean verifySignatures() {
        try {
            for (int i = 0; i < transactions.length; i++) {
                String t = transactions[i];
                String s = signatures[i];
                
                for (EncryptionSignature user : TransactionalData.USERS) {
                    if (user.verify(t, s)) {
                        break;
                    }
                }
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isValidPOA() {
        try {
            for (EncryptionSignature auth : TransactionalData.AUTHORITIES) {
                if (auth.verify(Integer.toString(Arrays.hashCode(transactions)), authoritySignature)) {
                    return true;
                }
            }
        } catch (Exception ignored) {}
        return false;
    }

    public byte[] getHashBytes() { return hashBytes; }
    public int getHash() { return Arrays.hashCode(hashBytes); }
    public TypeOfBlock getType() { return type; }
    public int getVotes() { return votes; }

    @Override
    public String toString() {
        return "Block" +
               "type = " + type +
               ", prevHash = " + previousHash +
               ", hash = " + getHash() +
               ", txCount = " + transactions.length + 
                (type == TypeOfBlock.PoW ? ", nonce = " + nonce : type == TypeOfBlock.PoS ? ", votes = " + votes : ", authSig = " + authoritySignature) +
               " }";
    }
}

// ----------------------------- Transaction.java -----------------------------

class Transaction {
    final int num, payer, payee;
    Transaction(int num, int payer, int payee) { this.num = num; this.payer = payer; this.payee = payee; }

    @Override
    public String toString() {
        return String.format("Transaction(num=%d, payer=%d, payee=%d)", num, payer, payee);
    }
}

// ----------------------------- EncryptionSignature.java -----------------------------

class EncryptionSignature {
    final PublicKey publicKey;
    private final PrivateKey privateKey;
    final String name;

    EncryptionSignature(String name) throws Exception {
        this.name = name;
        KeyPairGenerator gen = KeyPairGenerator.getInstance("RSA");
        gen.initialize(1024);
        KeyPair kp = gen.generateKeyPair();
        this.privateKey = kp.getPrivate();
        this.publicKey = kp.getPublic();
    }

    String sign(String msg) throws Exception {
        Signature sg = Signature.getInstance("SHA256withRSA");
        sg.initSign(privateKey);
        sg.update(msg.getBytes());
        return Base64.getEncoder().encodeToString(sg.sign());
    }

    boolean verify(String msg, String signature) throws Exception {
        Signature s = Signature.getInstance("SHA256withRSA");
        s.initVerify(publicKey);
        s.update(msg.getBytes());
        return s.verify(Base64.getDecoder().decode(signature));
    }
}

// ----------------------------- TransactionalData.java -----------------------------

class TransactionalData {
    static final EncryptionSignature USER1, USER2, USER3, USER4;
    static final EncryptionSignature AUTH1, AUTH2;
    static final EncryptionSignature[] USERS, AUTHORITIES;

    static final Transaction[] GENESIS_T, T1, T2;
    static final String[] GENESIS_S, S1, S2;
    static final String AUTH_S2;

    static {
        try {
            USER1 = new EncryptionSignature("Apollo");
            USER2 = new EncryptionSignature("Ares");
            USER3 = new EncryptionSignature("Dionysus");
            USER4 = new EncryptionSignature("Hephaestus");
            USERS = new[] { USER1, USER2, USER3, USER4 };

            AUTH1 = new EncryptionSignature("Momus");
            AUTH2 = new EncryptionSignature("Moros");
            AUTHORITIES = new[] { AUTH1, AUTH2 };

            GENESIS_T = new[] { new Transaction(20, 0, 1), new Transaction(20, 0, 2), new Transaction(20, 0, 3), new Transaction(20, 0, 4) };
            T1 = new[] { new Transaction(20, 2, 1), new Transaction(20, 1, 2) };
            T2 = new[] { new Transaction(20, 1, 3), new Transaction(40, 3, 1) };

            GENESIS_S = Arrays.stream(GENESIS_TRANSACTIONS)
                                 .map(Transaction::toString)
                                 .map(USER1::sign)
                                 .toArray(String[]::new);
            S1 = new String[] { USER1.sign(TXS1[0].toString()), USER2.sign(TXS1[1].toString()) };
            S2 = new String[] { USER3.sign(TXS2[0].toString()), USER1.sign(TXS2[1].toString()) };

            AUTH_S2 = AUTH1.sign(Integer.toString(Arrays.hashCode(TXS2)));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
