package ch.studer.germanclimatedataanalyser.common;

import ch.studer.germanclimatedataanalyser.model.StatisticRecord;

import java.util.ArrayList;

public interface Statistics {

    void printStatistics();

    boolean checkIsNewStationsID(String stationId);

    ArrayList<StatisticRecord> getStatisticRecords();
    //void setStatisticRecords(ArrayList<StatisticRecord> statisticRecords);
    StatisticRecord getActual();
    void setActual(StatisticRecord statisticRecord);
    void addActualToStatistics();
    //void writeDbAnzahl();
}
