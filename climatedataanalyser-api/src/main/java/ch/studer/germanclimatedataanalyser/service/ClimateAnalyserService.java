package ch.studer.germanclimatedataanalyser.service;

import ch.studer.germanclimatedataanalyser.model.dto.ClimateAnalyserRequestDto;
import ch.studer.germanclimatedataanalyser.model.dto.ClimateAnalyserResponseDto;

public interface ClimateAnalyserService {
    public ClimateAnalyserResponseDto getClimateAnalyserForBundesland(String bundesland);

    public ClimateAnalyserResponseDto getClimateAnalyticsByClimateAnalyserRequest(ClimateAnalyserRequestDto climateAnalyserRequestDto);
}
