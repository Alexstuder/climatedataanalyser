package ch.studer.germanclimatedataanalyser.dao;

import ch.studer.germanclimatedataanalyser.model.database.StationWeatherPerYear;

import java.util.List;

public interface StationWeatherDAO extends DAO {


    void save(StationWeatherPerYear stationWeatherPerYear);

    void saveAll(List<StationWeatherPerYear> stationWeatherPerYears);

}
