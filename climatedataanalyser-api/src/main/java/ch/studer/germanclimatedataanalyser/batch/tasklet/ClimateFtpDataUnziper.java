package ch.studer.germanclimatedataanalyser.batch.tasklet;

import com.madgag.compress.CompressUtil;
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
import org.springframework.core.io.Resource;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ClimateFtpDataUnziper implements Tasklet, InitializingBean {

    @Autowired
    private ApplicationContext applicationContext;

    @Value("${climate.path.unzipOutputFolderName}")
    private String unzipOutputFolderName;

    @Value("${climate.path.inputFolderName}")
    private String inputFolderName;

    @Value("${climate.path.ftpDataFolderName}")
    private String ftpDataFolderName;

    @Value("${climate.path.temperature.input.file.pattern}")
    private String inputFilePattern;

    static final private String CLASSPATH = "classpath*:";

    //private Resource unzipOutputFolder;

    private static final Logger log = LoggerFactory.getLogger(ClimateFtpDataUnziper.class);


    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {

        // Empty both Directory
        File ftpDataFolder = DirectoryUtilityImpl.getDirectory(ftpDataFolderName);
        File unzipOutputFolde = DirectoryUtilityImpl.createDir(unzipOutputFolderName);
        File inputFolder = DirectoryUtilityImpl.createDir(inputFolderName);

        // allZipFiles = getAllZipFiles();
        List<File> allZipFiles = getAllFilesFromDirectoryFiltered(ftpDataFolder, ".zip");

        // Unzip all Zip Data from FTP download
        for (File file : allZipFiles) {
            CompressUtil.unzip(new FileInputStream(file.getPath()), unzipOutputFolde);
        }

        moveAllClimateDataToInputFilesFolder(unzipOutputFolde, inputFolder);
        return RepeatStatus.FINISHED;
    }

    private void moveAllClimateDataToInputFilesFolder(File inputDirectory, File outputDirectory) {

        File[] files = inputDirectory.listFiles();

        for (File f : files)
            try {
                //Get the Files
                File inputFile = new File(inputDirectory.getPath() + "/" + f.getName());
                File outputFile = new File(outputDirectory.getPath() + "/" + f.getName());

                //Copy
                Files.copy(inputFile.toPath(), outputFile.toPath());
                log.debug("Unziped File  :" + outputFile.toPath());
            } catch (IOException e) {
                e.printStackTrace();
            }
    }

    private List<File> getAllFilesFromDirectoryFiltered(File directory, String classifier) {

        List<File> files = Arrays.stream(directory.listFiles())
                                         .filter(file -> file.getName().endsWith(classifier))
                                         .collect(Collectors.toList());

        return files;
    }

    private Resource[] getAllFilesFromDirectoryFiltered(String directory, String classifier) {

        Resource[] files = new Resource[0];
        {
            try {
                files = applicationContext.getResources(CLASSPATH + "/" + directory + "/" + classifier);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return files;
    }

    private File getDirectory(String directoryName) throws IOException {
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
    }

    public Resource[] getAllZipFiles() {

        Resource[] zipFiles = new Resource[0];

        {
            try {
                zipFiles = applicationContext.getResources(CLASSPATH + "/" + ftpDataFolderName + "/" + "*.zip");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return zipFiles;
    }

    @Override
    public void afterPropertiesSet() {
    }
}
