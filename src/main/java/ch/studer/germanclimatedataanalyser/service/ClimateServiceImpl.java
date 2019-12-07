package ch.studer.germanclimatedataanalyser.service;

import ch.studer.germanclimatedataanalyser.dao.StationClimateDAO;
import ch.studer.germanclimatedataanalyser.model.ClimateDifferenceAtStation;
import ch.studer.germanclimatedataanalyser.model.ClimateOLDAtStation;
import ch.studer.germanclimatedataanalyser.model.Station;
import ch.studer.germanclimatedataanalyser.model.database.StationClimate;
import ch.studer.germanclimatedataanalyser.model.database.StationWeatherPerYear;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.util.ArrayList;
import java.util.List;


public class ClimateServiceImpl implements ClimateService {


    @Value("#{new Integer('${climate.calculation.period.year}')}")
    int period;

    // Inludes all Cimate Records for a Station
    //ClimateOLDAtStation climateAtStation ;


    @Autowired
    StationWeatherService stationWeatherService;

    @Autowired
    StationClimateDAO stationClimateDAO;

    private static final Logger log = LoggerFactory.getLogger(ClimateServiceImpl.class);


    @Override
    public List<StationClimate> getClimateForStation(List<StationWeatherPerYear> stationWeatherPerYears) {
        List<StationClimate> stationClimates = new ArrayList<StationClimate>();




//        // get the first StaionId
//        if(list.size()>0) {
//            int actualStationId = list.get(0).getStationId();
//
//            // Transform all WeatherRecords from 1 Station to a new separated List
//            // fill all the holes
//            // calculate the CLimaRecords
//            // Write into DB
//            List<StationWeatherPerYear> stationWeatherPerYears = new ArrayList<StationWeatherPerYear>();
//            for (StationWeatherPerYear stationWeatherPerYear : list) {
//
//                //add actual Weather Record to the stationWeathers List
//
//                if (actualStationId == stationWeatherPerYear.getStationId()) {
//                    stationWeatherPerYears.add(stationWeatherPerYear);
//
//                } else {
//
//                    // fill the holes
//                    List<StationWeatherPerYear> stationWeatherPerYearsFilledHoles = stationWeatherService.fillHoles(stationWeatherPerYears);
//
//                    // calculate the climate Record
//                    List<StationClimate> stationClimates = climateService.getClimateForStation(stationWeatherPerYearsFilledHoles);
//
//                    // write ClimateRecord to DB
//                    if (stationClimates.size() > 0) {
//                        climateService.saveAllClimateAtStationId(stationClimates);
//                    }
//                    // get a new WeatherList
//
//                    // fill the actual Record to weatherList
//                }
//                LOG.debug(" " + stationWeatherPerYear.getStationId());
//
//            }
//        } else {
//            LOG.debug("No WeatherRecords available to calculate Climate Records. List.size = {} ",list.size());
//        }





        return stationClimates;
    }

    @Override
    public void saveAllClimateAtStationId(List<StationClimate> stationClimates) {
       this.stationClimateDAO.saveAll(stationClimates);
    }
}
