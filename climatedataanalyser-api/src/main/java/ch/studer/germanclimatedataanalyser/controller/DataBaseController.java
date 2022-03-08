package ch.studer.germanclimatedataanalyser.controller;

import ch.studer.germanclimatedataanalyser.model.dto.db.DbLoadResponseDto;
import ch.studer.germanclimatedataanalyser.service.ui.dbController.DbLoadInformationService;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
@RestController
@RequestMapping("/api/database")
@CrossOrigin
public class DataBaseController {

    @Autowired
    JobLauncher jobLauncher;

    @Autowired
    DbLoadInformationService dbLoadInformationService;

    @Autowired
    Job job;

    @GetMapping("/batchImportStart")
    public void handle(@RequestParam(value = "withFTP", defaultValue = "false") String withFTP) throws Exception {


        JobParameters jobParameters =
                new JobParametersBuilder()
                        .addLong("time", System.currentTimeMillis())
                        .addString("withFTP", withFTP)
                        .toJobParameters();
        jobLauncher.run(job, jobParameters);

    }

    @GetMapping("/")
    DbLoadResponseDto dbLoadInformationRequest() {
        return this.dbLoadInformationService.getDbLoadInformation();
    }

}



