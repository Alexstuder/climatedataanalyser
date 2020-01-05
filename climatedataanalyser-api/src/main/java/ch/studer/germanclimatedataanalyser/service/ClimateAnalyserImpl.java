package ch.studer.germanclimatedataanalyser.service;

import ch.studer.germanclimatedataanalyser.model.dto.ClimateAnalyserDto;
import org.springframework.beans.factory.annotation.Autowired;

public class ClimateAnalyserImpl implements ClimateAnalyserService{

    @Autowired
    ClimateService climateService;

    @Override
    public ClimateAnalyserDto getClimateAnalyserForBundesland(String bundesland) {
        ClimateAnalyserDto climateAnalyserDto = new ClimateAnalyserDto();

        climateService.getClimateForBundesland(bundesland);


        return climateAnalyserDto;
    }
}
