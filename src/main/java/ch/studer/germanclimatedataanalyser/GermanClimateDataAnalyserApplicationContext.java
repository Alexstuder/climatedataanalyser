package ch.studer.germanclimatedataanalyser;

import ch.studer.germanclimatedataanalyser.batch.reader.MonthReader;
import ch.studer.germanclimatedataanalyser.batch.reader.WeatherReader;
import ch.studer.germanclimatedataanalyser.batch.tasklet.DbCheck;
import ch.studer.germanclimatedataanalyser.model.ClimateOLDAtStation;
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

    //TODO REMOVE OLD
    @Bean
    ClimateService_OLD climateServiceOld(){return new ClimateServiceOLDImpl();
    }

    @Bean
    MonthService monthService(){
        return new MonthServiceImpl();
    }

    @Bean
    StationService stationService() {return new StationServiceImpl();}

    @Bean
    ClimateOLDAtStation climateAtStation() {return new ClimateOLDAtStation();}

    @Bean
    MonthReader monthReader() {return new MonthReader();}

    @Bean
    WeatherReader weatherReader() {return new WeatherReader();}

    @Bean
    public DbCheck dbCheck(){return new DbCheck();}
}
