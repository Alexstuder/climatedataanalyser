package ch.studer.germanclimatedataanalyser.model.dto;

import ch.studer.germanclimatedataanalyser.model.dto.helper.GpsPoint;

public class ClimateAnalyserRequestDto {

    // Defines the Bundesland to Compare
    private String bundesland;

    // Defines the GPS Coordinates
    private GpsPoint gps1;
    private GpsPoint gps2;


    // This Year will be Compared with all StationsID against the StationsId that exists in
    // following parameter : yearToCompare
    private String yearOrigine;
    private String yearToCompare;

    public ClimateAnalyserRequestDto() {
        // init every Request Parameter
        this.bundesland = "";
        this.gps1 = new GpsPoint();
        this.gps2 = new GpsPoint();
        this.yearOrigine = "";
        this.yearToCompare = "";
    }

    public String getBundesland() {
        return bundesland;
    }

    public void setBundesland(String bundesland) {
        this.bundesland = bundesland;
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

    public String getYearOrigine() {
        return yearOrigine;
    }

    public void setYearOrigine(String yearOrigine) {
        this.yearOrigine = yearOrigine;
    }

    public String getYearToCompare() {
        return yearToCompare;
    }

    public void setYearToCompare(String yearToCompare) {
        this.yearToCompare = yearToCompare;
    }
}
