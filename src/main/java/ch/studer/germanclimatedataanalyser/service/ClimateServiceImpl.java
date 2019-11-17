package ch.studer.germanclimatedataanalyser.service;

import ch.studer.germanclimatedataanalyser.model.ClimateAtStation;
import ch.studer.germanclimatedataanalyser.model.ClimateRecord;
import ch.studer.germanclimatedataanalyser.model.TemperatureByStationId;
import ch.studer.germanclimatedataanalyser.model.TemperatureRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.util.ArrayList;
import java.util.List;

public class ClimateServiceImpl implements ClimateService {


    @Autowired
    TemperaturesAtStationService temperaturesAtStationService;

    @Value("#{new Integer('${climate.calculation.period.year}')}")
    int period;

    // Inludes all Cimate Records for a Station
    ClimateAtStation climateAtStation ;


    @Override
    public ClimateAtStation getClimateAtStationId(int stationId) {
         climateAtStation = new ClimateAtStation(stationId);

        climateAtStation.setClimateRecords(getAllClimateRecordsForStation(temperaturesAtStationService.getTemperaturesBy(stationId)));

        // Print all Climate Records
        climateAtStation.print();


        return climateAtStation;
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
      ClimateRecord climateRecords = new ClimateRecord(temperaturesBy.getTemperatureRecordList().get(start).getYear(),temperaturesBy.getTemperatureRecordList().get(end).getYear());

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
