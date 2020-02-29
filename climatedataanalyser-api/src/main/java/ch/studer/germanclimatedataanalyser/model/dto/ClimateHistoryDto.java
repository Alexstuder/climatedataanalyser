package ch.studer.germanclimatedataanalyser.model.dto;

import ch.studer.germanclimatedataanalyser.model.database.StationClimate;

import java.util.ArrayList;
import java.util.List;

public class ClimateHistoryDto {

    static int CLIMATE_PERIOD = 30 ;
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

    public List<ClimateHistoryDto> getClimateHistory(String originYear, List<StationClimate> stationClimates) {

        //Build the ClimateHistoryDtos
        List<ClimateHistoryDto> climateHistoryDtos = new ArrayList<ClimateHistoryDto>();
        //Just to Collect all Records and get the average temperature
        List<StationClimate> tmpClimateHistory ;

        int noResult = 0 ;
        int formerSize = 0 ;
        int year = Integer.valueOf(originYear);

        // Just to make sure that we didnt hit just a hole ! Do it 5 times !
        // anyway 5 x 30 year = 1870 ; before this date we should not care about climate history
        while(noResult <= 5){

            tmpClimateHistory = new ArrayList<StationClimate>();
            for (StationClimate stationClimate : stationClimates){
                if (stationClimate.getEndPeriod().contentEquals(String.valueOf(year))){
                    tmpClimateHistory.add(stationClimate);
                }
            }
            if (tmpClimateHistory.size()>0){
                climateHistoryDtos.add(new ClimateHistoryDto().mapFrom(new StationClimate().getAverage(tmpClimateHistory)));
            }


            //check if there was noResult in this run !
            if (formerSize == tmpClimateHistory.size()){
                noResult++;
            }
            //store the actual Size in formerSize
            formerSize = tmpClimateHistory.size();
            year = year - CLIMATE_PERIOD ;

        }
        return climateHistoryDtos;
    }

    private ClimateHistoryDto mapFrom(StationClimate stationClimate) {
        ClimateHistoryDto climateHistoryDto = new ClimateHistoryDto();

        climateHistoryDto.setEndPeriod(stationClimate.getEndPeriod());
        climateHistoryDto.setStartPeriod(stationClimate.getStartPeriod());
        climateHistoryDto.setClimates(new ClimateAnalyserTempDto().mapFrom(stationClimate));


        return climateHistoryDto;
    }
}
