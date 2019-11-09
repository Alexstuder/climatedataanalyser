package ch.studer.germanclimatedataanalyser.batch.tasklet;

import ch.studer.germanclimatedataanalyser.common.Statistics;
import ch.studer.germanclimatedataanalyser.model.StatisticRecord;
import ch.studer.germanclimatedataanalyser.service.MonthService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class DbCheck {

    @Autowired
    Statistics statistics;

    @Autowired
    MonthService monthService;

    private static final Logger log = LoggerFactory.getLogger(DbCheck.class);



    public void checkDB(){

        if (!addCountOnDbToStatisticRecord()) {
            log.info("Couldn't write AnzahlToDb into the Statistic Record");


        }
    }

        public boolean addCountOnDbToStatisticRecord() {
        boolean status = true ;
        for (StatisticRecord statisticRecord : statistics.getStatisticRecords()) {

            log.info("Get AnzahlOnDb from Record :" + statisticRecord.getStationsID());
            statisticRecord.setAnzahlOnDb(monthService.getCountOnDb(statisticRecord.getStationsID()));

        }




            return status;
        }

    public void printDbStatus() {

        log.info("********************  DB Check  ********************");
        checkDB();
    }
}

