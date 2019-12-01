package ch.studer.germanclimatedataanalyser.batch.processor;

import ch.studer.germanclimatedataanalyser.model.MonthTemperatureAtStationRecord;
import ch.studer.germanclimatedataanalyser.model.RawMonthTemperatureAtStationRecord;
import ch.studer.germanclimatedataanalyser.model.Station;
import ch.studer.germanclimatedataanalyser.model.StationWeather;
import ch.studer.germanclimatedataanalyser.model.file.StationFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;

import java.math.BigDecimal;
import java.sql.Date;

public class WeatherProcessor implements ItemProcessor<RawMonthTemperatureAtStationRecord, StationWeather> {


    private static final Logger log = LoggerFactory.getLogger(WeatherProcessor.class);


    @Override
    public StationWeather process(RawMonthTemperatureAtStationRecord raw) throws Exception {

        return null;
    }

}
