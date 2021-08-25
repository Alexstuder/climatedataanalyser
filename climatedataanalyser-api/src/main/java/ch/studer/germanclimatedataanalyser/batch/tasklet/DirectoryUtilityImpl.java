package ch.studer.germanclimatedataanalyser.batch.tasklet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

// **************************************************************
//  Beim Zugriff auf das Filesystem gibt es 3 möglichkeiten zur Laufzeit
//     - Junit Test
//     - Spring-boot-Tomcat-Test
//     - Tomcat Container
//  Jede dieser Variante möchte berücksichtig werden$
//  Zusätzlich gibt es für Spreing die möglichketi aus dem src/resource Ordner zu lesen
//
//      Nachstehend um zu evaluieren welche Runtime umgebung aktive ist
//      ,gibt es folgende Möglichkeiten dies zu evaluieren.
//
//        log.info("catalina.base :" + System.getProperty("catalina.base"));
//        log.info("User.dir :" + System.getProperty("user.dir"));
//        log.info("Path.absolut Paths :" + Paths.get("").toAbsolutePath().toString());
//        log.info("File.absolutFile :" + new File("").getAbsoluteFile());
//
//        log.info("Path.of.toAbsolutPath :" + Path.of("").toAbsolutePath().toString());
//        log.info("FileSystem.getDefault :" + FileSystems.getDefault().getPath(".").toAbsolutePath());
//        log.info("FileSystem.getDefault :" + FileSystems.getDefault().getPath("WEB-INF"));
//        log.info("Directory created:" + directory.getAbsolutePath());
//
////
@Component
public class DirectoryUtilityImpl implements DirectoryUtility {

    static private String tomcatRootPath = null;
    static private String path;
    static private String contextPath;
    private static final String WEBAPPS = "/webapps";
    private static final String DATAFILES = "/dataFiles/";

    private static final Logger log = LoggerFactory.getLogger(DirectoryUtilityImpl.class);

    public DirectoryUtilityImpl() {
        // If it running in a Tomcat Server this method will be called
        tomcatRootPath = System.getProperty("catalina.base");
    }

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

        //not running in tomcat = running with junit
        if (tomcatRootPath == null) {
            path = System.getProperty("user.dir");
            log.info("user.dir");
        } else {
            //for Tomcat build path like : /opt/tomcat/webapps/ClimateAnalyser/dataFiles/
            path = tomcatRootPath + WEBAPPS + contextPath + DATAFILES;
            log.info("tomcat");
        }
        log.info("Path to files:" + path);

        File directory = null;
        try {
            directory = new File(path + directoryName);
        } catch (Exception e) {
            log.info("Here throws exeptions : " + e);
        }
        log.info("directory.name:" + directory.getName());
        log.info("ddddddddddddddddddddddddddddddddddddddddddd");
        log.info("directory.length:" + directory.list().length);
        log.info("eeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee");

        if (directory.list().length != 0) {
            log.info("fffffffffffffffffffffffffffffffffffffffx");
            deleteDirectoryFiles(directory);
            log.info("Directory has some files and needed to be deleteed first!");
        }
        log.info("ggggggggggggggggggggggggggggggggggggggggggggggggggggg");
        // Delete the directory it's self ;just to remove everything
        try {
            Files.deleteIfExists(directory.toPath());
        } catch (IOException e) {
            log.info("Files.deleteIfExists");
            // create a fresh directory
            e.printStackTrace();
        }
        Path directoryP = null;
        try {
            directoryP = Files.createDirectories(directory.toPath());
        } catch (IOException e) {
            e.printStackTrace();
        }
        log.debug("Path to File : " + directoryP.toFile());
        return directory;
    }

    public static File getDirectory(String folderName) {

        return new File(path + folderName);
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
