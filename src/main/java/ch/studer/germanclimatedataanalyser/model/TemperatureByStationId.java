package ch.studer.germanclimatedataanalyser.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class TemperatureByStationId {

    int stationId;
    List<TemperatureRecord> temperatureRecordList;

    private static final Logger log = LoggerFactory.getLogger(TemperatureByStationId.class);


    public TemperatureByStationId(int stationId) {
        this.stationId = stationId;
        this.temperatureRecordList = new ArrayList<TemperatureRecord>();
    }


    public int getStationId() {
        return stationId;
    }

    public void setStationId(int stationId) {
        this.stationId = stationId;
    }

    public List<TemperatureRecord> getTemperatureRecordList() {
        return temperatureRecordList;
    }

    public void setTemperatureRecordList(List<TemperatureRecord> temperatureRecordList) {
        this.temperatureRecordList = temperatureRecordList;
    }


    public void print() {

        //int numberRows = this.getTemperatureRecordList().size();

        log.info("-------------------------------------------------------------------------------------------------------------------");
        log.info("                            Temperature Records for Stations Id : " + this.stationId + "                                             ");
        log.info("-------------------------------------------------------------------------------------------------------------------");
        log.info("     |    Jan  |  Feb   |  Mar   |  Apr   |  Mai   |  Jun   |  Jul   |  Aug   |  Sep   | Oct    |  Nov   |  Dec   |");
        log.info("-------------------------------------------------------------------------------------------------------------------");

        for (TemperatureRecord temperatureRecord : temperatureRecordList) {


            log.info(getPrintLine(temperatureRecord));
            log.info("-------------------------------------------------------------------------------------------------------------------");


        }
        // TODO Ende Printen , damit klar ist , dass hier der Report fertig ist
    }
        // ####################################################
        // # Print
        // ####################################################
        private String getPrintLine(TemperatureRecord t){

            return t.getYear() + " | " + getPrintMonth(t.getJan()) + getPrintMonth(t.getFeb()) + getPrintMonth(t.getMar()) + getPrintMonth(t.getApr())
                    + getPrintMonth(t.getMai()) + getPrintMonth(t.getJun()) + getPrintMonth(t.getJul()) + getPrintMonth(t.getAug()) + getPrintMonth(t.getSep()) + getPrintMonth(t.getOct()) + getPrintMonth(t.getNov()) + getPrintMonth(t.getDec());
        }
        private String getPrintMonth ( double month){
            // use DecimalFormat
            //TODO Nur minus Zeichen Printen wenn vorhanden und den Platz dafür vorsehen !
            DecimalFormat decimalFormat = new DecimalFormat("#00.00##");
            String formatedTemperature = " ------ |";

            if (month != Double.MAX_VALUE) {
                formatedTemperature = " " + decimalFormat.format(month) + "  |";
            }

            return formatedTemperature;

        }
}