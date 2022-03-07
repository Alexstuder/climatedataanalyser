package ch.studer.germanclimatedataanalyser.batch.writer;

import ch.studer.germanclimatedataanalyser.generate.test.data.StationWeatherPerYearTestData;
import ch.studer.germanclimatedataanalyser.model.database.StationClimate;
import ch.studer.germanclimatedataanalyser.model.database.StationWeatherPerYear;
import ch.studer.germanclimatedataanalyser.service.db.ClimateService;
import ch.studer.germanclimatedataanalyser.service.db.StationWeatherService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ClimateWriterTest {

    @Mock
    ClimateService climateService;

    @Mock
    StationWeatherService stationWeatherService;

    @InjectMocks
    ClimateWriter climateWriter;


    private final static String START_DATE = "2018";

    private static final int stationId = 1;

    List<StationWeatherPerYear> stationWeatherPerYearList = new ArrayList<>();


    @Test
    void writeHappyTest() {

        // ******************
        // Build TestRecords
        // ******************
        stationWeatherPerYearList = new ArrayList<>();
        List<StationWeatherPerYear> weatherComplete = StationWeatherPerYearTestData.getStationWeatherPerYearList("2018", 1, false);
        stationWeatherPerYearList.addAll(weatherComplete);
        List<StationWeatherPerYear> weatherWithHoles = StationWeatherPerYearTestData.getHoles(weatherComplete);
        stationWeatherPerYearList.addAll(weatherWithHoles);

        List<StationWeatherPerYear> weatherComplete2 = StationWeatherPerYearTestData.getStationWeatherPerYearList("2017", 2, false);
        stationWeatherPerYearList.addAll(weatherComplete2);
        List<StationWeatherPerYear> weatherWithHoles2 = StationWeatherPerYearTestData.getHoles(weatherComplete2);
        stationWeatherPerYearList.addAll(weatherWithHoles2);


        // *********************
        // Mock ClimateService
        // *********************

        when(stationWeatherService.fillHoles(anyList())).thenReturn(weatherComplete);
//        doReturn(weatherComplete).when(stationWeatherService.fillHoles(weatherWithHoles));
        when(climateService.getClimateForStation(weatherComplete)).thenReturn(getClimate(weatherComplete));

        // ************
        // Start Test
        // ************
        Exception exception = null;

        // Test Happy Flow ClimateWrite and Assert exception == null
        try {
            climateWriter.write(stationWeatherPerYearList);
        } catch (Exception e) {
            e.printStackTrace();
            exception = e;
        }

        Assertions.assertNull(exception);
    }

    private List<StationClimate> getClimate(List<StationWeatherPerYear> weatherComplete) {
        List<StationClimate> stationClimates = new ArrayList<>();
        stationClimates.add(getClimateRecord(weatherComplete.get(0).getStationID()));

        return stationClimates;
    }

    private StationClimate getClimateRecord(int stationId) {

        StationClimate c = new StationClimate(stationId);
        c.setJanuar(new BigDecimal(1));
        c.setFebruar(new BigDecimal(2));
        c.setMaerz(new BigDecimal(3));
        c.setApril(new BigDecimal(4));
        c.setMai(new BigDecimal(5));
        c.setJuni(new BigDecimal(6));
        c.setJuli(new BigDecimal(7));
        c.setAugust(new BigDecimal(8));
        c.setSeptember(new BigDecimal(9));
        c.setOktober(new BigDecimal(10));
        c.setNovember(new BigDecimal(11));
        c.setDezember(new BigDecimal(12));

        return c;
    }

}
