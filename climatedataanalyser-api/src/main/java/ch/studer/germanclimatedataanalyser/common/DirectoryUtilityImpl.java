package ch.studer.germanclimatedataanalyser.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Files;
import java.util.ArrayList;

@Component
public class DirectoryUtilityImpl {

    private static final Logger log = LoggerFactory.getLogger(DirectoryUtilityImpl.class);

    private static File getPath(String pathName) {

        File path = null;
        //test if path is available if not ! exit PGM
        try {
            path = ResourceUtils.getFile("classpath:" + pathName);
        } catch (FileNotFoundException e) {
            throw new RuntimeException("-------------------Downloadfolder :" + pathName + "- doesn't exists ! ------------ " + e);
        }
        return path;
    }

    public static File getEmptyDirectory(String pathName, String folderName) {
        File directory = null;

        try {
            directory = new File(getPath(pathName).getPath() + "/" + folderName);
            if (directory.exists()) {
                deleteDirectory(directory);
            }
            Files.createDirectory(directory.toPath());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return directory;
    }

    // function to delete subdirectories and files
    private static void deleteDirectory(File file) {
        // store all the paths of files and folders present
        // inside directory
        for (File subfile : file.listFiles()) {

            // if it is a subfolder,e.g Rohan and Ritik,
            // recursiley call function to empty subfolder
            if (subfile.isDirectory()) {
                deleteDirectory(subfile);
            }

            // delete files and empty subfolders
            subfile.delete();
        }
        //delete the directory itself
        file.delete();
    }

    public static File getDirectory(String folderName) {
        return new File(getPath("download/" + folderName).getPath());
    }

    public static Resource[] getResources(File[] files, String pattern) {

        ArrayList<Resource> resources = new ArrayList<Resource>();
        //Filter Pattern
        for (File file : files) {
            if (file.getName().contains(pattern)) {
                resources.add(getResource(file));
            }
        }
        Resource[] returnR = new Resource[resources.size()];
        return resources.toArray(returnR);
    }

    public static Resource getResource(File directory, String fileName) throws FileNotFoundException {
        for (File file : directory.listFiles()) {
            if (file.getName().contains(fileName)) {
                return getResource(file);
            }
        }
        throw new FileNotFoundException("File :" + fileName + " existiert nicht !");
    }

    public static Resource getResource(File file) {
        return new FileSystemResource(file);
    }
}
