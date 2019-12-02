package ch.studer.germanclimatedataanalyser.service;

import ch.studer.germanclimatedataanalyser.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Date;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class TemperaturesAtStationServiceImpl implements TemperaturesAtStationService {

    @Autowired
    MonthService monthService;

    @Autowired
    StationService stationService;

    @Value("#{new Integer('${climate.calculation.period.year}')}")
    int period;

    private static final Logger LOG = LoggerFactory.getLogger(TemperaturesAtStationServiceImpl.class);

    @Override
    public TemperatureByStationId getTemperaturesBy(int stationsId) {

        // Get all Months Records ordered by EndDate desc for a ClimateService Period (30 years)
        List<Month> months = monthService.getMonthsByIdOrderDesc(stationsId);

        // Get a fresh Instance of TemperatureBySTationId
        TemperatureByStationId temperatureByStationId = new TemperatureByStationId(stationsId);

        // Fill and normalise all temperatureRecords
        if(months.size() > 0){
            getNormalised(temperatureByStationId,months);

            // print before calculating the average value for null temperature
            LOG.debug("print before calculating the average value for null temperature");
            print(temperatureByStationId);

            //calculating the average value for every null temperature
            calculateHoles(temperatureByStationId);

            // print after calculating the average value for null temperature
            LOG.debug("calculating the average value for every null temperature");
            print(temperatureByStationId);
        } else {
            LOG.debug("Nothing to normalise !");
        }

        return temperatureByStationId;
    }

    private void calculateHoles(TemperatureByStationId temperatureByStationId) {

        TemperatureDataMonthPerYear temperatureDataMonthPerYear = getAllTemperatureDateYearsInMonolyt(temperatureByStationId.getTemperatureRecordList());

        for(int i = 0 ; i < temperatureByStationId.getTemperatureRecordList().size(); i++){

            // Proof , if Jan is null  then replace with average Temp
            if(isNull(temperatureByStationId.getTemperatureRecordList().get(i).getJan())){
                temperatureByStationId.getTemperatureRecordList().get(i).setJan(calculateTemperature(temperatureDataMonthPerYear.getJan(), i));
            }
            // Proof , if Feb is null  then replace with average Temp
            if(isNull(temperatureByStationId.getTemperatureRecordList().get(i).getFeb())){
                temperatureByStationId.getTemperatureRecordList().get(i).setFeb(calculateTemperature(temperatureDataMonthPerYear.getFeb(), i));
            }
            // Proof , if Mar is null  then replace with average Temp
            if(isNull(temperatureByStationId.getTemperatureRecordList().get(i).getMar())){
                temperatureByStationId.getTemperatureRecordList().get(i).setMar(calculateTemperature(temperatureDataMonthPerYear.getMar(), i));
            }
            // Proof , if Apr is null  then replace with average Temp
            if(isNull(temperatureByStationId.getTemperatureRecordList().get(i).getApr())){
                temperatureByStationId.getTemperatureRecordList().get(i).setApr(calculateTemperature(temperatureDataMonthPerYear.getApr(), i));
            }
            // Proof , if Mai is null  then replace with average Temp
            if(isNull(temperatureByStationId.getTemperatureRecordList().get(i).getMai())){
                temperatureByStationId.getTemperatureRecordList().get(i).setMai(calculateTemperature(temperatureDataMonthPerYear.getMai(), i));
            }
            // Proof , if Jun is null  then replace with average Temp
            if(isNull(temperatureByStationId.getTemperatureRecordList().get(i).getJun())){
                temperatureByStationId.getTemperatureRecordList().get(i).setJun(calculateTemperature(temperatureDataMonthPerYear.getJun(), i));
            }
            // Proof , if Jul is null  then replace with average Temp
            if(isNull(temperatureByStationId.getTemperatureRecordList().get(i).getJul())){
                temperatureByStationId.getTemperatureRecordList().get(i).setJul(calculateTemperature(temperatureDataMonthPerYear.getJul(), i));
            }
            // Proof , if Aug is null  then replace with average Temp
            if(isNull(temperatureByStationId.getTemperatureRecordList().get(i).getAug())){
                temperatureByStationId.getTemperatureRecordList().get(i).setAug(calculateTemperature(temperatureDataMonthPerYear.getAug(), i));
            }
            // Proof , if Sep is null  then replace with average Temp
            if(isNull(temperatureByStationId.getTemperatureRecordList().get(i).getSep())){
                temperatureByStationId.getTemperatureRecordList().get(i).setSep(calculateTemperature(temperatureDataMonthPerYear.getSep(), i));
            }
            // Proof , if Oct is null  then replace with average Temp
            if(isNull(temperatureByStationId.getTemperatureRecordList().get(i).getOct())){
                temperatureByStationId.getTemperatureRecordList().get(i).setOct(calculateTemperature(temperatureDataMonthPerYear.getOct(), i));
            }
            // Proof , if Nov is null  then replace with average Temp
            if(isNull(temperatureByStationId.getTemperatureRecordList().get(i).getNov())){
                temperatureByStationId.getTemperatureRecordList().get(i).setNov(calculateTemperature(temperatureDataMonthPerYear.getNov(), i));
            }
            // Proof , if Dec is null  then replace with average Temp
            if(isNull(temperatureByStationId.getTemperatureRecordList().get(i).getDec())){
                temperatureByStationId.getTemperatureRecordList().get(i).setDec(calculateTemperature(temperatureDataMonthPerYear.getDec(), i));
            }


        }


    }

    private boolean isNull(final BigDecimal temperature) {

        // -99 gibt es nicht mehr ! Da der SQL die -999 entfernt ! --> Aus dem Code Entfernen !
        if (temperature != null && temperature.compareTo(BigDecimal.valueOf(-999)) != 0 ){
            return false;
        } else {
            return true;
        }
    }

    private BigDecimal calculateTemperature(List<TemperatureDataMonth> months , int index) {

        int start = index - period ;
        int end = index + period ;
        List<BigDecimal> averageTemperatures = new ArrayList<BigDecimal>();
        BigDecimal averageTemperature = new BigDecimal(0) ;


        // Get Start Index
        if(start <= 0){start = 0 ;}

        // Get End Index
        if (end > months.size()){end = months.size();}

        // Collect all not null Temperature
        for(int i = start ; i < end; i++){


            if((months.get(i).getTemperature() != null)
                && (months.get(i).getTemperature().compareTo(BigDecimal.valueOf(-999)) != 0)){
                averageTemperatures.add(months.get(i).getTemperature());
            }
        }
        // Build sum
        for(BigDecimal temperature: averageTemperatures){
            averageTemperature = averageTemperature.add(temperature);
        }

        // calculate average
        if(averageTemperatures.size() == 0){
            LOG.debug("Stop here !");
            return null;
        } else {
            return averageTemperature = averageTemperature.divide(BigDecimal.valueOf(averageTemperatures.size()), 4, RoundingMode.DOWN);
        }
    }

    @Override
    public void getTemperaturesForAll() {

        //TODO Return a List<TemperaturesByStationId>
        // This was just coded to Print and do a visual Test !
        for (Station station : stationService.getAllStations()){
            LOG.debug("STATION : " + station.getStationName());
            LOG.debug("STATION : " + station.getStationId());
            this.getTemperaturesBy(station.getStationId());
        }

    }

    private TemperatureByStationId getCLimate(TemperatureByStationId temperatureByStationId) {
        TemperatureByStationId climateByStationId1 = new TemperatureByStationId(temperatureByStationId.getStationId());

        return  climateByStationId1;
    }

    private void getNormalised(TemperatureByStationId temperatureByStationId, List<Month> months) {

        //TODO Check if months.get(0) exists or if DB is empty ¨!
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
                         LOG.info("Something went wrong !");
                         break;


             }
    }
         // Add Last builded temperatureRecord to List
        // Add TemperatureRecord to TemperatureRecordList
        temperatureByStationId.getTemperatureRecordList().add(temperatureRecord);
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

    private String getActualYear(Date messDatumEnde) {return messDatumEnde.toString().substring(0,4);}

    private String getActualMonth(Date messDatumEnde) {
        return messDatumEnde.toString().substring(5,7);
    }


    // ####################################################
    // # Print
    // ####################################################
    public void print(TemperatureByStationId t) {

        //int numberRows = this.getTemperatureRecordList().size();

        LOG.info("-------------------------------------------------------------------------------------------------------------------");
        LOG.info("                            Temperature Records for Stations Id : " + t.getStationId() + "                                             ");
        LOG.info("-------------------------------------------------------------------------------------------------------------------");
        LOG.info("     |    Jan  |  Feb   |  Mar   |  Apr   |  Mai   |  Jun   |  Jul   |  Aug   |  Sep   | Oct    |  Nov   |  Dec   |");
        LOG.info("-------------------------------------------------------------------------------------------------------------------");

        for (TemperatureRecord temperatureRecord : t.getTemperatureRecordList()) {


            LOG.info(getPrintLine(temperatureRecord));


        }
        LOG.info("-------------------------------------------------------------------------------------------------------------------");
        LOG.info("----------------  End Temperature Records for StationId : " +  t.getStationId() +" ----------------------------------------------------");
        LOG.info("-------------------------------------------------------------------------------------------------------------------");
    }
    // ####################################################
    // # Print
    // ####################################################
    private String getPrintLine(TemperatureRecord t){

        return t.getYear() + " | " + getPrintMonth(t.getJan()) + getPrintMonth(t.getFeb()) + getPrintMonth(t.getMar()) + getPrintMonth(t.getApr())
                + getPrintMonth(t.getMai()) + getPrintMonth(t.getJun()) + getPrintMonth(t.getJul()) + getPrintMonth(t.getAug()) + getPrintMonth(t.getSep()) + getPrintMonth(t.getOct()) + getPrintMonth(t.getNov()) + getPrintMonth(t.getDec());
    }
    private String getPrintMonth ( BigDecimal month){
        // use DecimalFormat
        String preSpace ;
        DecimalFormat decimalFormat = new DecimalFormat("#00.0000");
        String formatedTemperature = " ------ |";

        if (month != null ) {
            if (month.compareTo(BigDecimal.ZERO) < 0){preSpace = "";}
              else {preSpace = " ";}

            formatedTemperature = preSpace + decimalFormat.format(month) + "  |";
        }

        return formatedTemperature;

    }

}
