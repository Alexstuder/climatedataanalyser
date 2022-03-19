package ch.studer.germanclimatedataanalyser.controller;

import ch.studer.germanclimatedataanalyser.dao.StationClimateDAO;
import ch.studer.germanclimatedataanalyser.dao.StationDAO;
import ch.studer.germanclimatedataanalyser.generate.test.data.StationTestData;
import ch.studer.germanclimatedataanalyser.model.database.Station;
import ch.studer.germanclimatedataanalyser.model.database.StationClimate;
import ch.studer.germanclimatedataanalyser.model.dto.ClimateAnalyserRequestDto;
import ch.studer.germanclimatedataanalyser.model.dto.ClimateAnalyserResponseDto;
import ch.studer.germanclimatedataanalyser.model.dto.climaterecords.ClimateRecordsDto;
import ch.studer.germanclimatedataanalyser.service.ui.analytics.ClimateAnalyserService;
import ch.studer.germanclimatedataanalyser.service.ui.climateRecords.ClimateRecordService;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

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
    StationDAO stationDAO;

    @Autowired
    StationClimateDAO stationClimateDAO;

    private static final String BUNDESLAND = "testStation";
    private static final String startYear = "1900";
    private static final String endYear = "1910";

    @Before
    public void getTestData() {

        List<Station> beforTestStation = StationTestData.getStations(10, "beforTestStation");
        List<Station> testStation = StationTestData.getStations(10, BUNDESLAND);
        List<Station> afterTestStation = StationTestData.getStations(10, "afterTestStation");

        stationDAO.saveAll(beforTestStation);
        stationDAO.saveAll(testStation);
        stationDAO.saveAll(afterTestStation);

        List<StationClimate> beforeStationClimates = getStationClimateByStationAndBeginYearByAndNumber(beforTestStation, Integer.valueOf(startYear), Integer.valueOf(endYear) - Integer.valueOf(startYear));
        List<StationClimate> testStationClimates = getStationClimateByStationAndBeginYearByAndNumber(testStation, Integer.valueOf(startYear), Integer.valueOf(endYear) - Integer.valueOf(startYear));
        List<StationClimate> afterStationClimates = getStationClimateByStationAndBeginYearByAndNumber(afterTestStation, Integer.valueOf(startYear), Integer.valueOf(endYear) - Integer.valueOf(startYear));

        stationClimateDAO.saveAll(beforeStationClimates);
        stationClimateDAO.saveAll(testStationClimates);
        stationClimateDAO.saveAll(afterStationClimates);

    }

    @Test
    @Transactional
    void climateByClimateAnalyserRequest() {
        getTestData();
        System.out.println("Test here !");

        ClimateAnalyserResponseDto responseDto = climateAnalyserService.getClimateAnalyticsByClimateAnalyserRequest(getClimateAnalyserRequestDto());

        ClimateRecordsDto climateRecordsDto = climateRecordService.getClimateRecords(BUNDESLAND, "0", "0", "0", "0", startYear, endYear);

        List<Station> stations = stationDAO.getStationsFromBundesland(BUNDESLAND);
        System.out.println("Test here !");


    }

    private ClimateAnalyserRequestDto getClimateAnalyserRequestDto() {
        ClimateAnalyserRequestDto climateAnalyserRequestDto = new ClimateAnalyserRequestDto();
        climateAnalyserRequestDto.setBundesland(BUNDESLAND);
        climateAnalyserRequestDto.setYearOrigine(startYear);
        climateAnalyserRequestDto.setYearToCompare(endYear);

        return climateAnalyserRequestDto;
    }
}
