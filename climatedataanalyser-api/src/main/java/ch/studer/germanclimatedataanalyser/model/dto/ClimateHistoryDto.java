package ch.studer.germanclimatedataanalyser.model.dto;

public class ClimateHistoryDto {

    private String endPeriod;

    private String startPeriod;

    private ClimateAnalyserTempDto climates;

    public ClimateHistoryDto(){

        // inits all MonthTemp with 0 ;
        climates = new ClimateAnalyserTempDto() ;

    }


    public String getEndPeriod() {
        return endPeriod;
    }

    public void setEndPeriod(String endPeriod) {
        this.endPeriod = endPeriod;
    }

    public String getStartPeriod() {
        return startPeriod;
    }

    public void setStartPeriod(String startPeriod) {
        this.startPeriod = startPeriod;
    }

    public ClimateAnalyserTempDto getClimates() {
        return climates;
    }

    public void setClimates(ClimateAnalyserTempDto climates) {
        this.climates = climates;
    }
}
