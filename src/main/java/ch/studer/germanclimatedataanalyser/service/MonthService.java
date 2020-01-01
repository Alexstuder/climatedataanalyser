package ch.studer.germanclimatedataanalyser.service;

import ch.studer.germanclimatedataanalyser.model.database.Month;

import java.util.List;

public interface MonthService {

    void saveAllMonth(List<? extends Month> months);

    int getCountOnDb(int stationsID);

    List<Month> getMonthsById(int stationsID);

    List<Month> getMonthsByIdOrderDesc(int stationsId);

    List<Integer> getAllStationId();
}
