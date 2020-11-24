package ch.studer.germanclimatedataanalyser.service.ui.climateRecords;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;

@SpringBootTest
@TestPropertySource(locations = "classpath:test-it.properties")
@Sql({"classpath:StationClimate/H2Schema.sql","classpath:StationClimate/climates.sql", "classpath:StationClimate/stations.sql"})

public class ClimateRecordsServiceIntegrationTest {
    // Test Scopr
    // 1 Happy Test with Bundesland
    // 1 Happy Test with GPS Coordinates

}
