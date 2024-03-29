package ch.studer.germanclimatedataanalyser.batch.tasklet;

import ch.studer.germanclimatedataanalyser.common.DirectoryUtilityImpl;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;

public class ClimateFtpDataDownloader implements Tasklet {

    private static final Logger log = LoggerFactory.getLogger(ClimateFtpDataDownloader.class);

    @Autowired
    ApplicationContext applicationContext;

    @Value("${climate.path.unzipOutputFolderName}")
    private String unzipOutputFolderName;

    @Value("${climate.path.ftpDataFolderName}")
    private String ftpDataFolderName;

    @Value("${climate.ftp.server}")
    private String ftpServer;

    @Value("${climate.ftp.server.directory}")
    private String remoteDirectory;

    @Value("${climate.ftp.server.user}")
    private String ftpUser;

    @Value("${climate.ftp.server.pwd}")
    private String ftpPwd;

    @Value("${climate.path.downloadFolder}")
    private String downloadFolderName;
    private FTPClient ftpConnection;

    private static String withFTP = "false";

    public ClimateFtpDataDownloader() {


    }


    private FTPClient getFTPConection(String ftpUser, String ftpPwd) {

        FTPClient ftpClient = new FTPClient();
        try {
            ftpClient.connect(ftpServer);
            ftpClient.login(ftpUser, ftpPwd);

        } catch (Exception e) {
            throw new RuntimeException("Connection to FTP Server Failed : STOP the Program : " + e);
        }
        return ftpClient;
    }

    private void downloadFTPFiles(FTPFile[] ftpFiles) throws IOException {
        int counter = 0;
        File directory = null;
        log.info("Start Download  : " + LocalDateTime.now());

        directory = DirectoryUtilityImpl.getEmptyDirectory(downloadFolderName, ftpDataFolderName);

        for (FTPFile ftpFile : ftpFiles) {

            //TODO Work with a toggle switch to switch between test and production mode
            //     for (int i = 0; i < 2; i++) {
            // FTPFile ftpFile = ftpFiles[i];
            FileOutputStream out = new FileOutputStream(directory.getAbsoluteFile() + "/" + ftpFile.getName());
            try {
                ftpConnection.retrieveFile(ftpFile.getName(), out);
                log.debug("File {}", ftpFile, " downloaded !");
            } catch (Exception e) {

                throw new RuntimeException("Error in Download File : " + e);
            }
            out.close();

            if (ftpConnection.getReplyCode() == 550) {
                throw new RuntimeException("FTP Download Error : " + ftpConnection.getReplyString());
            }
            counter++;

        }
        log.info("*************************************************");

        log.info(counter + " Files downloaded !");
        log.info("End Download  : " + LocalDateTime.now());
    }

    public FTPFile[] list(FTPClient ftpConnection) {
        String[] filenameList;
        FTPFile[] ftpFiles;

        try {

            ftpFiles = ftpConnection.listFiles();

        } catch (Exception e) {
            throw new RuntimeException("Runtime Exeption in list FTP Files : " + e.getMessage());

        } finally {
            System.out.println("End of the List arrived !");
        }

        return ftpFiles;
    }

    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) {
        log.info("###############   Start Download from the Weather Server   ############");

        withFTP = (String) chunkContext.getStepContext().getJobParameters().get("withFTP");
        if (withFTP.contentEquals("true")) {
            try {
                ftpConnection = getFTPConection(ftpUser, ftpPwd);
                ftpConnection.setFileType(FTP.BINARY_FILE_TYPE);
                ftpConnection.enterLocalPassiveMode();
                ftpConnection.changeWorkingDirectory(remoteDirectory);
                log.info("Connected " + ftpConnection.getStatus());


                FTPFile[] ftpFiles = list(ftpConnection);
                downloadFTPFiles(ftpFiles);

                ftpConnection.logout();
                ftpConnection.disconnect();
            } catch (Exception e) {
                throw new RuntimeException("Error Connecting FTP Server : " + e);
            }
            log.info("#################   Ende Download from the Weather Server   ############");
        } else {
            log.info("FTP was skipt by parameter : climate.skipFTP = true");
        }

        return RepeatStatus.FINISHED;
    }

}
