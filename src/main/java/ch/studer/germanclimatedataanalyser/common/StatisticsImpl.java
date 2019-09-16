package ch.studer.germanclimatedataanalyser.common;

import ch.studer.germanclimatedataanalyser.model.StatisticRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;

public class StatisticsImpl implements Statistics{

    private StatisticRecord actual ;
    private ArrayList<StatisticRecord> statisticRecords;

    private static final Logger log = LoggerFactory.getLogger(StatisticsImpl.class);


    public StatisticsImpl() {

        this.statisticRecords =new ArrayList<StatisticRecord>();
        this.actual = new StatisticRecord();
        //this.actual = new StatisticRecord(0,"","","",0,0,0,0,true,"");

    }

    public StatisticRecord getActual() {
        return actual;
    }

    public void setActual(StatisticRecord actual) {
        this.actual = actual;
    }

    @Override
    public void addActualToStatistics() {

        this.getActual().setAnzahlProcess(this.getActual().getAnzahlProcess());
        this.getActual().setLastDatum(this.getActual().getLastDatum());
        this.getStatisticRecords().add(this.getActual());
    }

    public ArrayList<StatisticRecord> getStatisticRecords() {
        return statisticRecords;
    }

    public void setStatisticRecords(ArrayList<StatisticRecord> statisticRecords) {
        this.statisticRecords = statisticRecords;
    }

    @Override
    public void printStatistics() {
        if(statisticRecords == null || statisticRecords.isEmpty()){

        } else {

            for (StatisticRecord statisticRecord : statisticRecords){

                log.info(statisticRecord.toString());
            }
        }

    }

    @Override
    public boolean checkIsNewStationsID(String stationId) {
        if(this.getActual().getStationsID() == Integer.valueOf(stationId.trim().toString())){
            return  false ;
        } else {
            return true;
        }
    }
}
