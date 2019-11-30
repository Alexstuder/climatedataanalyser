package ch.studer.germanclimatedataanalyser.dao;

import ch.studer.germanclimatedataanalyser.model.Station;
import javassist.NotFoundException;

import java.util.List;

public interface StationDAO {


    public void save(Station station);

    public void saveAll(List<? extends Station> stations);

    public Station getStationsBy(int stationID) throws NotFoundException;

    public Station getStationByName(String stationName) throws NotFoundException;


    List<Station> getStationsFromBundesland(String bundesland);
}
