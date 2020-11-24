package ch.studer.germanclimatedataanalyser.service.ui.climateRecords;

import ch.studer.germanclimatedataanalyser.model.dto.climaterecords.ClimateRecordsDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;

@SpringBootTest
@TestPropertySource(locations = "classpath:test-it.properties")
@Sql({"classpath:ClimateRecordService/H2Schema.sql","classpath:ClimateRecordService/climates.sql", "classpath:ClimateRecordService/stations.sql"})
//@Sql({"classpath:ClimateRecordService/H2Schema.sql", "classpath:ClimateRecordService/climates.sql", "classpath:ClimateRecordService/stations.sql"})

public class ClimateRecordsServiceIntegrationTest {
    // Test Scopr
    // 1 Happy Test with Bundesland
    // 1 Happy Test with GPS Coordinates


    @Autowired
    ClimateRecordService climateRecordService;


    @Test
    void testClimateRecordServiceWithBundesland() {

        final String BUNDESLAND = "Baden-Württemberg";
        final String GPS_LAT_1 = "0";
        final String GPS_LONG_1 = "0";
        final String GPS_LAT_2 = "0";
        final String GPS_LONG_2 = "0";
        final String YEAR_FROM = "1960";
        final String YEAR_DISTANCE = "1";

        ClimateRecordsDto climateRecordsDto = climateRecordService.getClimateRecords(BUNDESLAND, GPS_LAT_1, GPS_LONG_1, GPS_LAT_2, GPS_LONG_2, YEAR_FROM, YEAR_DISTANCE);
       // climateRecordsDto.getClimateRecordList().get(99999);

    }

    @Test
    void testClimateRecordServiceWithCoordinates() {

        final String BUNDESLAND = "";
        final String GPS_LAT_1 = "20";
        final String GPS_LONG_1 = "10";
        final String GPS_LAT_2 = "10";
        final String GPS_LONG_2 = "20";
        final String YEAR_FROM = "1960";
        final String YEAR_DISTANCE = "5";

        ClimateRecordsDto climateRecordsDto = climateRecordService.getClimateRecords(BUNDESLAND, GPS_LAT_1, GPS_LONG_1, GPS_LAT_2, GPS_LONG_2, YEAR_FROM, YEAR_DISTANCE);


    }

}
