package ch.studer.germanclimatedataanalyser.batch.tasklet;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

class DirectoryUtilityImplTest {
    private static final String folderName = "/TestDirectory";

    @Test
    public void test() {
        int firstLoad = 40;
        int secondload = 20;
        File directory = null;
        try {
            directory = DirectoryUtilityImpl.createDir(folderName);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Assert.assertTrue(directory.isDirectory());
        fillDirectoryWithFiles(firstLoad, directory);

        File directoryTest = DirectoryUtilityImpl.getDirectory(folderName);
        Assert.assertEquals(directory, directoryTest);

        Assert.assertEquals(firstLoad, directoryTest.listFiles().length);

        // Do it again to make sure , the needed folder gets deleted before filling it again!
        try {
            directory = DirectoryUtilityImpl.createDir(folderName);
        } catch (IOException e) {
            e.printStackTrace();
        }
        fillDirectoryWithFiles(secondload, directory);
        Assert.assertEquals(secondload, directoryTest.listFiles().length);


    }


    private void fillDirectoryWithFiles(int load, File directory) {

        for (int i = 0; i < load; i++) {

            File file = new File(directory.getAbsoluteFile() + "/File" + i + ".txt");
            try {
                Files.createFile(file.getAbsoluteFile().toPath());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
