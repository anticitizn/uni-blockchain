
public enum Configuration {
    instance;

    public final String userDirectory = System.getProperty("user.dir");
    public final String fileSeparator = System.getProperty("file.separator");
    public final String pathToFilesForEncryption = userDirectory + fileSeparator + "filesToEncrypt";


}
