package ch.studer.germanclimatedataanalyser.dao;

import ch.studer.germanclimatedataanalyser.model.database.Station;
import ch.studer.germanclimatedataanalyser.model.database.StationWeatherPerYear;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private static final Logger LOG = LoggerFactory.getLogger(StationWeatherImpl.class);



    @Override
    public void save(StationWeatherPerYear stationWeatherPerYear) {

        // Get the current hibernate Session
        Session currentSession = getSession();
        getSession().save(stationWeatherPerYear);

    }

    @Override
    public void saveAll(List<StationWeatherPerYear> stationWeatherPerYears) {


        for (StationWeatherPerYear stationWeatherPerYear : stationWeatherPerYears){
           save(stationWeatherPerYear);
        }
    }

    @Override
    public List<Station> StationWeatherDAO(Station station) {
        return null;
    }
}
