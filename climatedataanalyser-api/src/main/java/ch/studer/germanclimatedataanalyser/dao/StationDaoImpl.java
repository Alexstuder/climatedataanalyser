package ch.studer.germanclimatedataanalyser.dao;

import ch.studer.germanclimatedataanalyser.model.database.Station;
import javassist.NotFoundException;
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

        for (Station station : stations) {
            this.save(station);
        }
    }

    @Override
    public Station getStationsBy(int stationID) throws NotFoundException {

        List<Station> stations;

        Session currentSession = getSession();

        Query<Station> theQuery = currentSession.createQuery("SELECT s FROM Station s WHERE s.stationId = :stationID ORDER BY dateBegin ASC", Station.class)
                .setParameter("stationID", stationID);

        // execute and get result list
        stations = theQuery.getResultList();

        if (stations.size() == 0) {
            throw new NotFoundException("Station : " + stationID + " not Found !");
        }

        // There is only one Station !
        return stations.get(0);

    }

    @Override
    public List<Station> getAllStations() {

        List<Station> stations;

        Session currentSession = getSession();

        Query<Station> theQuery = currentSession.createQuery("SELECT s FROM Station s", Station.class);

        stations = theQuery.getResultList();

        return stations;
    }

    @Override
    public Station getStationByName(String stationName) throws NotFoundException {

        List<Station> stations;

        Session currentSession = getSession();

        Query<Station> theQuery = currentSession.createQuery("SELECT s FROM Station s WHERE s.stationName = :stationName", Station.class)
                .setParameter("stationName", stationName);

        stations = theQuery.getResultList();

        if (stations.size() == 0) {
            throw new NotFoundException("Station : " + stationName + " not Found !");
        }
        // there is only One Station!
        return stations.get(0);
    }

    @Override
    public List<String> getAllBundeslaenderOrderAsc() {

        List<String> bundeslaender;

        Session currentSession = getSession();

        Query<String> theQuery = currentSession.createQuery("SELECT DISTINCT(s.bundesLand) FROM Station s ORDER BY s.bundesLand ASC", String.class);
        bundeslaender = theQuery.getResultList();

        return bundeslaender;
    }

    @Override
    public List<Station> getStationsFromBundesland(String bundesland) {
        List<Station> stations;

        Session currentSession = getSession();

        Query<Station> theQuery = currentSession.createQuery("SELECT s FROM Station s WHERE s.bundesLand = :bundesland", Station.class)
                .setParameter("bundesland", bundesland);

        stations = theQuery.getResultList();

        return stations;
    }

    @Override
    public long count() {

        Query<Long> theQuery = getSession().createQuery("SELECT count(*)  FROM Station s", Long.class);

        // execute and get result list
        return theQuery.getSingleResult();
    }
}
