import Blockchain.Block;
import Blockchain.Blockchain;
import Blockchain.Wallet;
import Configuration.Configuration;
import Person.Attacker;
import Person.BankAccount;
import Person.Miner;
import Person.ClueLess;
import Ransomware.EncryptionDecryption;
import Ransomware.RansomWareController;

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
        Blockchain blockchain = new Blockchain(1.0f);

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
        Blockchain blockchain = new Blockchain(5.0f);

        Attacker attacker = new Attacker("ed", new Wallet(blockchain));
        ClueLess clueLess= new ClueLess("ClueLess", new Wallet(blockchain), new BankAccount(5000));

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

        System.out.println("Oops, your files have been encrypted. With a payment of 0.02755 BTC all files will be decrypted");

        do{
            input = scanner.next();
            System.out.println(input);
        } while(!input.contains("exchange 0.02755BTC"));

        // ClueLess exchanges money for bitcoins
        clueLess.getBankAccount().setBalance(clueLess.getBankAccount().getBalance() - 1450);
        blockchain.getInitialWallet().sendFunds(clueLess.getWallet().getPublicKey(), 0.02755f);

        ransomWareController.decryption();
    }

}