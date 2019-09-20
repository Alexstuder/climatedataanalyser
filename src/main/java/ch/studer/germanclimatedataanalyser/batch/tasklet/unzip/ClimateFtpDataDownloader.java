package ch.studer.germanclimatedataanalyser.batch.tasklet.unzip;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;

public class ClimateFtpDataDownloader implements Tasklet {

    private static final Logger log = LoggerFactory.getLogger(ClimateFtpDataDownloader.class);



    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {

        log.info("Start the Download from the Weather Server");
        System.out.println("UUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUU");
        return null;
    }
}
