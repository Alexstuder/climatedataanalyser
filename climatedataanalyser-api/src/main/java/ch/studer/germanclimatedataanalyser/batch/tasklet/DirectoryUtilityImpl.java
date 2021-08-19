package ch.studer.germanclimatedataanalyser.batch.tasklet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
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

    private ApplicationContext applicationContext;
    static private Resource rootPath;
    static final private String CLASSPATH = "classpath*:";


    private static final Logger log = LoggerFactory.getLogger(DirectoryUtilityImpl.class);

    @PostConstruct
    private void init() {
        this.applicationContext = appCont;
        rootPath = applicationContext.getResource(CLASSPATH + "/");
        rootPath = applicationContext.getResource("src/test/resources/");
        log.info("RootPath :" + rootPath);
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

        Path path = null;
        File directory = null;
        try {
           // path = Paths.get(rootPath.getFile().getPath() + "/" + directoryName);
            log.info("catalina.base :" + System.getProperty( "catalina.base" ));
            log.info("User.dir :" + System.getProperty("user.dir"));
            log.info("Path.absolut Paths :" + Paths.get("").toAbsolutePath().toString());
            log.info("File.absolutFile :" + new File("").getAbsoluteFile());
//            log.info("getClass.getClassLoader :" + DirectoryUtilityImpl.class.getClass().getClassLoader().getResource("").getPath() );
            log.info("Path.of.toAbsolutPath :" + Path.of("").toAbsolutePath().toString());
            log.info("FileSystem.getDefault :" + FileSystems.getDefault().getPath(".").toAbsolutePath());
            log.info("FileSystem.getDefault :" + FileSystems.getDefault().getPath("WEB-INF"));
            directory = new File("dataFiles/" + directoryName);
            Files.deleteIfExists(directory.toPath());
            deleteDirectoryFiles(directory);
            Files.createDirectories(directory.toPath());
            log.info("Directory created:" + directory.getAbsolutePath() );
        } catch (IOException e) {
            e.printStackTrace();
        }


        return directory;
    }

    public static File getDirectory(String folderName){

        return new File("dataFiles/" + folderName);
        //return Paths.get(rootPath.getFile().getPath() + "/" + folderName).toFile();
    }

}
