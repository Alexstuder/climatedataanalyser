package ch.studer.germanclimatedataanalyser.batch.tasklet;

import com.madgag.compress.CompressUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ClimateFtpDataUnziper implements Tasklet, InitializingBean {

    @Value("${climate.path.unzipOutputFolderName}")
    private String unzipOutputFolderName;

    @Value("${climate.path.inputFolderName}")
    private String inputFolderName;

    @Value("${climate.path.ftpDataFolderName}")
    private String ftpDataFolderName;

    @Value("${climate.path.downloadFolder}")
    private String downloadFolderName;

    private static final Logger log = LoggerFactory.getLogger(ClimateFtpDataUnziper.class);


    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {

        // Empty both Directory
        //TODO A.Studer clean
        //File ftpDataFolder = DirectoryUtilityImpl.getDirectory(ftpDataFolderName);
        File ftpDataFolder = DirectoryUtilityImpl.getEmptyDirectory(downloadFolderName, ftpDataFolderName);
        //File unzipOutputFolde = DirectoryUtilityImpl.createDir(unzipOutputFolderName);
        File unzipOutputFolde = DirectoryUtilityImpl.getEmptyDirectory(downloadFolderName, unzipOutputFolderName);
        //File inputFolder = DirectoryUtilityImpl.createDir(inputFolderName);
        File inputFolder = DirectoryUtilityImpl.getEmptyDirectory(downloadFolderName, inputFolderName);

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

    @Override
    public void afterPropertiesSet() {
    }
}
