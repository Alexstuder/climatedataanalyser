package ch.studer.germanclimatedataanalyser.batch.tasklet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;
import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Component
public class DirectoryUtilityImpl implements DirectoryUtility {

    @Autowired
    private ApplicationContext appCont;
    @Autowired
    private ServletContext context;

    private ApplicationContext applicationContext;
    private ServletContext servletContext = null;
    static private String tomcatRootPath = null;
    static private String path;
    static private String contextPath;
    private static final String WEBAPPS = "/webapps";
    private static final String DATAFILES ="/dataFiles/";

    private static final Logger log = LoggerFactory.getLogger(DirectoryUtilityImpl.class);

    @PostConstruct
    private void init() {
        this.applicationContext = appCont;
        this.servletContext = context;
        // If it running in a Tomcat Server this method will be called
        tomcatRootPath = System.getProperty("catalina.base");
        contextPath = context.getContextPath();
        log.info("context.getcontextpath:" + context.getContextPath());
        log.info("context.getMajorVersion:" + context.getMajorVersion());
        log.info("context.getMinorVersion:" + context.getMinorVersion());
        log.info("catalina.base :" + System.getProperty("catalina.base"));
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
        } else {
            //for Tomcat build path like : /opt/tomcat/webapps/ClimateAnalyser/dataFiles/
            path = tomcatRootPath + WEBAPPS + contextPath + DATAFILES;
            log.info("Path to files :" + path);
        }

        File directory = null;
        try {
            log.info("catalina.base :" + System.getProperty("catalina.base"));
            log.info("User.dir :" + System.getProperty("user.dir"));
            log.info("Path.absolut Paths :" + Paths.get("").toAbsolutePath().toString());
            log.info("File.absolutFile :" + new File("").getAbsoluteFile());

            log.info("Path.of.toAbsolutPath :" + Path.of("").toAbsolutePath().toString());
            log.info("FileSystem.getDefault :" + FileSystems.getDefault().getPath(".").toAbsolutePath());
            log.info("FileSystem.getDefault :" + FileSystems.getDefault().getPath("WEB-INF"));

            directory = new File(path  + directoryName);
            Files.deleteIfExists(directory.toPath());
            deleteDirectoryFiles(directory);
            Files.createDirectories(directory.toPath());
            log.info("Directory created:" + directory.getAbsolutePath());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return directory;
    }

    public static File getDirectory(String folderName) {

        return new File(path + folderName);
    }

}
