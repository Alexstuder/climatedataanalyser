package ch.studer.germanclimatedataanalyser.service;

import ch.studer.germanclimatedataanalyser.model.StationWeather;

import java.util.List;

public interface StationWeatherService {

    public void saveAll(List<StationWeather> stationWeathers);
}
