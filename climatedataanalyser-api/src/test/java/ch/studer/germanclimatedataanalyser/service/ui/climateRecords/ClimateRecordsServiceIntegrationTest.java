package ch.studer.germanclimatedataanalyser.service.ui.climateRecords;

import ch.studer.germanclimatedataanalyser.generate.test.data.ClimateTestData;
import ch.studer.germanclimatedataanalyser.model.database.StationClimate;
import ch.studer.germanclimatedataanalyser.model.dto.climaterecords.ClimateRecordsDto;
import ch.studer.germanclimatedataanalyser.service.db.ClimateService;
import org.junit.Before;
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

        climateService.saveAllClimateAtStationId(ClimateTestData.getStationClimateOrderByStationIdAndBeginYearRemoveLater(1879, 2020,10));

    }


    @Test
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
        climateRecordsDto = climateRecordService.getClimateRecords(BUNDESLAND, GPS_LAT_1, GPS_LONG_1, GPS_LAT_2, GPS_LONG_2, YEAR_FROM, YEAR_DISTANCE);
       // climateRecordsDto.getClimateRecordList().size();

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
