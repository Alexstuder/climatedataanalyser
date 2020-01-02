package ch.studer.germanclimatedataanalyser.batch.processor;

import ch.studer.germanclimatedataanalyser.model.database.Month;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;

public class WeatherProcessor implements ItemProcessor<Month, Month> {


    private static final Logger log = LoggerFactory.getLogger(WeatherProcessor.class);


    @Override
    public Month process(Month raw) throws Exception {

        // At this time ; there is no difference between in and output record !

        return raw;
    }

}
