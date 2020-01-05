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
//        Assertions.assertEquals(0,climateAnalyserDto.getMostClimateAnalyseData().getJanuar().compareTo(new BigDecimal(2)));
        Assertions.assertEquals(climateAnalyserDto.getMostClimateAnalyseData().getJanuar(), new BigDecimal("2"));



        Assertions.assertEquals(climateAnalyserDto.getNewstClimateAnalyseData().getJanuar(), new BigDecimal("3"));

    }


}