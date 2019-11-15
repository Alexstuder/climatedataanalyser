package ch.studer.germanclimatedataanalyser.controller;

import ch.studer.germanclimatedataanalyser.service.ClimateAtStationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class controller {

        @Autowired
        ClimateAtStationService climateAtStationService;


        @RequestMapping("/run/{stationId}")
        String run(@PathVariable int stationId) {
            climateAtStationService.getClimateDataBy(stationId);
            return "Hello, " + stationId + "!";
        }

       @RequestMapping("/runAll")
        String run() {
            climateAtStationService.getClimateDataAll();
            return "Run All, ";
        }

        @RequestMapping("/hello/{name}")
        String hello(@PathVariable String name) {
            return "Hello, " + name + "!";
        }
}
