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
import org.springframework.core.io.Resource;

import java.io.*;
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
    private String remoteDirectory ;

    @Value("${climate.ftp.server.user}")
    private String ftpUser ;

    @Value("${climate.ftp.server.pwd}")
    private String ftpPwd ;

    private FTPClient ftpConnection ;




    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
        log.info("###############   Start the Download from the Weather Server   ############");

        try {
            ftpConnection = getFTPConection(ftpUser, ftpPwd);
            ftpConnection.setFileType(FTP.BINARY_FILE_TYPE);
            ftpConnection.enterLocalPassiveMode();
            ftpConnection.changeWorkingDirectory(remoteDirectory);
        log.info("Connected " +ftpConnection.getStatus());


        FTPFile[] ftpFiles =list(ftpConnection);
        downloadFTPFiles(ftpFiles,remoteDirectory);

        ftpConnection.logout();
        ftpConnection.disconnect();
        } catch (Exception e ) {
            throw new RuntimeException("Error Connecting FTP Server : "+e);
        }
        log.info("#################   Ende Download from the Weather Server   ############");

        return null;
    }

    private FTPClient getFTPConection(String ftpUser, String ftpPwd) throws IOException {

        FTPClient ftpClient = new FTPClient();
        String[]  filenameList;
        FTPFile[] ftpFiles;

        try {
            ftpClient.connect( ftpServer);
            ftpClient.login( ftpUser, ftpPwd );

            } catch (Exception e){
            System.out.println("Connection to FTP Server Failed : " + e);
        }
        return  ftpClient;



    }

    private void downloadFTPFiles(FTPFile[] ftpFiles, String remoteDirectory) throws IOException {

        int counter = 0 ;
        File directory = null;
        log.info("Start Download  : "+LocalDateTime.now().toString());
        try {
          directory = getDirectory(ftpDataFolderName);
        } catch (Exception e) {
            e.printStackTrace();
        }

        deleteDirectoryFiles(directory);
        log.info("FTPDataFolderName : " + directory.getPath());
        //for (FTPFile ftpFile : ftpFiles) {
        for (int i = 0 ; i<100 ; i++) {

        FileOutputStream out = new FileOutputStream(directory.getAbsoluteFile() +"/"+ftpFiles[i].getName());
        try {
            ftpConnection.retrieveFile(ftpFiles[i].getName(), out);
        } catch ( Exception e) {

            throw new RuntimeException("Error in Download File : "+e);
        }
        out.close();

        if(ftpConnection.getReplyCode()==550){
            throw new RuntimeException("FTP Download Error : " + ftpConnection.getReplyString());
        }
         counter++;

        }
        log.info("*************************************************");

        log.info(counter + " Files downloaded !");
        log.info("End Download  : "+LocalDateTime.now().toString());
    }

    public  FTPFile[] list( FTPClient ftpConnection ) throws IOException
    {
        String[]  filenameList;
        FTPFile[] ftpFiles;

        try {

            //ftpFiles = ftpConnection.listFiles(remoteDirectory);
            ftpFiles = ftpConnection.listFiles();
           /* for (FTPFile file : ftpFiles) {
                System.out.println(file.getName());
            }*/
        } catch (Exception e) {
            throw  new RuntimeException("Runtime Exeption in list FTP Files : " + e.getMessage());

            //ftpClient.logout();
        } finally {
            System.out.println("Finaly in List arrived !");
          //  ftpClient.disconnect();
        }

        return ftpFiles;
    }

    private void deleteDirectoryFiles(File directory) throws IOException {
        File[] allContent = null;
        allContent = directory.listFiles();

        // First check , if directory is empty
        if (allContent != null){
            for (File file : allContent){
                // delete all files and subdir
                if (file.isDirectory()){
                    deleteDirectoryFiles(file);
                } else {
                    file.delete();
                }
            }
        }
        // Make dir
        directory.mkdir();
    }

    private File getDirectory(String directoryName) throws IOException {
        Resource[] resources = new Resource[0];
        Resource resource = null;

        try {
            resources = applicationContext.getResources("classpath*:/"+directoryName);
        } catch (IOException e) {
            e.printStackTrace();
        }
        for (Resource directory : resources){
            if (directory.getFilename().equals(directoryName)){
                resource = directory;
            };
        }
        return resource.getFile();
    }
}
