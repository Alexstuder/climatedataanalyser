package ch.studer.germanclimatedataanalyser.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class TemperatureByStationId {

    int stationId;
    List<TemperatureRecord> temperatureRecordList;

    private static final Logger log = LoggerFactory.getLogger(TemperatureByStationId.class);


    public TemperatureByStationId(int stationId) {
        this.stationId = stationId;
        this.temperatureRecordList = new ArrayList<TemperatureRecord>();
    }


    public int getStationId() {
        return stationId;
    }

    public void setStationId(int stationId) {
        this.stationId = stationId;
    }

    public List<TemperatureRecord> getTemperatureRecordList() {
        return temperatureRecordList;
    }

    public void setTemperatureRecordList(List<TemperatureRecord> temperatureRecordList) {
        this.temperatureRecordList = temperatureRecordList;
    }
}