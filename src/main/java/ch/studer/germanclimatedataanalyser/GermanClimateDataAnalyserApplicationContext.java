package ch.studer.germanclimatedataanalyser;

import ch.studer.germanclimatedataanalyser.model.ClimateAtStation;
import ch.studer.germanclimatedataanalyser.service.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GermanClimateDataAnalyserApplicationContext {

    @Bean
    TemperaturesAtStationService temperaturesAtStationService(){
        return new TemperaturesAtStationServiceImpl();
    }

    @Bean
    ClimateService climateService(){return new ClimateServiceImpl();
    }

    @Bean
    MonthService monthService(){
        return new MonthServiceImpl();
    }
}
