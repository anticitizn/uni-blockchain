import java.util.ArrayList;
import java.util.HashMap;

public enum Configuration {
    instance;

    public final String userDirectory = System.getProperty("user.dir");
    public final String fileSeparator = System.getProperty("file.separator");
    public final String pathRansomWareJavaArchive = userDirectory + fileSeparator + "ransomWare" + fileSeparator;
    public final String pathToFilesForEncryption = userDirectory + fileSeparator + "filesToEncrypt";

    public final char[] ALPHABET = "123456789ABCDEFGHJKLMNPQRSTUVWXYZabcdefghijkmnopqrstuvwxyz".toCharArray();
    public final char ENCODED_ZERO = ALPHABET[0];
    public final int[] INDEXES = new int[128];

    public final String EC_GENERATOR_SPECIFICATION_ALGORITHM = "secp256r1";
    public final String MESSAGE_DIGEST_SHA_ALGORITHM = "SHA-256";
    public final String MESSAGE_DIGEST_MD5_ALGORITHM = "MD5";

    Transaction genesisTransaction;
    HashMap<String, TransactionOutput> utx0Map = new HashMap<>();
    float minimumTransaction = 0.1f;
    ArrayList<Block> blockchain = new ArrayList<>();
    int difficulty = 4;
    int transactionSequence = 0;

}
