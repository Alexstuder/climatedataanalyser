package ch.studer.germanclimatedataanalyser.model;

import ch.studer.germanclimatedataanalyser.service.StationService;
import ch.studer.germanclimatedataanalyser.service.TemperaturesAtStationService;
import javassist.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.NoResultException;
import java.util.ArrayList;
import java.util.List;

public class ClimateAtStation extends Climate {


    // StationsId
    private Station station;

    @Autowired
    StationService stationService;

    @Autowired
    TemperaturesAtStationService temperaturesAtStationService;

    private static final Logger log = LoggerFactory.getLogger(ClimateAtStation.class);
    // ####################################
    // # Constructor
    // ####################################
    public ClimateAtStation(){
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

    public List<ClimateRecord> getClimateRecords() {
        return super.getClimateRecords();
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

    private List<ClimateRecord> getAllClimateRecordsForStation() {

        TemperatureByStationId temperaturesBy = temperaturesAtStationService.getTemperaturesBy(station.getStationId());
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

}
