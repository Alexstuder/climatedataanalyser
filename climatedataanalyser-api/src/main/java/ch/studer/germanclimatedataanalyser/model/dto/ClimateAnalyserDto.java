package ch.studer.germanclimatedataanalyser.model.dto;

public class ClimateAnalyserDto {


    private String year;

    private String bundesland;

    private ClimateAnalyserTempDto original;

    private ClimateAnalyserTempDto compare;

    public ClimateAnalyserDto(){
        original = new ClimateAnalyserTempDto();
        compare = new ClimateAnalyserTempDto();
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
}
