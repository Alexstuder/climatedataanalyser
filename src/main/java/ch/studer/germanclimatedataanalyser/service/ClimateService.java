package ch.studer.germanclimatedataanalyser.service;

import ch.studer.germanclimatedataanalyser.model.ClimateAtStation;
import ch.studer.germanclimatedataanalyser.model.ClimateDifference;
import ch.studer.germanclimatedataanalyser.model.ClimateDifferenceAtStation;

import java.util.List;

public interface ClimateService {

    public ClimateAtStation getClimateAtStationId(int stationId);

    public ClimateDifferenceAtStation getDifference(int stationId);
}
