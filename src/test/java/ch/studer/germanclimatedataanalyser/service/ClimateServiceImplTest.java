package ch.studer.germanclimatedataanalyser.service;

import ch.studer.germanclimatedataanalyser.dao.StationClimateDAO;
import ch.studer.germanclimatedataanalyser.dao.StationWeatherDAO;
import ch.studer.germanclimatedataanalyser.generate.test.data.StationWeatherPerYearTestData;
import ch.studer.germanclimatedataanalyser.model.database.StationClimate;
import ch.studer.germanclimatedataanalyser.model.database.StationWeatherPerYear;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.util.ReflectionTestUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ClimateServiceImplTest {


    @Mock
    StationClimateDAO stationClimateDAO;

    @InjectMocks
    ClimateServiceImpl climateService;

    private static final Logger LOG = LoggerFactory.getLogger(ClimateServiceImplTest.class);
    @BeforeEach
    void setUp() {

        ReflectionTestUtils.setField(climateService,"period",30);
    }

    @Test
    void get1ClimateRecordForStation() {

        List<StationWeatherPerYear> stationWeatherPerYearList = StationWeatherPerYearTestData.getStationWeatherPerYearList("2018",1,false);

        List<StationClimate> stationClimates = climateService.getClimateForStation(stationWeatherPerYearList);


        Assertions.assertTrue(stationClimates.size()==1);
        for (StationClimate stationClimate : stationClimates){
            Assertions.assertTrue(stationClimate.getJanuar().compareTo(new BigDecimal("-1.111"))==0);
            Assertions.assertTrue(stationClimate.getFebruar().compareTo(new BigDecimal("-2.222"))==0);
            Assertions.assertTrue(stationClimate.getMaerz().compareTo(new BigDecimal("3.333"))==0);
            Assertions.assertTrue(stationClimate.getApril().compareTo(new BigDecimal("4.444"))==0);
            Assertions.assertTrue(stationClimate.getMai().compareTo(new BigDecimal("5.555"))==0);
            Assertions.assertTrue(stationClimate.getJuni().compareTo(new BigDecimal("6.666"))==0);
            Assertions.assertTrue(stationClimate.getJuli().compareTo(new BigDecimal("7.777"))==0);
            Assertions.assertTrue(stationClimate.getAugust().compareTo(new BigDecimal("8.888"))==0);
            Assertions.assertTrue(stationClimate.getSeptember().compareTo(new BigDecimal("9.999"))==0);
            Assertions.assertTrue(stationClimate.getOktober().compareTo(new BigDecimal("10.100"))==0);
            Assertions.assertTrue(stationClimate.getNovember().compareTo(new BigDecimal("-11.111"))==0);
            Assertions.assertTrue(stationClimate.getDezember().compareTo(new BigDecimal("-12.120"))==0);
        }







    }

    @Test
    void getClimateRecordForStation() {

        List<StationWeatherPerYear> stationWeatherPerYearList = StationWeatherPerYearTestData.getStationWeatherPerYearList("2018",1,false);
        stationWeatherPerYearList.addAll(StationWeatherPerYearTestData.getStationWeatherPerYearList("1988",1,false));

        List<StationClimate> stationClimates = climateService.getClimateForStation(stationWeatherPerYearList);


        Assertions.assertTrue(stationClimates.size()==31);
        for (StationClimate stationClimate : stationClimates){
            Assertions.assertTrue(stationClimate.getJanuar().compareTo(new BigDecimal("-1.111"))==0);
            Assertions.assertTrue(stationClimate.getFebruar().compareTo(new BigDecimal("-2.222"))==0);
            Assertions.assertTrue(stationClimate.getMaerz().compareTo(new BigDecimal("3.333"))==0);
            Assertions.assertTrue(stationClimate.getApril().compareTo(new BigDecimal("4.444"))==0);
            Assertions.assertTrue(stationClimate.getMai().compareTo(new BigDecimal("5.555"))==0);
            Assertions.assertTrue(stationClimate.getJuni().compareTo(new BigDecimal("6.666"))==0);
            Assertions.assertTrue(stationClimate.getJuli().compareTo(new BigDecimal("7.777"))==0);
            Assertions.assertTrue(stationClimate.getAugust().compareTo(new BigDecimal("8.888"))==0);
            Assertions.assertTrue(stationClimate.getSeptember().compareTo(new BigDecimal("9.999"))==0);
            Assertions.assertTrue(stationClimate.getOktober().compareTo(new BigDecimal("10.100"))==0);
            Assertions.assertTrue(stationClimate.getNovember().compareTo(new BigDecimal("-11.111"))==0);
            Assertions.assertTrue(stationClimate.getDezember().compareTo(new BigDecimal("-12.120"))==0);
        }
    }
}