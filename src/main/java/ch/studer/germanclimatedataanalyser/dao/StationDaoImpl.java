package ch.studer.germanclimatedataanalyser.dao;

import ch.studer.germanclimatedataanalyser.model.Station;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
public class StationDaoImpl implements StationDAO {

    @Autowired
    private EntityManager entityManager;

    private Session getSession() {
        return entityManager.unwrap(Session.class);
    }

    @Override
    public void save(Station station) {

        // Get the current hibernate Session
        Session currentSession = getSession();

        currentSession.saveOrUpdate(station);

    }

    @Override
    public void saveAll(List<? extends Station> stations) {

        for(Station station : stations){
            this.save(station);
        }
    }

    @Override
    public List<Station> getStationsBy(int stationID) {

        List<Station> stations = null;

        Session currentSession = getSession();

        Query<Station> theQuery = currentSession.createQuery("SELECT s FROM Station s WHERE s.stationsId = :stationsID ORDER BY dateBegin ASC", Station.class)
                .setParameter("stationsID",stationID);

        // execute and get result list
        stations = theQuery.getResultList();

        return stations;

    }
}
