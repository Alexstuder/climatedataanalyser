package ch.studer.germanclimatedataanalyser.service;

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

    @Transactional
    @Override
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
    public List<Month> getMonthsByIdOrderDesc(int stationsId) {

        return monthDAO.getMonthsByIdOrderDesc(stationsId);
    }

    @Override
    public List<Integer> getAllStationId() {

        return monthDAO.getAllStationID();
    }
}
