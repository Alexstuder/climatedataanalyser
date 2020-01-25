package ch.studer.germanclimatedataanalyser.model.dto;

import ch.studer.germanclimatedataanalyser.model.dto.helper.GpsPoint;

public class ClimateAnalyserResponseDto {


    private String year;

    private String bundesland;

    private GpsPoint gps1;

    private GpsPoint gps2;

    private ClimateAnalyserTempDto original;

    private ClimateAnalyserTempDto compare;

    private String errorMsg;

    public ClimateAnalyserResponseDto(){
        original = new ClimateAnalyserTempDto();
        compare = new ClimateAnalyserTempDto();
        errorMsg = "";
    }

    public void setYear(String year) {
        this.year = year;
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

    public String getYear() {
        return year;
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

    public GpsPoint getGps1() {return gps1;}

    public void setGps1(GpsPoint gps1) {this.gps1 = gps1;}

    public GpsPoint getGps2() {return gps2;}

    public void setGps2(GpsPoint gps2) {this.gps2 = gps2;}

    public String getErrorMsg() {return errorMsg;}

    public void setErrorMsg(String errorMsg) {this.errorMsg = errorMsg; }
}
