package ch.studer.germanclimatedataanalyser.service.ui.climateRecords;

import ch.studer.germanclimatedataanalyser.model.dto.climaterecords.ClimateRecordsDto;

public interface ClimateRecordService {

    public ClimateRecordsDto getClimateRecords(String bundesland
            , String gps1Lat
            , String gps1Long
            , String gps2Lat
            , String gps2Long
            , String yearFrom
            , String yearTo
    );

}
