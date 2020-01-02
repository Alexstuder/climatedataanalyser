package ch.studer.germanclimatedataanalyser.service;

import ch.studer.germanclimatedataanalyser.model.database.StationTemperature;
import ch.studer.germanclimatedataanalyser.model.database.StationWeatherPerYear;

import java.util.List;

public interface StationWeatherService {

    public void saveAll(List<StationWeatherPerYear> stationWeatherPerYears);

    public List<StationWeatherPerYear> fillHoles(List<? extends StationWeatherPerYear> stationWeatherPerYears);

}
