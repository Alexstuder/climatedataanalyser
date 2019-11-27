package ch.studer.germanclimatedataanalyser.service;

import ch.studer.germanclimatedataanalyser.model.Station;

import java.util.List;

public interface StationService {

    void saveAllStation(List<? extends Station> stations);

    public Station getStation(int stationId);

    public List<Station> getStation(String stationName);


}
