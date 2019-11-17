package ch.studer.germanclimatedataanalyser.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class TemperatureDataMonthPerYear {


    private static final Logger log = LoggerFactory.getLogger(TemperatureDataMonthPerYear.class);

    // All Temperature Data for Month Jan collected !
    private List<TemperatureDataMonth> jan ;

    // All Temperature Data for Month Feb collected !
    private List<TemperatureDataMonth> feb ;

    // All Temperature Data for Month Mar collected !
    private List<TemperatureDataMonth> mar ;

    // All Temperature Data for Month Apr collected !
    private List<TemperatureDataMonth> apr ;

    // All Temperature Data for Month Mai collected !
    private List<TemperatureDataMonth> mai ;

    // All Temperature Data for Month Jun collected !
    private List<TemperatureDataMonth> jun ;

    // All Temperature Data for Month Jul collected !
    private List<TemperatureDataMonth> jul ;

    // All Temperature Data for Month Aug collected !
    private List<TemperatureDataMonth> aug ;

    // All Temperature Data for Month Sep collected !
    private List<TemperatureDataMonth> sep ;

    // All Temperature Data for Month Oct collected !
    private List<TemperatureDataMonth> oct ;

    // All Temperature Data for Month Nov collected !
    private List<TemperatureDataMonth> nov ;

    // All Temperature Data for Month Dec collected !
    private List<TemperatureDataMonth> dec ;



    // ###########################################
    // #   Constructor
    // ###########################################

    public TemperatureDataMonthPerYear() {
        this.jan = new ArrayList<TemperatureDataMonth>();
        this.feb = new ArrayList<TemperatureDataMonth>();
        this.mar = new ArrayList<TemperatureDataMonth>();
        this.apr = new ArrayList<TemperatureDataMonth>();
        this.mai = new ArrayList<TemperatureDataMonth>();
        this.jun = new ArrayList<TemperatureDataMonth>();
        this.jul = new ArrayList<TemperatureDataMonth>();
        this.aug = new ArrayList<TemperatureDataMonth>();
        this.sep = new ArrayList<TemperatureDataMonth>();
        this.oct = new ArrayList<TemperatureDataMonth>();
        this.nov = new ArrayList<TemperatureDataMonth>();
        this.dec = new ArrayList<TemperatureDataMonth>();
    }
    // ###########################################
    // #   Getter
    // ###########################################


    public List<TemperatureDataMonth> getJan() {
        return jan;
    }

    public List<TemperatureDataMonth> getFeb() {
        return feb;
    }

    public List<TemperatureDataMonth> getMar() {
        return mar;
    }

    public List<TemperatureDataMonth> getApr() {
        return apr;
    }

    public List<TemperatureDataMonth> getMai() {
        return mai;
    }

    public List<TemperatureDataMonth> getJun() {
        return jun;
    }

    public List<TemperatureDataMonth> getJul() {
        return jul;
    }

    public List<TemperatureDataMonth> getAug() {
        return aug;
    }

    public List<TemperatureDataMonth> getSep() {
        return sep;
    }

    public List<TemperatureDataMonth> getOct() {
        return oct;
    }

    public List<TemperatureDataMonth> getNov() {
        return nov;
    }

    public List<TemperatureDataMonth> getDec() {
        return dec;
    }

    @Override
    public String toString() {
        return "TemperatureRecord{" +
                "jan=" + jan +
                ", feb=" + feb +
                ", mar=" + mar +
                ", apr=" + apr +
                ", mai=" + mai +
                ", jun=" + jun +
                ", jul=" + jul +
                ", aug=" + aug +
                ", sep=" + sep +
                ", oct=" + oct +
                ", nov=" + nov +
                ", dec=" + dec +
                '}';
    }

    public void printTemperatureDataYear(int stationsId){

        String highestYear = getHighestYear();
        int numberRows = getNumberRows();

        log.info("-------------------------------------------------------------------------------------------------------------------");
        log.info("                                      Stations Id : "+ stationsId +"                                               ");
        log.info("-------------------------------------------------------------------------------------------------------------------");
        log.info("     |    Jan  |  Feb   |  Mar   |  Apr   |  Mai   |  Jun   |  Jul   |  Aug   |  Sep   | Oct    |  Nov   |  Dec   |");
        log.info("-------------------------------------------------------------------------------------------------------------------");

        String yearCounter = highestYear;
        for(int i = 1 ; i <= numberRows ; i++){

          log.info(getPrintLine(yearCounter));
          yearCounter = String.valueOf(Integer.valueOf(yearCounter) - 1);
        log.info("-------------------------------------------------------------------------------------------------------------------");

        }
    }

    private String getPrintLine(String year) {
        return year + " | "+getPrintMonth(jan,year) + getPrintMonth(feb,year) + getPrintMonth(mar,year)  + getPrintMonth(apr,year) + getPrintMonth(mai,year)  + getPrintMonth(jun,year) +
               getPrintMonth(jul,year) + getPrintMonth(aug,year) + getPrintMonth(sep,year) + getPrintMonth(oct,year) + getPrintMonth(nov,year)  + getPrintMonth(dec,year)  ;
    }

    private String getPrintMonth(List<TemperatureDataMonth> months, String year) {
        // use DecimalFormat
        DecimalFormat decimalFormat = new DecimalFormat("#00.00##");
        String formatedTemperature = " ------ |";

        for(TemperatureDataMonth month : months){

            if(month.getYear().equals(year)){
                formatedTemperature= " " + decimalFormat.format(month.getTemperature()) + "  |";
            }
        }
                return formatedTemperature;
    }

    public int getNumberRows() {

        int numberRows = 0 ;

        numberRows = getHighesRow(numberRows, jan);
        numberRows = getHighesRow(numberRows, feb);
        numberRows = getHighesRow(numberRows, mar);
        numberRows = getHighesRow(numberRows, apr);
        numberRows = getHighesRow(numberRows, mai);
        numberRows = getHighesRow(numberRows, jun);
        numberRows = getHighesRow(numberRows, jul);
        numberRows = getHighesRow(numberRows, aug);
        numberRows = getHighesRow(numberRows, sep);
        numberRows = getHighesRow(numberRows, oct);
        numberRows = getHighesRow(numberRows, nov);
        numberRows = getHighesRow(numberRows, dec);

       return numberRows;
    }

    private int getHighesRow(int numberRows, List<TemperatureDataMonth> month) {
         if (numberRows > month.size()){
             return numberRows;
         } else {
             return month.size();
         }
    }

    public String getHighestYear(){
        String highestYear = "0000";

        highestYear = getHigher(highestYear , jan);
        highestYear = getHigher(highestYear , feb);
        highestYear = getHigher(highestYear , mar);
        highestYear = getHigher(highestYear , apr);
        highestYear = getHigher(highestYear , mai);
        highestYear = getHigher(highestYear , jun);
        highestYear = getHigher(highestYear , jul);
        highestYear = getHigher(highestYear , aug);
        highestYear = getHigher(highestYear , sep);
        highestYear = getHigher(highestYear , oct);
        highestYear = getHigher(highestYear , nov);
        highestYear = getHigher(highestYear , dec);

        return highestYear;
    }

    private String getHigher(String highestYear, List<TemperatureDataMonth> months) {
        String higher = highestYear;

        for(TemperatureDataMonth month : months){
            if(Integer.valueOf(higher) < Integer.valueOf(month.getYear())){
                higher = month.getYear();
            }
        }

        return higher;
    }
}
