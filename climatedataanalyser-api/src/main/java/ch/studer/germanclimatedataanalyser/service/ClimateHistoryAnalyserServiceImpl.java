package ch.studer.germanclimatedataanalyser.service;

import ch.studer.germanclimatedataanalyser.model.database.StationClimate;
import ch.studer.germanclimatedataanalyser.model.database.TemperatureForMonths;
import ch.studer.germanclimatedataanalyser.model.dto.ClimateHistoryDto;

import java.util.ArrayList;
import java.util.List;

public class ClimateHistoryAnalyserServiceImpl implements ClimateHistoryAnalyserService {

    @Override
    public List<ClimateHistoryDto> getClimateHistoryAverageData(String originYear, List<StationClimate> stationClimates) {
        //Build the ClimateHistoryDtos
        List<ClimateHistoryDto> climateHistoryDtos = new ArrayList<ClimateHistoryDto>();
        //Just for collecting  all Records and calculate the average temperature
        List<TemperatureForMonths> tmpTemperatureForMonths ;

        int noResult = 0 ;
        String endPeriod = originYear;

        // Just to make sure that we didnt hit just a hole ! Do it 5 times !
        // anyway 6 x 30 endPeriod = 1840 ; before this date we should not care too much about climate history
        while(noResult <= 5){

            tmpTemperatureForMonths = new ArrayList<TemperatureForMonths>();
            ClimateHistoryDto climateHistoryDto = new ClimateHistoryDto();
            for (StationClimate stationClimate : stationClimates){

                if (stationClimate.getEndPeriod().contentEquals(endPeriod)){

                    if (climateHistoryDto.getEndPeriod().isEmpty()){
                        climateHistoryDto.setEndPeriod(stationClimate.getEndPeriod());
                        climateHistoryDto.setStartPeriod(stationClimate.getStartPeriod());
                    }
                    tmpTemperatureForMonths.add(stationClimate.getTemperatureForMonths());
                }
            }
            if (tmpTemperatureForMonths.size()>0){
                climateHistoryDto.setClimatesMapFrom(new TemperatureForMonths().getAverage(tmpTemperatureForMonths));
                climateHistoryDtos.add(climateHistoryDto);
            }


            //check if there was noResult in this run !
            if (tmpTemperatureForMonths.size() == 0 ) {
                noResult++;
            }

            //calculate the next period
            endPeriod = String.valueOf(Integer.parseInt(endPeriod) - 30);

        }
        return climateHistoryDtos;
    }

    @Override
    public List<ClimateHistoryDto> getClimateHistoryEveryStationExistsData(String originYear, List<StationClimate> stationClimates) {
        return null;
    }
}
