package ch.studer.germanclimatedataanalyser.controller;

import ch.studer.germanclimatedataanalyser.model.ClimateAtStation;
import ch.studer.germanclimatedataanalyser.service.ClimateService;
import ch.studer.germanclimatedataanalyser.service.TemperaturesAtStationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class controller {

        @Autowired
        TemperaturesAtStationService temperaturesAtStationService;

        @Autowired
        ClimateService  climateService;


        /*@RequestMapping("/run/{stationId}")
        String run(@PathVariable int stationId) {
            temperaturesAtStationService.getTemperaturesBy(stationId);
            return "Hello, " + stationId + "!";
        }*/

        @RequestMapping("/climatAtStation/{stationId}")
        String run(@PathVariable int stationId) {
            climateService.getClimateAtStationId(stationId);
            return "Climate, " + stationId + "!";
        }

       @RequestMapping("/runAll")
        String run() {
            temperaturesAtStationService.getTemperaturesForAll();
            return "Run All, ";
        }

        @RequestMapping("/hello/{name}")
        String hello(@PathVariable String name) {
            return "Hello, " + name + "!";
        }
}
