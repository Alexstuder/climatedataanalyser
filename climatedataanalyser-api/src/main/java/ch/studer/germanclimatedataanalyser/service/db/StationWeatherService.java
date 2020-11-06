package ch.studer.germanclimatedataanalyser.service.db;

import ch.studer.germanclimatedataanalyser.model.database.StationWeatherPerYear;

import java.util.List;

public interface StationWeatherService {

    void saveAll(List<StationWeatherPerYear> stationWeatherPerYears);

    List<StationWeatherPerYear> fillHoles(List<? extends StationWeatherPerYear> stationWeatherPerYears);

}
