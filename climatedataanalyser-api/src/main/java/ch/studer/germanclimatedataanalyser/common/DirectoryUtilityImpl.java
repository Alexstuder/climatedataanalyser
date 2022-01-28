package ch.studer.germanclimatedataanalyser.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.util.ArrayList;

//TODO A.Studer Clean
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
            Files.deleteIfExists(directory.toPath());
            Files.createDirectory(directory.toPath());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return directory;
    }

    public File getFileFromURL() {
        URL url = this.getClass().getClassLoader().getResource("download");
        File file = null;
        try {
            file = new File(url.toURI());
        } catch (URISyntaxException e) {
            file = new File(url.getPath());
        } finally {
            return file;
        }
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
