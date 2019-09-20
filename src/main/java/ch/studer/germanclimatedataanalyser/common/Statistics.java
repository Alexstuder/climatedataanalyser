package ch.studer.germanclimatedataanalyser.common;

import ch.studer.germanclimatedataanalyser.model.StatisticRecord;

import java.util.ArrayList;

public interface Statistics {

    public void printStatistics();

    public boolean checkIsNewStationsID(String stationId);

    public ArrayList<StatisticRecord> getStatisticRecords();
    public void setStatisticRecords(ArrayList<StatisticRecord> statisticRecords);
    public StatisticRecord getActual();
    public void setActual(StatisticRecord statisticRecord);
    public void addActualToStatistics();
    public void writeDbAnzahl();
}
