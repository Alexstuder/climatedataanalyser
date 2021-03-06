package ch.studer.germanclimatedataanalyser.batch.listener;

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

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private EntityManager entityManager;

    private Session getSession() {
        return entityManager.unwrap(Session.class);
    }

//    @Autowired
//    public JobCompletionNotificationListener(JdbcTemplate jdbcTemplate) {
//        this.jdbcTemplate = jdbcTemplate;
//    }

    @Override
    public void beforeJob(JobExecution jobExecution) {

        log.info("****************************************************************");
        log.info("!!!                      JOB START                           !!!");
        log.info("****************************************************************");

        // Prepend the Tables
        jdbcTemplate.execute("Delete FROM station");
        jdbcTemplate.execute("Delete FROM month");
        jdbcTemplate.execute("Delete FROM weather");
        jdbcTemplate.execute("Delete FROM climate");


    }

    @Override
    public void afterJob(JobExecution jobExecution) {

        if (jobExecution.getStatus() == BatchStatus.COMPLETED) {
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


        log.info("****************************************************************");
        log.info("!!!          JOB ENDE                                        !!!");
        log.info("****************************************************************");

    }


}
