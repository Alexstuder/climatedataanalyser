package ch.studer.germanclimatedataanalyser.service.db;

import ch.studer.germanclimatedataanalyser.dao.StationDAO;
import ch.studer.germanclimatedataanalyser.model.database.Station;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
public class StationServiceImpl implements StationService {

    @Autowired
    private StationDAO stationDAO;

    @Override
    @Transactional
    public void saveAllStation(List<? extends Station> stations) {

        stationDAO.saveAll(stations);
    }


    @Override
    public Station getStation(int stationId) throws NotFoundException {
        return stationDAO.getStationsBy(stationId);
    }

    @Override
    public Station getStation(String stationName) throws NotFoundException {
        return stationDAO.getStationByName(stationName);
    }

    @Override
    public List<Station> getAllStations() {
        return stationDAO.getAllStations();
    }

    @Override
    public List<String> getAllBundeslaender() throws NotFoundException {
        return stationDAO.getAllBundeslaenderOrderAsc();
    }

    @Override
    public List<Station> getStationsFromBundesland(String bundesland) {
        return stationDAO.getStationsFromBundesland(bundesland);
    }

    @Override
    public boolean bundeslandExists(String bundesland) {
        boolean status = false;

        List<Station> stations = stationDAO.getStationsFromBundesland(bundesland);

        if (stations.size() > 0) {
            status = true;
        }
        return status;
    }


}
