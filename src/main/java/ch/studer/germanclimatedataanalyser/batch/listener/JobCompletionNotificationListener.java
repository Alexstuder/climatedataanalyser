package ch.studer.germanclimatedataanalyser.batch.listener;

import ch.studer.germanclimatedataanalyser.batch.tasklet.DbCheck;
import ch.studer.germanclimatedataanalyser.common.Statistic;
import ch.studer.germanclimatedataanalyser.model.Month;
import ch.studer.germanclimatedataanalyser.model.Station;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import java.util.List;

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

        log.debug("****************************************************************");
        log.debug("!!!                      JOB START                           !!!");
        log.debug("****************************************************************");

       jdbcTemplate.execute("Delete FROM STATION");
       jdbcTemplate.execute("Delete FROM MONTH");

    }

   @Override
   public void afterJob(JobExecution jobExecution) {

        // Add the last actual statistic Record to statistics
       statistic.addActualToStatistics();

        if(jobExecution.getStatus() == BatchStatus.COMPLETED) {
            log.debug("##########################################################");
            log.debug("!!!          JOB FINISHED! SUCCESSFULLY                !!!");
            log.debug("##########################################################");
        } else {
            log.debug("??????????????????????????????????????????????????????????");
            log.debug("!!!          JOB FAILED                                !!!");
            log.debug("??????????????????????????????????????????????????????????");
            log.debug("Batch Exit Status :" + jobExecution.getStatus().toString());

        }

      //  dbCheck.checkDB();

        log.debug("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        log.debug("!!!                     STATISTIC                            !!!");
        log.debug("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        //statistic.printStatistics();




       log.debug("****************************************************************");
       log.debug("!!!          JOB ENDE                                        !!!");
       log.debug("****************************************************************");

   }





}
