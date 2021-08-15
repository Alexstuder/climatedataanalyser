package ch.studer.germanclimatedataanalyser.batch.tasklet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.Resource;

import java.io.File;

public class DirectoryUtilityImpl implements DirectoryUtility {

    private static void deleteDirectoryFiles(File directory) {
        File[] allContent = null;
        allContent = directory.listFiles();

        // First check , if directory is empty
        if (allContent != null) {
            for (File file : allContent) {
                // delete all files and subdir
                if (file.isDirectory()) {
                    deleteDirectoryFiles(file);
                } else {
                    file.delete();
                }
            }
        }
    }

    static File createDir(String directoryName) {
        File directory = new File(directoryName);

        if (directory.exists()) {
            deleteDirectoryFiles(directory);
        }
        return new File(directoryName);
    }

}
