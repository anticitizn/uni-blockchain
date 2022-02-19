import java.io.File;
import java.io.IOException;
import java.util.Scanner;


public class Application {
    public static void main(String... args) {

        System.out.println(Configuration.instance.pathToFilesForEncryption);

        File folder = new File(Configuration.instance.pathToFilesForEncryption);
        File[] listOfFiles = folder.listFiles();


        for (int i = 0; i < listOfFiles.length; i++) {
            if (listOfFiles[i].isFile()) {
                System.out.println(listOfFiles[i].getName());

            } else if (listOfFiles[i].isDirectory()) {
                System.out.println(listOfFiles[i].getName());
            }
        }
        System.out.println();



        String originalString = "smart_home.txt";

        String encryptedString = AES256.encrypt(originalString);
        String decryptedString = AES256.decrypt(encryptedString);

        System.out.println(originalString);
        System.out.println(encryptedString);
        System.out.println(decryptedString);

        /*Scanner scanner = new Scanner(System.in);

        System.out.print("[insurance]                  : ");
        String insurance = scanner.nextLine();
        System.out.println(insurance);

        System.out.print("[tax]                        : ");
        double tax = scanner.nextDouble();
        System.out.println(tax);

        System.out.print("[defaultParkingSpaceID]      : ");
        int defaultParkingSpaceID = scanner.nextInt();
        System.out.println(defaultParkingSpaceID);*/


    }


}