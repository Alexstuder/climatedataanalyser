package ch.studer.germanclimatedataanalyser.batch.tasklet;

import ch.studer.germanclimatedataanalyser.common.Statistic;
import ch.studer.germanclimatedataanalyser.model.StatisticRecord;
import ch.studer.germanclimatedataanalyser.service.MonthService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class DbCheck {

    @Autowired
    Statistic statistic;

    @Autowired
    MonthService monthService;

    private static final Logger log = LoggerFactory.getLogger(DbCheck.class);



    public void checkDB(){

        // Count Records on DB and write it to the statistic Record
        if (!addCountOnDbToStatisticRecord()) {
            log.info("Couldn't write AnzahlToDb into the Statistic Record");
        }

        // Check on Statistic Records if counter of processed Records is equal to counter writen on DB

        if (!checkProcessedCounterIsEqualWritenOnDbCounter()) {
            log.info("Counter Processed is not equal Counter WrittenOnDb");
        } else {
            log.info("Counter Processed is EQUAL Counter WrittenOnDb");

        }


        // Check if BeginDate is the next day of LastDate of the predecessor record
        // so all records togheter are building a chain




    }

    private boolean checkProcessedCounterIsEqualWritenOnDbCounter() {
        boolean status = true;

        for(StatisticRecord statisticRecord : statistic.getStatisticRecords()){

            if(statisticRecord.getAnzahlProcess()!=statisticRecord.getAnzahlOnDb()){
                return false;
            }

        }
        return status;
    }

    public boolean addCountOnDbToStatisticRecord() {
        boolean status = true ;
        for (StatisticRecord statisticRecord : statistic.getStatisticRecords()) {

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

