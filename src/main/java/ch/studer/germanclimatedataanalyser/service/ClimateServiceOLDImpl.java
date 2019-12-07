package ch.studer.germanclimatedataanalyser.service;

import ch.studer.germanclimatedataanalyser.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.util.ArrayList;
import java.util.List;


public class ClimateServiceOLDImpl implements ClimateService_OLD {


    @Autowired
    TemperaturesAtStationService temperaturesAtStationService;

    @Value("#{new Integer('${climate.calculation.period.year}')}")
    int period;

    // Inludes all Cimate Records for a Station
    //ClimateOLDAtStation climateAtStation ;


    @Autowired
    StationService stationService;

    @Autowired
    ClimateOLDAtStation climateAtStation;

    private static final Logger log = LoggerFactory.getLogger(ClimateServiceOLDImpl.class);

    @Override
    public ClimateOLDAtStation getClimateAtStationId(String stationId) throws Exception{


        try{
            climateAtStation.getNewClimateAtStation(stationId);

        // Print all Climate_OLD Records
        climateAtStation.printClimateRecords();
        climateAtStation.printClimateDifferences();

        } catch (Exception e) {
             throw e;
        }


        return climateAtStation;
    }

    @Override
    public List<ClimateDifferenceAtStation> getClimateByBundesland(String bundesland) {
        List<ClimateDifferenceAtStation> climateDifferenceAtStations = new ArrayList<ClimateDifferenceAtStation>();

        // Get All Stations from Bundesland

        List<Station> stations = stationService.getStationsFromBundesland(bundesland);

        // Get all Climate_OLD Differences from Stations

        for(Station station : stations){
         //   climateDifferenceAtStations.add(getDifference(station.getStationId()));
        }

        return climateDifferenceAtStations;
    }

}
