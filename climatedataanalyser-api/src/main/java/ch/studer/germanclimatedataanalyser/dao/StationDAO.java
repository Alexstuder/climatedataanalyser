package ch.studer.germanclimatedataanalyser.dao;

import ch.studer.germanclimatedataanalyser.model.database.Station;
import javassist.NotFoundException;

import java.util.List;

public interface StationDAO extends DAO {


    void save(Station station);

    void saveAll(List<? extends Station> stations);

    Station getStationsBy(int stationID) throws NotFoundException;

    List<Station> getAllStations();

    Station getStationByName(String stationName) throws NotFoundException;

    List<String> getAllBundeslaenderOrderAsc();


    List<Station> getStationsFromBundesland(String bundesland);
}
