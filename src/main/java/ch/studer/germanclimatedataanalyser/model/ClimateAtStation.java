package ch.studer.germanclimatedataanalyser.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class ClimateAtStation {


    // StationsId
    private int stationId;

    // All ClimateRecords for a Station
    private List<ClimateRecord> climateRecords ;


    private static final Logger log = LoggerFactory.getLogger(ClimateAtStation.class);
    // ####################################
    // # Constructor
    // ####################################

    public ClimateAtStation(int stationId){
        this.stationId = stationId;
    }


    // ###################################
    // # Getter and Setter
    // ###################################
    public int getStationId() {
        return stationId;
    }

    public void setStationId(int stationId) {
        this.stationId = stationId;
    }

    public List<ClimateRecord> getClimateRecords() {
        return climateRecords;
    }

    public void setClimateRecords(List<ClimateRecord> climateRecords) {
        this.climateRecords = climateRecords;
    }
}
