package ch.studer.germanclimatedataanalyser.batch.processor;

import ch.studer.germanclimatedataanalyser.model.MonthTemperatureAtStationRecord;
import ch.studer.germanclimatedataanalyser.model.RawMonthTemperatureAtStationRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;

import java.math.BigDecimal;
import java.sql.Date;

public class WeatherProcessor implements ItemProcessor<RawMonthTemperatureAtStationRecord, MonthTemperatureAtStationRecord> {


    private static final Logger log = LoggerFactory.getLogger(WeatherProcessor.class);


    @Override
    public MonthTemperatureAtStationRecord process(RawMonthTemperatureAtStationRecord raw) throws Exception {

        MonthTemperatureAtStationRecord monthTemperatureAtStationRecord = new MonthTemperatureAtStationRecord();

        monthTemperatureAtStationRecord.setStationId(raw.getStationId());
        monthTemperatureAtStationRecord.setMessDatumBeginn(raw.getMessDatumBeginn());
        monthTemperatureAtStationRecord.setMessDatumEnd(raw.getMessDatumEnd());
        // Temperatur is already BigDecimal proofed by the first Import Job !
        monthTemperatureAtStationRecord.setTemperatur(new BigDecimal(raw.getTemperatur()));

        return monthTemperatureAtStationRecord;
    }

}
