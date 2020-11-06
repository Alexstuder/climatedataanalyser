package ch.studer.germanclimatedataanalyser.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/status")
@CrossOrigin
public class controller {


    private static final Logger LOG = LoggerFactory.getLogger(controller.class);


    @GetMapping("/")
    public List<String> handle() throws Exception {
        List<String> status = new ArrayList<String>();

        LOG.debug("Server is Running !");
        status.add("Server is running !");
        return status;

    }
//
//        @Autowired
//        JobLauncher jobLauncher;
//
//         @Autowired
//         Job job;

//         @RequestMapping("/batchImportStart")
//         public void handle() throws Exception{
//         JobParameters jobParameters =
//                new JobParametersBuilder()
//                        .addLong("time",System.currentTimeMillis())
//                        .toJobParameters();
//          jobLauncher.run(job, jobParameters);
//         }


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
