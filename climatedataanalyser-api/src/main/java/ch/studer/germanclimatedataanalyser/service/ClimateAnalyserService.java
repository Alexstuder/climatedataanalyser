package ch.studer.germanclimatedataanalyser.service;

import ch.studer.germanclimatedataanalyser.model.dto.ClimateAnalyserDto;

public interface ClimateAnalyserService {
    public ClimateAnalyserDto getClimateAnalyserForBundesland(String bundesland);
}
