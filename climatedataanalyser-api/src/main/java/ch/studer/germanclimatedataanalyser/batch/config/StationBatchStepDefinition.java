package ch.studer.germanclimatedataanalyser.batch.config;

import ch.studer.germanclimatedataanalyser.batch.processor.StationProcessor;
import ch.studer.germanclimatedataanalyser.batch.tasklet.DirectoryUtilityImpl;
import ch.studer.germanclimatedataanalyser.batch.writer.StationDBWriter;
import ch.studer.germanclimatedataanalyser.model.database.Station;
import ch.studer.germanclimatedataanalyser.model.file.StationFile;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.FixedLengthTokenizer;
import org.springframework.batch.item.file.transform.Range;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.transaction.annotation.Transactional;

import java.io.FileNotFoundException;

@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
@Configuration
public class StationBatchStepDefinition {

    @Autowired
    private StepBuilderFactory stepBuilderFactoryImport;

    @Value("${climate.path.ftpDataFolderName}")
    private String ftpDirectoryName;

    @Value("${climate.path.station.input.file.pattern}")
    private String stationFileName;

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

        tokenizer.setNames("stationsId", "dateBegin", "dateEnd", "stationHigh", "geoLatitude", "geoLength", "stationName", "bundesLand");
        tokenizer.setColumns(new Range(1, 5),
                new Range(6, 14),
                new Range(15, 23),
                new Range(24, 38),
                new Range(39, 50),
                new Range(51, 60),
                new Range(61, 102),
                new Range(103, 200)
        );
        return tokenizer;
    }

    @Bean
    @StepScope
    public FlatFileItemReader<StationFile> readerStation() {
        try {
            // Get the File as Resource object
            Resource inputResource = DirectoryUtilityImpl.getResource(DirectoryUtilityImpl.getDirectory(ftpDirectoryName), stationFileName);

            //Create reader instance
            FlatFileItemReader<StationFile> reader = new FlatFileItemReader<StationFile>();

            // There should be only One File ! So take the first one !
            reader.setResource(inputResource);

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

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    @Transactional
    @Bean
    public Step importStations() {
        return stepBuilderFactoryImport.get("import-station-records")
                .<StationFile, Station>chunk(100)
                .reader(readerStation())
                .processor(stationProcessor())
                .writer(stationWriter())
                .build()
                ;
    }
}
