package ch.studer.germanclimatedataanalyser.batch.tasklet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;
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


    @Autowired
    private ApplicationContext applicationContextBean;
    static ApplicationContext applicationContext;

    @Autowired
    private ServletContext context;


    static private String tomcatRootPath = null;
    static private String pathName;
    static private String contextPathName;
    private static final String WEBAPPS = "/webapps";
    private static final String DATAFILES = "/dataFiles/";

    private static final Logger log = LoggerFactory.getLogger(DirectoryUtilityImpl.class);

    public DirectoryUtilityImpl() {
        // If it running in a Tomcat Server this method will be called
        tomcatRootPath = System.getProperty("catalina.base");
    }

    @PostConstruct
    public void setContextPath() {
        applicationContext = applicationContextBean;
        contextPathName = context.getContextPath();
        log.info("ContextPath: {}", contextPathName);
    }

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


    private static final String getPathName() {

        String tmpPathName = null;
        //not running in tomcat = running with junit
        if (tomcatRootPath == null) {
            tmpPathName = System.getProperty("user.dir");
            log.info("It's not running on tomcat , so we try to write the Files in user.dir : " + pathName);
        } else {
            //for Tomcat build path like : /opt/tomcat/webapps/ClimateAnalyser/dataFiles/
            tmpPathName = tomcatRootPath + WEBAPPS + contextPathName + DATAFILES;
            //path = "/tmp" + DATAFILES;
            log.info("It's running on tomcat , so we try to write the ftp files in : " + pathName);
        }

        return tmpPathName;
    }

    static File createDir(String directoryName) {

        // get the correct PathName
        pathName = getPathName();
        // first create the directory dataFiles first
        new File(pathName).mkdir();
        File directory = new File(pathName + directoryName);

        // then create all other directory based on dataFiles (FtpData,InputFiles ...etc.)
        directory.mkdir();

        //Check if directory exists already and if there are some files in it, delete them !
        if (directory.isDirectory()) {
            //delete all Files
            File[] directoryFiles = directory.listFiles();
            if (directoryFiles.length != 0) {
                deleteDirectoryFiles(directory);
            }
        }
        return directory;
    }

    static File getDirectoryNew(String directoryName) throws FileNotFoundException {
        Resource[] resources = new Resource[0];
        Resource resource = null;
        File dir = ResourceUtils.getFile("classpath:" + directoryName);
        return dir;


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

    public static File getDirectory(String pathName, String folderName) {


        return new File(getPath(pathName) + "/" + folderName);
    }

    public static File getDirectory(String folderName) {

        //File f = new File(getPath("download/" + folderName).getPath());
        return new File(getPath("download/" + folderName).getPath());
    }


    // TODO replace the methode with a simple getDirectory !
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
