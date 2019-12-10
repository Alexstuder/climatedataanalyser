package ch.studer.germanclimatedataanalyser.service;

import ch.studer.germanclimatedataanalyser.dao.StationClimateDAO;
import ch.studer.germanclimatedataanalyser.model.database.StationClimate;
import ch.studer.germanclimatedataanalyser.model.database.StationWeatherPerYear;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.math.BigDecimal;
import java.math.RoundingMode;
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

        int start = 0;
        int end = start + period;

        for(StationWeatherPerYear stationWeatherPerYear:stationWeatherPerYears){

            if(end < stationWeatherPerYears.size()){
                StationClimate stationClimate = new StationClimate(stationWeatherPerYears.get(start).getStationID());
                stationClimate.setEndPeriod(stationWeatherPerYears.get(start).getYear());
                stationClimate.setStartPeriod(stationWeatherPerYears.get(end-1).getYear());

                BigDecimal januar = new BigDecimal(0);
                for (int i =start ; i < end ; i ++ ){

                    januar = januar.add(stationWeatherPerYears.get(i).getJanuar());
                }
//                   stationClimate.getJanuar().add(stationWeatherPerYears.get(i).getJanuar());
//                   stationClimate.getFebruar().add(stationWeatherPerYears.get(i).getFebruar());
//                   stationClimate.getMaerz().add(stationWeatherPerYears.get(i).getMaerz());
//                   stationClimate.getApril().add(stationWeatherPerYears.get(i).getApril());
//                   stationClimate.getMai().add(stationWeatherPerYears.get(i).getMai());
//                   stationClimate.getJuni().add(stationWeatherPerYears.get(i).getJuni());
//                   stationClimate.getJuli().add(stationWeatherPerYears.get(i).getJuli());
//                   stationClimate.getAugust().add(stationWeatherPerYears.get(i).getAugust());
//                   stationClimate.getSeptember().add(stationWeatherPerYears.get(i).getSeptember());
//                   stationClimate.getOktober().add(stationWeatherPerYears.get(i).getOktober());
//                   stationClimate.getNovember().add(stationWeatherPerYears.get(i).getNovember());
//                   stationClimate.getDezember().add(stationWeatherPerYears.get(i).getDezember());
                stationClimate.setJanuar(januar.divide(BigDecimal.valueOf(period),3 , RoundingMode.HALF_UP));
                stationClimates.add(stationClimate);
                start++;
                end = start + period;
            }
        }

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
