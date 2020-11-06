package ch.studer.germanclimatedataanalyser.model.dto;

import ch.studer.germanclimatedataanalyser.model.dto.helper.GpsPoint;

import java.util.ArrayList;
import java.util.List;

public class ClimateAnalyserResponseDto {


    private String originYear;

    private String yearToCompare;

    private String bundesland;

    private GpsPoint gps1;

    private GpsPoint gps2;

    private ClimateAnalyserTempDto original;

    private ClimateAnalyserTempDto compare;

    private List<ClimateHistoryDto> climateHistoryAverageDtos;

    private String errorMsg;

    public ClimateAnalyserResponseDto() {

        originYear = "";
        yearToCompare = "";
        bundesland = "";
        gps1 = new GpsPoint();
        gps2 = new GpsPoint();
        original = new ClimateAnalyserTempDto();
        compare = new ClimateAnalyserTempDto();
        climateHistoryAverageDtos = new ArrayList<ClimateHistoryDto>();
        errorMsg = "";
    }

    public void setYearToCompare(String yearToCompare) {
        this.yearToCompare = yearToCompare;
    }

    public void setOriginYear(String originYear) {
        this.originYear = originYear;
    }

    public void setBundesland(String bundesland) {
        this.bundesland = bundesland;
    }

    public void setOriginal(ClimateAnalyserTempDto original) {
        this.original = original;
    }

    public void setCompare(ClimateAnalyserTempDto compare) {
        this.compare = compare;
    }

    public String getOriginYear() {
        return originYear;
    }

    public String getYearToCompare() {
        return yearToCompare;
    }

    public String getBundesland() {
        return bundesland;
    }

    public ClimateAnalyserTempDto getOriginal() {
        return original;
    }

    public ClimateAnalyserTempDto getCompare() {
        return compare;
    }

    public GpsPoint getGps1() {
        return gps1;
    }

    public void setGps1(GpsPoint gps1) {
        this.gps1 = gps1;
    }

    public GpsPoint getGps2() {
        return gps2;
    }

    public void setGps2(GpsPoint gps2) {
        this.gps2 = gps2;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public List<ClimateHistoryDto> getClimateHistoryAverageDtos() {
        return climateHistoryAverageDtos;
    }

    public void setClimateHistoryAverageDtos(List<ClimateHistoryDto> climateHistoryAverageDtos) {
        this.climateHistoryAverageDtos = climateHistoryAverageDtos;
    }
}
