package ch.studer.germanclimatedataanalyser.batch.tasklet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import javax.servlet.ServletContext;
import java.io.File;
import java.io.FileNotFoundException;
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


    @Autowired
    private ServletContext context;


    static private String tomcatRootPath = null;
    static private String path;
    static private String contextPath;
    private static final String WEBAPPS = "/webapps";
    private static final String DATAFILES = "/dataFiles/";

    private static final Logger log = LoggerFactory.getLogger(DirectoryUtilityImpl.class);

    public DirectoryUtilityImpl() {
        // If it running in a Tomcat Server this method will be called
        tomcatRootPath = System.getProperty("catalina.base");
        contextPath = "/" + context.getContextPath();
        log.info("ContextPath: {}", contextPath);
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

        File directory = new File(path + directoryName);
        directory.mkdir();

        //Check if directory and if there are some files in it
        if (directory.isDirectory()) {
            //delete all Files
            File[] directoryFiles = directory.listFiles();
            if (directoryFiles.length != 0) {
                deleteDirectoryFiles(directory);
            }
        }
        log.info("directory.name:" + directory.getName());
        log.info("ddddddddddddddddddddddddddddddddddddddddddd");
        // if directory is directory and hase some Files in it : delete first content and then directory

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
