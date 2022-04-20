package ch.studer.germanclimatedataanalyser.dao;

import ch.studer.germanclimatedataanalyser.model.database.StationClimate;
import ch.studer.germanclimatedataanalyser.model.dto.helper.GpsPoint;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.math.BigDecimal;
import java.util.List;

@Repository
public class StationClimateImpl implements StationClimateDAO {

    @Autowired
    private EntityManager entityManager;

    private Session getSession() {
        return entityManager.unwrap(Session.class);
    }

    private static final Logger LOG = LoggerFactory.getLogger(StationClimateImpl.class);

    @Override
    @Transactional
    public void save(StationClimate stationClimate) {

        Session currentSession = getSession();

        currentSession.saveOrUpdate(stationClimate);

    }

    @Override
    @Transactional
    public void saveAll(List<StationClimate> stationClimates) {

        for (StationClimate stationClimate : stationClimates) {
            save(stationClimate);
        }

    }

    @Override
    @Transactional
    public List<StationClimate> getClimateForBundesland(String bundesland) {
        List<StationClimate> climateForBundesland;

        Session currentSession = getSession();

        Query<StationClimate> theQuery = currentSession.createQuery("SELECT c FROM StationClimate as c , Station as s WHERE  c.stationId = s.stationId and s.bundesLand = :bundesLand", StationClimate.class)
                .setParameter("bundesLand", bundesland);

        // execute and get result list
        climateForBundesland = theQuery.getResultList();

        // There is only one Station !
        return climateForBundesland;
    }

    @Override
    @Transactional
    public List<StationClimate> getClimateForGpsCoordinates(GpsPoint gps1, GpsPoint gps2) {
        List<StationClimate> climateForGpsCoordinates;

        Session currentSession = getSession();

        Query<StationClimate> theQuery = currentSession.createQuery("SELECT c FROM StationClimate as c , Station as s WHERE  c.stationId = s.stationId " +
                        "and ((s.geoLatitude BETWEEN :gps2Latitude AND :gps1Latitude) and ( s.geoLength BETWEEN :gps1Longitude AND :gps2Longitude))", StationClimate.class)
                .setParameter("gps1Latitude", BigDecimal.valueOf(gps1.getLatitude()))
                .setParameter("gps1Longitude", BigDecimal.valueOf(gps1.getLongitude()))
                .setParameter("gps2Latitude", BigDecimal.valueOf(gps2.getLatitude()))
                .setParameter("gps2Longitude", BigDecimal.valueOf(gps2.getLongitude()));

        // execute and get result list
        climateForGpsCoordinates = theQuery.getResultList();

        // There is only one Station !
        return climateForGpsCoordinates;
    }

    @Override
    @Transactional
    public List<StationClimate> getClimateForGpsCoordinatesFromYearOrderByYearAndStationId(GpsPoint gps1, GpsPoint gps2, String yearFrom) {
        List<StationClimate> climateForGpsCoordinates;

        Session currentSession = getSession();

        Query<StationClimate> theQuery = currentSession.createQuery("SELECT c FROM StationClimate as c , Station as s WHERE  c.stationId = s.stationId " +
                        " AND ((s.geoLatitude <= :gps1Latitude AND s.geoLength >= :gps1Longitude) AND ( s.geoLatitude >= :gps2Latitude AND s.geoLength <= :gps2Longitude))" +
                        " AND cast(c.startPeriod as int) >= :yearFrom" +
                        " Order by c.endPeriod asc , s.stationId asc", StationClimate.class)
                .setParameter("gps1Latitude", BigDecimal.valueOf(gps1.getLatitude()))
                .setParameter("gps1Longitude", BigDecimal.valueOf(gps1.getLongitude()))
                .setParameter("gps2Latitude", BigDecimal.valueOf(gps2.getLatitude()))
                .setParameter("gps2Longitude", BigDecimal.valueOf(gps2.getLongitude()))
                .setParameter("yearFrom", Integer.valueOf(yearFrom));

        // execute and get result list
        climateForGpsCoordinates = theQuery.getResultList();

        // There is only one Station !
        return climateForGpsCoordinates;
    }

    @Override
    @Transactional
    public List<StationClimate> getClimateForBundeslandFromYearOrderByYearAndStationId(String bundesland, String year) {
        List<StationClimate> climates;

        Query<StationClimate> theQuery = getSession().createQuery("SELECT c FROM StationClimate as c , Station as s WHERE  c.stationId = s.stationId " +
                        " AND s.bundesLand = :bundesLand " +
                        " AND c.endPeriod >= :yearFrom" +
                        " Order by c.endPeriod asc , s.stationId asc", StationClimate.class)
                .setParameter("bundesLand", bundesland)
                .setParameter("yearFrom", year);

        // execute and get result list
        climates = theQuery.getResultList();

        // There is only one Station !
        return climates;
    }

    @Override
    public long count() {


        Query<Long> theQuery = getSession().createQuery("SELECT count(*)  FROM StationClimate c", Long.class);

        // execute and get result list
        return theQuery.getSingleResult();
    }
}
