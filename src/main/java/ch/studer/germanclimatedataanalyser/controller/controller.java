package ch.studer.germanclimatedataanalyser.controller;

import ch.studer.germanclimatedataanalyser.service.ClimateService;
import ch.studer.germanclimatedataanalyser.service.TemperaturesAtStationService;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
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

        @Autowired
        JobLauncher jobLauncher;

         @Autowired
         Job job;

         @RequestMapping("/runit")
         public void handle() throws Exception{
         JobParameters jobParameters =
                new JobParametersBuilder()
                        .addLong("time",System.currentTimeMillis()).toJobParameters();
          jobLauncher.run(job, jobParameters);
         }


        /*@RequestMapping("/run/{stationId}")
        String run(@PathVariable int stationId) {
            temperaturesAtStationService.getTemperaturesBy(stationId);
            return "Hello, " + stationId + "!";
        }*/

        @RequestMapping("/climateAtStation/{stationId}")
        String run(@PathVariable int stationId) {
            climateService.getDifference(stationId);
           // climateService.getClimateAtStationId(stationId);
            return "Climate, " + stationId + "!";
        }
        @RequestMapping("/climateByStationName/{stationName}")
        String run(@PathVariable String stationName) {
            climateService.getClimateByStationName(stationName);
           // climateService.getClimateAtStationId(stationId);
            return "Climate, " + stationName + "!";
        }
        @RequestMapping("/climate/ByBundesland/{bundesland}")
        String climateByBundesland(@PathVariable String bundesland) {
            climateService.getClimateByBundesland(bundesland);
            return "Climate, " + bundesland + "!";
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
