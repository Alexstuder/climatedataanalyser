package ch.studer.germanclimatedataanalyser.batch.tasklet;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;

@RunWith(SpringRunner.class)
@SpringBootTest
class DirectoryUtilityImplTest {


    private static final Logger log = LoggerFactory.getLogger(DirectoryUtilityImplTest.class);
    private static final String DOWNLOAD = "download";
    private static final String FOLDER_NAME = "testFolder";
    private static final String FOLDER_NOT_FOUND = "NOTFOUND";

    //TODO A.Studer Clean
    public void test() {
        int firstLoad = 40;
        int secondLoad = 20;
        File directory = null;
        log.debug("FolderName :" + FOLDER_NAME);
        directory = DirectoryUtilityImpl.createDir(FOLDER_NAME);
        assert directory != null;
        Assertions.assertTrue(directory.isDirectory());
        fillDirectoryWithFiles(firstLoad, directory);

        File directoryTest = DirectoryUtilityImpl.getDirectory(FOLDER_NAME);
        Assertions.assertEquals(directory, directoryTest);

        Assertions.assertEquals(firstLoad, directoryTest.listFiles().length);

        // Do it again to make sure , the needed folder gets deleted before filling it again!
        directory = DirectoryUtilityImpl.createDir(FOLDER_NAME);
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

    @Test
    void createDir() {
        File dir = null;
        dir = DirectoryUtilityImpl.getEmptyDirectory(DOWNLOAD, FOLDER_NAME);

        Assertions.assertNotNull(dir, "dir, souhld not be null !");
        //the directory is not longer needed so clean your space!
        dir.delete();
    }

    @Test
    void createDirNoFolderFoundError() {
        File dir = null;
        FileNotFoundException fileNotFoundException = null;

        dir = DirectoryUtilityImpl.getEmptyDirectory(DOWNLOAD, FOLDER_NOT_FOUND);

        Assertions.assertSame(FileNotFoundException.class, fileNotFoundException);
        Assertions.assertNotNull(dir, "dir, souhld not be null !");
    }


}

