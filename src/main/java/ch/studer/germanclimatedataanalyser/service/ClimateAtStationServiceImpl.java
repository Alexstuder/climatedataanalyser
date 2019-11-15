package ch.studer.germanclimatedataanalyser.service;

import ch.studer.germanclimatedataanalyser.common.Statistic;
import ch.studer.germanclimatedataanalyser.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.sql.Date;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class ClimateAtStationServiceImpl implements ClimateAtStationService {

    @Autowired
    MonthService monthService;

    @Value("climate.calculation.period.year")
    String period;

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
            TemperatureByStationId temperatureByStationId = new TemperatureByStationId(stationsId);
            getNormalised(temperatureByStationId,months);

            temperatureByStationId.print();

            // if there are some holes ! Calculate a temperature for it !


            // get all ClimateRecords : 1 = Period of 30 years
            TemperatureByStationId climateByStationId = getCLimate(temperatureByStationId);

            // print Differences !


        return climateAtStation;
    }

    @Override
    public void getClimateDataAll() {
        for (Integer stationId : monthService.getAllStationId()){
            this.getClimateDataBy(stationId);
        }

    }

    private TemperatureByStationId getCLimate(TemperatureByStationId temperatureByStationId) {
        TemperatureByStationId climateByStationId1 = new TemperatureByStationId(temperatureByStationId.getStationId());






        return  climateByStationId1;
    }

    private void getNormalised(TemperatureByStationId temperatureByStationId, List<Month> months) {

        String processingEndDate = getActualYear(months.get(0).getMessDatumEnde());
        String actualEndDate = getActualYear(months.get(0).getMessDatumEnde());

        // get at fresh temperatureRecord
        TemperatureRecord temperatureRecord = new TemperatureRecord(actualEndDate);

         for(Month month : months){
             //log.info(month.getMessDatumEnde().toString());

             if (!processingEndDate.contentEquals(getActualYear(month.getMessDatumEnde()))){

                // Add TemperatureRecord to TemperatureRecordList
                temperatureByStationId.getTemperatureRecordList().add(temperatureRecord);


                // Set processingDate to actualDate
                processingEndDate = getActualYear(month.getMessDatumEnde());

                // Get a fres TemperatureRecord
                temperatureRecord = new TemperatureRecord(processingEndDate);

             }

                 switch(getActualMonth(month.getMessDatumEnde())){
                     case "12":
                         temperatureRecord.setDec(month.getMoTt());
                         break;
                     case "11":
                         temperatureRecord.setNov(month.getMoTt());
                         break;
                     case "10":
                         temperatureRecord.setOct(month.getMoTt());
                         break;
                     case "09":
                         temperatureRecord.setSep(month.getMoTt());
                         break;
                     case "08":
                         temperatureRecord.setAug(month.getMoTt());
                         break;
                     case "07":
                         temperatureRecord.setJul(month.getMoTt());
                         break;
                     case "06":
                         temperatureRecord.setJun(month.getMoTt());
                         break;
                     case "05":
                         temperatureRecord.setMai(month.getMoTt());
                         break;
                     case "04":
                         temperatureRecord.setApr(month.getMoTt());
                         break;
                     case "03":
                         temperatureRecord.setMar(month.getMoTt());
                         break;
                     case "02":
                         temperatureRecord.setFeb(month.getMoTt());
                         break;
                     case "01":
                         temperatureRecord.setJan(month.getMoTt());
                         break;
                     default:
                         log.info("Something went wrong !");
                         break;


             }
    }
    }


    private TemperatureDataMonthPerYear getAllTemperatureDateYearsInMonolyt(List<Month> months) {


        // Get an actual Record but just init with null
        TemperatureDataMonthPerYear actualTempYearRecord = new TemperatureDataMonthPerYear();

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


    // ####################################################
    // # Print
    // ####################################################
    private String getPrintLine(TemperatureRecord t) {

        return t.getYear() + " | "+getPrintMonth(t.getJan()) + getPrintMonth(t.getFeb()) + getPrintMonth(t.getMar())  + getPrintMonth(t.getApr())
                + getPrintMonth(t.getMai())  + getPrintMonth(t.getJun()) + getPrintMonth(t.getJul()) + getPrintMonth(t.getAug()) + getPrintMonth(t.getSep()) + getPrintMonth(t.getOct()) + getPrintMonth(t.getNov())  + getPrintMonth(t.getDec()) ;
    }
    private String getPrintMonth(double month) {
        // use DecimalFormat
        DecimalFormat decimalFormat = new DecimalFormat("#00.00##");
        String formatedTemperature = " ------ |";

        if (month != Double.MAX_VALUE) {
          formatedTemperature= " " + decimalFormat.format(month) + "  |";
        }

        return formatedTemperature;
    }

}
