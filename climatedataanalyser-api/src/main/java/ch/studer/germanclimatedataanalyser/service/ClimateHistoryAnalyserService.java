package ch.studer.germanclimatedataanalyser.service;

import ch.studer.germanclimatedataanalyser.model.database.StationClimate;
import ch.studer.germanclimatedataanalyser.model.dto.ClimateHistoryDto;

import java.util.List;

public interface ClimateHistoryAnalyserService {

    public List<ClimateHistoryDto> getClimateHistoryAverageData(String originYear, List<StationClimate> stationClimates);
    public List<ClimateHistoryDto> getClimateHistoryEveryStationExistsData(String originYear, List<StationClimate> stationClimates);



}
