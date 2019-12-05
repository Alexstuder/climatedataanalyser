package ch.studer.germanclimatedataanalyser.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public abstract class Climate_OLD {

    @Value("#{new Integer('${climate.calculation.period.year}')}")
    int period;

    private List<ClimateRecord_OLD> climateRecordOLDS = null;

    private List<ClimateDifference> climateDifferences = null;

    private static final Logger log = LoggerFactory.getLogger(Climate_OLD.class);



    public Climate_OLD(){
        this.climateRecordOLDS = new ArrayList<ClimateRecord_OLD>();
        this.climateDifferences = new ArrayList<ClimateDifference>();

    };

    // CONSTRUCTOR
    public void setClimate(List<ClimateRecord_OLD> climateRecordOLDS) {
        this.climateRecordOLDS = climateRecordOLDS;
        this.climateDifferences = calculateClimatesDifferences();
    }

    public List<ClimateRecord_OLD> getClimateRecordOLDS() {
        return climateRecordOLDS;
    }

    public List<ClimateDifference> getClimateDifferences() {
        return climateDifferences;
    }


    private java.util.List<ClimateDifference> calculateClimatesDifferences() {

        List<ClimateDifference> climateDifferences =  new ArrayList<ClimateDifference>();
        //ClimateOLDAtStation climateAtStation = getClimateAtStationId(stationId);

        for(int i = 0; i < (climateRecordOLDS.size() -period) ; i++){

            // Get a Fresh ClimateDifferenc Record
            ClimateDifference climateDifference = new ClimateDifference(climateRecordOLDS.get(i)
                    , climateRecordOLDS.get(i + period));

            climateDifferences.add(climateDifference);
        }
        return climateDifferences;
    }

        protected void printClimateRecords(String titel) {
        DecimalFormat decimalFormat = new DecimalFormat("00000");

        log.info("######################################################################################################################################################");
        log.info("#                                                                Climate_OLD Records for " + titel + "                                               #");
        log.info("######################################################################################################################################################");
        log.info("        |        |    Jan   |    Feb   |   Mar    |   Apr    |   Mai    |   Jun    |   Jul    |   Aug    |   Sep    |  Oct     |   Nov    |   Dec    |");
        log.info("######################################################################################################################################################");

        for (ClimateRecord_OLD climateRecordOLD : this.getClimateRecordOLDS()) {
            log.info(getPrintLine(climateRecordOLD));
        }

        log.info("######################################################################################################################################################");
        log.info("#------------------------------------------------------- End Climate_OLD Records "+ titel +" --------------------------------------------------#");
        log.info("######################################################################################################################################################");

    }

    protected void printDifference(String titel) {
        DecimalFormat decimalFormat = new DecimalFormat("00000");

        log.info("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
        log.info("@                                                                Climate_OLD Differences for " + titel + "                                         @");
        log.info("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
        log.info("        |        |    Jan   |    Feb   |   Mar    |   Apr    |   Mai    |   Jun    |   Jul    |   Aug    |   Sep    |  Oct     |   Nov    |   Dec    |");
        log.info("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");

        for (ClimateDifference climateDifference : this.getClimateDifferences()) {
            log.info(getPrintLine(climateDifference.getClimateFirstPeriod()));
            log.info(getPrintLine(climateDifference.getClimateSecondPeriod()));
            log.info(getPrintDifferenceLine(climateDifference.getDifference()));
        }

        log.info("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
        log.info("@------------------------------------------------------- End Climate_OLD Differences for "+ titel +" ----------------------------------------------@");
        log.info("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
    }

    // ####################################################
    // # Print Climate_OLD Period
    // ####################################################
    private String getPrintDifferenceLine(TemperatureRecord t){

        return "        |        |" + getPrintMonth(t.getJan()) + getPrintMonth(t.getFeb()) + getPrintMonth(t.getMar()) + getPrintMonth(t.getApr()) + getPrintMonth(t.getMai())
                + getPrintMonth(t.getJun()) + getPrintMonth(t.getJul()) + getPrintMonth(t.getAug()) + getPrintMonth(t.getSep()) + getPrintMonth(t.getOct()) + getPrintMonth(t.getNov()) + getPrintMonth(t.getDec());
    }

    // ####################################################
    // # Print Climate_OLD Period
    // ####################################################
    private String getPrintLine(ClimateRecord_OLD c){

        return "   " + c.getStartDate() + " |  " + c.getEndPeriod() + "  |" + getPrintMonth(c.getTempJan()) + getPrintMonth(c.getTempFeb()) + getPrintMonth(c.getTempMar()) + getPrintMonth(c.getTempApr()) + getPrintMonth(c.getTempMai())
                + getPrintMonth(c.getTempJun()) + getPrintMonth(c.getTempJul()) + getPrintMonth(c.getTempAug()) + getPrintMonth(c.getTempSep()) + getPrintMonth(c.getTempOkt()) + getPrintMonth(c.getTempNov()) + getPrintMonth(c.getTempDez());
    }

    private String getPrintMonth ( BigDecimal month){
        String preSpace = "  ";
        // use DecimalFormat
        DecimalFormat decimalFormat = new DecimalFormat("00.0000");
        String formatedTemperature = " ------ |";

        if(month.compareTo(BigDecimal.ZERO) < 0){
            preSpace = " ";
        }

        if (month.compareTo(BigDecimal.valueOf(Double.MAX_VALUE)) != 0) {
            formatedTemperature = preSpace + decimalFormat.format(month) + " |";
        }

        return formatedTemperature;

    }


}
