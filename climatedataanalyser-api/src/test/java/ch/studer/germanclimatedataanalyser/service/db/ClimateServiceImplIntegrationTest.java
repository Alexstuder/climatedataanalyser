package ch.studer.germanclimatedataanalyser.service.db;

import ch.studer.germanclimatedataanalyser.generate.test.data.ClimateTestData;
import ch.studer.germanclimatedataanalyser.generate.test.data.StationTestData;
import ch.studer.germanclimatedataanalyser.model.database.Station;
import ch.studer.germanclimatedataanalyser.model.database.StationClimate;
import ch.studer.germanclimatedataanalyser.model.dto.ClimateAnalyserRequestDto;
import ch.studer.germanclimatedataanalyser.model.dto.ClimateAnalyserResponseDto;
import ch.studer.germanclimatedataanalyser.model.dto.helper.GpsPoint;
import ch.studer.germanclimatedataanalyser.service.ui.analytics.ClimateAnalyserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;

import java.math.BigDecimal;
import java.util.List;

@SpringBootTest
@TestPropertySource(locations = "classpath:test-it.properties")
@Sql({"classpath:schema.sql"})
class ClimateServiceImplIntegrationTest {

    @Autowired
    ClimateService climateService;

    @Autowired
    StationService stationService;


    @Autowired
    ClimateAnalyserService climateAnalyserService;


    private static final Logger LOG = LoggerFactory.getLogger(ClimateServiceImplIntegrationTest.class);

    @BeforeEach
    void setUp() {

        List<StationClimate> stationClimates = ClimateTestData.getStationClimateOrderByStationIdAndBeginYear(14, 2016, 20);

        climateService.saveAllClimateAtStationId(stationClimates);
        //ReflectionTestUtils.setField(climateService,"period",30);

        List<Station> stations = StationTestData.getStations(15, null);
        stationService.saveAllStation(stations);

    }

    @Test
    void getClimateByGpsForClimateRecords() {

        GpsPoint gps1 = new GpsPoint(49, 9);
        GpsPoint gps2 = new GpsPoint(38, 20);
        String fromYear = "1995";
        List<StationClimate> stationClimates = climateService.getClimateForGpsCoordinatesFromYearOrderedByFromYearAndStations(gps1, gps2, fromYear);

        //Check size
        Assertions.assertEquals(10, stationClimates.size());

        //Check Order by startPeriod and Stations
        Assertions.assertEquals("1995", stationClimates.get(0).getStartPeriod());
        Assertions.assertEquals(8, stationClimates.get(0).getStationId());

        Assertions.assertEquals("1995", stationClimates.get(3).getStartPeriod());
        Assertions.assertEquals(11, stationClimates.get(3).getStationId());

        Assertions.assertEquals("1996", stationClimates.get(5).getStartPeriod());
        Assertions.assertEquals(10, stationClimates.get(5).getStationId());


        Assertions.assertEquals("1997", stationClimates.get(8).getStartPeriod());
        Assertions.assertEquals(11, stationClimates.get(8).getStationId());


    }

    @Test
    void getClimateByGps() {

        GpsPoint gps1 = new GpsPoint(49, 9);
        GpsPoint gps2 = new GpsPoint(38, 20);
        List<StationClimate> stationClimates = climateService.getClimateForGpsCoordinates(gps1, gps2);

        ClimateAnalyserRequestDto climateAnalyserRequestDto = new ClimateAnalyserRequestDto();
        climateAnalyserRequestDto.setBundesland("");
        climateAnalyserRequestDto.setYearOrigine("2017");
        climateAnalyserRequestDto.setYearToCompare("2024");

        climateAnalyserRequestDto.setGps1(gps1);
        climateAnalyserRequestDto.setGps2(gps2);

        ClimateAnalyserResponseDto climateAnalyserResponseDto =
                climateAnalyserService.getClimateAnalyticsByClimateAnalyserRequest(climateAnalyserRequestDto);

        Assertions.assertTrue(climateAnalyserResponseDto.getErrorMsg().isEmpty());

        // Assertion the OriginYear
        Assertions.assertEquals(new BigDecimal("6.500"), climateAnalyserResponseDto.getOriginal().getJanuar());
        Assertions.assertEquals(new BigDecimal("7.500"), climateAnalyserResponseDto.getOriginal().getFebruar());
        Assertions.assertEquals(new BigDecimal("8.500"), climateAnalyserResponseDto.getOriginal().getMaerz());
        Assertions.assertEquals(new BigDecimal("9.500"), climateAnalyserResponseDto.getOriginal().getApril());
        Assertions.assertEquals(new BigDecimal("10.500"), climateAnalyserResponseDto.getOriginal().getMai());
        Assertions.assertEquals(new BigDecimal("11.500"), climateAnalyserResponseDto.getOriginal().getJuni());
        Assertions.assertEquals(new BigDecimal("12.500"), climateAnalyserResponseDto.getOriginal().getJuli());
        Assertions.assertEquals(new BigDecimal("13.500"), climateAnalyserResponseDto.getOriginal().getAugust());
        Assertions.assertEquals(new BigDecimal("14.500"), climateAnalyserResponseDto.getOriginal().getSeptember());
        Assertions.assertEquals(new BigDecimal("15.500"), climateAnalyserResponseDto.getOriginal().getOktober());
        Assertions.assertEquals(new BigDecimal("16.500"), climateAnalyserResponseDto.getOriginal().getNovember());
        Assertions.assertEquals(new BigDecimal("17.500"), climateAnalyserResponseDto.getOriginal().getDezember());

        // Assertion the StationIds from the year to compare
        Assertions.assertEquals(new BigDecimal("9.500"), climateAnalyserResponseDto.getCompare().getJanuar());
        Assertions.assertEquals(new BigDecimal("10.500"), climateAnalyserResponseDto.getCompare().getFebruar());
        Assertions.assertEquals(new BigDecimal("11.500"), climateAnalyserResponseDto.getCompare().getMaerz());
        Assertions.assertEquals(new BigDecimal("12.500"), climateAnalyserResponseDto.getCompare().getApril());
        Assertions.assertEquals(new BigDecimal("13.500"), climateAnalyserResponseDto.getCompare().getMai());
        Assertions.assertEquals(new BigDecimal("14.500"), climateAnalyserResponseDto.getCompare().getJuni());
        Assertions.assertEquals(new BigDecimal("15.500"), climateAnalyserResponseDto.getCompare().getJuli());
        Assertions.assertEquals(new BigDecimal("16.500"), climateAnalyserResponseDto.getCompare().getAugust());
        Assertions.assertEquals(new BigDecimal("17.500"), climateAnalyserResponseDto.getCompare().getSeptember());
        Assertions.assertEquals(new BigDecimal("18.500"), climateAnalyserResponseDto.getCompare().getOktober());
        Assertions.assertEquals(new BigDecimal("19.500"), climateAnalyserResponseDto.getCompare().getNovember());
        Assertions.assertEquals(new BigDecimal("20.500"), climateAnalyserResponseDto.getCompare().getDezember());

    }
}
