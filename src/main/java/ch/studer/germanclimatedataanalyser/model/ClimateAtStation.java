package ch.studer.germanclimatedataanalyser.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.DecimalFormat;
import java.util.List;

public class ClimateAtStation {


    // StationsId
    private int stationId;

    // All ClimateRecords for a Station
    private List<ClimateRecord> climateRecords ;


    private static final Logger log = LoggerFactory.getLogger(ClimateAtStation.class);
    // ####################################
    // # Constructor
    // ####################################

    public ClimateAtStation(int stationId){
        this.stationId = stationId;
    }


    // ###################################
    // # Getter and Setter
    // ###################################
    public int getStationId() {
        return stationId;
    }

    public void setStationId(int stationId) {
        this.stationId = stationId;
    }

    public List<ClimateRecord> getClimateRecords() {
        return climateRecords;
    }

    public void setClimateRecords(List<ClimateRecord> climateRecords) {
        this.climateRecords = climateRecords;
    }


    public void print() {
    //int numberRows = this.getTemperatureRecordList().size();

        log.info("######################################################################################################################################################");
        log.info("                                   Climate Records for Stations Id : " + this.stationId + "                                             ");
        log.info("######################################################################################################################################################");
        log.info("        |        |    Jan   |    Feb   |   Mar    |   Apr    |   Mai    |   Jun    |   Jul    |   Aug    |   Sep    |  Oct     |   Nov    |   Dec    |");
        log.info("######################################################################################################################################################");

        for (ClimateRecord climateRecord : getClimateRecords()) {


        log.info(getPrintLine(climateRecord));
        log.info("######################################################################################################################################################");


         }
        // TODO Ende Printen , damit klar ist , dass hier der Report fertig ist
     }
    // ####################################################
    // # Print
    // ####################################################
    private String getPrintLine(ClimateRecord c){

        return "   " + c.getStartDate() + " |  " + c.getEndPeriod() + "  |" + getPrintMonth(c.getTempJan()) + getPrintMonth(c.getTempFeb()) + getPrintMonth(c.getTempMar()) + getPrintMonth(c.getTempApr()) + getPrintMonth(c.getTempMai())
                + getPrintMonth(c.getTempJun()) + getPrintMonth(c.getTempJul()) + getPrintMonth(c.getTempAug()) + getPrintMonth(c.getTempSep()) + getPrintMonth(c.getTempOkt()) + getPrintMonth(c.getTempNov()) + getPrintMonth(c.getTempDez());
    }
    private String getPrintMonth ( double month){
        // use DecimalFormat
        DecimalFormat decimalFormat = new DecimalFormat("00.0000");
        String formatedTemperature = " ------ |";

        if (month != Double.MAX_VALUE) {
            formatedTemperature = " " + decimalFormat.format(month) + "  |";
        }

        return formatedTemperature;

    }

}
