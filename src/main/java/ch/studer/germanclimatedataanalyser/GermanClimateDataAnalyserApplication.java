package ch.studer.germanclimatedataanalyser;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
public class GermanClimateDataAnalyserApplication {

	public static void main(String[] args)
	{
		SpringApplication.run(GermanClimateDataAnalyserApplication.class, args);
	}

}
