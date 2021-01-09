package ch.studer.germanclimatedataanalyser.batch.tasklet;

import com.madgag.compress.CompressUtil;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ClimateFtpDataUnziper implements Tasklet, InitializingBean {

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    DirectoryHandler directoryHandler;

    /*@Value("${climate.path.unzipOutputFolderName}")
    private String unzipOutputFolderName;

    @Value("${climate.path.inputFolderName}")
    private String inputFolderName;

    @Value("${climate.path.ftpDataFolderName}")
    private String ftpDataFolderName;

    @Value("${climate.path.temperature.input.file.pattern}")
    private String inputFilePattern;

    static final private String CLASSPATH = "classpath*:";*/

    //private Resource unzipOutputFolder;


    @Value("${climate.path.temperature.input.file.pattern}")
    private String inputFilePatttern;

    @Value("${climate.path.temperature.input.file.type}")
    private String inputFileType;


    private static final Logger log = LoggerFactory.getLogger(ClimateFtpDataUnziper.class);


    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {

  /*      // Empty both Directory
        DirectoryUtility.deleteDirectoryFilesOld(getDirectory(unzipOutputFolderName));
        DirectoryUtility.deleteDirectoryFilesOld(getDirectory(inputFolderName));*/

        // allZipFiles = getAllZipFiles();
        //Resource[] allZipFiles = getAllFilesFromDirectory(directoryHandler.getFtpDataFolder(), ".txt");
        ArrayList<File> allZipFiles = directoryHandler.getAllFilesFromDirectory(directoryHandler.getFtpDataFolder(), inputFilePatttern, inputFileType);

        // Unzip all Zip Data from FTP download
        for (File file : allZipFiles) {
            CompressUtil.unzip(new FileInputStream(file), directoryHandler.getUnzipOutputFolder());
        }
/*

        // Unzip all Zip Data from FTP download
        for (Resource resource : allZipFiles) {
            CompressUtil.unzip(new FileInputStream(resource.getFile().getPath()), getDirectory(unzipOutputFolderName));
        }
*/
        try {
            FileUtils.copyDirectory(directoryHandler.getUnzipOutputFolder(), directoryHandler.getInputFolder());
        } catch (IOException e) {
            e.printStackTrace();
        }

        //moveAllClimateDataToInputFilesFolder(directoryHandler.getUnzipOutputFolder(), directoryHandler.getInputFolder());
        //moveAllClimateDataToInputFilesFolder(getDirectory(unzipOutputFolderName), getDirectory(inputFolderName));
        return RepeatStatus.FINISHED;
    }

    /*private void moveAllClimateDataToInputFilesFolder(File inputDirectory, File outputDirectory){


        try {
            FileUtils.copyDirectory(inputDirectory,outputDirectory);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //Files.copy(Path.of(inputDirectory.getPath()), Path.of(outputDirectory.toPath()));


        *//*Resource[] resources = getAllFilesFromDirectory(unzipOutputFolderName, inputFilePattern);

        for (Resource resource : resources) {
            try {
                //Get the Files
                File inputFile = new File(inputDirectory.getPath() + "/" + resource.getFile().getName());
                File outputFile = new File(outputDirectory.getPath() + "/" + resource.getFile().getName());

                //Copy
                Files.copy(inputFile.toPath(), outputFile.toPath());
                log.debug("Unziped File  :" + outputFile.toPath());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
*//*
    }*/



    /*private Resource[] getAllFilesFromDirectory(String directory, String classifier) {

        Resource[] files = new Resource[0];
        {
            try {
                files = applicationContext.getResources(CLASSPATH + "/" + directory + "/" + classifier);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return files;
    }*/

    /*private File getDirectory(String directoryName) throws IOException {
        Resource[] resources = new Resource[0];
        Resource resource = null;
        try {
            resources = applicationContext.getResources(CLASSPATH + "/" + directoryName);
            Resource[] rootPath = applicationContext.getResources(CLASSPATH + "/");
            //mkdir if directory does not exist ;
            if (resources.length == 0) {
                log.info("Directory is 0");
                File tempFile = new File(rootPath[0].getFile().getPath() + "/" + directoryName);
                tempFile.mkdir();
                resources = applicationContext.getResources(CLASSPATH + "/" + directoryName);

            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        for (Resource directory : resources) {
            if (directory.getFilename().equals(directoryName)) {
                resource = directory;
            }
        }
        return resource.getFile();
    }*/

/*    public Resource[] getAllZipFiles() {

        Resource[] zipFiles = new Resource[0];
        {
            try {
                zipFiles = applicationContext.getResources(CLASSPATH + "/" + ftpDataFolderName + "/" + "*.zip");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return zipFiles;
    }*/

    @Override
    public void afterPropertiesSet() {
    }
}
