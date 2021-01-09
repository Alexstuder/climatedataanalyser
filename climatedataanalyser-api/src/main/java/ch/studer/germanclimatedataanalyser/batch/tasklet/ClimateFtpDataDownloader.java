package ch.studer.germanclimatedataanalyser.batch.tasklet;

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

public class ClimateFtpDataDownloader extends DirectoryHandler implements Tasklet {

    private static final Logger log = LoggerFactory.getLogger(ClimateFtpDataDownloader.class);

    @Autowired
    ApplicationContext applicationContext;

    /*@Value("${climate.path.linuxFolderName}")
    private String linuxFolderName;

    @Value("${climate.path.windowsFolderName}")
    private String windowsFolderName;

    @Value("${climate.path.unzipOutputFolderName}")
    private String unzipOutputFolderName;


    @Value("${climate.path.ftpDataFolderName}")
    private String ftpDataFolderName;*/
    @Autowired
    DirectoryHandler directoryHandler;

    @Value("${climate.ftp.server}")
    private String ftpServer;

    @Value("${climate.ftp.server.directory}")
    private String remoteDirectory;

    @Value("${climate.ftp.server.user}")
    private String ftpUser;

    @Value("${climate.ftp.server.pwd}")
    private String ftpPwd;

    private FTPClient ftpConnection;


    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) {
        log.info("###############   Start the Download from the Weather Server ! ############");

        try {
            ftpConnection = getFTPConection(ftpUser, ftpPwd);
            ftpConnection.setFileType(FTP.BINARY_FILE_TYPE);
            ftpConnection.enterLocalPassiveMode();
            ftpConnection.changeWorkingDirectory(remoteDirectory);
            log.info("Connected " + ftpConnection.getStatus());
            log.debug("getList ");

            FTPFile[] ftpFiles = list(ftpConnection);
            downloadFTPFiles(ftpFiles, remoteDirectory);

            ftpConnection.logout();
            ftpConnection.disconnect();
        } catch (Exception e) {
            throw new RuntimeException("Error Connecting FTP Server : " + e);
        }
        log.info("#################   Ende Download from the Weather Server   ############");

        return null;
    }

    private FTPClient getFTPConection(String ftpUser, String ftpPwd) {

        FTPClient ftpClient = new FTPClient();
        String[] filenameList;
        FTPFile[] ftpFiles;

        try {
            ftpClient.connect(ftpServer);
            ftpClient.login(ftpUser, ftpPwd);

        } catch (Exception e) {
            System.out.println("Connection to FTP Server Failed : " + e);
        }
        return ftpClient;


    }

    private void downloadFTPFiles(FTPFile[] ftpFiles, String remoteDirectory) throws IOException {

        int counter = 0;
    /*    File directory = null;
        log.info("Start Download  : " + LocalDateTime.now().toString());
        try {
            directory = getDirectory(ftpDataFolderName);
        } catch (Exception e) {
            e.printStackTrace();
        }

        DirectoryUtility.deleteDirectoryFilesOld(directory);
*/


        File directory = directoryHandler.getFtpDataFolder();


        log.info("FTPDataFolderName : " + directory.getPath());

        for (FTPFile ftpFile : ftpFiles) {

            FileOutputStream out = new FileOutputStream(directory.getAbsoluteFile() + "/" + ftpFile.getName());
            try {
                ftpConnection.retrieveFile(ftpFile.getName(), out);
                log.info("File {}", ftpFile, " downloaded !");
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
        log.info("End Download  : " + LocalDateTime.now().toString());
    }

    public FTPFile[] list(FTPClient ftpConnection) {
        String[] filenameList;
        FTPFile[] ftpFiles;

        try {

            //ftpFiles = ftpConnection.listFiles(remoteDirectory);
            ftpFiles = ftpConnection.listFiles();
           /* for (FTPFile file : ftpFiles) {
                System.out.println(file.getName());
            }*/
        } catch (Exception e) {
            throw new RuntimeException("Runtime Exeption in list FTP Files : " + e.getMessage());

            //ftpClient.logout();
        } finally {
            System.out.println("Finaly in List arrived !");
            //  ftpClient.disconnect();
        }

        return ftpFiles;
    }

   /* private File getDirectory() throws IOException {

        File directory;

        String osName = System.getProperty("os.name");
        String fs = System.getProperty("file.separator");


        if (osName.toUpperCase().contains("WINDOWS")) {
            directory = new File(windowsFolderName + fs + ftpDataFolderName);
        } else {
            if (osName.toUpperCase().contains("LINUX")) {
                directory = new File(linuxFolderName + fs + ftpDataFolderName);
            } else {
                throw new RuntimeException("Unknown Operating System :" + osName);
            }
        }

        // delete if already exists !
        directory.delete();

        try {

            if (!directory.mkdirs()) {
                throw new RuntimeException("Error creating directory :" + directory.getAbsolutePath());
            }
        } catch (SecurityException e) {
            throw new RuntimeException("Security Exception trying to create directory :" + directory.getAbsolutePath() + " \\nl" + e);
        }

        return directory.getAbsoluteFile();


       *//* Resource[] resources = new Resource[0];
        Resource resource = null;

        log.info("***************************************************************************");
        log.info("Die Applikation läuft auf Stufe :" + System.getProperty("catalina.base"));
        log.info("Catalina home                   :" + System.getProperty("catalina.home"));
        log.info("Die Applikation läuft auf OS    :" + System.getProperty("os.name"));
        log.info("User dir                        :" + System.getProperty("user.dir"));
        log.info("Java Classpath                  :" + System.getProperty("java.class.path"));
        System.getProperties().list(System.out);

        try {
            resources = applicationContext.getResources("classpath*:/" + directoryName);
        } catch (IOException e) {
            e.printStackTrace();
        }
        log.info("Prüfen ob directory vorhanden");
        if (resources.length > 0) {
            log.info("Das direcotry wurde gefunden!");
            for (Resource directory : resources) {
                if (directory.getFilename().equals(directoryName)) {
                    return directory.getFile();
                }
            }

        } else {
            log.info("Das directory wurde nicht gefunden");
            File newDirectory = new File(directoryName);
            newDirectory.mkdir();

            return newDirectory;
        }
        log.info("***************************************************************************");*//*
    }*/
}
