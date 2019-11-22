package ch.studer.germanclimatedataanalyser.service;

import ch.studer.germanclimatedataanalyser.model.ClimateAtStation;
import ch.studer.germanclimatedataanalyser.model.ClimateDifference;

import java.util.List;

public interface ClimateService {

    public ClimateAtStation getClimateAtStationId(int stationId);

    public List<ClimateDifference> getDifference(int stationId);
}
