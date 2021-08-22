package ch.studer.germanclimatedataanalyser.controller;

import ch.studer.germanclimatedataanalyser.model.dto.climaterecords.ClimateRecordsDto;
import ch.studer.germanclimatedataanalyser.service.ui.climateRecords.ClimateRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/climateRecords")
@CrossOrigin
public class Climates {

    @Autowired
    private ClimateRecordService climateRecordService;


    @GetMapping("/")
    ClimateRecordsDto climateByClimateAnalyserRequest(
            final @RequestParam(name = "bundesland") String bundesland,
            final @RequestParam(name = "gps1.lat") String gps1Lat,
            final @RequestParam(name = "gps1.long") String gps1Long,
            final @RequestParam(name = "gps2.lat") String gps2Lat,
            final @RequestParam(name = "gps2.long") String gps2Long,
            final @RequestParam(name = "startYear") String startYear,
            final @RequestParam(name = "distanceYear") String distanceYear
    ) {
        return this.climateRecordService.getClimateRecords(
                bundesland, gps1Lat, gps1Long, gps2Lat, gps2Long, startYear, distanceYear);
    }


}
