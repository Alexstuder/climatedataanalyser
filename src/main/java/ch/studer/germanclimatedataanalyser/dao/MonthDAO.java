package ch.studer.germanclimatedataanalyser.dao;

import ch.studer.germanclimatedataanalyser.model.Month;

import java.util.List;

public interface MonthDAO {

    public void save(Month month);

    public void saveAll(List<? extends Month> months);

    int getCountOnDb(int stationsId);

    List<Month> getMonthsById(int stationsId);

    List<Month> getMonthsByIdOrderDesc(int stationsId);

    List<Integer> getAllStationID();
}
