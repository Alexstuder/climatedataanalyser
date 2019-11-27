package ch.studer.germanclimatedataanalyser.service;

import ch.studer.germanclimatedataanalyser.dao.StationDAO;
import ch.studer.germanclimatedataanalyser.model.Station;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public class StationServiceImpl implements StationService {

    @Autowired
    private StationDAO stationDAO;

    @Override
    @Transactional
    public void saveAllStation(List<? extends Station> stations) {

        stationDAO.saveAll(stations);
    }


    @Override
    public Station getStation(int stationId) {
        return null;
    }

    @Override
    public List<Station> getStation(String stationName) {
        return stationDAO.getStationByName(stationName);
    }
}
