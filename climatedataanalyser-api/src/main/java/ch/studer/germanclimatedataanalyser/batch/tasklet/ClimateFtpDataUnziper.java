package ch.studer.germanclimatedataanalyser.batch.tasklet;

import ch.studer.germanclimatedataanalyser.common.DirectoryUtilityImpl;
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
import java.util.ArrayList;
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

    @Value("${count.files.to.process}")
    private String countFileToProcess;

    @Value("${unzipper.test.modus}")
    private String unzipperTestModus;

    private static final Logger log = LoggerFactory.getLogger(ClimateFtpDataUnziper.class);


    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {


        File ftpDataFolder = DirectoryUtilityImpl.getDirectory(ftpDataFolderName);
        File unzipOutputFolde = DirectoryUtilityImpl.getEmptyDirectory(downloadFolderName, unzipOutputFolderName);
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
                // log.warn("Unziped File  :" + outputFile.toPath());
            } catch (IOException e) {
                e.printStackTrace();
            }
    }

    private List<File> getAllFilesFromDirectoryFiltered(File directory, String classifier) {

        List<File> files = Arrays.stream(directory.listFiles())
                .filter(file -> file.getName().endsWith(classifier))
                .collect(Collectors.toList());
        if (unzipperTestModus.contains("true")) {
            files = getReducedTestFiles(files);
        }

        return files;
    }

    private List<File> getReducedTestFiles(List<File> files) {
        List<File> filesTest = new ArrayList<>();

        for (int i = 0; i < Integer.valueOf(countFileToProcess); i++) {
            filesTest.add(files.get(i));
        }

        return filesTest;
    }

    @Override
    public void afterPropertiesSet() {
    }
}
