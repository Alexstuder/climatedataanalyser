package ch.studer.germanclimatedataanalyser.service.ui.climateRecords;

import ch.studer.germanclimatedataanalyser.generate.test.data.ClimateTestData;
import ch.studer.germanclimatedataanalyser.model.database.StationClimate;
import ch.studer.germanclimatedataanalyser.model.dto.climaterecords.ClimateRecordsDto;
import ch.studer.germanclimatedataanalyser.service.db.ClimateService;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@TestPropertySource(locations = "classpath:test-it.properties")
@Sql({"classpath:ClimateRecordService/H2Schema.sql", "classpath:ClimateRecordService/climates.sql", "classpath:ClimateRecordService/stations.sql"})
//@Sql({"classpath:ClimateRecordService/H2Schema.sql", "classpath:ClimateRecordService/climates.sql", "classpath:ClimateRecordService/stations.sql"})

public class ClimateRecordsServiceIntegrationTest {
    // Test Scope

    // 1 Happy Test with Bundesland and some Records calculated with diff.
    // 1 Happy Test with Bundesland and only one Record calculated without diff. first record on table

    // 1 Happy Test with GPS Coordinates and some Records calculated with diff.
    // 1 Happy Test with GPS Coordinates only one Record calculated without diff. last record on table


    @Autowired
    ClimateRecordService climateRecordService;

    @Autowired
    ClimateService climateService;

    @Before
    void setup() {

        // Stations 1-5 = Valid / Station 6-9 = notValid
        // YearFrom 1880 - 1930

        List<StationClimate> stationClimates = new ArrayList<StationClimate>();
        StationClimate stationClimate = new StationClimate();
        stationClimate.setEndPeriod("1900");
        stationClimate.setEndPeriod("1929");
        stationClimate.setJanuar(new BigDecimal("1"));
        stationClimate.setFebruar(new BigDecimal("1"));
        stationClimate.setMaerz(new BigDecimal("1"));
        stationClimate.setApril(new BigDecimal("1"));
        stationClimate.setMai(new BigDecimal("1"));
        stationClimate.setJuni(new BigDecimal("1"));
        stationClimate.setJuli(new BigDecimal("1"));
        stationClimate.setAugust(new BigDecimal("1"));
        stationClimate.setSeptember(new BigDecimal("1"));
        stationClimate.setOktober(new BigDecimal("1"));
        stationClimate.setNovember(new BigDecimal("1"));
        stationClimate.setDezember(new BigDecimal("1"));

        stationClimates.add(stationClimate);

        climateService.saveAllClimateAtStationId(ClimateTestData.getStationClimateOrderByStationIdAndBeginYearRemoveLater(1879, 2020, 10));

    }


    //@Test
    @Ignore
    void testClimateRecordServiceWithBundesland() {

        //setup();

        final String BUNDESLAND = "Baden-Württemberg";
        final String GPS_LAT_1 = "0";
        final String GPS_LONG_1 = "0";
        final String GPS_LAT_2 = "0";
        final String GPS_LONG_2 = "0";
        final String YEAR_FROM = "1960";
        final String YEAR_DISTANCE = "5";

        ClimateRecordsDto climateRecordsDto = climateRecordService.getClimateRecords(BUNDESLAND, GPS_LAT_1, GPS_LONG_1, GPS_LAT_2, GPS_LONG_2, YEAR_FROM, YEAR_DISTANCE);
        assertRecords(climateRecordsDto);
    }

    // @Test
    @Ignore
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
        Assertions.assertEquals(1,climateRecordsFirstDto.getClimateRecordList().size());
        Assertions.assertEquals("1850 - 1879",climateRecordsFirstDto.getClimateRecordList().get(0).getHeader());
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

        Assertions.assertEquals(1,climateRecordsLastDto.getClimateRecordList().size());
        Assertions.assertEquals("1991 - 2020",climateRecordsLastDto.getClimateRecordList().get(0).getHeader());

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
