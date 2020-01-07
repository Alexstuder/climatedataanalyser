package ch.studer.germanclimatedataanalyser.model.dto;

public class ClimateAnalyserDto {


    private String year;

    private String bundesland;

    private ClimateAnalyserTempDto mostClimateAnalyseData;

    private ClimateAnalyserTempDto climateAnalyseDataByStationId;

    public ClimateAnalyserDto(){
        mostClimateAnalyseData = new ClimateAnalyserTempDto();
        climateAnalyseDataByStationId = new ClimateAnalyserTempDto();
    }

    public void setYear(String year) {
        this.year = year;
    }

    public void setBundesland(String bundesland) {
        this.bundesland = bundesland;
    }

    public void setMostClimateAnalyseData(ClimateAnalyserTempDto mostClimateAnalyseData) {
        this.mostClimateAnalyseData = mostClimateAnalyseData;
    }

    public void setClimateAnalyseDataByStationId(ClimateAnalyserTempDto climateAnalyseDataByStationId) {
        this.climateAnalyseDataByStationId = climateAnalyseDataByStationId;
    }

    public String getYear() {
        return year;
    }

    public String getBundesland() {
        return bundesland;
    }

    public ClimateAnalyserTempDto getMostClimateAnalyseData() {
        return mostClimateAnalyseData;
    }

    public ClimateAnalyserTempDto getClimateAnalyseDataByStationId() {
        return climateAnalyseDataByStationId;
    }
}
