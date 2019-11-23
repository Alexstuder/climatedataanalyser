package ch.studer.germanclimatedataanalyser.batch.config;

import ch.studer.germanclimatedataanalyser.batch.listener.JobCompletionNotificationListener;
import ch.studer.germanclimatedataanalyser.batch.listener.StepProcessorListener;
import ch.studer.germanclimatedataanalyser.batch.listener.StepWriterListener;
import ch.studer.germanclimatedataanalyser.batch.processor.ClimateMonthProcessor;
import ch.studer.germanclimatedataanalyser.batch.processor.StationProcessor;
import ch.studer.germanclimatedataanalyser.batch.tasklet.ClimateFtpDataDownloader;
import ch.studer.germanclimatedataanalyser.batch.tasklet.ClimateFtpDataUnziper;
import ch.studer.germanclimatedataanalyser.batch.tasklet.DbCheck;
import ch.studer.germanclimatedataanalyser.batch.writer.ClimateMonthDBWriter;
import ch.studer.germanclimatedataanalyser.batch.writer.StationDBWriter;
import ch.studer.germanclimatedataanalyser.common.Statistic;
import ch.studer.germanclimatedataanalyser.common.StatisticImpl;
import ch.studer.germanclimatedataanalyser.model.Month;
import ch.studer.germanclimatedataanalyser.model.MonthFile;
import ch.studer.germanclimatedataanalyser.model.Station;
import ch.studer.germanclimatedataanalyser.model.StationFile;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.MultiResourceItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.batch.item.file.transform.FixedLengthTokenizer;
import org.springframework.batch.item.file.transform.Range;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.io.IOException;

@Configuration
@EnableBatchProcessing
public class ClimateMonthBatchConfiguration {

    @Autowired
    public JobBuilderFactory jobBuilderFactoryImport;

    @Autowired
    public StepBuilderFactory stepBuilderFactoryImport;

    @Autowired
    public DataSource dataSource;

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
    public MultiResourceItemReader<MonthFile> multiResourceItemReader()
    {
        Resource[] inputResources = null;
        FileSystemXmlApplicationContext patternResolver = new FileSystemXmlApplicationContext();
        try {
            inputResources = patternResolver.getResources("classpath*:/"+ "InputFiles/produkt*.txt");
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
    public FlatFileItemReader<MonthFile> reader()
    {
        //Create reader instance
        FlatFileItemReader<MonthFile> reader = new FlatFileItemReader<MonthFile>();

        //Set number of lines to skips. Use it if file has header rows.
        reader.setLinesToSkip(1);

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
    public ClimateMonthProcessor processor() {
        return new ClimateMonthProcessor();
    }

    @Bean
    @StepScope
    public ClimateMonthDBWriter writer() {
        return new ClimateMonthDBWriter();
    }

    @Bean
    public FlatFileItemReader<StationFile> readerStation()
    {
        //Create reader instance
        FlatFileItemReader<StationFile> reader = new FlatFileItemReader<StationFile>();

        //Set number of lines to skips. Use it if file has header rows.
        reader.setLinesToSkip(2);

        //Configure how each line will be parsed and mapped to different values
        reader.setLineMapper(new DefaultLineMapper() {
            {
                //3 columns in each row
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
        tokenizer.setColumns(new Range(1-5),
                new Range(7-14),
                new Range(16-23),
                new Range(24-38),
                new Range(39-50),
                new Range(51-60),
                new Range(62-102),
                new Range(103-160)
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
               .start(unzipFiles())
               .next(importTemperatureRecords())
               .next(importStations())
               .build()
                ;
    }
    @Transactional
    @Bean
    public Step importTemperatureRecords(){
        return stepBuilderFactoryImport.get("step01")
                .<MonthFile,Month> chunk(10)
                .reader(multiResourceItemReader())
                .listener(new StepProcessorListener(statistics()))
                .processor(processor())
                .listener(new StepWriterListener(statistics()))
                .writer(writer())
                .build()
                ;
    }

    @Transactional
    @Bean
    public Step importStations(){
        return stepBuilderFactoryImport.get("step01")
                .<StationFile, Station> chunk(10)
                .reader(readerStation())
                //.listener(new StepProcessorListener(statistics()))
                .processor(stationProcessor())
                //.listener(new StepWriterListener(statistics()))
                .writer(stationWriter())
                .build()
                ;
    }
}
