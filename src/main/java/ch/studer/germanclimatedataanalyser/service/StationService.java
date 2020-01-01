package ch.studer.germanclimatedataanalyser.service;

import ch.studer.germanclimatedataanalyser.model.database.Station;
import javassist.NotFoundException;

import java.util.List;

public interface StationService {

    void saveAllStation(List<? extends Station> stations);

    public Station getStation(int stationId) throws NotFoundException;

    public Station getStation(String stationName) throws NotFoundException;
    public List<Station> getAllStations() ;


    List<Station> getStationsFromBundesland(String bundesland);
}
