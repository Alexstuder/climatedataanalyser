package ch.studer.germanclimatedataanalyser.service;

import ch.studer.germanclimatedataanalyser.model.ClimateDifferenceAtStation;
import ch.studer.germanclimatedataanalyser.model.ClimateOLDAtStation;
import ch.studer.germanclimatedataanalyser.model.ClimateRecord_OLD;
import ch.studer.germanclimatedataanalyser.model.database.StationClimate;
import ch.studer.germanclimatedataanalyser.model.database.StationWeatherPerYear;

import java.util.List;

public interface ClimateService {

    public List<StationClimate> getClimateForStation(List<StationWeatherPerYear> stationWeatherPerYears);

    public void saveAllClimateAtStationId(List<StationClimate> stationClimates);


}
