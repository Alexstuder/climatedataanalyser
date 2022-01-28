package ch.studer.germanclimatedataanalyser.batch.config;

import ch.studer.germanclimatedataanalyser.batch.processor.WeatherProcessor;
import ch.studer.germanclimatedataanalyser.batch.reader.MonthReader;
import ch.studer.germanclimatedataanalyser.batch.writer.WeatherWriter;
import ch.studer.germanclimatedataanalyser.model.database.Month;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.Transactional;

@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
@Configuration
public class WeatherBatchStepDefinition {

    @Autowired
    private JobBuilderFactory jobBuilderFactoryImport;

    @Autowired
    private StepBuilderFactory stepBuilderFactoryImport;


    @Autowired
    private MonthReader monthReader;

    @Bean
    @StepScope
    public WeatherProcessor weatherProcessor() {
        return new WeatherProcessor();
    }

    @Bean
    @StepScope
    public WeatherWriter weatherWriter() {
        return new WeatherWriter();
    }

    @Transactional
    @Bean("importWeatherRecords")
    public Step importWeatherRecords() {
        return stepBuilderFactoryImport.get("import-weather-records")
                .<Month, Month>chunk(100)
                //.reader(temperatureFromDbReader())
                .reader(monthReader.getMonthFromDbReader())
                //.listener(new StepProcessorListener(statistics()))
                .processor(weatherProcessor())
                //.listener(new StepWriterListener(statistics()))
                .writer(weatherWriter())
                .build()
                ;
    }

}
