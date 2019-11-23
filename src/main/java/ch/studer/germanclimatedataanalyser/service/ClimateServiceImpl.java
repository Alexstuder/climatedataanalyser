package ch.studer.germanclimatedataanalyser.service;

import ch.studer.germanclimatedataanalyser.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class ClimateServiceImpl implements ClimateService {


    @Autowired
    TemperaturesAtStationService temperaturesAtStationService;

    @Value("#{new Integer('${climate.calculation.period.year}')}")
    int period;

    // Inludes all Cimate Records for a Station
    ClimateAtStation climateAtStation ;


    private static final Logger log = LoggerFactory.getLogger(ClimateServiceImpl.class);

    @Override
    public ClimateAtStation getClimateAtStationId(int stationId) {
         climateAtStation = new ClimateAtStation(stationId);

        climateAtStation.setClimateRecords(getAllClimateRecordsForStation(temperaturesAtStationService.getTemperaturesBy(stationId)));

        // Print all Climate Records
        print(climateAtStation);


        return climateAtStation;
    }

    @Override
    public ClimateDifferenceAtStation getDifference(int stationId) {
        // Get Climate at Station Data
        climateAtStation = this.getClimateAtStationId(stationId);

        // Get the Difference between a Period
        ClimateDifferenceAtStation climateDiffAtStation = calculateDifference(climateAtStation.getStationId());

        printDifference(climateDiffAtStation);

        return climateDiffAtStation;
    }



    private ClimateDifferenceAtStation calculateDifference(int stationId) {

        ClimateDifferenceAtStation climateDifferenceAtStation = new ClimateDifferenceAtStation(stationId);

        for(int i = 0 ; i < (climateAtStation.getClimateRecords().size() -period) ; i++){

            // Get a Fresh ClimateDifferenc Record
            ClimateDifference climateDifference = new ClimateDifference(climateAtStation.getClimateRecords().get(i)
                                                                      , climateAtStation.getClimateRecords().get(i + period));

            climateDifferenceAtStation.getClimateDifferences().add(climateDifference);
        }
        return climateDifferenceAtStation;
    }


    private List<ClimateRecord> getAllClimateRecordsForStation(TemperatureByStationId temperaturesBy) {
        List<ClimateRecord> climateRecords = new ArrayList<ClimateRecord>();

            int start = 0 ;
            int end = 0;

            end = end + period ;
            for (int i = end; i < temperaturesBy.getTemperatureRecordList().size();i++){


               end = start + period;
               // Proof if all needed Records exist ; then build a CLimate Record
               if((temperaturesBy.getTemperatureRecordList().get(start) != null) && (temperaturesBy.getTemperatureRecordList().get(end) != null)  ) {

                   climateRecords.add(getClimateForaPeriod(start, end, temperaturesBy));
               }
               start++;

            }

        return climateRecords;
    }

    private ClimateRecord getClimateForaPeriod(int start, int end, TemperatureByStationId temperaturesBy) {
        // Get a fresh Record StartDate = start and end Date = end-1 (cause the start of a Java List is 0 !)
      ClimateRecord climateRecords = new ClimateRecord(temperaturesBy.getTemperatureRecordList().get(start).getYear(),temperaturesBy.getTemperatureRecordList().get(end-1).getYear());

      // Cumulate all Month temperature
      for(int i = start ; i < end ; i++ ){
          climateRecords.setTempJan(climateRecords.getTempJan() + temperaturesBy.getTemperatureRecordList().get(i).getJan());
          climateRecords.setTempFeb(climateRecords.getTempFeb() + temperaturesBy.getTemperatureRecordList().get(i).getFeb());
          climateRecords.setTempMar(climateRecords.getTempMar() + temperaturesBy.getTemperatureRecordList().get(i).getMar());
          climateRecords.setTempApr(climateRecords.getTempApr() + temperaturesBy.getTemperatureRecordList().get(i).getApr());
          climateRecords.setTempMai(climateRecords.getTempMai() + temperaturesBy.getTemperatureRecordList().get(i).getMai());
          climateRecords.setTempJun(climateRecords.getTempJun() + temperaturesBy.getTemperatureRecordList().get(i).getJun());
          climateRecords.setTempJul(climateRecords.getTempJul() + temperaturesBy.getTemperatureRecordList().get(i).getJul());
          climateRecords.setTempAug(climateRecords.getTempAug() + temperaturesBy.getTemperatureRecordList().get(i).getAug());
          climateRecords.setTempSep(climateRecords.getTempSep() + temperaturesBy.getTemperatureRecordList().get(i).getSep());
          climateRecords.setTempOkt(climateRecords.getTempOkt() + temperaturesBy.getTemperatureRecordList().get(i).getOct());
          climateRecords.setTempNov(climateRecords.getTempNov() + temperaturesBy.getTemperatureRecordList().get(i).getNov());
          climateRecords.setTempDez(climateRecords.getTempDez() + temperaturesBy.getTemperatureRecordList().get(i).getDec());
      }


      // // Divide to get the average

        climateRecords.setTempJan(climateRecords.getTempJan() / period);
        climateRecords.setTempFeb(climateRecords.getTempFeb() / period);
        climateRecords.setTempMar(climateRecords.getTempMar() / period);
        climateRecords.setTempApr(climateRecords.getTempApr() / period);
        climateRecords.setTempMai(climateRecords.getTempMai() / period);
        climateRecords.setTempJun(climateRecords.getTempJun() / period);
        climateRecords.setTempJul(climateRecords.getTempJul() / period);
        climateRecords.setTempAug(climateRecords.getTempAug() / period);
        climateRecords.setTempSep(climateRecords.getTempSep() / period);
        climateRecords.setTempOkt(climateRecords.getTempOkt() / period);
        climateRecords.setTempNov(climateRecords.getTempNov() / period);
        climateRecords.setTempDez(climateRecords.getTempDez() / period);

      return climateRecords ;
    }

    private void printDifference(ClimateDifferenceAtStation d) {
        DecimalFormat decimalFormat = new DecimalFormat("00000");

        log.info("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
        log.info("@                                                                Climate Differences for Stations Id : " + decimalFormat.format(d.getStationId()) + "                                         @");
        log.info("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
        log.info("        |        |    Jan   |    Feb   |   Mar    |   Apr    |   Mai    |   Jun    |   Jul    |   Aug    |   Sep    |  Oct     |   Nov    |   Dec    |");
        log.info("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");

        for (ClimateDifference climateDifference : d.getClimateDifferences()) {
            log.info(getPrintLine(climateDifference.getClimateFirstPeriod()));
            log.info(getPrintLine(climateDifference.getClimateSecondPeriod()));
            log.info(getPrintDifferenceLine(climateDifference.getDifference()));
        }

        log.info("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
        log.info("@------------------------------------------------------- End Climate Differences for StationId : "+ decimalFormat.format(d.getStationId()) +" ----------------------------------------------@");
        log.info("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
    }

    // ####################################################
    // # Print Climate Period
    // ####################################################
    private String getPrintDifferenceLine(TemperatureRecord t){

        return "        |        |" + getPrintMonth(t.getJan()) + getPrintMonth(t.getFeb()) + getPrintMonth(t.getMar()) + getPrintMonth(t.getApr()) + getPrintMonth(t.getMai())
                + getPrintMonth(t.getJun()) + getPrintMonth(t.getJul()) + getPrintMonth(t.getAug()) + getPrintMonth(t.getSep()) + getPrintMonth(t.getOct()) + getPrintMonth(t.getNov()) + getPrintMonth(t.getDec());
    }


    public void print(ClimateAtStation c) {
        DecimalFormat decimalFormat = new DecimalFormat("00000");

        log.info("######################################################################################################################################################");
        log.info("#                                                                Climate Records for Stations Id : " + decimalFormat.format(c.getStationId()) + "                                             #");
        log.info("######################################################################################################################################################");
        log.info("        |        |    Jan   |    Feb   |   Mar    |   Apr    |   Mai    |   Jun    |   Jul    |   Aug    |   Sep    |  Oct     |   Nov    |   Dec    |");
        log.info("######################################################################################################################################################");

        for (ClimateRecord climateRecord : c.getClimateRecords()) {
            log.info(getPrintLine(climateRecord));
        }

        log.info("######################################################################################################################################################");
        log.info("#------------------------------------------------------- End Climate Records for StationId : "+ decimalFormat.format(c.getStationId()) +" --------------------------------------------------#");
        log.info("######################################################################################################################################################");

    }
    // ####################################################
    // # Print Climate Period
    // ####################################################
    private String getPrintLine(ClimateRecord c){

        return "   " + c.getStartDate() + " |  " + c.getEndPeriod() + "  |" + getPrintMonth(c.getTempJan()) + getPrintMonth(c.getTempFeb()) + getPrintMonth(c.getTempMar()) + getPrintMonth(c.getTempApr()) + getPrintMonth(c.getTempMai())
                + getPrintMonth(c.getTempJun()) + getPrintMonth(c.getTempJul()) + getPrintMonth(c.getTempAug()) + getPrintMonth(c.getTempSep()) + getPrintMonth(c.getTempOkt()) + getPrintMonth(c.getTempNov()) + getPrintMonth(c.getTempDez());
    }

    private String getPrintMonth ( double month){
        String preSpace = "  ";
        // use DecimalFormat
        DecimalFormat decimalFormat = new DecimalFormat("00.0000");
        String formatedTemperature = " ------ |";

        if(month < 0){
            preSpace = " ";
        }

        if (month != Double.MAX_VALUE) {
            formatedTemperature = preSpace + decimalFormat.format(month) + " |";
        }

        return formatedTemperature;

    }


}
