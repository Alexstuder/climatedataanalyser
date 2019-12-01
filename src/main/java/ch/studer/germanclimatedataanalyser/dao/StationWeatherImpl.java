package ch.studer.germanclimatedataanalyser.dao;

import ch.studer.germanclimatedataanalyser.model.Station;
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
    public void save(StationWeatherImpl stationWeather) {

        Session currentSession = getSession();

        currentSession.saveOrUpdate(stationWeather);

    }

    @Override
    public void saveAll(List<StationWeatherImpl> stationWeather) {

    }

    @Override
    public List<Station> StationWeatherDAO(Station station) {
        return null;
    }
}
