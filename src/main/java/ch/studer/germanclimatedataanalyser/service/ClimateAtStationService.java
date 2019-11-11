package ch.studer.germanclimatedataanalyser.service;

import ch.studer.germanclimatedataanalyser.model.ClimateAtStation;

public interface ClimateAtStationService {

    public ClimateAtStation getClimateDataBy(int stationsId);
}
