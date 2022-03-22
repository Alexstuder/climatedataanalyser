package ch.studer.germanclimatedataanalyser.controller;

import ch.studer.germanclimatedataanalyser.generate.test.data.StationTestData;
import ch.studer.germanclimatedataanalyser.model.database.Station;
import ch.studer.germanclimatedataanalyser.model.database.StationClimate;
import ch.studer.germanclimatedataanalyser.model.dto.ClimateAnalyserRequestDto;
import ch.studer.germanclimatedataanalyser.model.dto.ClimateAnalyserResponseDto;
import ch.studer.germanclimatedataanalyser.model.dto.TemperatureForMonthDto;
import ch.studer.germanclimatedataanalyser.model.dto.climaterecords.ClimateRecord;
import ch.studer.germanclimatedataanalyser.model.dto.climaterecords.ClimateRecordsDto;
import ch.studer.germanclimatedataanalyser.service.db.ClimateService;
import ch.studer.germanclimatedataanalyser.service.db.StationService;
import ch.studer.germanclimatedataanalyser.service.ui.analytics.ClimateAnalyserService;
import ch.studer.germanclimatedataanalyser.service.ui.climateRecords.ClimateRecordService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static ch.studer.germanclimatedataanalyser.generate.test.data.ClimateTestData.getStationClimateByStationAndBeginYearByAndNumber;

@SpringBootTest
@TestPropertySource(locations = "classpath:test-it.properties")
@Sql({"classpath:schema.sql"})
class ClimatesTest {

    @Autowired
    private ClimateRecordService climateRecordService;

    @Autowired
    private ClimateAnalyserService climateAnalyserService;

    @Autowired
    StationService stationService;

    @Autowired
    ClimateService climateService;

    private static final String BUNDESLAND = "testStation";
    private static final String startYear = "1900";
    private static final String endYear = "1910";

    @BeforeEach
    void setUp() {

        List<Station> beforTestStation = StationTestData.getStations(10, "beforTestStation");
        List<Station> testStation = StationTestData.getStations(10, BUNDESLAND);
        List<Station> afterTestStation = StationTestData.getStations(10, "afterTestStation");

        stationService.saveAllStation(beforTestStation);
        stationService.saveAllStation(testStation);
        stationService.saveAllStation(afterTestStation);

        List<StationClimate> beforeStationClimates = getStationClimateByStationAndBeginYearByAndNumber(beforTestStation, Integer.parseInt(startYear), Integer.parseInt(endYear) - Integer.parseInt(startYear));
        List<StationClimate> testStationClimates = getStationClimateByStationAndBeginYearByAndNumber(testStation, Integer.parseInt(startYear), Integer.parseInt(endYear) - Integer.parseInt(startYear));
        List<StationClimate> afterStationClimates = getStationClimateByStationAndBeginYearByAndNumber(afterTestStation, Integer.parseInt(startYear), Integer.parseInt(endYear) - Integer.parseInt(startYear));

        climateService.saveAllClimateAtStationId(beforeStationClimates);
        climateService.saveAllClimateAtStationId(testStationClimates);
        climateService.saveAllClimateAtStationId(afterStationClimates);

    }

    @Test
    void climateByClimateAnalyserRequest() {

        System.out.println("Test here !");

        ClimateAnalyserResponseDto responseDto = climateAnalyserService.getClimateAnalyticsByClimateAnalyserRequest(getClimateAnalyserRequestDto());

        ClimateRecordsDto climateRecordsDto = climateRecordService.getClimateRecords(BUNDESLAND, "0", "0", "0", "0", startYear, endYear);

        assertClimateRecords(responseDto.getClimateHistoryAverageDtos().get(0).getClimates(), climateRecordsDto.getClimateRecordList().get(0));

    }

    private void assertClimateRecords(TemperatureForMonthDto climates, ClimateRecord climateRecordList) {
        Assertions.assertEquals(climates.getJanuar(), climateRecordList.getJanuar());
        Assertions.assertEquals(climates.getFebruar(), climateRecordList.getFebruar());
        Assertions.assertEquals(climates.getMaerz(), climateRecordList.getMaerz());
        Assertions.assertEquals(climates.getApril(), climateRecordList.getApril());
        Assertions.assertEquals(climates.getMai(), climateRecordList.getMai());
        Assertions.assertEquals(climates.getJuni(), climateRecordList.getJuni());
        Assertions.assertEquals(climates.getJuli(), climateRecordList.getJuli());
        Assertions.assertEquals(climates.getAugust(), climateRecordList.getAugust());
        Assertions.assertEquals(climates.getSeptember(), climateRecordList.getSeptember());
        Assertions.assertEquals(climates.getOktober(), climateRecordList.getOktober());
        Assertions.assertEquals(climates.getNovember(), climateRecordList.getNovember());
        Assertions.assertEquals(climates.getDezember(), climateRecordList.getDezember());
    }

    private ClimateAnalyserRequestDto getClimateAnalyserRequestDto() {
        ClimateAnalyserRequestDto climateAnalyserRequestDto = new ClimateAnalyserRequestDto();
        climateAnalyserRequestDto.setBundesland(BUNDESLAND);
        climateAnalyserRequestDto.setYearOrigine(startYear);
        climateAnalyserRequestDto.setYearToCompare(endYear);

        return climateAnalyserRequestDto;
    }
}
