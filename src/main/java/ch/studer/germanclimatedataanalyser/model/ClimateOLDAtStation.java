package ch.studer.germanclimatedataanalyser.model;

import ch.studer.germanclimatedataanalyser.service.StationService;
import ch.studer.germanclimatedataanalyser.service.TemperaturesAtStationService;
import javassist.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.NoResultException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

public class ClimateOLDAtStation extends Climate_OLD {


    // StationsId
    private Station station;

    @Autowired
    StationService stationService;

    @Autowired
    TemperaturesAtStationService temperaturesAtStationService;

    private static final Logger log = LoggerFactory.getLogger(ClimateOLDAtStation.class);
    // ####################################
    // # Constructor
    // ####################################
    public ClimateOLDAtStation(){
      super();

    }

    public void getNewClimateAtStation(String station) throws NoResultException{

        this.station = new Station();

        if(stationExists(station)){
          super.setClimate(getAllClimateRecordsForStation());

        } else {
            throw new NoResultException("No Station :" +station + " found! ");
        }
    }

    public void printClimateRecords(){
        super.printClimateRecords(getStation().getStationName());
    }
    public void printClimateDifferences(){
        super.printDifference(getStation().getStationName());
    }
    // ###################################
    // # Getter and Setter
    // ###################################
    public Station getStation() {
        return station;
    }

    public List<ClimateRecord_OLD> getClimateRecordOLDS() {
        return super.getClimateRecordOLDS();
    }

    private boolean stationExists(String station)  {

        boolean exists = false ;
        int stationId;

        try{// Try to Parse if it is stationId
            stationId = Integer.valueOf(station);
            this.station = new Station();
            try {

                this.station = stationService.getStation(stationId);
            }catch (NotFoundException exe ){
                exists = false;
            }
        } catch (NumberFormatException e)
        {
            try
            {
            // then try to get the Station by it's name
            this.station = stationService.getStation(station);

            } catch (NotFoundException exe){
                exists = false;

           }
        }

        if(this.station.getStationName() != null){
            exists = true;
        }

        return exists;
    }

    private List<ClimateRecord_OLD> getAllClimateRecordsForStation() {

        TemperatureByStationId temperaturesBy = temperaturesAtStationService.getTemperaturesBy(station.getStationId());
        List<ClimateRecord_OLD> climateRecordOLDS = new ArrayList<ClimateRecord_OLD>();

        int start = 0 ;
        int end = 0;

        end = end + period ;
        for (int i = end; i < temperaturesBy.getTemperatureRecordList().size();i++){


            end = start + period;
            // Proof if all needed Records exist ; then build a CLimate Record
            if((temperaturesBy.getTemperatureRecordList().get(start) != null) && (temperaturesBy.getTemperatureRecordList().get(end) != null)  ) {

                climateRecordOLDS.add(getClimateForaPeriod(start, end, temperaturesBy));
            }
            start++;

        }

        return climateRecordOLDS;
    }

    private ClimateRecord_OLD getClimateForaPeriod(int start, int end, TemperatureByStationId temperaturesBy) {
        // Get a fresh Record StartDate = start and end Date = end-1 (cause the start of a Java List is 0 !)
        ClimateRecord_OLD climateRecordsOLD = new ClimateRecord_OLD(temperaturesBy.getTemperatureRecordList().get(start).getYear(),temperaturesBy.getTemperatureRecordList().get(end-1).getYear());

        // Cumulate all Month temperature
        for(int i = start ; i < end ; i++ ){
            climateRecordsOLD.setTempJan(climateRecordsOLD.getTempJan().add(temperaturesBy.getTemperatureRecordList().get(i).getJan()));
            climateRecordsOLD.setTempFeb(climateRecordsOLD.getTempFeb().add(temperaturesBy.getTemperatureRecordList().get(i).getFeb()));
            climateRecordsOLD.setTempMar(climateRecordsOLD.getTempMar().add(temperaturesBy.getTemperatureRecordList().get(i).getMar()));
            climateRecordsOLD.setTempApr(climateRecordsOLD.getTempApr().add(temperaturesBy.getTemperatureRecordList().get(i).getApr()));
            climateRecordsOLD.setTempMai(climateRecordsOLD.getTempMai().add(temperaturesBy.getTemperatureRecordList().get(i).getMai()));
            climateRecordsOLD.setTempJun(climateRecordsOLD.getTempJun().add(temperaturesBy.getTemperatureRecordList().get(i).getJun()));
            climateRecordsOLD.setTempJul(climateRecordsOLD.getTempJul().add(temperaturesBy.getTemperatureRecordList().get(i).getJul()));
            climateRecordsOLD.setTempAug(climateRecordsOLD.getTempAug().add(temperaturesBy.getTemperatureRecordList().get(i).getAug()));
            climateRecordsOLD.setTempSep(climateRecordsOLD.getTempSep().add(temperaturesBy.getTemperatureRecordList().get(i).getSep()));
            climateRecordsOLD.setTempOkt(climateRecordsOLD.getTempOkt().add(temperaturesBy.getTemperatureRecordList().get(i).getOct()));
            climateRecordsOLD.setTempNov(climateRecordsOLD.getTempNov().add(temperaturesBy.getTemperatureRecordList().get(i).getNov()));
            climateRecordsOLD.setTempDez(climateRecordsOLD.getTempDez().add(temperaturesBy.getTemperatureRecordList().get(i).getDec()));
        }


        // // Divide to get the average

        BigDecimal bdPeriod = new BigDecimal(period);
        climateRecordsOLD.setTempJan(climateRecordsOLD.getTempJan().divide(bdPeriod,4, RoundingMode.DOWN));
        climateRecordsOLD.setTempFeb(climateRecordsOLD.getTempFeb().divide(bdPeriod,4, RoundingMode.DOWN));
        climateRecordsOLD.setTempMar(climateRecordsOLD.getTempMar().divide(bdPeriod,4, RoundingMode.DOWN));
        climateRecordsOLD.setTempApr(climateRecordsOLD.getTempApr().divide(bdPeriod,4, RoundingMode.DOWN));
        climateRecordsOLD.setTempMai(climateRecordsOLD.getTempMai().divide(bdPeriod,4, RoundingMode.DOWN));
        climateRecordsOLD.setTempJun(climateRecordsOLD.getTempJun().divide(bdPeriod,4, RoundingMode.DOWN));
        climateRecordsOLD.setTempJul(climateRecordsOLD.getTempJul().divide(bdPeriod,4, RoundingMode.DOWN));
        climateRecordsOLD.setTempAug(climateRecordsOLD.getTempAug().divide(bdPeriod,4, RoundingMode.DOWN));
        climateRecordsOLD.setTempSep(climateRecordsOLD.getTempSep().divide(bdPeriod,4, RoundingMode.DOWN));
        climateRecordsOLD.setTempOkt(climateRecordsOLD.getTempOkt().divide(bdPeriod,4, RoundingMode.DOWN));
        climateRecordsOLD.setTempNov(climateRecordsOLD.getTempNov().divide(bdPeriod,4, RoundingMode.DOWN));
        climateRecordsOLD.setTempDez(climateRecordsOLD.getTempDez().divide(bdPeriod,4, RoundingMode.DOWN));

        return climateRecordsOLD;
    }

}
