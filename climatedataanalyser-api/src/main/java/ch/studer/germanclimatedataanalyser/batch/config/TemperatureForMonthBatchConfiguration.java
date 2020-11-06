package ch.studer.germanclimatedataanalyser.batch.config;

import ch.studer.germanclimatedataanalyser.batch.listener.StepProcessorListener;
import ch.studer.germanclimatedataanalyser.batch.listener.StepWriterListener;
import ch.studer.germanclimatedataanalyser.batch.processor.TemperatureForMonthProcessor;
import ch.studer.germanclimatedataanalyser.batch.writer.TemperatureForMonthDBWriter;
import ch.studer.germanclimatedataanalyser.model.database.Month;
import ch.studer.germanclimatedataanalyser.model.file.MonthFile;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.MultiResourceItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;

@Configuration
public class TemperatureForMonthBatchConfiguration {

    @Autowired
    private JobBuilderFactory jobBuilderFactoryImport;

    @Autowired
    private StepBuilderFactory stepBuilderFactoryImport;

    @Value("${climate.path.temperature.input.file.pattern}")
    private String inputFilePattern;

    @Value("${climate.path.inputFolderName}")
    private String inputDirectory;

    static final private String CLASSPATH = "classpath*:";

    @Bean
    @StepScope
    public MultiResourceItemReader<MonthFile> monthFilesReader() {
        Resource[] inputResources = null;
        FileSystemXmlApplicationContext patternResolver = new FileSystemXmlApplicationContext();
        try {
            //inputResources = patternResolver.getResources("classpath*:/"+ "InputFiles/produkt*.txt");
            //.getResources("classpath*:/"+ directory+"/"+classifier);
            inputResources = patternResolver.getResources(CLASSPATH + "/" + inputDirectory + "/" + inputFilePattern);
        } catch (IOException e) {
            e.printStackTrace();
        }
        MultiResourceItemReader<MonthFile> resourceItemReader = new MultiResourceItemReader<MonthFile>();
        resourceItemReader.setResources(inputResources);

        resourceItemReader.setDelegate(reader());
        return resourceItemReader;
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    @Bean
    @StepScope
    public FlatFileItemReader<MonthFile> reader() {
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

                        setNames("stationsId"
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
                                , "eor");
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
    public TemperatureForMonthProcessor temperaturProcessor() {
        return new TemperatureForMonthProcessor();
    }

    @Bean
    @StepScope
    public TemperatureForMonthDBWriter monthWriter() {
        return new TemperatureForMonthDBWriter();
    }


    @Transactional
    @Bean
    public Step importTemperatureRecords() {
        return stepBuilderFactoryImport.get("import-temperature-records")
                .<MonthFile, Month>chunk(10000)
                .reader(monthFilesReader())
                .listener(new StepProcessorListener())
                .processor(temperaturProcessor())
                .listener(new StepWriterListener())
                .writer(monthWriter())
                .build()
                ;
    }


}
