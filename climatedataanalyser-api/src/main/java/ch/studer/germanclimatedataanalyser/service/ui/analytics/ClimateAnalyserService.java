package ch.studer.germanclimatedataanalyser.service.ui.analytics;

import ch.studer.germanclimatedataanalyser.model.dto.ClimateAnalyserRequestDto;
import ch.studer.germanclimatedataanalyser.model.dto.ClimateAnalyserResponseDto;

public interface ClimateAnalyserService {

    ClimateAnalyserResponseDto getClimateAnalyticsByClimateAnalyserRequest(ClimateAnalyserRequestDto climateAnalyserRequestDto);
}
