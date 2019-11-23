package ch.studer.germanclimatedataanalyser.dao;

import ch.studer.germanclimatedataanalyser.model.Station;

import java.util.List;

public interface StationDAO {


    public void save(Station station);

    public void saveAll(List<Station> stations);

    public List<Station> getStationsBy(int stationID);


}
