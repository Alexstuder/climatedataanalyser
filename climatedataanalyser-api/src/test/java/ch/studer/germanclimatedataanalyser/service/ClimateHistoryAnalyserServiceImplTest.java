package ch.studer.germanclimatedataanalyser.service;

import ch.studer.germanclimatedataanalyser.generate.test.data.ClimateTestData;
import ch.studer.germanclimatedataanalyser.model.database.StationClimate;
import ch.studer.germanclimatedataanalyser.model.dto.ClimateHistoryDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

class ClimateHistoryAnalyserServiceImplTest {
    final static String ORIGIN_YEAR = "2017";
    private static int BEGIN_PERIOD = 1898;
    private static int END_PERIOD = 2017;

    @Test
    void getClimateHistoryAverageData() {

        //* Get some Test Data for climateService
        List<StationClimate> stationClimates = ClimateTestData.getStationClimate(BEGIN_PERIOD,END_PERIOD,3);

        //Folgender Test : Es existieren 3 StationsId , alle Jahre vorhanden
        // Station Id       :          :   1  :   2   :   3
        // Expected Periods : 2017-1988    x      x       x
        // Expected Periods : 1987-1958    x      x       x
        // Expected Periods : 1957-1928    x      x       x
        // Expected Periods : 1927-1898    x      x       x

        //* Execute Test
        List<ClimateHistoryDto> climateHistoryAverageDtos = new ClimateHistoryAnalyserServiceImpl().getClimateHistoryAverageData(ORIGIN_YEAR,stationClimates);

        // First Period
        Assertions.assertEquals(climateHistoryAverageDtos.get(0).getStartPeriod(), "1988");
        Assertions.assertEquals(climateHistoryAverageDtos.get(0).getEndPeriod(), ORIGIN_YEAR);
        Assertions.assertEquals(climateHistoryAverageDtos.get(0).getClimates().getJanuar(), new BigDecimal("2.000"));
        Assertions.assertEquals(climateHistoryAverageDtos.get(0).getClimates().getFebruar(), new BigDecimal("3.000"));
        Assertions.assertEquals(climateHistoryAverageDtos.get(0).getClimates().getMaerz(), new BigDecimal("4.000"));
        Assertions.assertEquals(climateHistoryAverageDtos.get(0).getClimates().getApril(), new BigDecimal("5.000"));
        Assertions.assertEquals(climateHistoryAverageDtos.get(0).getClimates().getMai(), new BigDecimal("6.000"));
        Assertions.assertEquals(climateHistoryAverageDtos.get(0).getClimates().getJuni(), new BigDecimal("7.000"));
        Assertions.assertEquals(climateHistoryAverageDtos.get(0).getClimates().getJuli(), new BigDecimal("8.000"));
        Assertions.assertEquals(climateHistoryAverageDtos.get(0).getClimates().getAugust(), new BigDecimal("9.000"));
        Assertions.assertEquals(climateHistoryAverageDtos.get(0).getClimates().getSeptember(), new BigDecimal("10.000"));
        Assertions.assertEquals(climateHistoryAverageDtos.get(0).getClimates().getOktober(), new BigDecimal("11.000"));
        Assertions.assertEquals(climateHistoryAverageDtos.get(0).getClimates().getNovember(), new BigDecimal("12.000"));
        Assertions.assertEquals(climateHistoryAverageDtos.get(0).getClimates().getDezember(), new BigDecimal("13.000"));
        //Second Period
        Assertions.assertEquals(climateHistoryAverageDtos.get(1).getStartPeriod(), "1958");
        Assertions.assertEquals(climateHistoryAverageDtos.get(1).getEndPeriod(), "1987");
        Assertions.assertEquals(climateHistoryAverageDtos.get(1).getClimates().getJanuar(), new BigDecimal("2.000"));
        Assertions.assertEquals(climateHistoryAverageDtos.get(1).getClimates().getFebruar(), new BigDecimal("3.000"));
        Assertions.assertEquals(climateHistoryAverageDtos.get(1).getClimates().getMaerz(), new BigDecimal("4.000"));
        Assertions.assertEquals(climateHistoryAverageDtos.get(1).getClimates().getApril(), new BigDecimal("5.000"));
        Assertions.assertEquals(climateHistoryAverageDtos.get(1).getClimates().getMai(), new BigDecimal("6.000"));
        Assertions.assertEquals(climateHistoryAverageDtos.get(1).getClimates().getJuni(), new BigDecimal("7.000"));
        Assertions.assertEquals(climateHistoryAverageDtos.get(1).getClimates().getJuli(), new BigDecimal("8.000"));
        Assertions.assertEquals(climateHistoryAverageDtos.get(1).getClimates().getAugust(), new BigDecimal("9.000"));
        Assertions.assertEquals(climateHistoryAverageDtos.get(1).getClimates().getSeptember(), new BigDecimal("10.000"));
        Assertions.assertEquals(climateHistoryAverageDtos.get(1).getClimates().getOktober(), new BigDecimal("11.000"));
        Assertions.assertEquals(climateHistoryAverageDtos.get(1).getClimates().getNovember(), new BigDecimal("12.000"));
        Assertions.assertEquals(climateHistoryAverageDtos.get(1).getClimates().getDezember(), new BigDecimal("13.000"));
        //third Period
        Assertions.assertEquals(climateHistoryAverageDtos.get(2).getStartPeriod(), "1928");
        Assertions.assertEquals(climateHistoryAverageDtos.get(2).getEndPeriod(), "1957");
        Assertions.assertEquals(climateHistoryAverageDtos.get(2).getClimates().getJanuar(), new BigDecimal("2.000"));
        Assertions.assertEquals(climateHistoryAverageDtos.get(2).getClimates().getFebruar(), new BigDecimal("3.000"));
        Assertions.assertEquals(climateHistoryAverageDtos.get(2).getClimates().getMaerz(), new BigDecimal("4.000"));
        Assertions.assertEquals(climateHistoryAverageDtos.get(2).getClimates().getApril(), new BigDecimal("5.000"));
        Assertions.assertEquals(climateHistoryAverageDtos.get(2).getClimates().getMai(), new BigDecimal("6.000"));
        Assertions.assertEquals(climateHistoryAverageDtos.get(2).getClimates().getJuni(), new BigDecimal("7.000"));
        Assertions.assertEquals(climateHistoryAverageDtos.get(2).getClimates().getJuli(), new BigDecimal("8.000"));
        Assertions.assertEquals(climateHistoryAverageDtos.get(2).getClimates().getAugust(), new BigDecimal("9.000"));
        Assertions.assertEquals(climateHistoryAverageDtos.get(2).getClimates().getSeptember(), new BigDecimal("10.000"));
        Assertions.assertEquals(climateHistoryAverageDtos.get(2).getClimates().getOktober(), new BigDecimal("11.000"));
        Assertions.assertEquals(climateHistoryAverageDtos.get(2).getClimates().getNovember(), new BigDecimal("12.000"));
        Assertions.assertEquals(climateHistoryAverageDtos.get(2).getClimates().getDezember(), new BigDecimal("13.000"));
        // forth Period
        Assertions.assertEquals(climateHistoryAverageDtos.get(3).getStartPeriod(), "1898");
        Assertions.assertEquals(climateHistoryAverageDtos.get(3).getEndPeriod(), "1927");
        Assertions.assertEquals(climateHistoryAverageDtos.get(3).getClimates().getJanuar(), new BigDecimal("2.000"));
        Assertions.assertEquals(climateHistoryAverageDtos.get(3).getClimates().getFebruar(), new BigDecimal("3.000"));
        Assertions.assertEquals(climateHistoryAverageDtos.get(3).getClimates().getMaerz(), new BigDecimal("4.000"));
        Assertions.assertEquals(climateHistoryAverageDtos.get(3).getClimates().getApril(), new BigDecimal("5.000"));
        Assertions.assertEquals(climateHistoryAverageDtos.get(3).getClimates().getMai(), new BigDecimal("6.000"));
        Assertions.assertEquals(climateHistoryAverageDtos.get(3).getClimates().getJuni(), new BigDecimal("7.000"));
        Assertions.assertEquals(climateHistoryAverageDtos.get(3).getClimates().getJuli(), new BigDecimal("8.000"));
        Assertions.assertEquals(climateHistoryAverageDtos.get(3).getClimates().getAugust(), new BigDecimal("9.000"));
        Assertions.assertEquals(climateHistoryAverageDtos.get(3).getClimates().getSeptember(), new BigDecimal("10.000"));
        Assertions.assertEquals(climateHistoryAverageDtos.get(3).getClimates().getOktober(), new BigDecimal("11.000"));
        Assertions.assertEquals(climateHistoryAverageDtos.get(3).getClimates().getNovember(), new BigDecimal("12.000"));
        Assertions.assertEquals(climateHistoryAverageDtos.get(3).getClimates().getDezember(), new BigDecimal("13.000"));


        Assertions.assertEquals(climateHistoryAverageDtos.size(),4);
    }

    @Test
    void happyPermutationTest(){
        //Folgender Test : Es existieren 7 StationsId , in jeder Periode fehlt eine Station
        // Station Id       :          :   1  :   2   :   3   :    4   :   5   :  6   :    7
        // Expected Periods : 2017-1988    x      x       x        x
        // Expected Periods : 1987-1958    x      x       x                                x
        // Expected Periods : 1957-1928    x      x                               x        x
        // Expected Periods : 1927-1898    x                               x      x        x

        //* Get a full stack of testData
        List<StationClimate> stationClimates = ClimateTestData.getStationClimate(BEGIN_PERIOD,END_PERIOD,7);

        //Remove year and stationID from full stack testData
        // Station Id       :          :   1  :   2   :   3   :    4   :   5   :  6   :    7
        // Expected Periods : 2017-1988    x      x       x        x
        int[] stationIdToRemove = {5,6,7};
        String yearToRemove = "2017";
        stationClimates = new ClimateTestData().removeClimates(yearToRemove,stationIdToRemove,stationClimates);

        //Remove year and stationID from full stack testData
        // Station Id       :          :   1  :   2   :   3   :    4   :   5   :  6   :    7
        // Expected Periods : 1987-1958    x      x       x                                x
        stationIdToRemove = new int[]{4, 5, 6};
        yearToRemove = "1987";
        stationClimates = new ClimateTestData().removeClimates(yearToRemove,stationIdToRemove,stationClimates);

        //Remove year and stationID from full stack testData
        // Station Id       :          :   1  :   2   :   3   :    4   :   5   :  6   :    7
        // Expected Periods : 1957-1928    x      x                               x        x
        stationIdToRemove = new int[]{3, 4, 5};
        yearToRemove = "1957";
        stationClimates = new ClimateTestData().removeClimates(yearToRemove,stationIdToRemove,stationClimates);

        //Remove year and stationID from full stack testData
        // Station Id       :          :   1  :   2   :   3   :    4   :   5   :  6   :    7
        // Expected Periods : 1927-1898    x                               x      x        x
        stationIdToRemove = new int[]{2, 3, 4};
        yearToRemove = "1927";
        stationClimates = new ClimateTestData().removeClimates(yearToRemove,stationIdToRemove,stationClimates);


        // Die konzeptionelle Frage stellt sich hier : Welcher Algorythmus kommt hier zur anwendung ?
        // 1. Es wird lediglich die Station, welche über alle Perioden existierte zur Berechnung herangezogen!
        // 2. Es wird das mittel aller zur Verfügung stehenden StationId berechnet ?
        // 3. Es werden lediglich die Perioden angezeigt , welche min über x Stationen verfügen oder vom
        //    durchschnittlich vorhandenen Stationen Abweichen !
        // 4. Variante 1 mit zusätzlichen Statistischen Angaben(Anzahl Stationen ,durchschn. Höhe)
        // 5 . 1-3 Als Parameter auswählbar !
        //* Define Mock szenario

        //* Execute Test
        List<ClimateHistoryDto> climateHistoryAverageDtos = new ClimateHistoryAnalyserServiceImpl().getClimateHistoryAverageData(ORIGIN_YEAR,stationClimates);


        // First Period
        Assertions.assertEquals(climateHistoryAverageDtos.get(0).getStartPeriod(), "1988");
        Assertions.assertEquals(climateHistoryAverageDtos.get(0).getEndPeriod(), ORIGIN_YEAR);
        Assertions.assertEquals(climateHistoryAverageDtos.get(0).getClimates().getJanuar(), new BigDecimal("2.500"));
        Assertions.assertEquals(climateHistoryAverageDtos.get(0).getClimates().getFebruar(), new BigDecimal("3.500"));
        Assertions.assertEquals(climateHistoryAverageDtos.get(0).getClimates().getMaerz(), new BigDecimal("4.500"));
        Assertions.assertEquals(climateHistoryAverageDtos.get(0).getClimates().getApril(), new BigDecimal("5.500"));
        Assertions.assertEquals(climateHistoryAverageDtos.get(0).getClimates().getMai(), new BigDecimal("6.500"));
        Assertions.assertEquals(climateHistoryAverageDtos.get(0).getClimates().getJuni(), new BigDecimal("7.500"));
        Assertions.assertEquals(climateHistoryAverageDtos.get(0).getClimates().getJuli(), new BigDecimal("8.500"));
        Assertions.assertEquals(climateHistoryAverageDtos.get(0).getClimates().getAugust(), new BigDecimal("9.500"));
        Assertions.assertEquals(climateHistoryAverageDtos.get(0).getClimates().getSeptember(), new BigDecimal("10.500"));
        Assertions.assertEquals(climateHistoryAverageDtos.get(0).getClimates().getOktober(), new BigDecimal("11.500"));
        Assertions.assertEquals(climateHistoryAverageDtos.get(0).getClimates().getNovember(), new BigDecimal("12.500"));
        Assertions.assertEquals(climateHistoryAverageDtos.get(0).getClimates().getDezember(), new BigDecimal("13.500"));
        //Second Period
        Assertions.assertEquals(climateHistoryAverageDtos.get(1).getStartPeriod(), "1958");
        Assertions.assertEquals(climateHistoryAverageDtos.get(1).getEndPeriod(), "1987");
        Assertions.assertEquals(climateHistoryAverageDtos.get(1).getClimates().getJanuar(), new BigDecimal("3.250"));
        Assertions.assertEquals(climateHistoryAverageDtos.get(1).getClimates().getFebruar(), new BigDecimal("4.250"));
        Assertions.assertEquals(climateHistoryAverageDtos.get(1).getClimates().getMaerz(), new BigDecimal("5.250"));
        Assertions.assertEquals(climateHistoryAverageDtos.get(1).getClimates().getApril(), new BigDecimal("6.250"));
        Assertions.assertEquals(climateHistoryAverageDtos.get(1).getClimates().getMai(), new BigDecimal("7.250"));
        Assertions.assertEquals(climateHistoryAverageDtos.get(1).getClimates().getJuni(), new BigDecimal("8.250"));
        Assertions.assertEquals(climateHistoryAverageDtos.get(1).getClimates().getJuli(), new BigDecimal("9.250"));
        Assertions.assertEquals(climateHistoryAverageDtos.get(1).getClimates().getAugust(), new BigDecimal("10.250"));
        Assertions.assertEquals(climateHistoryAverageDtos.get(1).getClimates().getSeptember(), new BigDecimal("11.250"));
        Assertions.assertEquals(climateHistoryAverageDtos.get(1).getClimates().getOktober(), new BigDecimal("12.250"));
        Assertions.assertEquals(climateHistoryAverageDtos.get(1).getClimates().getNovember(), new BigDecimal("13.250"));
        Assertions.assertEquals(climateHistoryAverageDtos.get(1).getClimates().getDezember(), new BigDecimal("14.250"));
        //third Period
        Assertions.assertEquals(climateHistoryAverageDtos.get(2).getStartPeriod(), "1928");
        Assertions.assertEquals(climateHistoryAverageDtos.get(2).getEndPeriod(), "1957");
        Assertions.assertEquals(climateHistoryAverageDtos.get(2).getClimates().getJanuar(), new BigDecimal("4.000"));
        Assertions.assertEquals(climateHistoryAverageDtos.get(2).getClimates().getFebruar(), new BigDecimal("5.000"));
        Assertions.assertEquals(climateHistoryAverageDtos.get(2).getClimates().getMaerz(), new BigDecimal("6.000"));
        Assertions.assertEquals(climateHistoryAverageDtos.get(2).getClimates().getApril(), new BigDecimal("7.000"));
        Assertions.assertEquals(climateHistoryAverageDtos.get(2).getClimates().getMai(), new BigDecimal("8.000"));
        Assertions.assertEquals(climateHistoryAverageDtos.get(2).getClimates().getJuni(), new BigDecimal("9.000"));
        Assertions.assertEquals(climateHistoryAverageDtos.get(2).getClimates().getJuli(), new BigDecimal("10.000"));
        Assertions.assertEquals(climateHistoryAverageDtos.get(2).getClimates().getAugust(), new BigDecimal("11.000"));
        Assertions.assertEquals(climateHistoryAverageDtos.get(2).getClimates().getSeptember(), new BigDecimal("12.000"));
        Assertions.assertEquals(climateHistoryAverageDtos.get(2).getClimates().getOktober(), new BigDecimal("13.000"));
        Assertions.assertEquals(climateHistoryAverageDtos.get(2).getClimates().getNovember(), new BigDecimal("14.000"));
        Assertions.assertEquals(climateHistoryAverageDtos.get(2).getClimates().getDezember(), new BigDecimal("15.000"));
        // forth Period
        Assertions.assertEquals(climateHistoryAverageDtos.get(3).getStartPeriod(), "1898");
        Assertions.assertEquals(climateHistoryAverageDtos.get(3).getEndPeriod(), "1927");
        Assertions.assertEquals(climateHistoryAverageDtos.get(3).getClimates().getJanuar(), new BigDecimal("4.750"));
        Assertions.assertEquals(climateHistoryAverageDtos.get(3).getClimates().getFebruar(), new BigDecimal("5.750"));
        Assertions.assertEquals(climateHistoryAverageDtos.get(3).getClimates().getMaerz(), new BigDecimal("6.750"));
        Assertions.assertEquals(climateHistoryAverageDtos.get(3).getClimates().getApril(), new BigDecimal("7.750"));
        Assertions.assertEquals(climateHistoryAverageDtos.get(3).getClimates().getMai(), new BigDecimal("8.750"));
        Assertions.assertEquals(climateHistoryAverageDtos.get(3).getClimates().getJuni(), new BigDecimal("9.750"));
        Assertions.assertEquals(climateHistoryAverageDtos.get(3).getClimates().getJuli(), new BigDecimal("10.750"));
        Assertions.assertEquals(climateHistoryAverageDtos.get(3).getClimates().getAugust(), new BigDecimal("11.750"));
        Assertions.assertEquals(climateHistoryAverageDtos.get(3).getClimates().getSeptember(), new BigDecimal("12.750"));
        Assertions.assertEquals(climateHistoryAverageDtos.get(3).getClimates().getOktober(), new BigDecimal("13.750"));
        Assertions.assertEquals(climateHistoryAverageDtos.get(3).getClimates().getNovember(), new BigDecimal("14.750"));
        Assertions.assertEquals(climateHistoryAverageDtos.get(3).getClimates().getDezember(), new BigDecimal("15.750"));


        Assertions.assertEquals(climateHistoryAverageDtos.size(),4);



    }

    @Test
    void happyHistoryHoleTest(){
        //Folgender Test : Es existiert eine grosse Lücke zwischen den Perioden
        // Station Id       :          :   1  :   2   :   3   :
        // Expected Periods : 2017-1988    x      x       x
        // Expected Periods : 1987-1958
        // Expected Periods : 1957-1928
        // Expected Periods : 1927-1898    x      x       x
        //* Get a full stack of testData
        List<StationClimate> stationClimates = ClimateTestData.getStationClimate(BEGIN_PERIOD,END_PERIOD,3);

        // Remove   Periods : 1987-1958
        int[] stationIdToRemove = {1,2,3};
        String yearToRemove = "1987";
        stationClimates = new ClimateTestData().removeClimates(yearToRemove,stationIdToRemove,stationClimates);

        // Remove   Periods : 1957-1928
        stationIdToRemove = new int[]{1, 2, 3};
        yearToRemove = "1957";
        stationClimates = new ClimateTestData().removeClimates(yearToRemove,stationIdToRemove,stationClimates);

        //* Execute Test
        List<ClimateHistoryDto> climateHistoryAverageDtos = new ClimateHistoryAnalyserServiceImpl().getClimateHistoryAverageData(ORIGIN_YEAR,stationClimates);

        // First Period
        Assertions.assertEquals(climateHistoryAverageDtos.get(0).getStartPeriod(), "1988");
        Assertions.assertEquals(climateHistoryAverageDtos.get(0).getEndPeriod(), ORIGIN_YEAR);
        Assertions.assertEquals(climateHistoryAverageDtos.get(0).getClimates().getJanuar(), new BigDecimal("2.000"));
        Assertions.assertEquals(climateHistoryAverageDtos.get(0).getClimates().getFebruar(), new BigDecimal("3.000"));
        Assertions.assertEquals(climateHistoryAverageDtos.get(0).getClimates().getMaerz(), new BigDecimal("4.000"));
        Assertions.assertEquals(climateHistoryAverageDtos.get(0).getClimates().getApril(), new BigDecimal("5.000"));
        Assertions.assertEquals(climateHistoryAverageDtos.get(0).getClimates().getMai(), new BigDecimal("6.000"));
        Assertions.assertEquals(climateHistoryAverageDtos.get(0).getClimates().getJuni(), new BigDecimal("7.000"));
        Assertions.assertEquals(climateHistoryAverageDtos.get(0).getClimates().getJuli(), new BigDecimal("8.000"));
        Assertions.assertEquals(climateHistoryAverageDtos.get(0).getClimates().getAugust(), new BigDecimal("9.000"));
        Assertions.assertEquals(climateHistoryAverageDtos.get(0).getClimates().getSeptember(), new BigDecimal("10.000"));
        Assertions.assertEquals(climateHistoryAverageDtos.get(0).getClimates().getOktober(), new BigDecimal("11.000"));
        Assertions.assertEquals(climateHistoryAverageDtos.get(0).getClimates().getNovember(), new BigDecimal("12.000"));
        Assertions.assertEquals(climateHistoryAverageDtos.get(0).getClimates().getDezember(), new BigDecimal("13.000"));
        //Second Period
        Assertions.assertEquals(climateHistoryAverageDtos.get(1).getStartPeriod(), "1898");
        Assertions.assertEquals(climateHistoryAverageDtos.get(1).getEndPeriod(), "1927");
        Assertions.assertEquals(climateHistoryAverageDtos.get(1).getClimates().getJanuar(), new BigDecimal("2.000"));
        Assertions.assertEquals(climateHistoryAverageDtos.get(1).getClimates().getFebruar(), new BigDecimal("3.000"));
        Assertions.assertEquals(climateHistoryAverageDtos.get(1).getClimates().getMaerz(), new BigDecimal("4.000"));
        Assertions.assertEquals(climateHistoryAverageDtos.get(1).getClimates().getApril(), new BigDecimal("5.000"));
        Assertions.assertEquals(climateHistoryAverageDtos.get(1).getClimates().getMai(), new BigDecimal("6.000"));
        Assertions.assertEquals(climateHistoryAverageDtos.get(1).getClimates().getJuni(), new BigDecimal("7.000"));
        Assertions.assertEquals(climateHistoryAverageDtos.get(1).getClimates().getJuli(), new BigDecimal("8.000"));
        Assertions.assertEquals(climateHistoryAverageDtos.get(1).getClimates().getAugust(), new BigDecimal("9.000"));
        Assertions.assertEquals(climateHistoryAverageDtos.get(1).getClimates().getSeptember(), new BigDecimal("10.000"));
        Assertions.assertEquals(climateHistoryAverageDtos.get(1).getClimates().getOktober(), new BigDecimal("11.000"));
        Assertions.assertEquals(climateHistoryAverageDtos.get(1).getClimates().getNovember(), new BigDecimal("12.000"));
        Assertions.assertEquals(climateHistoryAverageDtos.get(1).getClimates().getDezember(), new BigDecimal("13.000"));

        Assertions.assertEquals(climateHistoryAverageDtos.size(),2);


    }



    @Test
    void errorHistoryTest(){
        //Absolutly No History DataFound !
        //TODO Implement Test !
        // ?? Empty ? Null ?
    }


    // Folgende Test sollen möglichst viele Permutationen abdecken
    // 1. Die erste Station ist als einzige in allen Perioden vorhanden
    // 2. Die letzte Station ist als einzige in allen Perioden vorhanden
    // 3. Alle Stationen sind in allen Perioden vorhanden
    // 4. Jeder Station fehlt eine Periode = keine Daten werden hier geliefert !
    // 5. Jede zweite Station ist in allen Periode vorhanden und jeder zweiten wiederum fehlt eine Periode

    @Test
    void getClimateHistoryEveryStationExistsData() {


        // 1. Die erste Station ist als einzige in allen Perioden vorhanden
        // Station Id       :          :   1  :   2   :   3   :    4     :   5     :     6
        // Expected Periods : 2017-1988    x
        // Expected Periods : 1987-1958    x
        // Expected Periods : 1957-1928    x
        // Expected Periods : 1927-1898    x

        // 2. Die letzte Station ist als einzige in allen Perioden vorhanden
        // Station Id       :          :   1  :   2   :   3   :    4     :   5     :     6
        // Expected Periods : 2017-1988                                                  x
        // Expected Periods : 1987-1958                                                  x
        // Expected Periods : 1957-1928                                                  x
        // Expected Periods : 1927-1898                                                  x


        // 3. Alle Stationen sind in allen Perioden vorhanden
        // Station Id       :          :   1  :   2   :   3   :    4     :   5     :     6
        // Expected Periods : 2017-1988    x      x       x        x         x           x
        // Expected Periods : 1987-1958    x      x       x        x         x           x
        // Expected Periods : 1957-1928    x      x       x        x         x           x
        // Expected Periods : 1927-1898    x      x       x        x         x           x

        // 4. Jeder Station fehlt eine Periode = keine Daten werden hier geliefert !
        // Station Id       :          :   1  :   2   :   3   :    4
        // Expected Periods : 2017-1988           x       x        x
        // Expected Periods : 1987-1958    x              x        x
        // Expected Periods : 1957-1928    x      x                x
        // Expected Periods : 1927-1898    x      x       x

        // 5. Jede zweite Station ist in allen Periode vorhanden und jeder zweiten wiederum fehlt eine Periode
        // Station Id       :          :   1  :   2   :   3   :    4     :   5     :     6
        // Expected Periods : 2017-1988    x      x       x        x         x           x
        // Expected Periods : 1987-1958    x              x        x         x           x
        // Expected Periods : 1957-1928    x      x       x                  x           x
        // Expected Periods : 1927-1898    x      x       x        x         x



    }
}