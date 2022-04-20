package ch.studer.germanclimatedataanalyser.dao;

import ch.studer.germanclimatedataanalyser.model.database.Month;

import java.util.List;

public interface MonthDAO extends DAO {

    void save(Month month);

    void saveAll(List<? extends Month> months);

    int getCountOnDb(int stationsId);

    List<Month> getMonthsById(int stationsId);

    List<Month> getMonthsByIdOrderDesc(int stationsId);

    List<Integer> getAllStationID();
}
