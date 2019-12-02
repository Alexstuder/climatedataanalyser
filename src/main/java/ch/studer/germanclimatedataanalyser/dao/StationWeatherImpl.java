package ch.studer.germanclimatedataanalyser.dao;

import ch.studer.germanclimatedataanalyser.model.Station;
import ch.studer.germanclimatedataanalyser.model.StationWeather;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
public class StationWeatherImpl implements StationWeatherDAO{

    @Autowired
    private  EntityManager entityManager;

    private Session getSession() {
        return entityManager.unwrap(Session.class);
    }


    @Override
    public void save(StationWeather stationWeather) {

        // Get the current hibernate Session
        Session currentSession = getSession();
        getSession().save(stationWeather);

    }

    @Override
    public void saveAll(List<StationWeather> stationWeathers) {


        for (StationWeather stationWeather : stationWeathers){
            save(stationWeather);
        }
    }

    @Override
    public List<Station> StationWeatherDAO(Station station) {
        return null;
    }
}
