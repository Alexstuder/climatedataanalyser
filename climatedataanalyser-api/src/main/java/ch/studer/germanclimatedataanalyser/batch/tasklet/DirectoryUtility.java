package ch.studer.germanclimatedataanalyser.batch.tasklet;

import java.io.File;
import java.io.IOException;

public class DirectoryUtility {


    static void deleteDirectoryFilesOld(File directory) throws IOException {
        File[] allContent = null;
        allContent = directory.listFiles();

        // First check , if directory is empty
        if (allContent != null) {
            for (File file : allContent) {
                // delete all files and subdir
                if (file.isDirectory()) {
                    deleteDirectoryFilesOld(file);
                } else {
                    file.delete();
                }
            }
        }
        // Make dir
        directory.mkdir();
    }

}
