package ch.studer.germanclimatedataanalyser.model;

import java.util.List;

public class ClimateAtStation {


    // StationsId
    private int stationsId;

    // All ClimateRecords for a Station
    private List<ClimateRecord> climateRecords ;

    // ####################################
    // # Constructor
    // ####################################

    public ClimateAtStation(int stationsId){
        this.stationsId = stationsId;
    }


    // ###################################
    // # Getter and Setter
    // ###################################
    public int getStationsId() {
        return stationsId;
    }

    public void setStationsId(int stationsId) {
        this.stationsId = stationsId;
    }

    public List<ClimateRecord> getClimateRecords() {
        return climateRecords;
    }

    public void setClimateRecords(List<ClimateRecord> climateRecords) {
        this.climateRecords = climateRecords;
    }

}
