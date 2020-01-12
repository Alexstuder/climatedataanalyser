package ch.studer.germanclimatedataanalyser.dao;

import ch.studer.germanclimatedataanalyser.model.database.StationClimate;

import java.util.List;

public interface StationClimateDAO {


    public void save(StationClimate stationClimate);

    public void saveAll(List<StationClimate> stationClimates);

    public List<StationClimate> getClimateForBundesland(String bundesland);
}
