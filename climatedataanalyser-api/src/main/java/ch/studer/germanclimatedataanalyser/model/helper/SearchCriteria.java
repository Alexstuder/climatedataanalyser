package ch.studer.germanclimatedataanalyser.model.helper;

import java.util.ArrayList;
import java.util.List;

public class SearchCriteria {

    String yearWithMostRecords;
    List<Integer> stationId;


    public SearchCriteria() {
        this.yearWithMostRecords = "0";
        this.stationId = new ArrayList<Integer>();
    }

    public String getYearWithMostRecords() {
        return yearWithMostRecords;
    }

    public void setYearWithMostRecords(String yearWithMostRecords) {
        this.yearWithMostRecords = yearWithMostRecords;
    }

    public List<Integer> getStationId() {
        return stationId;
    }

    public void setStationId(List<Integer> stationId) {
        this.stationId = stationId;
    }
}
