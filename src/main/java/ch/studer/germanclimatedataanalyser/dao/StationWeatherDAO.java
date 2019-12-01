package ch.studer.germanclimatedataanalyser.dao;

import ch.studer.germanclimatedataanalyser.model.Station;
import ch.studer.germanclimatedataanalyser.model.StationWeather;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

public interface StationWeatherDAO {


    public void save(StationWeather stationWeather);

    public void saveAll(List<StationWeather> stationWeather);

    public List<Station> StationWeatherDAO(Station station);

}
