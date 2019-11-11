package ch.studer.germanclimatedataanalyser;

import ch.studer.germanclimatedataanalyser.service.ClimateAtStationService;
import ch.studer.germanclimatedataanalyser.service.ClimateAtStationServiceImpl;
import ch.studer.germanclimatedataanalyser.service.MonthService;
import ch.studer.germanclimatedataanalyser.service.MonthServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GermanClimateDataAnalyserApplicationContext {

    @Bean
    ClimateAtStationService climateAtStationService(){
        return new ClimateAtStationServiceImpl();
    }

    @Bean
    MonthService monthService(){
        return new MonthServiceImpl();
    }
}
