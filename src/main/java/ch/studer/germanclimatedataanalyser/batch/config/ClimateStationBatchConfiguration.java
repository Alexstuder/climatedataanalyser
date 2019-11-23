package ch.studer.germanclimatedataanalyser.batch.config;

import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ClimateStationBatchConfiguration {

    @Autowired
    public JobBuilderFactory jobBuilderFactory;


}
