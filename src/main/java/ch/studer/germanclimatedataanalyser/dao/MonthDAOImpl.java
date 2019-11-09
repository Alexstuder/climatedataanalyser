package ch.studer.germanclimatedataanalyser.dao;

import ch.studer.germanclimatedataanalyser.model.Month;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
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

        if (months == null){
           Session currentSession = getSession();

           Query<Month> theQuery = currentSession.createQuery("SELECT m FROM Month m WHERE m.stationsId = :stationsID", Month.class)
                                                 .setParameter("stationsID",stationsID);


           // execute and get result list
           List<Month> newmonths = theQuery.getResultList();

           countOnDb = newmonths.size();
        }

        return countOnDb;
    }
}
