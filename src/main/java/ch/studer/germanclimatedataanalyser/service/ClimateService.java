package ch.studer.germanclimatedataanalyser.service;

import ch.studer.germanclimatedataanalyser.model.ClimateAtStation;

public interface ClimateService {

    public ClimateAtStation getClimateAtStationId(int stationId);
}
