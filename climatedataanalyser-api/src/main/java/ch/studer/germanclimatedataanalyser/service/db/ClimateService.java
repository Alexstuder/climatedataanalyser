package ch.studer.germanclimatedataanalyser.service.db;

import ch.studer.germanclimatedataanalyser.model.database.StationClimate;
import ch.studer.germanclimatedataanalyser.model.database.StationWeatherPerYear;
import ch.studer.germanclimatedataanalyser.model.dto.helper.GpsPoint;

import java.util.List;

public interface ClimateService {

    List<StationClimate> getClimateForStation(List<StationWeatherPerYear> stationWeatherPerYears);

    List<StationClimate> getClimateForBundesland(String bundesland);

    void saveAllClimateAtStationId(List<StationClimate> stationClimates);

    List<StationClimate> getClimateForGpsCoordinates(GpsPoint gps1, GpsPoint gps2);
}
