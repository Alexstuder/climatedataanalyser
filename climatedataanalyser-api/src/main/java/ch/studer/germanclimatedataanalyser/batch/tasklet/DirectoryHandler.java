package ch.studer.germanclimatedataanalyser.batch.tasklet;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DirectoryHandler implements InitializingBean {


    @Value("${climate.path.linuxFolderName}")
    private String linuxFolderName;

    @Value("${climate.path.windowsFolderName}")
    private String windowsFolderName;

    @Value("${climate.path.unzipOutputFolderName}")
    private String unzipOutputFolderName;

    @Value("${climate.path.ftpDataFolderName}")
    private String ftpDataFolderName;

    @Value("${climate.path.inputFolderName}")
    private String inputFolderName;

    @Value("${climate.path.temperature.input.file.pattern}")
    private String inputFilePatttern;

    @Value("${climate.path.temperature.input.file.type}")
    private String inputFileType;

    @Value("${climate.path.station.input.file.pattern}")
    private String stationFilePattern;

    @Value("${climate.path.station.input.file.type}")
    private String stationFileType;

    private String fs; // FileSeparator
    private String path; // Path OS specific

    private File ftpDataFolder;
    private File unzipOutputFolder;
    private File inputFolder;

    private ArrayList<File> folders = new ArrayList<File>();

    public DirectoryHandler() {


    }

    private void createFolders(ArrayList<File> folders) {

        /*for (File folder : folders) {

            // delete the folder if exists
            try {
                //TODO Remove comment for delete
              //  FileUtils.deleteDirectory(folder);
            } catch (IOException e) {
                e.printStackTrace();
            }

            // create an empty folder
            try {
                if (!folder.mkdirs()) {
                    throw new RuntimeException("Error creating directory :" + folder.getAbsolutePath());

                }
            } catch (SecurityException e) {
                throw new RuntimeException("Security Exception trying to create directory :" + folder.getAbsolutePath() + " \\nl" + e);
            }
        }*/
    }

    public File getFtpDataFolder() {
        return ftpDataFolder;
    }

    public File getUnzipOutputFolder() {
        return unzipOutputFolder;
    }

    public File getInputFolder() {
        return inputFolder;
    }

    public String getFs() {
        return fs;
    }

    @Override
    public void afterPropertiesSet() throws Exception {

        // get the path for windows or linux
        fs = System.getProperty("file.separator");

        if (System.getProperty("os.name").toUpperCase().contains("WINDOWS")) {
            path = windowsFolderName;
        } else {
            if (System.getProperty("os.name").toUpperCase().contains("LINUX")) {
                path = linuxFolderName;
            } else {
                throw new RuntimeException("Unknown Operating System :" + System.getProperty("os.name"));
            }
        }

        // define needed Folder
        ftpDataFolder = new File(path + fs + ftpDataFolderName);
        unzipOutputFolder = new File(path + fs + unzipOutputFolderName);
        inputFolder = new File(path + fs + inputFolderName);

        // add them to an ArrayList
        folders.add(ftpDataFolder);
        folders.add(unzipOutputFolder);
        folders.add(inputFolder);

        // create all needed folders
        createFolders(folders);

    }

    public List<File> getAllFilesFromDirectory(File directory, String filePattern, String fileTyp) {


        List<File> filesTmp = Arrays.asList(directory.listFiles());
        ArrayList<File> files = new ArrayList<File>(filesTmp);

        //for (File file : files) {
        for (int i = 0; i < files.size(); i++) {

            // remove all not needed files
            if (!(files.get(i).getName().endsWith(fileTyp) && files.get(i).getName().contains(filePattern))) {
                files.remove(i--);
            }

        }

        return files;
    }

}
