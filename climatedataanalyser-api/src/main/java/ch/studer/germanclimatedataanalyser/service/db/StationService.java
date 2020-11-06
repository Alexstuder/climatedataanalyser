package ch.studer.germanclimatedataanalyser.service.db;

import ch.studer.germanclimatedataanalyser.model.database.Station;
import javassist.NotFoundException;

import java.util.List;

public interface StationService {

    void saveAllStation(List<? extends Station> stations);

    Station getStation(int stationId) throws NotFoundException;

    Station getStation(String stationName) throws NotFoundException;

    List<Station> getAllStations();

    List<String> getAllBundeslaender() throws NotFoundException;


    List<Station> getStationsFromBundesland(String bundesland);

    boolean bundeslandExists(String bundesland);
}
