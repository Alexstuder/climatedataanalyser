package ch.studer.germanclimatedataanalyser.service;

import ch.studer.germanclimatedataanalyser.model.database.StationClimate;
import ch.studer.germanclimatedataanalyser.model.dto.ClimateHistoryDto;

import java.util.List;

public interface ClimateHistoryAnalyserService {

    public List<ClimateHistoryDto> getClimateHistoryData(String originYear, List<StationClimate> stationClimates);



}
