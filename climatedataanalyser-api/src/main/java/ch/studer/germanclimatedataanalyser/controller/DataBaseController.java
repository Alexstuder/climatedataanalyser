package ch.studer.germanclimatedataanalyser.controller;

import ch.studer.germanclimatedataanalyser.model.dto.DbLoadResponseDto;
import ch.studer.germanclimatedataanalyser.service.ui.dbController.DbLoadInformationService;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    public void handle() throws Exception {
        JobParameters jobParameters =
                new JobParametersBuilder()
                        .addLong("time", System.currentTimeMillis())
                        .toJobParameters();
        jobLauncher.run(job, jobParameters);

    }

    @PostMapping("/")
    DbLoadResponseDto dbLoadInformationRequest() {
        DbLoadResponseDto r = this.dbLoadInformationService.getDbLoadInformation();
        return r;
    }

}



