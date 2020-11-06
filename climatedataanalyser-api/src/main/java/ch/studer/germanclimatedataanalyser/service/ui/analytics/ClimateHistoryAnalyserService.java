package ch.studer.germanclimatedataanalyser.service.ui.analytics;

import ch.studer.germanclimatedataanalyser.model.database.StationClimate;
import ch.studer.germanclimatedataanalyser.model.dto.ClimateHistoryDto;

import java.util.List;

public interface ClimateHistoryAnalyserService {

    List<ClimateHistoryDto> getClimateHistoryAverageData(String originYear, List<StationClimate> stationClimates);

    List<ClimateHistoryDto> getClimateHistoryEveryStationExistsData(String originYear, List<StationClimate> stationClimates);


}
