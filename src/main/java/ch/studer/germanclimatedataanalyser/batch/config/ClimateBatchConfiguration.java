package ch.studer.germanclimatedataanalyser.batch.config;

import ch.studer.germanclimatedataanalyser.batch.processor.ClimateProcessor;
import ch.studer.germanclimatedataanalyser.batch.reader.WeatherReader;
import ch.studer.germanclimatedataanalyser.batch.writer.ClimateWriter;
import ch.studer.germanclimatedataanalyser.model.database.StationTemperature;
import ch.studer.germanclimatedataanalyser.model.database.StationWeatherPerYear;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.Transactional;

@Configuration
public class ClimateBatchConfiguration {

    @Autowired
    private JobBuilderFactory jobBuilderFactoryImport;

    @Autowired
    private StepBuilderFactory stepBuilderFactoryImport;



    @Autowired
    private WeatherReader weatherReader;

    @Bean
    @StepScope
    public ClimateProcessor climateProcessor() {
        return new ClimateProcessor();
    }

    @Bean
    @StepScope
    public ClimateWriter climateWriter(){
        return new ClimateWriter();
    }

    @Transactional
    @Bean("importClimateRecords")
    public Step importClimateRecords(){
        return stepBuilderFactoryImport.get("import-weather-records")
                .<StationWeatherPerYear, StationWeatherPerYear> chunk(5000)
                //.reader(temperatureFromDbReader())
                .reader(weatherReader.getWeatherFromDbReader() )
                //.listener(new StepProcessorListener(statistics()))
                .processor(climateProcessor())
                //.listener(new StepWriterListener(statistics()))
                .writer(climateWriter())
                .build()
                ;
    }

}
