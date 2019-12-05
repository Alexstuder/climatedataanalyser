package ch.studer.germanclimatedataanalyser.model;

import ch.studer.germanclimatedataanalyser.service.StationService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

public class ClimateOLDAtArea extends Climate_OLD {

    @Autowired
    StationService stationService;



    private Point upper_left;
    private Point down_right;

    public ClimateOLDAtArea(List<String> bundeslaender) {

        List<Station> stations = new ArrayList<Station>();

        // Get all Stations from Bundeslaender
        if(bundeslaender.size()>0){
          for(String bundesland : bundeslaender){
              stations.addAll(stationService.getStationsFromBundesland(bundesland));
          }
        }

        List<ClimateOLDAtStation> climateAtStations = new ArrayList<ClimateOLDAtStation>();
        for(Station station : stations){
            ClimateOLDAtStation climateAtStation = new ClimateOLDAtStation();
            climateAtStation.getNewClimateAtStation(station.getStationName());
            climateAtStations.add(climateAtStation);
        }

        List<ClimateRecord_OLD> climateRecordOLDS = new ArrayList<ClimateRecord_OLD>();
        for (ClimateOLDAtStation climateAtStation : climateAtStations){
            climateRecordOLDS.addAll(climateAtStation.getClimateRecordOLDS());
        }

        // Set ClimatRecords and get the Diffenrences from it
        //TODO Die Liste muss noch vorerst aggregiert werden !
        // Nach Clima Jahr !?
        super.setClimate(climateRecordOLDS);


    }

    public ClimateOLDAtArea(Point upper_left, Point down_right) {
        this.upper_left = upper_left;
        this.down_right = down_right;
    }


}
