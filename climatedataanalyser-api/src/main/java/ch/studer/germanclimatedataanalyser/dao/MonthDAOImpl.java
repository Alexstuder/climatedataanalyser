package ch.studer.germanclimatedataanalyser.dao;

import ch.studer.germanclimatedataanalyser.model.database.Month;
import org.hibernate.Session;
import org.hibernate.jpa.QueryHints;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

@Repository
public class MonthDAOImpl implements MonthDAO {

    @Autowired
    private EntityManager entityManager;

    private List<Month> months = null ;

    private Session getSession() {
        return entityManager.unwrap(Session.class);
    }

    @Override
    public void save(Month month) {

        // get the current hibernate session
        Session currentSession = getSession();

        currentSession.saveOrUpdate(month);

    }

    @Override
    public void saveAll(List<? extends Month> months) {

        // get the current hibernate session
        Session currentSession = getSession();

        for (Month month : months)
        {
            currentSession.saveOrUpdate(month);
        }

    }

    @Override
    public int getCountOnDb(int stationsID) {

        int countOnDb = 0 ;

           Session currentSession = getSession();

           Query<Month> theQuery = currentSession.createQuery("SELECT m FROM Month m WHERE m.stationsId = :stationsID", Month.class)
                                                 .setParameter("stationsID",stationsID);


           // execute and get result list
           List<Month> newmonths = theQuery.getResultList();

           countOnDb = newmonths.size();

        return countOnDb;
    }

    @Override
    public List<Month> getMonthsById(int stationsID) {

        List<Month> months = null;

        Session currentSession = getSession();

        Query<Month> theQuery = currentSession.createQuery("SELECT m FROM Month m WHERE m.stationsId = :stationsID ORDER BY messDatumBeginn ASC", Month.class)
                .setParameter("stationsID",stationsID);

        // execute and get result list
        months = theQuery.getResultList();

        return months;
    }

    @Override
    public List<Month> getMonthsByIdOrderDesc(int stationsId) {
        List<Month> months = null;

        Session currentSession = getSession();

        Query<Month> theQuery = currentSession.createQuery("SELECT m FROM Month m WHERE m.stationsId = :stationsID AND m.moTt <> -999 ORDER BY messDatumBeginn DESC", Month.class)
                .setParameter("stationsID",stationsId);

        // execute and get result list
        months = theQuery.getResultList();

        return months;
    }

    @Override
    public List<Integer> getAllStationID() {
        List<Integer> stationIds = new ArrayList<Integer>();

        Session currentSession = getSession();

        Query<Integer> theQuery = currentSession.createQuery("SELECT DISTINCT stationsId FROM Month m ORDER By 1", Integer.class);
        theQuery.setHint(QueryHints.HINT_PASS_DISTINCT_THROUGH, false);

        // execute and get result list
        stationIds = theQuery.getResultList();


        return stationIds;
    }
}
