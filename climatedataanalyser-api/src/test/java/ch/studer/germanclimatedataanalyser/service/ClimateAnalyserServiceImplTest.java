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
        Assertions.assertEquals(climateAnalyserDto.getMostClimateAnalyseData().getJanuar(), new BigDecimal("2.000"));
        Assertions.assertEquals(climateAnalyserDto.getMostClimateAnalyseData().getFebruar(), new BigDecimal("3.000"));
        Assertions.assertEquals(climateAnalyserDto.getMostClimateAnalyseData().getMaerz(), new BigDecimal("4.000"));
        Assertions.assertEquals(climateAnalyserDto.getMostClimateAnalyseData().getApril(), new BigDecimal("5.000"));
        Assertions.assertEquals(climateAnalyserDto.getMostClimateAnalyseData().getMai(), new BigDecimal("6.000"));
        Assertions.assertEquals(climateAnalyserDto.getMostClimateAnalyseData().getJuni(), new BigDecimal("7.000"));
        Assertions.assertEquals(climateAnalyserDto.getMostClimateAnalyseData().getJuli(), new BigDecimal("8.000"));
        Assertions.assertEquals(climateAnalyserDto.getMostClimateAnalyseData().getAugust(), new BigDecimal("9.000"));
        Assertions.assertEquals(climateAnalyserDto.getMostClimateAnalyseData().getSeptember(), new BigDecimal("10.000"));
        Assertions.assertEquals(climateAnalyserDto.getMostClimateAnalyseData().getOktober(), new BigDecimal("11.000"));
        Assertions.assertEquals(climateAnalyserDto.getMostClimateAnalyseData().getNovember(), new BigDecimal("12.000"));
        Assertions.assertEquals(climateAnalyserDto.getMostClimateAnalyseData().getDezember(), new BigDecimal("13.000"));


        // Assert Newest
        Assertions.assertEquals(climateAnalyserDto.getNewstClimateAnalyseData().getJanuar(), new BigDecimal("3.000"));
        Assertions.assertEquals(climateAnalyserDto.getNewstClimateAnalyseData().getFebruar(), new BigDecimal("4.000"));
        Assertions.assertEquals(climateAnalyserDto.getNewstClimateAnalyseData().getMaerz(), new BigDecimal("5.000"));
        Assertions.assertEquals(climateAnalyserDto.getNewstClimateAnalyseData().getApril(), new BigDecimal("6.000"));
        Assertions.assertEquals(climateAnalyserDto.getNewstClimateAnalyseData().getMai(), new BigDecimal("7.000"));
        Assertions.assertEquals(climateAnalyserDto.getNewstClimateAnalyseData().getJuni(), new BigDecimal("8.000"));
        Assertions.assertEquals(climateAnalyserDto.getNewstClimateAnalyseData().getJuli(), new BigDecimal("9.000"));
        Assertions.assertEquals(climateAnalyserDto.getNewstClimateAnalyseData().getAugust(), new BigDecimal("10.000"));
        Assertions.assertEquals(climateAnalyserDto.getNewstClimateAnalyseData().getSeptember(), new BigDecimal("11.000"));
        Assertions.assertEquals(climateAnalyserDto.getNewstClimateAnalyseData().getOktober(), new BigDecimal("12.000"));
        Assertions.assertEquals(climateAnalyserDto.getNewstClimateAnalyseData().getNovember(), new BigDecimal("13.000"));
        Assertions.assertEquals(climateAnalyserDto.getNewstClimateAnalyseData().getDezember(), new BigDecimal("14.000"));

    }


}