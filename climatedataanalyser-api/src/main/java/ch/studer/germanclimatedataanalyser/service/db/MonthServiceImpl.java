package ch.studer.germanclimatedataanalyser.service.db;

import ch.studer.germanclimatedataanalyser.dao.MonthDAO;
import ch.studer.germanclimatedataanalyser.model.database.Month;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class MonthServiceImpl implements MonthService {

    @Autowired
    private MonthDAO monthDAO;

    @Override
    @Transactional
    public void saveAllMonth(List<? extends Month> months) {

        monthDAO.saveAll(months);
    }

    @Override
    @Transactional
    public int getCountOnDb(int stationsID) {
        return monthDAO.getCountOnDb(stationsID);
    }

    @Override
    @Transactional
    public List<Month> getMonthsById(int stationsID) {
        return monthDAO.getMonthsById(stationsID);
    }

    @Override
    @Transactional
    public List<Month> getMonthsByIdOrderDesc(int stationsId) {

        return monthDAO.getMonthsByIdOrderDesc(stationsId);
    }

    @Override
    @Transactional
    public List<Integer> getAllStationId() {

        return monthDAO.getAllStationID();
    }
}
