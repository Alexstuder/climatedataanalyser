package ch.studer.germanclimatedataanalyser.dao;

import ch.studer.germanclimatedataanalyser.model.Station;
import ch.studer.germanclimatedataanalyser.model.database.StationClimate;
import ch.studer.germanclimatedataanalyser.model.database.StationWeatherPerYear;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
public class StationClimateImpl implements StationClimateDAO{

    @Autowired
    private  EntityManager entityManager;

    private Session getSession() {
        return entityManager.unwrap(Session.class);
    }

    private static final Logger LOG = LoggerFactory.getLogger(StationClimateImpl.class);

    @Override
    public void save(StationClimate stationClimate) {

    }

    @Override
    public void saveAll(List<StationClimate> stationClimates) {

        for(StationClimate stationClimate : stationClimates){
            save(stationClimate);
        }

    }
}
