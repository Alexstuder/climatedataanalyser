package ch.studer.germanclimatedataanalyser.batch.listener;

import ch.studer.germanclimatedataanalyser.common.Statistics;
import ch.studer.germanclimatedataanalyser.common.StatisticsImpl;
import ch.studer.germanclimatedataanalyser.model.StatisticRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class JobCompletionNotificationListener extends JobExecutionListenerSupport {

    private static final Logger log = LoggerFactory.getLogger(JobCompletionNotificationListener.class);

    private final JdbcTemplate jdbcTemplate ;

    @Autowired
    public Statistics statistic;

    @Autowired
    public JobCompletionNotificationListener(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void beforeJob(JobExecution jobExecution){

        log.info("****************************************************************");
        log.info("!!!                      JOB START                           !!!");
        log.info("****************************************************************");

        // Init the StatisticsImpl
       // statistic = new StatisticsImpl();
    }

   @Override
   public void afterJob(JobExecution jobExecution) {

        // Add the last actual statistic Record to statistics
       statistic.addActualToStatistics();

        if(jobExecution.getStatus() == BatchStatus.COMPLETED) {
            log.info("##########################################################");
            log.info("!!!          JOB FINISHED! SUCCESSFULLY                !!!");
            log.info("##########################################################");
        } else {
            log.info("??????????????????????????????????????????????????????????");
            log.info("!!!          JOB FAILED                                !!!");
            log.info("??????????????????????????????????????????????????????????");
            log.info("Batch Exit Status :" + jobExecution.getStatus().toString());

        }

        log.info("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        log.info("!!!                     STATISTIC                            !!!");
        log.info("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        statistic.printStatistics();




       log.info("****************************************************************");
       log.info("!!!          JOB ENDE                                        !!!");
       log.info("****************************************************************");

   }





}
