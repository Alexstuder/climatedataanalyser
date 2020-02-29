package ch.studer.germanclimatedataanalyser.service;

import ch.studer.germanclimatedataanalyser.generate.test.data.ClimateTestData;
import ch.studer.germanclimatedataanalyser.model.database.StationClimate;
import ch.studer.germanclimatedataanalyser.model.dto.ClimateAnalyserRequestDto;
import ch.studer.germanclimatedataanalyser.model.dto.ClimateAnalyserResponseDto;
import ch.studer.germanclimatedataanalyser.model.dto.helper.GpsPoint;
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

    @Mock
    StationService stationService;

    @InjectMocks
    ClimateAnalyserServiceImpl climateAnalyserService;

    private static String EXISTING_BUNDESLAND = "Bundesland";
    private static String YEAR_ORIGINE = "2017";
    private static String YEAR_TO_COMPARE = "2019";
    private static int BEGIN_PERIOD = 1898;
    private static int END_PERIOD = 2017;

    @Test
    void happyHistoryTest(){


        //* Get some Test Data for climateService
        List<StationClimate> stationClimates = ClimateTestData.getStationClimate(BEGIN_PERIOD,END_PERIOD,3);

        //Folgender Test : Es existieren 3 StationsId , alles Jahre vorhanden
        // Station Id       :          :   1  :   2   :   3
        // Expected Periods : 2017-1988    x      x       x
        // Expected Periods : 1987-1958    x      x       x
        // Expected Periods : 1957-1928    x      x       x
        // Expected Periods : 1927-1898    x      x       x


        //* Define Mock szenario
        when(climateService.getClimateForBundesland(EXISTING_BUNDESLAND)).thenReturn(stationClimates);
        when(!stationService.bundeslandExists(EXISTING_BUNDESLAND)).thenReturn(true);


        ClimateAnalyserRequestDto climateAnalyserRequestDto = new ClimateAnalyserRequestDto();

        climateAnalyserRequestDto.setBundesland(EXISTING_BUNDESLAND);
        climateAnalyserRequestDto.setYearOrigine(YEAR_ORIGINE);
        climateAnalyserRequestDto.setYearToCompare(YEAR_TO_COMPARE);


        //* Execute Test
        ClimateAnalyserResponseDto climateAnalyserResponseDto = climateAnalyserService.getClimateAnalyticsByClimateAnalyserRequest(climateAnalyserRequestDto);

        //* Assert errorMSg has to be empty
        Assertions.assertEquals("", climateAnalyserResponseDto.getErrorMsg());
        //* Assert most ClimateAnalyseData
        Assertions.assertEquals(YEAR_ORIGINE,climateAnalyserResponseDto.getOriginYear());
        Assertions.assertEquals(YEAR_TO_COMPARE,climateAnalyserResponseDto.getYearToCompare());
        Assertions.assertEquals(EXISTING_BUNDESLAND, climateAnalyserResponseDto.getBundesland());
        // First Period
        Assertions.assertEquals(climateAnalyserResponseDto.getClimateHistoryDtos().get(0).getStartPeriod(), "1988");
        Assertions.assertEquals(climateAnalyserResponseDto.getClimateHistoryDtos().get(0).getEndPeriod(), YEAR_ORIGINE);
        Assertions.assertEquals(climateAnalyserResponseDto.getClimateHistoryDtos().get(0).getClimates().getJanuar(), new BigDecimal("2.000"));
        Assertions.assertEquals(climateAnalyserResponseDto.getClimateHistoryDtos().get(0).getClimates().getFebruar(), new BigDecimal("3.000"));
        Assertions.assertEquals(climateAnalyserResponseDto.getClimateHistoryDtos().get(0).getClimates().getMaerz(), new BigDecimal("4.000"));
        Assertions.assertEquals(climateAnalyserResponseDto.getClimateHistoryDtos().get(0).getClimates().getApril(), new BigDecimal("5.000"));
        Assertions.assertEquals(climateAnalyserResponseDto.getClimateHistoryDtos().get(0).getClimates().getMai(), new BigDecimal("6.000"));
        Assertions.assertEquals(climateAnalyserResponseDto.getClimateHistoryDtos().get(0).getClimates().getJuni(), new BigDecimal("7.000"));
        Assertions.assertEquals(climateAnalyserResponseDto.getClimateHistoryDtos().get(0).getClimates().getJuli(), new BigDecimal("8.000"));
        Assertions.assertEquals(climateAnalyserResponseDto.getClimateHistoryDtos().get(0).getClimates().getAugust(), new BigDecimal("9.000"));
        Assertions.assertEquals(climateAnalyserResponseDto.getClimateHistoryDtos().get(0).getClimates().getSeptember(), new BigDecimal("10.000"));
        Assertions.assertEquals(climateAnalyserResponseDto.getClimateHistoryDtos().get(0).getClimates().getOktober(), new BigDecimal("11.000"));
        Assertions.assertEquals(climateAnalyserResponseDto.getClimateHistoryDtos().get(0).getClimates().getNovember(), new BigDecimal("12.000"));
        Assertions.assertEquals(climateAnalyserResponseDto.getClimateHistoryDtos().get(0).getClimates().getDezember(), new BigDecimal("13.000"));
        //Second Period
        Assertions.assertEquals(climateAnalyserResponseDto.getClimateHistoryDtos().get(1).getStartPeriod(), "1958");
        Assertions.assertEquals(climateAnalyserResponseDto.getClimateHistoryDtos().get(1).getEndPeriod(), "1987");
        Assertions.assertEquals(climateAnalyserResponseDto.getClimateHistoryDtos().get(1).getClimates().getJanuar(), new BigDecimal("2.000"));
        Assertions.assertEquals(climateAnalyserResponseDto.getClimateHistoryDtos().get(1).getClimates().getFebruar(), new BigDecimal("3.000"));
        Assertions.assertEquals(climateAnalyserResponseDto.getClimateHistoryDtos().get(1).getClimates().getMaerz(), new BigDecimal("4.000"));
        Assertions.assertEquals(climateAnalyserResponseDto.getClimateHistoryDtos().get(1).getClimates().getApril(), new BigDecimal("5.000"));
        Assertions.assertEquals(climateAnalyserResponseDto.getClimateHistoryDtos().get(1).getClimates().getMai(), new BigDecimal("6.000"));
        Assertions.assertEquals(climateAnalyserResponseDto.getClimateHistoryDtos().get(1).getClimates().getJuni(), new BigDecimal("7.000"));
        Assertions.assertEquals(climateAnalyserResponseDto.getClimateHistoryDtos().get(1).getClimates().getJuli(), new BigDecimal("8.000"));
        Assertions.assertEquals(climateAnalyserResponseDto.getClimateHistoryDtos().get(1).getClimates().getAugust(), new BigDecimal("9.000"));
        Assertions.assertEquals(climateAnalyserResponseDto.getClimateHistoryDtos().get(1).getClimates().getSeptember(), new BigDecimal("10.000"));
        Assertions.assertEquals(climateAnalyserResponseDto.getClimateHistoryDtos().get(1).getClimates().getOktober(), new BigDecimal("11.000"));
        Assertions.assertEquals(climateAnalyserResponseDto.getClimateHistoryDtos().get(1).getClimates().getNovember(), new BigDecimal("12.000"));
        Assertions.assertEquals(climateAnalyserResponseDto.getClimateHistoryDtos().get(1).getClimates().getDezember(), new BigDecimal("13.000"));
        //third Period
        Assertions.assertEquals(climateAnalyserResponseDto.getClimateHistoryDtos().get(2).getStartPeriod(), "1928");
        Assertions.assertEquals(climateAnalyserResponseDto.getClimateHistoryDtos().get(2).getEndPeriod(), "1957");
        Assertions.assertEquals(climateAnalyserResponseDto.getClimateHistoryDtos().get(2).getClimates().getJanuar(), new BigDecimal("2.000"));
        Assertions.assertEquals(climateAnalyserResponseDto.getClimateHistoryDtos().get(2).getClimates().getFebruar(), new BigDecimal("3.000"));
        Assertions.assertEquals(climateAnalyserResponseDto.getClimateHistoryDtos().get(2).getClimates().getMaerz(), new BigDecimal("4.000"));
        Assertions.assertEquals(climateAnalyserResponseDto.getClimateHistoryDtos().get(2).getClimates().getApril(), new BigDecimal("5.000"));
        Assertions.assertEquals(climateAnalyserResponseDto.getClimateHistoryDtos().get(2).getClimates().getMai(), new BigDecimal("6.000"));
        Assertions.assertEquals(climateAnalyserResponseDto.getClimateHistoryDtos().get(2).getClimates().getJuni(), new BigDecimal("7.000"));
        Assertions.assertEquals(climateAnalyserResponseDto.getClimateHistoryDtos().get(2).getClimates().getJuli(), new BigDecimal("8.000"));
        Assertions.assertEquals(climateAnalyserResponseDto.getClimateHistoryDtos().get(2).getClimates().getAugust(), new BigDecimal("9.000"));
        Assertions.assertEquals(climateAnalyserResponseDto.getClimateHistoryDtos().get(2).getClimates().getSeptember(), new BigDecimal("10.000"));
        Assertions.assertEquals(climateAnalyserResponseDto.getClimateHistoryDtos().get(2).getClimates().getOktober(), new BigDecimal("11.000"));
        Assertions.assertEquals(climateAnalyserResponseDto.getClimateHistoryDtos().get(2).getClimates().getNovember(), new BigDecimal("12.000"));
        Assertions.assertEquals(climateAnalyserResponseDto.getClimateHistoryDtos().get(2).getClimates().getDezember(), new BigDecimal("13.000"));
        // forth Period
        Assertions.assertEquals(climateAnalyserResponseDto.getClimateHistoryDtos().get(3).getStartPeriod(), "1898");
        Assertions.assertEquals(climateAnalyserResponseDto.getClimateHistoryDtos().get(3).getEndPeriod(), "1927");
        Assertions.assertEquals(climateAnalyserResponseDto.getClimateHistoryDtos().get(3).getClimates().getJanuar(), new BigDecimal("2.000"));
        Assertions.assertEquals(climateAnalyserResponseDto.getClimateHistoryDtos().get(3).getClimates().getFebruar(), new BigDecimal("3.000"));
        Assertions.assertEquals(climateAnalyserResponseDto.getClimateHistoryDtos().get(3).getClimates().getMaerz(), new BigDecimal("4.000"));
        Assertions.assertEquals(climateAnalyserResponseDto.getClimateHistoryDtos().get(3).getClimates().getApril(), new BigDecimal("5.000"));
        Assertions.assertEquals(climateAnalyserResponseDto.getClimateHistoryDtos().get(3).getClimates().getMai(), new BigDecimal("6.000"));
        Assertions.assertEquals(climateAnalyserResponseDto.getClimateHistoryDtos().get(3).getClimates().getJuni(), new BigDecimal("7.000"));
        Assertions.assertEquals(climateAnalyserResponseDto.getClimateHistoryDtos().get(3).getClimates().getJuli(), new BigDecimal("8.000"));
        Assertions.assertEquals(climateAnalyserResponseDto.getClimateHistoryDtos().get(3).getClimates().getAugust(), new BigDecimal("9.000"));
        Assertions.assertEquals(climateAnalyserResponseDto.getClimateHistoryDtos().get(3).getClimates().getSeptember(), new BigDecimal("10.000"));
        Assertions.assertEquals(climateAnalyserResponseDto.getClimateHistoryDtos().get(3).getClimates().getOktober(), new BigDecimal("11.000"));
        Assertions.assertEquals(climateAnalyserResponseDto.getClimateHistoryDtos().get(3).getClimates().getNovember(), new BigDecimal("12.000"));
        Assertions.assertEquals(climateAnalyserResponseDto.getClimateHistoryDtos().get(3).getClimates().getDezember(), new BigDecimal("13.000"));

    }

    @Test
    void happyHistoryPermutationTest(){
        //Folgender Test : Es existieren 7 StationsId , in jeder Periode fehlt eine Station
        // Station Id       :          :   1  :   2   :   3   :    4   :   5   :  6   :    7
        // Expected Periods : 2017-1988    x      x       x        x
        // Expected Periods : 1987-1958    x      x       x                                x
        // Expected Periods : 1957-1928    x      x                               x        x
        // Expected Periods : 1927-1898    x                               x      x        x

        //* Get a full stack of testData
        List<StationClimate> stationClimates = ClimateTestData.getStationClimate(BEGIN_PERIOD,END_PERIOD,7);
        System.out.println("************************* start after generating ****************");
        for(StationClimate stationClimate : stationClimates){
            System.out.println(stationClimate.getEndPeriod()+ " : "+stationClimate.getStationId());
        }
        System.out.println("************************* start after generating ****************");

        //Remove year and stationID from full stack testData
        // Station Id       :          :   1  :   2   :   3   :    4   :   5   :  6   :    7
        // Expected Periods : 2017-1988    x      x       x        x
        int[] stationIdToRemove = {5,6,7};
        String yearToRemove = "2017";
        stationClimates = new ClimateTestData().removeClimates(yearToRemove,stationIdToRemove,stationClimates);
        System.out.println("************************* start after removing ****************");
        for(StationClimate stationClimate : stationClimates){
            System.out.println(stationClimate.getEndPeriod()+ " : "+stationClimate.getStationId());
        }
        System.out.println("************************* start after removing ****************");
//        //Remove year and stationID from full stack testData
//        // Station Id       :          :   1  :   2   :   3   :    4   :   5   :  6   :    7
//        // Expected Periods : 1987-1958    x      x       x                                x
//        stationIdToRemove = new int[]{4, 5, 6};
//        yearToRemove = "1987";
//        stationClimates = new ClimateTestData().removeClimates(yearToRemove,stationIdToRemove,stationClimates);
//
//        //Remove year and stationID from full stack testData
//        // Station Id       :          :   1  :   2   :   3   :    4   :   5   :  6   :    7
//        // Expected Periods : 1957-1928    x      x                               x        x
//        stationIdToRemove = new int[]{3, 4, 5};
//        yearToRemove = "1957";
//        stationClimates = new ClimateTestData().removeClimates(yearToRemove,stationIdToRemove,stationClimates);
//
//        //Remove year and stationID from full stack testData
//        // Station Id       :          :   1  :   2   :   3   :    4   :   5   :  6   :    7
//        // Expected Periods : 1927-1898    x                               x      x        x
//        stationIdToRemove = new int[]{2, 3, 4};
//        yearToRemove = "1927";
//        stationClimates = new ClimateTestData().removeClimates(yearToRemove,stationIdToRemove,stationClimates);


        // Die konzeptionelle Frage stellt sich hier : Welcher Algorythmus kommt hier zur anwendung ?
        // 1. Es wird lediglich die Station, welche über alle Perioden existierte zur Berechnung herangezogen!
        // 2. Es wird das mittel aller zur Verfügung stehenden StationId berechnet ?
        // 3. Es werden lediglich die Perioden angezeigt , welche min über x Stationen verfügen oder vom
        //    durchschnittlich vorhandenen Stationen Abweichen !
        // 4. Variante 1 mit zusätzlichen Statistischen Angaben(Anzahl Stationen ,durchschn. Höhe)
        // 5 . 1-3 Als Parameter auswählbar !
        //* Define Mock szenario

        when(climateService.getClimateForBundesland(EXISTING_BUNDESLAND)).thenReturn(stationClimates);

        when(!stationService.bundeslandExists(EXISTING_BUNDESLAND)).thenReturn(true);


        // Prepend RequestParameter
        ClimateAnalyserRequestDto climateAnalyserRequestDto = new ClimateAnalyserRequestDto();

        climateAnalyserRequestDto.setBundesland(EXISTING_BUNDESLAND);
        climateAnalyserRequestDto.setYearOrigine(YEAR_ORIGINE);
        climateAnalyserRequestDto.setYearToCompare(YEAR_ORIGINE);

        //* Execute Test
        ClimateAnalyserResponseDto climateAnalyserResponseDto = climateAnalyserService.getClimateAnalyticsByClimateAnalyserRequest(climateAnalyserRequestDto);

        //* Assert errorMSg has to be empty
        Assertions.assertEquals("", climateAnalyserResponseDto.getErrorMsg());
        //* Assert most ClimateAnalyseData
        // First Period
        Assertions.assertEquals(climateAnalyserResponseDto.getClimateHistoryDtos().get(0).getStartPeriod(), "1988");
        Assertions.assertEquals(climateAnalyserResponseDto.getClimateHistoryDtos().get(0).getEndPeriod(), YEAR_ORIGINE);
        Assertions.assertEquals(climateAnalyserResponseDto.getClimateHistoryDtos().get(0).getClimates().getJanuar(), new BigDecimal("2.500"));
        Assertions.assertEquals(climateAnalyserResponseDto.getClimateHistoryDtos().get(0).getClimates().getFebruar(), new BigDecimal("3.500"));
        Assertions.assertEquals(climateAnalyserResponseDto.getClimateHistoryDtos().get(0).getClimates().getMaerz(), new BigDecimal("4.500"));
        Assertions.assertEquals(climateAnalyserResponseDto.getClimateHistoryDtos().get(0).getClimates().getApril(), new BigDecimal("5.500"));
        Assertions.assertEquals(climateAnalyserResponseDto.getClimateHistoryDtos().get(0).getClimates().getMai(), new BigDecimal("6.500"));
        Assertions.assertEquals(climateAnalyserResponseDto.getClimateHistoryDtos().get(0).getClimates().getJuni(), new BigDecimal("7.500"));
        Assertions.assertEquals(climateAnalyserResponseDto.getClimateHistoryDtos().get(0).getClimates().getJuli(), new BigDecimal("8.500"));
        Assertions.assertEquals(climateAnalyserResponseDto.getClimateHistoryDtos().get(0).getClimates().getAugust(), new BigDecimal("9.500"));
        Assertions.assertEquals(climateAnalyserResponseDto.getClimateHistoryDtos().get(0).getClimates().getSeptember(), new BigDecimal("10.500"));
        Assertions.assertEquals(climateAnalyserResponseDto.getClimateHistoryDtos().get(0).getClimates().getOktober(), new BigDecimal("11.500"));
        Assertions.assertEquals(climateAnalyserResponseDto.getClimateHistoryDtos().get(0).getClimates().getNovember(), new BigDecimal("12.500"));
        Assertions.assertEquals(climateAnalyserResponseDto.getClimateHistoryDtos().get(0).getClimates().getDezember(), new BigDecimal("13.500"));
        //Second Period
        Assertions.assertEquals(climateAnalyserResponseDto.getClimateHistoryDtos().get(1).getStartPeriod(), "1958");
        Assertions.assertEquals(climateAnalyserResponseDto.getClimateHistoryDtos().get(1).getEndPeriod(), "1987");
        Assertions.assertEquals(climateAnalyserResponseDto.getClimateHistoryDtos().get(1).getClimates().getJanuar(), new BigDecimal("3.250"));
        Assertions.assertEquals(climateAnalyserResponseDto.getClimateHistoryDtos().get(1).getClimates().getFebruar(), new BigDecimal("4.250"));
        Assertions.assertEquals(climateAnalyserResponseDto.getClimateHistoryDtos().get(1).getClimates().getMaerz(), new BigDecimal("5.250"));
        Assertions.assertEquals(climateAnalyserResponseDto.getClimateHistoryDtos().get(1).getClimates().getApril(), new BigDecimal("6.250"));
        Assertions.assertEquals(climateAnalyserResponseDto.getClimateHistoryDtos().get(1).getClimates().getMai(), new BigDecimal("7.250"));
        Assertions.assertEquals(climateAnalyserResponseDto.getClimateHistoryDtos().get(1).getClimates().getJuni(), new BigDecimal("8.250"));
        Assertions.assertEquals(climateAnalyserResponseDto.getClimateHistoryDtos().get(1).getClimates().getJuli(), new BigDecimal("9.250"));
        Assertions.assertEquals(climateAnalyserResponseDto.getClimateHistoryDtos().get(1).getClimates().getAugust(), new BigDecimal("10.250"));
        Assertions.assertEquals(climateAnalyserResponseDto.getClimateHistoryDtos().get(1).getClimates().getSeptember(), new BigDecimal("11.250"));
        Assertions.assertEquals(climateAnalyserResponseDto.getClimateHistoryDtos().get(1).getClimates().getOktober(), new BigDecimal("12.250"));
        Assertions.assertEquals(climateAnalyserResponseDto.getClimateHistoryDtos().get(1).getClimates().getNovember(), new BigDecimal("13.250"));
        Assertions.assertEquals(climateAnalyserResponseDto.getClimateHistoryDtos().get(1).getClimates().getDezember(), new BigDecimal("14.250"));
        //third Period
        Assertions.assertEquals(climateAnalyserResponseDto.getClimateHistoryDtos().get(2).getStartPeriod(), "1928");
        Assertions.assertEquals(climateAnalyserResponseDto.getClimateHistoryDtos().get(2).getEndPeriod(), "1957");
        Assertions.assertEquals(climateAnalyserResponseDto.getClimateHistoryDtos().get(2).getClimates().getJanuar(), new BigDecimal("4.000"));
        Assertions.assertEquals(climateAnalyserResponseDto.getClimateHistoryDtos().get(2).getClimates().getFebruar(), new BigDecimal("5.000"));
        Assertions.assertEquals(climateAnalyserResponseDto.getClimateHistoryDtos().get(2).getClimates().getMaerz(), new BigDecimal("6.000"));
        Assertions.assertEquals(climateAnalyserResponseDto.getClimateHistoryDtos().get(2).getClimates().getApril(), new BigDecimal("7.000"));
        Assertions.assertEquals(climateAnalyserResponseDto.getClimateHistoryDtos().get(2).getClimates().getMai(), new BigDecimal("8.000"));
        Assertions.assertEquals(climateAnalyserResponseDto.getClimateHistoryDtos().get(2).getClimates().getJuni(), new BigDecimal("9.000"));
        Assertions.assertEquals(climateAnalyserResponseDto.getClimateHistoryDtos().get(2).getClimates().getJuli(), new BigDecimal("10.000"));
        Assertions.assertEquals(climateAnalyserResponseDto.getClimateHistoryDtos().get(2).getClimates().getAugust(), new BigDecimal("11.000"));
        Assertions.assertEquals(climateAnalyserResponseDto.getClimateHistoryDtos().get(2).getClimates().getSeptember(), new BigDecimal("12.000"));
        Assertions.assertEquals(climateAnalyserResponseDto.getClimateHistoryDtos().get(2).getClimates().getOktober(), new BigDecimal("13.000"));
        Assertions.assertEquals(climateAnalyserResponseDto.getClimateHistoryDtos().get(2).getClimates().getNovember(), new BigDecimal("14.000"));
        Assertions.assertEquals(climateAnalyserResponseDto.getClimateHistoryDtos().get(2).getClimates().getDezember(), new BigDecimal("15.000"));
        // forth Period
        Assertions.assertEquals(climateAnalyserResponseDto.getClimateHistoryDtos().get(3).getStartPeriod(), "1898");
        Assertions.assertEquals(climateAnalyserResponseDto.getClimateHistoryDtos().get(3).getEndPeriod(), "1927");
        Assertions.assertEquals(climateAnalyserResponseDto.getClimateHistoryDtos().get(3).getClimates().getJanuar(), new BigDecimal("4.750"));
        Assertions.assertEquals(climateAnalyserResponseDto.getClimateHistoryDtos().get(3).getClimates().getFebruar(), new BigDecimal("5.750"));
        Assertions.assertEquals(climateAnalyserResponseDto.getClimateHistoryDtos().get(3).getClimates().getMaerz(), new BigDecimal("6.750"));
        Assertions.assertEquals(climateAnalyserResponseDto.getClimateHistoryDtos().get(3).getClimates().getApril(), new BigDecimal("7.750"));
        Assertions.assertEquals(climateAnalyserResponseDto.getClimateHistoryDtos().get(3).getClimates().getMai(), new BigDecimal("8.750"));
        Assertions.assertEquals(climateAnalyserResponseDto.getClimateHistoryDtos().get(3).getClimates().getJuni(), new BigDecimal("9.750"));
        Assertions.assertEquals(climateAnalyserResponseDto.getClimateHistoryDtos().get(3).getClimates().getJuli(), new BigDecimal("10.750"));
        Assertions.assertEquals(climateAnalyserResponseDto.getClimateHistoryDtos().get(3).getClimates().getAugust(), new BigDecimal("11.750"));
        Assertions.assertEquals(climateAnalyserResponseDto.getClimateHistoryDtos().get(3).getClimates().getSeptember(), new BigDecimal("12.750"));
        Assertions.assertEquals(climateAnalyserResponseDto.getClimateHistoryDtos().get(3).getClimates().getOktober(), new BigDecimal("13.750"));
        Assertions.assertEquals(climateAnalyserResponseDto.getClimateHistoryDtos().get(3).getClimates().getNovember(), new BigDecimal("14.750"));
        Assertions.assertEquals(climateAnalyserResponseDto.getClimateHistoryDtos().get(3).getClimates().getDezember(), new BigDecimal("15.750"));
    }
@Test
    void happyHistoryHoleTest(){
        //Folgender Test : Es existiert eine grosse Lücke zwischen den Perioden
        // Station Id       :          :   1  :   2   :   3   :
        // Expected Periods : 2017-1988    x      x       x
        // Expected Periods : 1987-1958
        // Expected Periods : 1957-1928
        // Expected Periods : 1927-1898    x      x       x

    }

    @Test
    void errorHistoryTest(){
        //Absolutly No History DataFound !

    }


    @Test
    void happyInputVerificationAndValidationTests(){

        //* Define Mock szenario
        when(stationService.bundeslandExists(EXISTING_BUNDESLAND)).thenReturn(true);

        //* Get some Test Data for climateService
        List<StationClimate> stationClimates = ClimateTestData.getStationClimate(2014,2016,3);
        //* Define Mock szenario
        when(climateService.getClimateForBundesland(EXISTING_BUNDESLAND)).thenReturn(stationClimates);

        //* Define Reuqest Parameter
        ClimateAnalyserRequestDto climateAnalyserRequestDto = new ClimateAnalyserRequestDto();
        climateAnalyserRequestDto.setBundesland(EXISTING_BUNDESLAND);
        climateAnalyserRequestDto.setYearOrigine(YEAR_ORIGINE);
        climateAnalyserRequestDto.setYearToCompare(YEAR_TO_COMPARE);

        // Execute Test :
        ClimateAnalyserResponseDto climateAnalyserResponseDto = climateAnalyserService.getClimateAnalyticsByClimateAnalyserRequest(climateAnalyserRequestDto);

        //* Assert errorMSg has to be empty
        Assertions.assertEquals("", climateAnalyserResponseDto.getErrorMsg());
        //* Assert most ClimateAnalyseData
        Assertions.assertEquals(YEAR_ORIGINE,climateAnalyserResponseDto.getOriginYear());
        Assertions.assertEquals(YEAR_TO_COMPARE,climateAnalyserResponseDto.getYearToCompare());
        Assertions.assertEquals(EXISTING_BUNDESLAND, climateAnalyserResponseDto.getBundesland());
        Assertions.assertEquals(climateAnalyserResponseDto.getOriginal().getJanuar(), new BigDecimal("2.000"));
        Assertions.assertEquals(climateAnalyserResponseDto.getOriginal().getFebruar(), new BigDecimal("3.000"));
        Assertions.assertEquals(climateAnalyserResponseDto.getOriginal().getMaerz(), new BigDecimal("4.000"));
        Assertions.assertEquals(climateAnalyserResponseDto.getOriginal().getApril(), new BigDecimal("5.000"));
        Assertions.assertEquals(climateAnalyserResponseDto.getOriginal().getMai(), new BigDecimal("6.000"));
        Assertions.assertEquals(climateAnalyserResponseDto.getOriginal().getJuni(), new BigDecimal("7.000"));
        Assertions.assertEquals(climateAnalyserResponseDto.getOriginal().getJuli(), new BigDecimal("8.000"));
        Assertions.assertEquals(climateAnalyserResponseDto.getOriginal().getAugust(), new BigDecimal("9.000"));
        Assertions.assertEquals(climateAnalyserResponseDto.getOriginal().getSeptember(), new BigDecimal("10.000"));
        Assertions.assertEquals(climateAnalyserResponseDto.getOriginal().getOktober(), new BigDecimal("11.000"));
        Assertions.assertEquals(climateAnalyserResponseDto.getOriginal().getNovember(), new BigDecimal("12.000"));
        Assertions.assertEquals(climateAnalyserResponseDto.getOriginal().getDezember(), new BigDecimal("13.000"));


        // Assert Newest
        Assertions.assertEquals(climateAnalyserResponseDto.getCompare().getJanuar(), new BigDecimal("3.000"));
        Assertions.assertEquals(climateAnalyserResponseDto.getCompare().getFebruar(), new BigDecimal("4.000"));
        Assertions.assertEquals(climateAnalyserResponseDto.getCompare().getMaerz(), new BigDecimal("5.000"));
        Assertions.assertEquals(climateAnalyserResponseDto.getCompare().getApril(), new BigDecimal("6.000"));
        Assertions.assertEquals(climateAnalyserResponseDto.getCompare().getMai(), new BigDecimal("7.000"));
        Assertions.assertEquals(climateAnalyserResponseDto.getCompare().getJuni(), new BigDecimal("8.000"));
        Assertions.assertEquals(climateAnalyserResponseDto.getCompare().getJuli(), new BigDecimal("9.000"));
        Assertions.assertEquals(climateAnalyserResponseDto.getCompare().getAugust(), new BigDecimal("10.000"));
        Assertions.assertEquals(climateAnalyserResponseDto.getCompare().getSeptember(), new BigDecimal("11.000"));
        Assertions.assertEquals(climateAnalyserResponseDto.getCompare().getOktober(), new BigDecimal("12.000"));
        Assertions.assertEquals(climateAnalyserResponseDto.getCompare().getNovember(), new BigDecimal("13.000"));
        Assertions.assertEquals(climateAnalyserResponseDto.getCompare().getDezember(), new BigDecimal("14.000"));




        // **************************************************************
        // Test : GPS Coordinates

        //* Define Reuqest Parameter
        // * Execute Test : GPS1 has no valid coordinates(positiv too big values)
        double posValidLatitude = 90.00;
        double posValidLongitude = 180.00;
        double negValidLatitude = -90.00;
        double negValidLongitude = -180.00;

        climateAnalyserRequestDto = new ClimateAnalyserRequestDto();
        climateAnalyserRequestDto.setGps1(new GpsPoint(posValidLatitude,negValidLongitude));
        climateAnalyserRequestDto.setGps2(new GpsPoint(negValidLatitude,posValidLongitude));
        climateAnalyserRequestDto = new ClimateAnalyserRequestDto();
        climateAnalyserRequestDto.setBundesland(EXISTING_BUNDESLAND);
        climateAnalyserRequestDto.setYearOrigine(YEAR_ORIGINE);
        climateAnalyserRequestDto.setYearToCompare(YEAR_TO_COMPARE);

        // Execute Test :
        climateAnalyserResponseDto = climateAnalyserService.getClimateAnalyticsByClimateAnalyserRequest(climateAnalyserRequestDto);

        //* Assert most ClimateAnalyseData
        Assertions.assertEquals("", climateAnalyserResponseDto.getErrorMsg());




    }

 @Test
    void errorInputVerificationAndValidationTests(){

            ClimateAnalyserRequestDto climateAnalyserRequestDto = new ClimateAnalyserRequestDto();

         // **************************************************************
         // * Execute Test : No Bundesland and no Gps Coordinates entered
         ClimateAnalyserResponseDto climateAnalyserResponseDto = climateAnalyserService.getClimateAnalyticsByClimateAnalyserRequest(climateAnalyserRequestDto);
         //* Assert most ClimateAnalyseData
         Assertions.assertEquals("Neither a Bundesland nor GPS coordinates have been entered", climateAnalyserResponseDto.getErrorMsg());


         // ********************************************************
         // * Execute Test : Bundesland and Gps Coordinates entered
         climateAnalyserRequestDto = new ClimateAnalyserRequestDto();
         climateAnalyserRequestDto.setBundesland(EXISTING_BUNDESLAND);
         climateAnalyserRequestDto.setGps1(new GpsPoint(0,0));
         climateAnalyserResponseDto = climateAnalyserService.getClimateAnalyticsByClimateAnalyserRequest(climateAnalyserRequestDto);
         //* Assert most ClimateAnalyseData
         Assertions.assertEquals("A Bundesland was selected and GPS coordinates entered at the same time !", climateAnalyserResponseDto.getErrorMsg());

         // ********************************************
         // * Execute Test : Bundesland does not Exists
         final String NOT_EXISTING_BUNDESLAND = "Glattfelden";

         //* Define Mock szenario
         when(stationService.bundeslandExists(NOT_EXISTING_BUNDESLAND)).thenReturn(false);

         climateAnalyserRequestDto = new ClimateAnalyserRequestDto();
         climateAnalyserRequestDto.setBundesland(NOT_EXISTING_BUNDESLAND);
         climateAnalyserResponseDto = climateAnalyserService.getClimateAnalyticsByClimateAnalyserRequest(climateAnalyserRequestDto);
         //* Assert most ClimateAnalyseData
         Assertions.assertEquals("Bundesland : " + NOT_EXISTING_BUNDESLAND + " doesn't exists !", climateAnalyserResponseDto.getErrorMsg());

         // ********************************************************
         // * Execute Test : GPS1 has no valid coordinates(positiv too big values)
         double invalidLatitude = 90.001;
         double invalidLongitude = 180.001;
         double validLatitude = 90.00;
         double validLongitude = 180.00;

         climateAnalyserRequestDto = new ClimateAnalyserRequestDto();
         climateAnalyserRequestDto.setGps1(new GpsPoint(invalidLatitude,invalidLongitude));
         climateAnalyserRequestDto.setGps2(new GpsPoint(validLatitude,validLongitude));

         climateAnalyserResponseDto = climateAnalyserService.getClimateAnalyticsByClimateAnalyserRequest(climateAnalyserRequestDto);

         //* Assert most ClimateAnalyseData
         Assertions.assertEquals("GPS1 Coordinates are not valid ! : Latitude (-90, 0,90) :" + invalidLatitude + " Longitude(-180,0,180)" + invalidLongitude , climateAnalyserResponseDto.getErrorMsg());

         // ********************************************************
         // * Execute Test : GPS2 has no valid coordinates (positiv too big values)
         climateAnalyserRequestDto = new ClimateAnalyserRequestDto();
         climateAnalyserRequestDto.setGps1(new GpsPoint(validLatitude,validLongitude));
         climateAnalyserRequestDto.setGps2(new GpsPoint(invalidLatitude,invalidLongitude));

         climateAnalyserResponseDto = climateAnalyserService.getClimateAnalyticsByClimateAnalyserRequest(climateAnalyserRequestDto);

         //* Assert most ClimateAnalyseData
         Assertions.assertEquals("GPS2 Coordinates are not valid ! : Latitude (-90, 0,90) :" + invalidLatitude + " Longitude(-180,0,180)" + invalidLongitude , climateAnalyserResponseDto.getErrorMsg());

         // ********************************************************
         // * Execute Test : GPS1 has no valid coordinates
         invalidLatitude = -90.001;
         invalidLongitude = -180.001;
         validLatitude = 90.00;
         validLongitude = 180.00;

         climateAnalyserRequestDto = new ClimateAnalyserRequestDto();
         climateAnalyserRequestDto.setGps1(new GpsPoint(invalidLatitude,invalidLongitude));
         climateAnalyserRequestDto.setGps2(new GpsPoint(validLatitude,validLongitude));

         climateAnalyserResponseDto = climateAnalyserService.getClimateAnalyticsByClimateAnalyserRequest(climateAnalyserRequestDto);

         //* Assert most ClimateAnalyseData
         Assertions.assertEquals("GPS1 Coordinates are not valid ! : Latitude (-90, 0,90) :" + invalidLatitude + " Longitude(-180,0,180)" + invalidLongitude , climateAnalyserResponseDto.getErrorMsg());

         // ********************************************************
         // * Execute Test : GPS2 has no valid coordinates
         climateAnalyserRequestDto = new ClimateAnalyserRequestDto();
         climateAnalyserRequestDto.setGps1(new GpsPoint(validLatitude,validLongitude));
         climateAnalyserRequestDto.setGps2(new GpsPoint(invalidLatitude,invalidLongitude));

         climateAnalyserResponseDto = climateAnalyserService.getClimateAnalyticsByClimateAnalyserRequest(climateAnalyserRequestDto);

         //* Assert most ClimateAnalyseData
         Assertions.assertEquals("GPS2 Coordinates are not valid ! : Latitude (-90, 0,90) :" + invalidLatitude + " Longitude(-180,0,180)" + invalidLongitude , climateAnalyserResponseDto.getErrorMsg());


     // ********************************************************
     // * Execute Test : YearOrigine is not numeric

     when(stationService.bundeslandExists(EXISTING_BUNDESLAND)).thenReturn(true);
     climateAnalyserRequestDto = new ClimateAnalyserRequestDto();
     climateAnalyserRequestDto.setBundesland(EXISTING_BUNDESLAND);
     climateAnalyserRequestDto.setYearOrigine("notNumeric");

     climateAnalyserResponseDto = climateAnalyserService.getClimateAnalyticsByClimateAnalyserRequest(climateAnalyserRequestDto);

     //* Assert most ClimateAnalyseData
     Assertions.assertEquals("Only Numbers for origin Year are allowed !", climateAnalyserResponseDto.getErrorMsg());



     // ********************************************************
     // * Execute Test : YearOrigine is not numeric

     when(stationService.bundeslandExists(EXISTING_BUNDESLAND)).thenReturn(true);
     climateAnalyserRequestDto = new ClimateAnalyserRequestDto();
     climateAnalyserRequestDto.setBundesland(EXISTING_BUNDESLAND);
     climateAnalyserRequestDto.setYearOrigine(YEAR_ORIGINE);
     climateAnalyserRequestDto.setYearToCompare("notNumeric");

     climateAnalyserResponseDto = climateAnalyserService.getClimateAnalyticsByClimateAnalyserRequest(climateAnalyserRequestDto);


     Assertions.assertEquals("Only Numbers for year to compare are allowed !", climateAnalyserResponseDto.getErrorMsg());

     // ********************************************************
     // * Execute Test : YearOrigine 2100 has no Climate Records

     String NO_YEAR = "2100";

     //* Get some Test Data for climateService
     List<StationClimate> stationClimates = ClimateTestData.getStationClimate(2014,2016,3);
     //* Define Mock szenario
     when(climateService.getClimateForBundesland(EXISTING_BUNDESLAND)).thenReturn(stationClimates);
     when(stationService.bundeslandExists(EXISTING_BUNDESLAND)).thenReturn(true);

     climateAnalyserRequestDto = new ClimateAnalyserRequestDto();
     climateAnalyserRequestDto.setBundesland(EXISTING_BUNDESLAND);
     climateAnalyserRequestDto.setYearOrigine(NO_YEAR);
     climateAnalyserRequestDto.setYearToCompare(NO_YEAR);

     climateAnalyserResponseDto = climateAnalyserService.getClimateAnalyticsByClimateAnalyserRequest(climateAnalyserRequestDto);


     Assertions.assertEquals("No climate data for the year: "+ NO_YEAR +" found !", climateAnalyserResponseDto.getErrorMsg());

     // ********************************************************
     // * Execute Test : YearToCompare dont exists in 2014

     String yearToCompare = "2100";

     StationClimate stationClimateStationIdDontExistsInOrigine = new StationClimate();
     stationClimateStationIdDontExistsInOrigine.setStationId(7777);
     stationClimateStationIdDontExistsInOrigine.setEndPeriod(yearToCompare);
     stationClimates.add(stationClimateStationIdDontExistsInOrigine);

     when(climateService.getClimateForBundesland(EXISTING_BUNDESLAND)).thenReturn(stationClimates);
     when(stationService.bundeslandExists(EXISTING_BUNDESLAND)).thenReturn(true);
     climateAnalyserRequestDto = new ClimateAnalyserRequestDto();
     climateAnalyserRequestDto.setBundesland(EXISTING_BUNDESLAND);
     climateAnalyserRequestDto.setYearOrigine(YEAR_ORIGINE);
     climateAnalyserRequestDto.setYearToCompare(yearToCompare);

     climateAnalyserResponseDto = climateAnalyserService.getClimateAnalyticsByClimateAnalyserRequest(climateAnalyserRequestDto);


     Assertions.assertEquals("No StationId from the year "+ yearToCompare +" was found, which may already have existed in the year " + YEAR_ORIGINE,  climateAnalyserResponseDto.getErrorMsg());


 }

}
