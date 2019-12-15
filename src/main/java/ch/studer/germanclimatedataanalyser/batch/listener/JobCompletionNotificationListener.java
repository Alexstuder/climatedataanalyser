package ch.studer.germanclimatedataanalyser.batch.listener;

import ch.studer.germanclimatedataanalyser.batch.tasklet.DbCheck;
import ch.studer.germanclimatedataanalyser.common.Statistic;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;

@Component
public class JobCompletionNotificationListener extends JobExecutionListenerSupport {

    private static final Logger log = LoggerFactory.getLogger(JobCompletionNotificationListener.class);

    private final JdbcTemplate jdbcTemplate ;

    @Autowired
    public Statistic statistic;

    @Autowired
    private DbCheck dbCheck;


    @Autowired
    private EntityManager entityManager;

    private Session getSession() {
        return entityManager.unwrap(Session.class);
    }

    @Autowired
    public JobCompletionNotificationListener(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void beforeJob(JobExecution jobExecution){

        log.info("****************************************************************");
        log.info("!!!                      JOB START                           !!!");
        log.info("****************************************************************");

        // Prepend the Tables
        jdbcTemplate.execute("Delete FROM month");
        jdbcTemplate.execute("Delete FROM station");
        jdbcTemplate.execute("Delete FROM weather");
        jdbcTemplate.execute("Delete FROM climate");



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

      //  dbCheck.checkDB();

        log.info("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        log.info("!!!                     STATISTIC                            !!!");
        log.info("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        //statistic.printStatistics();




       log.info("****************************************************************");
       log.info("!!!          JOB ENDE                                        !!!");
       log.info("****************************************************************");

   }





}
