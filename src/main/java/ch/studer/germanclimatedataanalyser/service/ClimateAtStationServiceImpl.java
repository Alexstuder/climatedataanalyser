package ch.studer.germanclimatedataanalyser.service;

import ch.studer.germanclimatedataanalyser.model.ClimateAtStation;
import ch.studer.germanclimatedataanalyser.model.Month;
import ch.studer.germanclimatedataanalyser.model.TemperatureDataMonth;
import ch.studer.germanclimatedataanalyser.model.TemperatureDataYear;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.sql.Date;
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
            temperatureDataYears.printTemperatureDataYear(stationsId);

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
            String actualYear = getActualYear(month.getMessDatumEnde());

            switch(actualMont){
                case "12":
                    actualTempYearRecord.getDec().add(new TemperatureDataMonth(actualYear,month.getMoTt()));
                break;
                case "11":
                    actualTempYearRecord.getNov().add(new TemperatureDataMonth(actualYear,month.getMoTt()));
                break;
                case "10":
                    actualTempYearRecord.getOct().add(new TemperatureDataMonth(actualYear,month.getMoTt()));
                break;
                case "09":
                    actualTempYearRecord.getSep().add(new TemperatureDataMonth(actualYear,month.getMoTt()));
                break;
                case "08":
                    actualTempYearRecord.getAug().add(new TemperatureDataMonth(actualYear,month.getMoTt()));
                break;
                case "07":
                    actualTempYearRecord.getJul().add(new TemperatureDataMonth(actualYear,month.getMoTt()));
                break;
                case "06":
                    actualTempYearRecord.getJun().add(new TemperatureDataMonth(actualYear,month.getMoTt()));
                break;
                case "05":
                    actualTempYearRecord.getMai().add(new TemperatureDataMonth(actualYear,month.getMoTt()));
                break;
                case "04":
                    actualTempYearRecord.getApr().add(new TemperatureDataMonth(actualYear,month.getMoTt()));
                break;
                case "03":
                    actualTempYearRecord.getMar().add(new TemperatureDataMonth(actualYear,month.getMoTt()));
                break;
                case "02":
                    actualTempYearRecord.getFeb().add(new TemperatureDataMonth(actualYear,month.getMoTt()));
                break;
                case "01":
                    actualTempYearRecord.getJan().add(new TemperatureDataMonth(actualYear,month.getMoTt()));
                break;
                default:
                 log.info("Something went wrong !");
                break;
            }

            // get the first Dec Record

                log.info(actualMont);
                log.info(actualYear);
                log.info(months.toString());




        }


        return actualTempYearRecord ;


    }

    private String getActualYear(Date messDatumEnde) {return messDatumEnde.toString().substring(0,4);


    }

    private String getActualMonth(Date messDatumEnde) {
        return messDatumEnde.toString().substring(5,7);
    }
}
