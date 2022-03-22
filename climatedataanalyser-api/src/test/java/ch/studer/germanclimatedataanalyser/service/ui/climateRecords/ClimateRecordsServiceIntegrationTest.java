package ch.studer.germanclimatedataanalyser.service.ui.climateRecords;

import ch.studer.germanclimatedataanalyser.generate.test.data.ClimateTestData;
import ch.studer.germanclimatedataanalyser.model.dto.climaterecords.ClimateRecordsDto;
import ch.studer.germanclimatedataanalyser.service.db.ClimateService;
import org.junit.Before;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;

@SpringBootTest
@TestPropertySource(locations = "classpath:test-it.properties")
@Sql({"classpath:schema.sql", "classpath:ClimateRecordService/climates.sql", "classpath:ClimateRecordService/stations.sql"})

public class ClimateRecordsServiceIntegrationTest {
    @Autowired
    ClimateRecordService climateRecordService;

    @Autowired
    ClimateService climateService;

    @Before
    public void setup() {
        climateService.saveAllClimateAtStationId(ClimateTestData.getStationClimateOrderByStationIdAndBeginYearRemoveLater(1879, 2020, 10));
    }


    @Test
    void testClimateRecordServiceWithBundesland() {

        final String BUNDESLAND = "Baden-Württemberg";
        final String GPS_LAT_1 = "0";
        final String GPS_LONG_1 = "0";
        final String GPS_LAT_2 = "0";
        final String GPS_LONG_2 = "0";
        final String YEAR = "1989";
        final String YEAR_DISTANCE = "5";

        ClimateRecordsDto climateRecordsDto = climateRecordService.getClimateRecords(BUNDESLAND, GPS_LAT_1, GPS_LONG_1, GPS_LAT_2, GPS_LONG_2, YEAR, YEAR_DISTANCE);
        assertRecords(climateRecordsDto);
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

        ClimateRecordsDto climateRecordsGPSDto = climateRecordService.getClimateRecords(BUNDESLAND, GPS_LAT_1, GPS_LONG_1, GPS_LAT_2, GPS_LONG_2, YEAR_FROM, YEAR_DISTANCE);
        assertRecords(climateRecordsGPSDto);

    }

    @Test
    void testClimateRecordServiceOnlyFirstRecord() {

        final String BUNDESLAND = "Nordrhein-Westfalen";
        final String GPS_LAT_1 = "40";
        final String GPS_LONG_1 = "9";
        final String GPS_LAT_2 = "0";
        final String GPS_LONG_2 = "9.999";
        final String YEAR_FROM = "1850";
        final String YEAR_DISTANCE = "5";

        ClimateRecordsDto climateRecordsFirstDto = climateRecordService.getClimateRecords(BUNDESLAND, GPS_LAT_1, GPS_LONG_1, GPS_LAT_2, GPS_LONG_2, YEAR_FROM, YEAR_DISTANCE);
        Assertions.assertEquals(1, climateRecordsFirstDto.getClimateRecordList().size());
        Assertions.assertEquals("1850 - 1879", climateRecordsFirstDto.getClimateRecordList().get(0).getHeader());
    }

    @Test
    void testClimateRecordServiceOnlyLastRecord() {

        final String BUNDESLAND = "";
        final String GPS_LAT_1 = "3";
        final String GPS_LONG_1 = "0";
        final String GPS_LAT_2 = "0";
        final String GPS_LONG_2 = "3";
        final String YEAR_FROM = "1850";
        final String YEAR_DISTANCE = "5";

        ClimateRecordsDto climateRecordsLastDto = climateRecordService.getClimateRecords(BUNDESLAND, GPS_LAT_1, GPS_LONG_1, GPS_LAT_2, GPS_LONG_2, YEAR_FROM, YEAR_DISTANCE);

        Assertions.assertEquals(1, climateRecordsLastDto.getClimateRecordList().size());
        Assertions.assertEquals("1991 - 2020", climateRecordsLastDto.getClimateRecordList().get(0).getHeader());

    }

    void assertRecords(ClimateRecordsDto climateRecordsDto) {

        Assertions.assertEquals(15, climateRecordsDto.getClimateRecordList().size());

        Assertions.assertEquals("1960 - 1989", climateRecordsDto.getClimateRecordList().get(0).getHeader());
        Assertions.assertEquals("1965 - 1994", climateRecordsDto.getClimateRecordList().get(2).getHeader());
        Assertions.assertEquals("1970 - 1999", climateRecordsDto.getClimateRecordList().get(4).getHeader());
        Assertions.assertEquals("1975 - 2004", climateRecordsDto.getClimateRecordList().get(6).getHeader());
        Assertions.assertEquals("1980 - 2009", climateRecordsDto.getClimateRecordList().get(8).getHeader());
        Assertions.assertEquals("1985 - 2014", climateRecordsDto.getClimateRecordList().get(10).getHeader());
        Assertions.assertEquals("1990 - 2019", climateRecordsDto.getClimateRecordList().get(12).getHeader());
        Assertions.assertEquals("1991 - 2020", climateRecordsDto.getClimateRecordList().get(14).getHeader());

    }

}
