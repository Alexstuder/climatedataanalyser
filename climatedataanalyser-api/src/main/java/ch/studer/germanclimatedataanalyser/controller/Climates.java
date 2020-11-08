package ch.studer.germanclimatedataanalyser.controller;


import ch.studer.germanclimatedataanalyser.model.dto.climaterecords.ClimateRecord;
import ch.studer.germanclimatedataanalyser.model.dto.climaterecords.ClimateRecordsDto;
import ch.studer.germanclimatedataanalyser.service.ui.climateRecords.ClimateRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/climateRecords")
@CrossOrigin
public class Climates {

    @Autowired
    ClimateRecordService climateRecordService;


    @GetMapping("/")
    ClimateRecordsDto climateByClimateAnalyserRequest(
            @RequestParam(name = "bundesland") String bundesland,
            @RequestParam(name = "gps1.lat") String gps1Lat,
            @RequestParam(name = "gps1.long") String gps1Long,
            @RequestParam(name = "gps2.lat") String gps2Lat,
            @RequestParam(name = "gps2.long") String gps2Long,
            @RequestParam(name = "startYear") String startYear,
            @RequestParam(name = "distanceYear") String distanceYear
    ) {
        return this.climateRecordService.getClimateRecords(bundesland, gps1Lat, gps1Long, gps2Lat, gps2Long, startYear, distanceYear);
    }


}
