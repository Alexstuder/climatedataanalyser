package ch.studer.germanclimatedataanalyser.model;

import ch.studer.germanclimatedataanalyser.service.StationService;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class ClimateAtArea extends Climate{

    @Autowired
    StationService stationService;



    private Point upper_left;
    private Point down_right;

    public ClimateAtArea(List<String> bundeslaender) {

        List<Station> stations = new ArrayList<Station>();

        // Get all Stations from Bundeslaender
        if(bundeslaender.size()>0){
          for(String bundesland : bundeslaender){
              stations.addAll(stationService.getStationsFromBundesland(bundesland));
          }
        }

        List<ClimateAtStation> climateAtStations = new ArrayList<ClimateAtStation>();
        for(Station station : stations){
            ClimateAtStation climateAtStation = new ClimateAtStation();
            climateAtStation.getNewClimateAtStation(station.getStationName());
            climateAtStations.add(climateAtStation);
        }

        List<ClimateRecord> climateRecords = new ArrayList<ClimateRecord>();
        for (ClimateAtStation climateAtStation : climateAtStations){
            climateRecords.addAll(climateAtStation.getClimateRecords());
        }

        // Set ClimatRecords and get the Diffenrences from it
        super.setClimate(climateRecords);


    }

    public ClimateAtArea(Point upper_left, Point down_right) {
        this.upper_left = upper_left;
        this.down_right = down_right;
    }


}
