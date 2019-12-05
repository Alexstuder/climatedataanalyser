package ch.studer.germanclimatedataanalyser.service;

import ch.studer.germanclimatedataanalyser.dao.StationWeatherDAO;
import ch.studer.germanclimatedataanalyser.model.database.StationTemperature;
import ch.studer.germanclimatedataanalyser.model.database.StationWeatherPerYear;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StationWeatherServiceImpl implements StationWeatherService {

    @Autowired
    StationWeatherDAO stationWeatherDAO;

    @Override
    public void saveAll(List<StationWeatherPerYear> stationWeatherPerYears) {
      stationWeatherDAO.saveAll(stationWeatherPerYears);
    }
}
