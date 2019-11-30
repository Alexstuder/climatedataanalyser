package ch.studer.germanclimatedataanalyser.service;

import ch.studer.germanclimatedataanalyser.model.Station;
import javassist.NotFoundException;

import java.util.List;

public interface StationService {

    void saveAllStation(List<? extends Station> stations);

    public Station getStation(int stationId) throws NotFoundException;

    public Station getStation(String stationName) throws NotFoundException;


    List<Station> getStationsFromBundesland(String bundesland);
}
