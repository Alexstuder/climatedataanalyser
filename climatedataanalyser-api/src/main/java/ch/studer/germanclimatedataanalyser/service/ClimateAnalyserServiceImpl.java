package ch.studer.germanclimatedataanalyser.service;

import ch.studer.germanclimatedataanalyser.model.database.StationClimate;
import ch.studer.germanclimatedataanalyser.model.dto.ClimateAnalyserDto;
import ch.studer.germanclimatedataanalyser.model.dto.ClimateAnalyserOneTemp;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class ClimateAnalyserServiceImpl implements ClimateAnalyserService{

    @Autowired
    ClimateService climateService;

    @Override
    public ClimateAnalyserDto getClimateAnalyserForBundesland(String bundesland) {
        ClimateAnalyserDto climateAnalyserDto = new ClimateAnalyserDto();


        climateAnalyserDto.setBundesland(bundesland);

        calculateDifferenceClimate(climateAnalyserDto,climateService.getClimateForBundesland(bundesland));

        return climateAnalyserDto;

    }

    private void calculateDifferenceClimate(ClimateAnalyserDto climateAnalyserDto, List<StationClimate> climateForBundesland) {

      climateAnalyserDto.setNewstClimateAnalyseData(getDataOnlyWithTheExistingToday(climateForBundesland));

      climateAnalyserDto.setMostClimateAnalyseData(getDataWithMostRecievedRecords(climateForBundesland));


    }

    private ClimateAnalyserOneTemp getDataWithMostRecievedRecords(List<StationClimate> climateForBundesland) {
        ClimateAnalyserOneTemp mostRecievedRecords = new ClimateAnalyserOneTemp();


        return  mostRecievedRecords;
    }

    private ClimateAnalyserOneTemp getDataOnlyWithTheExistingToday(List<StationClimate> climateForBundesland) {
        ClimateAnalyserOneTemp onlyExistingToday = new ClimateAnalyserOneTemp();


        return  onlyExistingToday;
    }
}
