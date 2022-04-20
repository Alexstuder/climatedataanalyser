package ch.studer.germanclimatedataanalyser.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DataAccess {

    @Autowired
    MonthDAO monthDAO;

    @Autowired
    StationDAO stationDAO;

    @Autowired
    StationClimateDAO stationClimateDAO;

    @Autowired
    StationWeatherDAO stationWeatherDAO;


    public MonthDAO getMonthDAO() {
        return monthDAO;
    }

    public StationDAO getStationDAO() {
        return stationDAO;
    }

    public StationClimateDAO getStationClimateDAO() {
        return stationClimateDAO;
    }

    public StationWeatherDAO getStationWeatherDAO() {
        return stationWeatherDAO;
    }
}
