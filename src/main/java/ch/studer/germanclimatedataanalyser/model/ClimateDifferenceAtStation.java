package ch.studer.germanclimatedataanalyser.model;

import java.util.ArrayList;
import java.util.List;

public class ClimateDifferenceAtStation {

    private final Station  station ;


    private List<ClimateDifference> climateDifferences;


    public ClimateDifferenceAtStation(Station station) {
        this.station = station;
        this.climateDifferences = new ArrayList<ClimateDifference>();
    }

    public Station getStation() {
        return station;
    }

    public List<ClimateDifference> getClimateDifferences() {
        return climateDifferences;
    }

    public void setClimateDifferences(List<ClimateDifference> climateDifferences) {
        this.climateDifferences = climateDifferences;
    }
}
