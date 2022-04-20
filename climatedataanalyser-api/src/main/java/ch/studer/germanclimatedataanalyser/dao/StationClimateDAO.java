package ch.studer.germanclimatedataanalyser.dao;

import ch.studer.germanclimatedataanalyser.model.database.StationClimate;
import ch.studer.germanclimatedataanalyser.model.dto.helper.GpsPoint;

import java.util.List;

public interface StationClimateDAO extends DAO{


    void save(StationClimate stationClimate);

    void saveAll(List<StationClimate> stationClimates);

    List<StationClimate> getClimateForBundesland(String bundesland);

    List<StationClimate> getClimateForGpsCoordinates(GpsPoint gps1, GpsPoint gps2);

    List<StationClimate> getClimateForGpsCoordinatesFromYearOrderByYearAndStationId(GpsPoint gps1, GpsPoint gps2, String yearFrom);

    List<StationClimate> getClimateForBundeslandFromYearOrderByYearAndStationId(String bundesland, String yearFrom);


}
