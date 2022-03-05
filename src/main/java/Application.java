import java.io.*;
import java.util.ArrayList;
import java.util.Random;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.interfaces.ECPrivateKey;
import java.security.interfaces.ECPublicKey;
import java.security.spec.ECGenParameterSpec;
import java.security.spec.ECPoint;
import java.util.Scanner;


public class Application {
    public static void main(String... args) throws IOException {


        Scanner scanner = new Scanner(System.in);
        String input;

        do{
            input = scanner.next();
            System.out.println(input);
            System.out.println("!(input.contains(\"launch\") && input.contains(\"report.jar\")): "+ !(input.contains("launch") && input.contains("report.jar")));
        }while(!(input.contains("launch") && input.contains("report.jar")));
        report(scanner, input);


        System.out.println();

        // S02 test
        Blockchain blockchain = new Blockchain();

        Wallet walletA = blockchain.getInitialWallet();
        Wallet walletB = new Wallet(blockchain);

        ArrayList<Miner> miners = new ArrayList<>();
        miners.add(new Miner("Bob", blockchain));
        miners.add(new Miner("Eve", blockchain));
        miners.add(new Miner("Sam", blockchain));

        Block block01 = blockchain.getNewBlock();
        block01.addTransaction(walletA.sendFunds(walletB.getPublicKey(), 0.5f));

        Random rand = new Random();
        int n = rand.nextInt(3);

        miners.get(n).mine(block01);

        Application application = new Application();

    }

    public static void report(Scanner scanner, String input){

        RansomWareController ransomWareController = new RansomWareController();
        EncryptionDecryption encryptionDecryption = new EncryptionDecryption();

        boolean transactionSuccessful = false;
        boolean timeOver = false;
        float bitcoinAmountForDecryption = 0.02755f;
        String transactionState = "transaction";

        try {
            ProcessBuilder processBuilder = new ProcessBuilder("C:\\Program Files\\Java\\jdk-17.0.1\\bin\\jarsigner", "-verify", "ransomWare/report.jar");
            Process process = processBuilder.start();
            process.waitFor();

            InputStream inputStream = process.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String line;
            boolean isComponentAccepted = false;

            while ((line = bufferedReader.readLine()) != null) {
                System.out.println(line);
                if (line.contains("verified")) {
                    isComponentAccepted = true;
                }
            }

            if (isComponentAccepted) {
                System.out.println("component accepted");
            } else {
                System.out.println("component rejected");
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        ransomWareController.addSubscriber(encryptionDecryption);

        ransomWareController.encryption();
        ransomWareController.decryption();
    }



    private String adjustTo64(String string) {
        return switch (string.length()) {
            case 62 -> "00" + string;
            case 63 -> "0" + string;
            case 64 -> string;
            default -> throw new IllegalArgumentException("not a valid key | " + string);
        };
    }

    public String bytesToHex(byte[] bytes) {
        byte[] hexArray = "0123456789ABCDEF".getBytes(StandardCharsets.US_ASCII);
        byte[] hexChars = new byte[bytes.length * 2];

        for (int j = 0; j < bytes.length; j++) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = hexArray[v >>> 4];
            hexChars[j * 2 + 1] = hexArray[v & 0x0F];
        }

        return new String(hexChars, StandardCharsets.UTF_8);
    }

    public void generateKeys() {
        try {
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("EC");
            ECGenParameterSpec ecGeneratorParameterSpecification = new ECGenParameterSpec(Configuration.instance.EC_GENERATOR_SPECIFICATION_ALGORITHM);

            keyPairGenerator.initialize(ecGeneratorParameterSpecification);
            KeyPair keyPair = keyPairGenerator.generateKeyPair();

            PublicKey publicKey = keyPair.getPublic();
            PrivateKey privateKey = keyPair.getPrivate();

            ECPrivateKey ecPrivateKey = (ECPrivateKey) privateKey;
            String result = adjustTo64(ecPrivateKey.getS().toString(16)).toUpperCase();
            System.out.println("s[" + result.length() + "] (stored in wallet) | " + result);
            ECPublicKey ecPublicKey = (ECPublicKey) publicKey;
            ECPoint ecPoint = ecPublicKey.getW();
            String sx = adjustTo64(ecPoint.getAffineX().toString(16)).toUpperCase();
            String sy = adjustTo64(ecPoint.getAffineY().toString(16)).toUpperCase();
            String bcPublicKey = "04" + sx + sy;
            System.out.println("bcPublicKey              | " + bcPublicKey);

            MessageDigest shaMessageDigest01 = MessageDigest.getInstance(Configuration.instance.MESSAGE_DIGEST_SHA_ALGORITHM);
            byte[] resultSHAMessageDigest = shaMessageDigest01.digest(bcPublicKey.getBytes(StandardCharsets.UTF_8));
            System.out.println("shaMessageDigest01       | " + bytesToHex(resultSHAMessageDigest).toUpperCase());

            MessageDigest md5MessageDigest01 = MessageDigest.getInstance(Configuration.instance.MESSAGE_DIGEST_MD5_ALGORITHM);
            byte[] md5MessageDigest02 = md5MessageDigest01.digest(resultSHAMessageDigest);
            byte[] md5MessageDigest03 = new byte[md5MessageDigest02.length + 1];
            md5MessageDigest03[0] = 0;
            System.arraycopy(md5MessageDigest02, 0, md5MessageDigest03, 1, md5MessageDigest02.length);
            System.out.println("md5MessageDigest01       | " + bytesToHex(md5MessageDigest03).toUpperCase());

            byte[] shaMessageDigest02 = shaMessageDigest01.digest(md5MessageDigest03);
            System.out.println("shaMessageDigest02       | " + bytesToHex(shaMessageDigest02).toUpperCase());

            byte[] shaMessageDigest03 = shaMessageDigest01.digest(shaMessageDigest02);
            System.out.println("shaMessageDigest03       | " + bytesToHex(shaMessageDigest03).toUpperCase());

            byte[] temp = new byte[25];
            System.arraycopy(md5MessageDigest02, 0, temp, 0, md5MessageDigest02.length);
            System.arraycopy(shaMessageDigest03, 0, temp, 20, 5);
            System.out.println("transaction address      | " + Base58.encode(temp));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }


}