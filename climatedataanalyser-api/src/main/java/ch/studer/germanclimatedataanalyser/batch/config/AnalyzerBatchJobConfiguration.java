package ch.studer.germanclimatedataanalyser.batch.config;

import ch.studer.germanclimatedataanalyser.batch.listener.JobCompletionNotificationListener;
import ch.studer.germanclimatedataanalyser.batch.tasklet.ClimateFtpDataDownloader;
import ch.studer.germanclimatedataanalyser.batch.tasklet.ClimateFtpDataUnziper;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.Transactional;

@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
@Configuration
@EnableBatchProcessing
@Import({WeatherBatchStepDefinition.class, StationBatchStepDefinition.class})
public class AnalyzerBatchJobConfiguration {

    @Autowired
    private JobBuilderFactory jobBuilderFactoryImport;

    @Autowired
    private StepBuilderFactory stepBuilderFactoryImport;

    @Autowired
    private ClimateBatchStepDefinition climateBatchConfiguration;

    @Autowired
    private WeatherBatchStepDefinition weatherBatchConfiguration;

    @Autowired
    private StationBatchStepDefinition stationBatchConfiguration;

    @Autowired
    private TemperatureForMonthBatchConfiguration temperatureForMonthBatchConfiguration;

    // # First Tasklet : Download the Files in specific Folder

    @Bean
    public ClimateFtpDataDownloader download() {

        return new ClimateFtpDataDownloader();
    }

    @Transactional
    @Bean
    public Step downloadFiles() {
        return stepBuilderFactoryImport.get("download")
                .tasklet(download())
                .build();
    }

    // # Second Tasklet : Unzip the Files and move to the next folder
    @Bean
    public ClimateFtpDataUnziper unziper() {
        return new ClimateFtpDataUnziper();
    }

    @Transactional
    @Bean
    public Step unzipFiles() {
        return stepBuilderFactoryImport.get("unzipFiles")
                .tasklet(unziper())
                .build();
    }

    // ##################################################################################
    // # Job Definition
    // ##################################################################################

    @Bean
    public Job importGermanClimateDataJob(JobCompletionNotificationListener listener) {
        return jobBuilderFactoryImport.get("importGermanClimateDataJob")
                .incrementer(new RunIdIncrementer())
                .listener(listener)
                .start(downloadFiles())
                .next(unzipFiles())
                .next(temperatureForMonthBatchConfiguration.importTemperatureRecords())
                .next(stationBatchConfiguration.importStations())
                .next(weatherBatchConfiguration.importWeatherRecords())
                .next(climateBatchConfiguration.importClimateRecords())
                .build()
                ;
    }
}



