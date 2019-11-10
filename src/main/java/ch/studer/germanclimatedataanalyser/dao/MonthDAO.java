package ch.studer.germanclimatedataanalyser.dao;

import ch.studer.germanclimatedataanalyser.model.Month;

import java.util.List;

public interface MonthDAO {

    public void save(Month month);

    public void saveAll(List<? extends Month> months);

    int getCountOnDb(int stationsID);

    List<Month> getMonthsById(int stationsID);
}
