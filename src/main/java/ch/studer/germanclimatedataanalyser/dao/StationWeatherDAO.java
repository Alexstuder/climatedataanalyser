package ch.studer.germanclimatedataanalyser.dao;

import ch.studer.germanclimatedataanalyser.model.Station;
import ch.studer.germanclimatedataanalyser.model.database.StationTemperature;
import ch.studer.germanclimatedataanalyser.model.database.StationWeatherPerYear;

import java.util.List;

public interface StationWeatherDAO {


    public void save(StationWeatherPerYear stationWeatherPerYear);

    public void saveAll(List<StationWeatherPerYear> stationWeatherPerYears);

    public List<Station> StationWeatherDAO(Station station);

}
