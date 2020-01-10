package ch.studer.germanclimatedataanalyser.service;

import ch.studer.germanclimatedataanalyser.generate.test.data.ClimateTestData;
import ch.studer.germanclimatedataanalyser.model.database.StationClimate;
import ch.studer.germanclimatedataanalyser.model.dto.ClimateAnalyserDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ClimateAnalyserServiceImplTest {

    @Mock
    ClimateService climateService;

    @InjectMocks
    ClimateAnalyserServiceImpl climateAnalyserService;


    @Test
    void happyTest(){

        //* Get some Test Data for climateService
        List<StationClimate> stationClimates = ClimateTestData.getStationClimate(0);


        //* Define Mock szenario
        when(climateService.getClimateForBundesland("Berlin")).thenReturn(stationClimates);

        //* Execute Test
        ClimateAnalyserDto climateAnalyserDto = climateAnalyserService.getClimateAnalyserForBundesland("Berlin");

        //* Assert most ClimateAnalyseData
        Assertions.assertEquals(climateAnalyserDto.getYear(), "2017");
        Assertions.assertEquals(climateAnalyserDto.getBundesland(), "Berlin");
        Assertions.assertEquals(climateAnalyserDto.getOriginal().getJanuar(), new BigDecimal("2.000"));
        Assertions.assertEquals(climateAnalyserDto.getOriginal().getFebruar(), new BigDecimal("3.000"));
        Assertions.assertEquals(climateAnalyserDto.getOriginal().getMaerz(), new BigDecimal("4.000"));
        Assertions.assertEquals(climateAnalyserDto.getOriginal().getApril(), new BigDecimal("5.000"));
        Assertions.assertEquals(climateAnalyserDto.getOriginal().getMai(), new BigDecimal("6.000"));
        Assertions.assertEquals(climateAnalyserDto.getOriginal().getJuni(), new BigDecimal("7.000"));
        Assertions.assertEquals(climateAnalyserDto.getOriginal().getJuli(), new BigDecimal("8.000"));
        Assertions.assertEquals(climateAnalyserDto.getOriginal().getAugust(), new BigDecimal("9.000"));
        Assertions.assertEquals(climateAnalyserDto.getOriginal().getSeptember(), new BigDecimal("10.000"));
        Assertions.assertEquals(climateAnalyserDto.getOriginal().getOktober(), new BigDecimal("11.000"));
        Assertions.assertEquals(climateAnalyserDto.getOriginal().getNovember(), new BigDecimal("12.000"));
        Assertions.assertEquals(climateAnalyserDto.getOriginal().getDezember(), new BigDecimal("13.000"));


        // Assert Newest
        Assertions.assertEquals(climateAnalyserDto.getCompare().getJanuar(), new BigDecimal("3.000"));
        Assertions.assertEquals(climateAnalyserDto.getCompare().getFebruar(), new BigDecimal("4.000"));
        Assertions.assertEquals(climateAnalyserDto.getCompare().getMaerz(), new BigDecimal("5.000"));
        Assertions.assertEquals(climateAnalyserDto.getCompare().getApril(), new BigDecimal("6.000"));
        Assertions.assertEquals(climateAnalyserDto.getCompare().getMai(), new BigDecimal("7.000"));
        Assertions.assertEquals(climateAnalyserDto.getCompare().getJuni(), new BigDecimal("8.000"));
        Assertions.assertEquals(climateAnalyserDto.getCompare().getJuli(), new BigDecimal("9.000"));
        Assertions.assertEquals(climateAnalyserDto.getCompare().getAugust(), new BigDecimal("10.000"));
        Assertions.assertEquals(climateAnalyserDto.getCompare().getSeptember(), new BigDecimal("11.000"));
        Assertions.assertEquals(climateAnalyserDto.getCompare().getOktober(), new BigDecimal("12.000"));
        Assertions.assertEquals(climateAnalyserDto.getCompare().getNovember(), new BigDecimal("13.000"));
        Assertions.assertEquals(climateAnalyserDto.getCompare().getDezember(), new BigDecimal("14.000"));

    }


}