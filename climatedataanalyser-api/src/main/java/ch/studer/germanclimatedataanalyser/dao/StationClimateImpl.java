package ch.studer.germanclimatedataanalyser.dao;

import ch.studer.germanclimatedataanalyser.model.database.StationClimate;
import org.hibernate.Session;
import org.hibernate.query.Query;
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

        Session currentSession = getSession();

        currentSession.saveOrUpdate(stationClimate);

    }

    @Override
    public void saveAll(List<StationClimate> stationClimates) {

        for(StationClimate stationClimate : stationClimates){
            save(stationClimate);
        }

    }

    @Override
    public List<StationClimate> getClimateForBundesland(String bundesland) {
        List<StationClimate>  climateForBundesland = null;

        Session currentSession = getSession();

        Query<StationClimate> theQuery = currentSession.createQuery("SELECT c FROM Climate c WHERE c.bundesLand = :bundesLand", StationClimate.class)
                .setParameter("bundesLand",bundesland);

        // execute and get result list
        climateForBundesland = theQuery.getResultList();

        // There is only one Station !
        return climateForBundesland;
    }
}
