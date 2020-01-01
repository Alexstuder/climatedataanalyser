package ch.studer.germanclimatedataanalyser.controller;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class controller {

        @Autowired
        JobLauncher jobLauncher;

         @Autowired
         Job job;

         @RequestMapping("/batchImportStart")
         public void handle() throws Exception{
         JobParameters jobParameters =
                new JobParametersBuilder()
                        .addLong("time",System.currentTimeMillis())
                        .toJobParameters();
          jobLauncher.run(job, jobParameters);
         }


         // TODO Remove commented code .... when implemented some other REST
//        @RequestMapping("/climateByStationId/{stationId}")
//        String climateByStationId(@PathVariable String stationId) throws Exception {
//            climateServiceOLD.getClimateAtStationId(stationId);
//           // climateServiceOLD.getClimateAtStationId(stationId);
//            return "Climate_OLD, " + stationId + "!";
//        }
//        @RequestMapping("/climateByStationName/{stationName}")
//        String climateByStationName(@PathVariable String stationName) throws Exception {
//            climateServiceOLD.getClimateAtStationId(stationName);
//           // climateServiceOLD.getClimateAtStationId(stationId);
//            return "Climate_OLD, " + stationName + "!";
//        }
//        @RequestMapping("/climate/ByBundesland/{bundesland}")
//        String climateByBundesland(@PathVariable String bundesland) {
//            climateServiceOLD.getClimateByBundesland(bundesland);
//            return "Climate_OLD, " + bundesland + "!";
//        }

//
//        @RequestMapping("/hello/{name}")
//        String hello(@PathVariable String name) {
//            return "Hello, " + name + "!";
//        }
}
