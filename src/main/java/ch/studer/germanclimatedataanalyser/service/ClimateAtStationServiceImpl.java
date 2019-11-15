package ch.studer.germanclimatedataanalyser.service;

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

    @Value("#{new Integer('${climate.calculation.period.year}')}")
    int period;

    private static final String JAN = "JAN";

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

            calculateHoles(temperatureByStationId);
            temperatureByStationId.print();

            // if there are some holes ! Calculate a temperature for it !


            // get all ClimateRecords : 1 = Period of 30 years
            TemperatureByStationId climateByStationId = getCLimate(temperatureByStationId);

            // print Differences !


        return climateAtStation;
    }

    private void calculateHoles(TemperatureByStationId temperatureByStationId) {

        TemperatureDataMonthPerYear temperatureDataMonthPerYear = getAllTemperatureDateYearsInMonolyt(temperatureByStationId.getTemperatureRecordList());

        for(int i = 0 ; i < temperatureByStationId.getTemperatureRecordList().size(); i++){

            if(temperatureByStationId.getTemperatureRecordList().get(i).getJan() == Double.MAX_VALUE){
                temperatureByStationId.getTemperatureRecordList().get(i).setJan(calculateTemperature(temperatureDataMonthPerYear.getJan(), i));
            }
        }


    }

    private Double calculateTemperature(List<TemperatureDataMonth> months , int index) {


        return ((getAverageTemperature(index,"BEFORE",months) + getAverageTemperature(index,"AFTER",months)) / 2);



    }

    private Double getAverageTemperature(int index, String modus, List<TemperatureDataMonth> months) {
        Double averageTemperature = 0d;
        List<Double> collectTemperature = new ArrayList<Double>();
        Double sumAverageTemperature = 0d;

        // Modus 1 go further and -1 go Back
        int step;
        int end;
        int start;

        if (modus.equals("BEFORE")) {
            // step back in Time
            step = -1;

            // Begin in List<>
            start = 0 ;
            end = index;
            if (index - period >= 0) {
                start = index - period;
            }
        } else {
            // Step furhter in Time
            step = 1;

            // end of List<>
            start = index;
            end = months.size();

            if (index + period <= months.size() ) {
                end = index + period;
            }
        }

        // Only get valid Temperature
        for (int i = start; i < end; i = i + step) {
            // filter null values
            if (months.get(i).getTemperature() != Double.MAX_VALUE){
              collectTemperature.add(months.get(i).getTemperature());
            }
        }

        // Building Sum
        for(Double temp : collectTemperature){
          sumAverageTemperature = sumAverageTemperature + temp;

        }

        // Calculate Average
        averageTemperature = sumAverageTemperature / collectTemperature.size();

        return averageTemperature;
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


    private TemperatureDataMonthPerYear getAllTemperatureDateYearsInMonolyt(List<TemperatureRecord> temperatureRecords) {

       // Get an actual Record but just init with null
       TemperatureDataMonthPerYear actualTempYearRecord = new TemperatureDataMonthPerYear();



        for (TemperatureRecord t : temperatureRecords){

            actualTempYearRecord.getJan().add(new TemperatureDataMonth(t.getYear(),t.getJan()));
            actualTempYearRecord.getFeb().add(new TemperatureDataMonth(t.getYear(),t.getFeb()));
            actualTempYearRecord.getMar().add(new TemperatureDataMonth(t.getYear(),t.getMar()));
            actualTempYearRecord.getApr().add(new TemperatureDataMonth(t.getYear(),t.getApr()));
            actualTempYearRecord.getMai().add(new TemperatureDataMonth(t.getYear(),t.getMai()));
            actualTempYearRecord.getJun().add(new TemperatureDataMonth(t.getYear(),t.getJun()));
            actualTempYearRecord.getJul().add(new TemperatureDataMonth(t.getYear(),t.getJul()));
            actualTempYearRecord.getAug().add(new TemperatureDataMonth(t.getYear(),t.getAug()));
            actualTempYearRecord.getSep().add(new TemperatureDataMonth(t.getYear(),t.getSep()));
            actualTempYearRecord.getOct().add(new TemperatureDataMonth(t.getYear(),t.getOct()));
            actualTempYearRecord.getNov().add(new TemperatureDataMonth(t.getYear(),t.getNov()));
            actualTempYearRecord.getDec().add(new TemperatureDataMonth(t.getYear(),t.getDec()));

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
