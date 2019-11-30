package ch.studer.germanclimatedataanalyser.service;

import ch.studer.germanclimatedataanalyser.model.ClimateAtStation;
import ch.studer.germanclimatedataanalyser.model.ClimateDifferenceAtStation;

import java.util.List;

public interface ClimateService {

    public ClimateAtStation getClimateAtStationId(String stationId) throws Exception;

    public List<ClimateDifferenceAtStation> getClimateByBundesland(String bundesland);
}
