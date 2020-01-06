package ch.studer.germanclimatedataanalyser.model.dto;

public class ClimateAnalyserDto {


    private String year;

    private String bundesland;

    private ClimateAnalyserOneTemp mostClimateAnalyseData;

    private ClimateAnalyserOneTemp climateAnalyseDataByStationId;

    public ClimateAnalyserDto(){
        mostClimateAnalyseData = new ClimateAnalyserOneTemp();
        climateAnalyseDataByStationId = new ClimateAnalyserOneTemp();
    }

    public void setYear(String year) {
        this.year = year;
    }

    public void setBundesland(String bundesland) {
        this.bundesland = bundesland;
    }

    public void setMostClimateAnalyseData(ClimateAnalyserOneTemp mostClimateAnalyseData) {
        this.mostClimateAnalyseData = mostClimateAnalyseData;
    }

    public void setClimateAnalyseDataByStationId(ClimateAnalyserOneTemp climateAnalyseDataByStationId) {
        this.climateAnalyseDataByStationId = climateAnalyseDataByStationId;
    }

    public String getYear() {
        return year;
    }

    public String getBundesland() {
        return bundesland;
    }

    public ClimateAnalyserOneTemp getMostClimateAnalyseData() {
        return mostClimateAnalyseData;
    }

    public ClimateAnalyserOneTemp getClimateAnalyseDataByStationId() {
        return climateAnalyseDataByStationId;
    }
}
