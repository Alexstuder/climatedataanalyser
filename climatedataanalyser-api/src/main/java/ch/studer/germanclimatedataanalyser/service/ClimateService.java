package ch.studer.germanclimatedataanalyser.service;

import ch.studer.germanclimatedataanalyser.model.database.StationClimate;
import ch.studer.germanclimatedataanalyser.model.database.StationWeatherPerYear;
import ch.studer.germanclimatedataanalyser.model.dto.helper.GpsPoint;

import java.util.List;

public interface ClimateService {

    public List<StationClimate> getClimateForStation(List<StationWeatherPerYear> stationWeatherPerYears);

    public List<StationClimate> getClimateForBundesland(String bundesland);

    public void saveAllClimateAtStationId(List<StationClimate> stationClimates);

    public List<StationClimate> getClimateForGpsCoordinates(GpsPoint gps1, GpsPoint gps2);
}
