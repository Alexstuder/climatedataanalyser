package ch.studer.germanclimatedataanalyser.batch.tasklet;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;

class DirectoryHandlerTest {


    @Test
    public void testDirectoryHandler() {

        DirectoryHandler directoryHandler = new DirectoryHandler();

        try {

            // Declare needed private field
            Field fieldWin = null;
            fieldWin = DirectoryHandler.class.getDeclaredField("windowsFolderName");
            fieldWin.setAccessible(true);
            fieldWin.set(directoryHandler, "C:\\temp\\ClimateAnalyzer");

            // Declare needed private field
            Field fieldLinux = DirectoryHandler.class.getDeclaredField("linuxFolderName");
            fieldLinux.setAccessible(true);
            fieldLinux.set(directoryHandler, "/tmp/ClimateAnalyzer");

            // Declare needed private field
            Field fieldFTP = DirectoryHandler.class.getDeclaredField("ftpDataFolderName");
            fieldFTP.setAccessible(true);
            fieldFTP.set(directoryHandler, "FTPData");

            // Declare needed private field
            Field fieldUnzip = DirectoryHandler.class.getDeclaredField("unzipOutputFolderName");
            fieldUnzip.setAccessible(true);
            fieldUnzip.set(directoryHandler, "Unzip");

            // Declare needed private field
            Field fieldInput = DirectoryHandler.class.getDeclaredField("inputFolderName");
            fieldInput.setAccessible(true);
            fieldInput.set(directoryHandler, "Input");

        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        try {
            directoryHandler.afterPropertiesSet();
        } catch (Exception e) {
            e.printStackTrace();
        }

        Assertions.assertTrue(directoryHandler.getFtpDataFolder().isDirectory());
        Assertions.assertTrue(directoryHandler.getUnzipOutputFolder().isDirectory());
        Assertions.assertTrue(directoryHandler.getInputFolder().isDirectory());


    }
}
