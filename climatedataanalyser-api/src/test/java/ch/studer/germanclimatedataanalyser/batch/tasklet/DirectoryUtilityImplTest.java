package ch.studer.germanclimatedataanalyser.batch.tasklet;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

class DirectoryUtilityImplTest {

    private static final Logger log = LoggerFactory.getLogger(DirectoryUtilityImplTest.class);
    private static final String folderName = "/TestDirectory";

    @Test
    public void test() {
        int firstLoad = 40;
        int secondLoad = 20;
        File directory = null;
        log.debug("FolderName :" + folderName);
        directory = DirectoryUtilityImpl.createDir(folderName);
        assert directory != null;
        Assertions.assertTrue(directory.isDirectory());
        fillDirectoryWithFiles(firstLoad, directory);

        File directoryTest = DirectoryUtilityImpl.getDirectory(folderName);
        Assertions.assertEquals(directory, directoryTest);

        Assertions.assertEquals(firstLoad, directoryTest.listFiles().length);

        // Do it again to make sure , the needed folder gets deleted before filling it again!
        directory = DirectoryUtilityImpl.createDir(folderName);
        fillDirectoryWithFiles(secondLoad, directory);
        Assertions.assertEquals(secondLoad, directoryTest.listFiles().length);


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
