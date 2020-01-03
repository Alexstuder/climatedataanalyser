package ch.studer.germanclimatedataanalyser.service;

import ch.studer.germanclimatedataanalyser.generate.test.data.StationWeatherPerYearTestData;
import ch.studer.germanclimatedataanalyser.model.database.StationClimate;
import ch.studer.germanclimatedataanalyser.model.database.StationWeatherPerYear;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.util.ReflectionTestUtils;

import java.math.BigDecimal;
import java.util.List;

class ClimateServiceImplTest {


    ClimateServiceImpl climateService = new ClimateServiceImpl();

    private static final Logger LOG = LoggerFactory.getLogger(ClimateServiceImplTest.class);
    @BeforeEach
    void setUp() {

        ReflectionTestUtils.setField(climateService,"period",30);
    }

    @Test
    void get0ClimateRecordForStation() {

        // Build 29 StationWeather records to get 0 climate record as result

        List<StationWeatherPerYear> stationWeatherPerYearList = StationWeatherPerYearTestData.getStationWeatherPerYearList("2018",1,false);

        // remove any random Record
        stationWeatherPerYearList.remove(15);

        List<StationClimate> stationClimates = climateService.getClimateForStation(stationWeatherPerYearList);


        Assertions.assertEquals(29, stationWeatherPerYearList.size());
        Assertions.assertEquals(0, stationClimates.size());
    }

 @Test
    void get1ClimateRecordForStation() {

        // Build 30 StationWeather records to get 1 climate record as result

        List<StationWeatherPerYear> stationWeatherPerYearList = StationWeatherPerYearTestData.getStationWeatherPerYearList("2018",1,false);

        List<StationClimate> stationClimates = climateService.getClimateForStation(stationWeatherPerYearList);


     Assertions.assertEquals(1, stationClimates.size());
        for (StationClimate stationClimate : stationClimates){
            Assertions.assertEquals(0, stationClimate.getJanuar().compareTo(new BigDecimal("-1.111")));
            Assertions.assertEquals(0, stationClimate.getFebruar().compareTo(new BigDecimal("-2.222")));
            Assertions.assertEquals(0, stationClimate.getMaerz().compareTo(new BigDecimal("3.333")));
            Assertions.assertEquals(0, stationClimate.getApril().compareTo(new BigDecimal("4.444")));
            Assertions.assertEquals(0, stationClimate.getMai().compareTo(new BigDecimal("5.555")));
            Assertions.assertEquals(0, stationClimate.getJuni().compareTo(new BigDecimal("6.666")));
            Assertions.assertEquals(0, stationClimate.getJuli().compareTo(new BigDecimal("7.777")));
            Assertions.assertEquals(0, stationClimate.getAugust().compareTo(new BigDecimal("8.888")));
            Assertions.assertEquals(0, stationClimate.getSeptember().compareTo(new BigDecimal("9.999")));
            Assertions.assertEquals(0, stationClimate.getOktober().compareTo(new BigDecimal("10.100")));
            Assertions.assertEquals(0, stationClimate.getNovember().compareTo(new BigDecimal("-11.111")));
            Assertions.assertEquals(0, stationClimate.getDezember().compareTo(new BigDecimal("-12.120")));
        }
    }

    @Test
    void get2ClimateRecordForStation() {

        // Build 31 StationWeather Records to get 2 Climate Records as Result

        List<StationWeatherPerYear> stationWeatherPerYearList = StationWeatherPerYearTestData.getStationWeatherPerYearList("2018",1,false);
        stationWeatherPerYearList.add(StationWeatherPerYearTestData.getStationWeatherPerYearTestData("1988",1));

        List<StationClimate> stationClimates = climateService.getClimateForStation(stationWeatherPerYearList);


        Assertions.assertEquals(2, stationClimates.size());
        for (StationClimate stationClimate : stationClimates){
            Assertions.assertEquals(0, stationClimate.getJanuar().compareTo(new BigDecimal("-1.111")));
            Assertions.assertEquals(0, stationClimate.getFebruar().compareTo(new BigDecimal("-2.222")));
            Assertions.assertEquals(0, stationClimate.getMaerz().compareTo(new BigDecimal("3.333")));
            Assertions.assertEquals(0, stationClimate.getApril().compareTo(new BigDecimal("4.444")));
            Assertions.assertEquals(0, stationClimate.getMai().compareTo(new BigDecimal("5.555")));
            Assertions.assertEquals(0, stationClimate.getJuni().compareTo(new BigDecimal("6.666")));
            Assertions.assertEquals(0,stationClimate.getJuli().compareTo(new BigDecimal("7.777")));
            Assertions.assertEquals(0, stationClimate.getAugust().compareTo(new BigDecimal("8.888")));
            Assertions.assertEquals(0, stationClimate.getSeptember().compareTo(new BigDecimal("9.999")));
            Assertions.assertEquals(0, stationClimate.getOktober().compareTo(new BigDecimal("10.100")));
            Assertions.assertEquals(0, stationClimate.getNovember().compareTo(new BigDecimal("-11.111")));
            Assertions.assertEquals(0, stationClimate.getDezember().compareTo(new BigDecimal("-12.120")));
        }
    }
}