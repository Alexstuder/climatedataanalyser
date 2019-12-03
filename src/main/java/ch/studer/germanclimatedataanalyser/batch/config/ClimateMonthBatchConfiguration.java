package ch.studer.germanclimatedataanalyser.batch.config;

import ch.studer.germanclimatedataanalyser.batch.listener.JobCompletionNotificationListener;
import ch.studer.germanclimatedataanalyser.batch.listener.StepProcessorListener;
import ch.studer.germanclimatedataanalyser.batch.listener.StepWriterListener;
import ch.studer.germanclimatedataanalyser.batch.processor.ClimateMonthProcessor;
import ch.studer.germanclimatedataanalyser.batch.processor.StationProcessor;
import ch.studer.germanclimatedataanalyser.batch.processor.WeatherProcessor;
import ch.studer.germanclimatedataanalyser.batch.reader.MonthReader;
import ch.studer.germanclimatedataanalyser.batch.tasklet.ClimateFtpDataDownloader;
import ch.studer.germanclimatedataanalyser.batch.tasklet.ClimateFtpDataUnziper;
import ch.studer.germanclimatedataanalyser.batch.tasklet.DbCheck;
import ch.studer.germanclimatedataanalyser.batch.writer.ClimateMonthDBWriter;
import ch.studer.germanclimatedataanalyser.batch.writer.StationDBWriter;
import ch.studer.germanclimatedataanalyser.batch.writer.WeatherWriter;
import ch.studer.germanclimatedataanalyser.common.Statistic;
import ch.studer.germanclimatedataanalyser.common.StatisticImpl;
import ch.studer.germanclimatedataanalyser.model.*;
import ch.studer.germanclimatedataanalyser.model.file.MonthFile;
import ch.studer.germanclimatedataanalyser.model.file.StationFile;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.configuration.annotation.*;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.batch.item.database.builder.JdbcCursorItemReaderBuilder;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.MultiResourceItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.batch.item.file.transform.FixedLengthTokenizer;
import org.springframework.batch.item.file.transform.Range;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;

@Configuration
@EnableBatchProcessing
@Import({WeatherBatchConfiguration.class,StationBatchConfiguration.class})
public class ClimateMonthBatchConfiguration {

    @Autowired
    private JobBuilderFactory jobBuilderFactoryImport;

    @Autowired
    private StepBuilderFactory stepBuilderFactoryImport;

//    @Autowired
//    private DataSource dataSource;
//
//    @Autowired
//    private MonthReader monthReader;

    @Autowired
    private WeatherBatchConfiguration weatherBatchConfiguration;

    @Autowired
    private StationBatchConfiguration stationBatchConfiguration;

    @Autowired
    private MonthTemperatureBatchConfiguration monthTemperatureBatchConfiguration;

    // # First Tasklet : Download the Files in specific Folder

    @Bean
    public ClimateFtpDataDownloader download() {

        return new ClimateFtpDataDownloader(); }

    @Transactional
    @Bean
    public Step downloadFiles(){
        return stepBuilderFactoryImport.get("download")
                .tasklet(download())
                .build();
    }
    // # Second Tasklet : Unzip the Files and move to the next folder
    @Bean
    public ClimateFtpDataUnziper unziper(){
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
    public Job importGermanClimateDataJob(JobCompletionNotificationListener listener){
        return jobBuilderFactoryImport.get("importGermanClimateDataJob")
               .incrementer(new RunIdIncrementer())
               .listener(listener)
               .start(downloadFiles())
               .next(unzipFiles())
               .next(monthTemperatureBatchConfiguration.importTemperatureRecords())
               .next(stationBatchConfiguration.importStations())
               .next(weatherBatchConfiguration.importWeatherRecords())
               .build()
                ;
    }
}



