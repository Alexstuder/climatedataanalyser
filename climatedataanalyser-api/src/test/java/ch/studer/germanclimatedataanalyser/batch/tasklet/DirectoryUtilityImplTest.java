package ch.studer.germanclimatedataanalyser.batch.tasklet;

import ch.studer.germanclimatedataanalyser.common.DirectoryUtilityImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;

class DirectoryUtilityImplTest {


    private static final Logger log = LoggerFactory.getLogger(DirectoryUtilityImplTest.class);
    private static final String DOWNLOAD = "download";
    private static final String FOLDER_NAME = "testFolder";
    private static final String FOLDER_NOT_FOUND = "NOTFOUND";

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

        Assertions.assertNotNull(dir, "dir, should not be null !");
        //the directory is not longer needed so clean your space!
        dir.delete();
    }

    @Test
    void testFullDirectory() {

        int LOAD = 10;
        int SUBDIR = 1;
        //Create a dir
        File dir = DirectoryUtilityImpl.getEmptyDirectory(DOWNLOAD, FOLDER_NAME);
        File subdir = DirectoryUtilityImpl.getEmptyDirectory(DOWNLOAD + "/" + FOLDER_NAME, FOLDER_NAME);
        //fill the directory with files
        fillDirectoryWithFiles(LOAD, dir);
        //fill the subdir with files
        fillDirectoryWithFiles(LOAD, subdir);
        Assertions.assertEquals(LOAD + SUBDIR, dir.listFiles().length);//LOAD and 1 subdir
        Assertions.assertEquals(LOAD, subdir.listFiles().length);

        //create the same dir and empty the dir
        dir = DirectoryUtilityImpl.getEmptyDirectory(DOWNLOAD, FOLDER_NAME);
        Assertions.assertEquals(true, dir.isDirectory());
        Assertions.assertEquals(0, dir.listFiles().length);
        Assertions.assertEquals(false, subdir.exists());
    }


    @Test
    void createDirNoFolderFoundError() {
        File dir = null;
        FileNotFoundException fileNotFoundException = null;

        dir = DirectoryUtilityImpl.getEmptyDirectory(FOLDER_NOT_FOUND, FOLDER_NOT_FOUND);

        Assertions.assertSame(null, dir);
    }


}

