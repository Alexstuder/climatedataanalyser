package ch.studer.germanclimatedataanalyser.batch.tasklet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.Resource;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

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
        Path path = Paths.get(directoryName);

        try {
            Files.deleteIfExists(path);
        } catch (IOException e) {
            deleteDirectoryFiles(path.toFile());
            e.printStackTrace();
        }

        try {
            Files.createDirectories(path);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return path.toFile();
    }

}
