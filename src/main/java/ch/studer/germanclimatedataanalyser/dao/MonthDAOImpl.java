package ch.studer.germanclimatedataanalyser.dao;

import ch.studer.germanclimatedataanalyser.model.Month;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
public class MonthDAOImpl implements MonthDAO {

    @Autowired
    private EntityManager entityManager;

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
}
