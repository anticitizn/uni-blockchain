import Blockchain.Block;
import Blockchain.Blockchain;
import Blockchain.Wallet;
import Person.Attacker;
import Person.BankAccount;
import Person.Miner;
import Person.ClueLess;
import Ransomware.EncryptionDecryption;
import Ransomware.RansomWareController;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

import java.io.*;
import java.util.ArrayList;
import java.util.Random;
import java.security.*;
import java.util.Scanner;
import java.util.Timer;


public class Application {
    public static void main(String... args) {
        Security.addProvider(new BouncyCastleProvider());

        Scanner scanner = new Scanner(System.in);
        String input;


        do{
            input = scanner.nextLine();
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
        block01.addTransaction(walletA.sendFunds(walletB.getPublicKey(), 1.0f));

        Random rand = new Random();
        int n = rand.nextInt(3);

        miners.get(n).mine(block01);

        pressure(scanner, input);
    }

    public static void report(Scanner scanner, String input) {
        Blockchain blockchain = new Blockchain(5.0f);

        Attacker attacker = new Attacker("ed", new Wallet(blockchain));
        ClueLess clueLess= new ClueLess("ClueLess", new Wallet(blockchain), new BankAccount(5000));

        RansomWareController ransomWareController = new RansomWareController();
        EncryptionDecryption encryptionDecryption = new EncryptionDecryption();

        boolean transactionSuccessful = false;


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

        do {
            input = scanner.nextLine();
        } while(!input.contains("exchange btc"));

        // ClueLess exchanges money for bitcoins
        clueLess.getBankAccount().setBalance(clueLess.getBankAccount().getBalance() - 1450);
        blockchain.getInitialWallet().sendFunds(clueLess.getWallet().getPublicKey(), 0.0275f);

        do {
            input = scanner.nextLine();
        } while(!input.contains("show balance"));
        System.out.println(clueLess.getBankAccount().getBalance());
        System.out.println(clueLess.getWallet().getBalance());

        do {
            input = scanner.nextLine();
        } while(!input.contains("show recipient"));
        PublicKey attackerBitcoinAddress = attacker.getWallet().getPublicKey();
        System.out.println(attackerBitcoinAddress);

        do {
            input = scanner.nextLine();
        } while(!input.contains("pay btc"));

        clueLess.getWallet().sendFunds(attackerBitcoinAddress, 0.0275f);

        transactionSuccessful = attacker.getWallet().getBalance() == 0.0275f;

        do {
            input = scanner.nextLine();
        } while(!input.contains("check payment"));

        if(transactionSuccessful){
            ransomWareController.decryption();
        }

    }

    public static void pressure(Scanner scanner, String input) {
        Blockchain blockchain = new Blockchain(1.0f);

        Attacker attacker = new Attacker("ed", new Wallet(blockchain));
        ClueLess clueLess= new ClueLess("ClueLess", new Wallet(blockchain), new BankAccount(5000));

        blockchain.getInitialWallet().sendFunds(clueLess.getWallet().getPublicKey(), 1.0f);

        RansomWareController ransomWareController = new RansomWareController();
        EncryptionDecryption encryptionDecryption = new EncryptionDecryption();

        ransomWareController.addSubscriber(encryptionDecryption);
        ransomWareController.encryption();

        Timer pressureTimer = new Timer();
        PsychologicalPressure psychologicalPressure = new PsychologicalPressure(0.02755f, ransomWareController);
        pressureTimer.scheduleAtFixedRate(psychologicalPressure, 60000, 60000);

        do {
            input = scanner.nextLine();
        } while(!input.contains("pay btc") && psychologicalPressure.getCounter() < 5);

        if (psychologicalPressure.getCounter() >= 5)
        {
            pressureTimer.cancel();
            pressureTimer.purge();
            return;
        }

        float attackerOldBalance = attacker.getWallet().getBalance();
        clueLess.getWallet().sendFunds(attacker.getWallet().getPublicKey(), psychologicalPressure.getRansomAmount());

        if (attacker.getWallet().getBalance() >= attackerOldBalance + psychologicalPressure.getRansomAmount())
        {
            ransomWareController.decryption();
            pressureTimer.cancel();
            pressureTimer.purge();
        }
    }

}