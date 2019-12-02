package ch.studer.germanclimatedataanalyser.batch.config;

import ch.studer.germanclimatedataanalyser.batch.listener.JobCompletionNotificationListener;
import ch.studer.germanclimatedataanalyser.batch.listener.StepProcessorListener;
import ch.studer.germanclimatedataanalyser.batch.listener.StepWriterListener;
import ch.studer.germanclimatedataanalyser.batch.processor.ClimateMonthProcessor;
import ch.studer.germanclimatedataanalyser.batch.processor.StationProcessor;
import ch.studer.germanclimatedataanalyser.batch.processor.WeatherProcessor;
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
public class ClimateMonthBatchConfiguration {

    @Autowired
    private JobBuilderFactory jobBuilderFactoryImport;

    @Autowired
    private StepBuilderFactory stepBuilderFactoryImport;

    @Autowired
    private DataSource dataSource;



    @Value("${climate.path.temperature.input.file.pattern}")
    private String inputFilePattern;

    @Value("${climate.path.inputFolderName}")
    private String inputDirectory;

    @Value("${climate.path.ftpDataFolderName}")
    private String FtpDirectory;

    @Value("${climate.path.station.input.file.pattern}")
    private String stationFileName;

    static final private String CLASSPATH = "classpath*:";

    @Bean
    public Statistic statistics(){
        return new StatisticImpl();
    }

    @Bean
    public DbCheck dbCheck(){return new DbCheck();
    }

    @Bean
    public ClimateFtpDataUnziper unziper(){
        return new ClimateFtpDataUnziper();
    }


    @Bean
    @StepScope
    public MultiResourceItemReader<MonthFile> monthFilesReader()
    {
        Resource[] inputResources = null;
        FileSystemXmlApplicationContext patternResolver = new FileSystemXmlApplicationContext();
        try {
            //inputResources = patternResolver.getResources("classpath*:/"+ "InputFiles/produkt*.txt");
            //.getResources("classpath*:/"+ directory+"/"+classifier);
            inputResources = patternResolver.getResources(CLASSPATH+"/"+ inputDirectory+"/"+ inputFilePattern);
        } catch (IOException e) {
            e.printStackTrace();
        }
        MultiResourceItemReader<MonthFile> resourceItemReader = new MultiResourceItemReader<MonthFile>();
        resourceItemReader.setResources(inputResources);

        resourceItemReader.setDelegate(reader());
        return resourceItemReader;
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    @Bean
    @StepScope
    public FlatFileItemReader<MonthFile> reader()
    {
        //Create reader instance
        FlatFileItemReader<MonthFile> reader = new FlatFileItemReader<MonthFile>();

        //Set number of lines to skips. Use it if file has header rows.
        reader.setLinesToSkip(1);
        reader.setEncoding("utf-8");

        //Configure how each line will be parsed and mapped to different values
        reader.setLineMapper(new DefaultLineMapper() {
            {
                //3 columns in each row
                setLineTokenizer(new DelimitedLineTokenizer() {

                    {

                        setNames(new String[]{"stationsId"
                                , "messDatumBeginn"
                                , "messDatumEnde"
                                , "qn4"
                                , "moN"
                                , "moTt"
                                , "moTx"
                                , "moTn"
                                , "moFk"
                                , "mxTx"
                                , "mxFx"
                                , "mxTn"
                                , "moSdS"
                                , "qn6"
                                , "moRr"
                                , "mxRs"
                                , "eor"});
                        setDelimiter(";");
                    }
                });
                //Set values in Employee class
                setFieldSetMapper(new BeanWrapperFieldSetMapper<MonthFile>() {
                    {
                        setTargetType(MonthFile.class);
                    }
                });
            }
        });
        return reader;
    }

    @Bean
    @StepScope
    public ClimateMonthProcessor temperaturProcessor() {
        return new ClimateMonthProcessor();
    }

    @Bean
    @StepScope
    public ClimateMonthDBWriter monthWriter() {
        return new ClimateMonthDBWriter();
    }

    @Bean
    @StepScope
    public FlatFileItemReader<StationFile> readerStation()
    {
        Resource[] inputResources = null;
        FileSystemXmlApplicationContext patternResolver = new FileSystemXmlApplicationContext();
        try {
            inputResources = patternResolver.getResources(CLASSPATH+"/"+FtpDirectory+"/"+stationFileName);
        } catch (IOException e) {
            e.printStackTrace();
        }

        //Create reader instance
        FlatFileItemReader<StationFile> reader = new FlatFileItemReader<StationFile>();

        // There should be only One File ! So take the first one !
        reader.setResource(inputResources[0]);

        //Set the right encoding for ANSI
        reader.setEncoding("Cp1252");

        //Set number of lines to skips. Use it if file has header rows.
        reader.setLinesToSkip(2);

        //Configure how each line will be parsed and mapped to different values
        reader.setLineMapper(new DefaultLineMapper() {
            {
                //
                setLineTokenizer(stationTokenizer());
                //Set values in Employee class
                setFieldSetMapper(new BeanWrapperFieldSetMapper<StationFile>() {
                    {
                        setTargetType(StationFile.class);
                    }
                });
            }
        });
        return reader;
    }

    @Bean
    @StepScope
    public StationProcessor stationProcessor() {
        return new StationProcessor();
    }

    @Bean
    @StepScope
    public StationDBWriter stationWriter() {
        return new StationDBWriter();
    }



    public FixedLengthTokenizer stationTokenizer() {
        FixedLengthTokenizer tokenizer = new FixedLengthTokenizer();

        tokenizer.setNames("stationsId", "dateBegin", "dateEnd", "stationHigh","geoLatitude","geoLength","stationName","bundesLand");
        tokenizer.setColumns(new Range(1,5),
                new Range(6,14),
                new Range(15,23),
                new Range(24,38),
                new Range(39,50),
                new Range(51,60),
                new Range(61,102),
                new Range(103,200)
        );
        return tokenizer;
    }


    // #############################################################################
    // # First Job : Download the Files in specific Folder
    // #############################################################################

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

    @Transactional
    @Bean
    public Step unzipFiles() {
        return stepBuilderFactoryImport.get("unzipFiles")
                .tasklet(unziper())
                .build();
    }

    // ##################################################################################
    // # Job 2. Unzip the Files and move the needed File into the InputFile folder
    // ##################################################################################

    @Bean
    public Job importClimateMonthDataJob(JobCompletionNotificationListener listener){
        return jobBuilderFactoryImport.get("importClimateMonthDataJob")
               .incrementer(new RunIdIncrementer())
               .listener(listener)
               //.start(downloadFiles())
               //.start(unzipFiles())
               //.start(importTemperatureRecords())
               // .next(importStations())
                .start(importWeatherRecords())
               .build()
                ;
    }
    @Transactional
    @Bean
    public Step importTemperatureRecords(){
        return stepBuilderFactoryImport.get("import-temperature-records")
                .<MonthFile,Month> chunk(10000)
                .reader(monthFilesReader())
                .listener(new StepProcessorListener(statistics()))
                .processor(temperaturProcessor())
                .listener(new StepWriterListener(statistics()))
                .writer(monthWriter())
                .build()
                ;
    }



    @Bean
    @StepScope
    public WeatherProcessor weatherProcessor() {
        return new WeatherProcessor();
    }

    @Bean@StepScope
    public WeatherWriter weatherWriter(){
        return new WeatherWriter();
    }

    @Transactional
    @Bean
    public Step importWeatherRecords(){
        return stepBuilderFactoryImport.get("import-weather-records")
                .<RawMonthTemperatureAtStationRecord, MonthTemperatureAtStationRecord> chunk(5000)
                .reader(temperatureFromDbReader())
                //.listener(new StepProcessorListener(statistics()))
                .processor(weatherProcessor())
                //.listener(new StepWriterListener(statistics()))
                .writer(weatherWriter())
                .build()
                ;
    }
    
    
    @Transactional
    @Bean
    public Step importStations(){
        return stepBuilderFactoryImport.get("import-station-records")
                .<StationFile, Station> chunk(100)
                .reader(readerStation())
                //.listener(new StepProcessorListener(statistics()))
                .processor(stationProcessor())
                //.listener(new StepWriterListener(statistics()))
                .writer(stationWriter())
                .build()
                ;
    }
    @Bean
    @StepScope
    public JdbcCursorItemReader<RawMonthTemperatureAtStationRecord> temperatureFromDbReader(){
        return new JdbcCursorItemReaderBuilder<RawMonthTemperatureAtStationRecord>()
                .dataSource(this.dataSource)
                .name("weatherReader")
                .sql("select STATIONS_ID,MESS_DATUM_BEGINN,MESS_DATUM_ENDE,MO_TT from MONTH")
                .rowMapper(new TemperatureRowMapper())
                .build();



    }

}
    class TemperatureRowMapper implements RowMapper<RawMonthTemperatureAtStationRecord> {

        final String STATIONS_ID = "STATIONS_ID";
        final String MESS_DATUM_BEGINN = "MESS_DATUM_BEGINN";
        final String MESS_DATUM_ENDE = "MESS_DATUM_ENDE";
        final String MO_TT = "MO_TT";

        @Override
        public RawMonthTemperatureAtStationRecord mapRow(ResultSet resultSet, int i) throws SQLException {
            RawMonthTemperatureAtStationRecord monthRecord = new RawMonthTemperatureAtStationRecord();

            monthRecord.setStationId(resultSet.getString(STATIONS_ID));
            monthRecord.setMessDatumBeginn(resultSet.getString(MESS_DATUM_BEGINN));
            monthRecord.setMessDatumEnd(resultSet.getString(MESS_DATUM_ENDE));
            monthRecord.setTemperatur(resultSet.getString(MO_TT));

            return monthRecord;
        }
    }


