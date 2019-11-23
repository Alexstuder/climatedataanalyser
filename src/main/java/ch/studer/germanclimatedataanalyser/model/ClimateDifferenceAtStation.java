package ch.studer.germanclimatedataanalyser.model;

import java.util.ArrayList;
import java.util.List;

public class ClimateDifferenceAtStation {

    private final int stationId ;


    private List<ClimateDifference> climateDifferences;


    public ClimateDifferenceAtStation(int stationId) {
        this.stationId = stationId;
        this.climateDifferences = new ArrayList<ClimateDifference>();
    }

    public int getStationId() {
        return stationId;
    }

    public List<ClimateDifference> getClimateDifferences() {
        return climateDifferences;
    }

    public void setClimateDifferences(List<ClimateDifference> climateDifferences) {
        this.climateDifferences = climateDifferences;
    }
}
