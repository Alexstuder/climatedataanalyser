package ch.studer.germanclimatedataanalyser.batch.tasklet;

import ch.studer.germanclimatedataanalyser.common.Statistic;

import ch.studer.germanclimatedataanalyser.model.Month;
import ch.studer.germanclimatedataanalyser.model.StatisticRecord;
import ch.studer.germanclimatedataanalyser.service.MonthService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.Date;
import java.util.Calendar;
import java.util.List;

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

        if (!checkDateChain()) {
            log.info("Broken Chain in Date registered !");

        }




    }

    private boolean checkDateChain() {

        boolean status = true ;
        Date beginDate = null ;
        Date endDate = null ;

        int counter = 0 ;

        log.debug("##################### Date Check Beginn ! #####################");

        for(StatisticRecord statisticRecord : statistic.getStatisticRecords()){
        //TODO A.Studer Implement the logic for Date check !

            List<Month> checkMonths = monthService.getMonthsById(statisticRecord.getStationsID());

            for(Month month : checkMonths){

                //log.info(month.toString());
                beginDate = month.getMessDatumBeginn();

                if(endDate!=null){


                     if(month.getMessDatumBeginn().compareTo(addDays(endDate,1))!=0){

                     log.debug("StationsId :" + month.getStationsId());
                     log.debug("Daten sind nicht die selben !");
                     counter++;
                     log.debug("                         EndDate :" + endDate);
                     log.debug("BeginDate :" + beginDate);
                     log.debug("EndDate + 1 :" + addDays(endDate,1));

                     }

                }

                endDate = month.getMessDatumEnde();




            }





        }
        log.debug("Anzahl Lücken insgesamt :" + counter);
        log.debug("##################### Date Check End ! #####################");

        return status;
    }
    public static Date addDays(Date date, int days) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.DATE, days);
        return new Date(c.getTimeInMillis());

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

            statisticRecord.setAnzahlOnDb(monthService.getCountOnDb(statisticRecord.getStationsID()));

        }




            return status;
        }

    public void printDbStatus() {

        log.debug("********************  DB Check  ********************");
        checkDB();
    }
}

