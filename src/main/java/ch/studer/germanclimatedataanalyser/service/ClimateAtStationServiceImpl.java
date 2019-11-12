package ch.studer.germanclimatedataanalyser.service;

import ch.studer.germanclimatedataanalyser.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class ClimateAtStationServiceImpl implements ClimateAtStationService {

    @Autowired
    MonthService monthService;

    @Value("climate.calculation.period.year")
    int period;

    private static final Logger log = LoggerFactory.getLogger(ClimateAtStationServiceImpl.class);

    @Override
    public ClimateAtStation getClimateDataBy(int stationsId) {


        ClimateAtStation climateAtStation = new ClimateAtStation(stationsId);

        // Get all Months Records ordered by EndDate desc for a Climate Period (30 years)
        List<Month> months = monthService.getMonthsByIdOrderDesc(stationsId);

        // calculate the average Temperature for every Month
        // Don't forget to proof if all Data are available


            // get all Temperature organized by their month
            // Only years with ALL 12 Month are allowed
            // just cut the begin and end ... !?
            TemperatureDataYear temperatureDataYears = new TemperatureDataYear();
            temperatureDataYears = getAllTemperatureDateYears(months);

            // if there are some holes ! Calculate a temperature for it !


            // get all ClimateRecords : 1 = Period of 30 years

            // print Differences !


        return climateAtStation;
    }

    private TemperatureDataYear getAllTemperatureDateYears(List<Month> months) {


        // Get an actual Record but just init with null
        TemperatureDataYear actualTempYearRecord = new TemperatureDataYear();

        for (Month month : months){

            String actualMont = getActualMonth(month.getMessDatumEnde());
            log.info(actualMont);

            if(actualMont.contentEquals("12")){

                TemperatureDataMonth temperatureDataMonth = new TemperatureDataMonth(month.getMessDatumEnde().toString().substring(0,4),month.getMessDatumEnde().toString().substring(5,7),month.getMoTt());
                 actualTempYearRecord.getDec().add(temperatureDataMonth);

                log.info(months.toString());
            }


        }


        return actualTempYearRecord ;


    }

    private String getActualMonth(Date messDatumEnde) {
        return messDatumEnde.toString().substring(5,7);
    }
}
