package ch.studer.germanclimatedataanalyser;

import ch.studer.germanclimatedataanalyser.batch.reader.MonthReader;
import ch.studer.germanclimatedataanalyser.batch.reader.WeatherReader;
import ch.studer.germanclimatedataanalyser.service.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GermanClimateDataAnalyserApplicationContext {

    @Bean
    ClimateService climateService(){return new ClimateServiceImpl();
    }

    @Bean
    MonthService monthService(){
        return new MonthServiceImpl();
    }

    @Bean
    StationService stationService() {return new StationServiceImpl();}

    @Bean
    MonthReader monthReader() {return new MonthReader();}

    @Bean
    WeatherReader weatherReader() {return new WeatherReader();}
}
