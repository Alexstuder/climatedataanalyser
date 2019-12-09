package ch.studer.germanclimatedataanalyser.batch.writer;

import ch.studer.germanclimatedataanalyser.generate.test.data.StationWeatherPerYearTestData;
import ch.studer.germanclimatedataanalyser.model.database.StationClimate;
import ch.studer.germanclimatedataanalyser.model.database.StationWeatherPerYear;
import ch.studer.germanclimatedataanalyser.service.ClimateService;
import ch.studer.germanclimatedataanalyser.service.StationWeatherService;
import org.hamcrest.Matcher;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.internal.matchers.Any;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
class ClimateWriterTest {

    @Mock
    ClimateService climateService;

    @Mock
    StationWeatherService stationWeatherService;

    @InjectMocks
    ClimateWriter climateWriter;



    private final static String START_DATE = "2018";

    @Value("#{new Integer('${climate.calculation.period.year}')}")
    private int period;

    @Value("#{new Integer('${climate.temperature.big.decimal.null.value}')}")
    private static BigDecimal NULL_TEMPERATURE;


    private static final int stationId = 1 ;

    List<StationWeatherPerYear> stationWeatherPerYearList = new ArrayList<StationWeatherPerYear>();

    @BeforeEach
    void setUp() {

    }

    private List<StationWeatherPerYear> getStationWeatherPerYearList(String startDate,int stationId) {
        List<StationWeatherPerYear> l = new ArrayList<>();

        int start = Integer.valueOf(startDate);

        for(int i = start; i > start-period ;i-- ){
            l.add(getStationWeatherPerYear(i,stationId));
        }

        return l;
    }

    private StationWeatherPerYear getStationWeatherPerYear(int i,int stationId) {

        StationWeatherPerYear s = new StationWeatherPerYear(stationId);
        s.setYear(String.valueOf(i));
        s.setStationId(stationId);
        s.setJanuar(new BigDecimal(-1.111));
        s.setFebruar(new BigDecimal(-2.222));
        s.setMaerz(new BigDecimal(3.333));
        s.setApril(new BigDecimal(4.444));
        s.setMai(new BigDecimal(5.555));
        s.setJuni(new BigDecimal(6.666));
        s.setJuli(new BigDecimal(7.777));
        s.setAugust(new BigDecimal(8.888));
        s.setSeptember(new BigDecimal(9.999));
        s.setOktober(new BigDecimal(10.10));
        s.setNovember(new BigDecimal(-11.111));
        s.setDezember(new BigDecimal(-12.12));



        return s;
    }

    @Test
    void writeHappyTest() {

        // ******************
        // Build TestRecords
        // ******************
        stationWeatherPerYearList = new ArrayList<StationWeatherPerYear>();
        List<StationWeatherPerYear> weatherComplete = StationWeatherPerYearTestData.getStationWeatherPerYearList("2018",1);
        stationWeatherPerYearList.addAll(weatherComplete);
        List<StationWeatherPerYear> weatherWithHoles = StationWeatherPerYearTestData.getHoles(weatherComplete);
        stationWeatherPerYearList.addAll(weatherWithHoles);

        List<StationWeatherPerYear> weatherComplete2 = StationWeatherPerYearTestData.getStationWeatherPerYearList("2017",2);
        stationWeatherPerYearList.addAll(weatherComplete2);
        List<StationWeatherPerYear> weatherWithHoles2 = StationWeatherPerYearTestData.getHoles(weatherComplete2);
        stationWeatherPerYearList.addAll(weatherWithHoles2);




        // *********************
        // Mock ClimateService
        // *********************

        // **************************
        //Mock StationWeatherService
        when(stationWeatherService.fillHoles(weatherWithHoles)).thenReturn(weatherComplete);
        when(climateService.getClimateForStation(weatherComplete)).thenReturn(getClimate(weatherComplete));
        //TODO Proof if the Methode is Called with the right parameter ! ...but how !!??
        //when(climateService.saveAllClimateAtStationId());
        // **************************

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
        // TODO Mockito Write Assertion to proof , if the CAll to DB was right !? ..but how ??
    }

    private List<StationClimate> getClimate(List<StationWeatherPerYear> weatherComplete) {
        List<StationClimate> stationClimates = new ArrayList<StationClimate>();
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


    private List<StationWeatherPerYear> getHoles(List<StationWeatherPerYear> weatherComplete) {

        List<StationWeatherPerYear> weatherWithHoles = new ArrayList<StationWeatherPerYear>();

        int i = 0;
        for(StationWeatherPerYear s : weatherComplete){
           i++;
           StationWeatherPerYear n = new StationWeatherPerYear(s.getStationID());
           if (i != 1) n.setJanuar(s.getJanuar());else n.setJanuar(NULL_TEMPERATURE);
           if (i != 2) n.setFebruar(s.getFebruar());else n.setFebruar(NULL_TEMPERATURE);
           if (i != 3) n.setMaerz(s.getMaerz());else n.setMaerz(NULL_TEMPERATURE);
           if (i != 4) n.setApril(s.getApril());else n.setApril(NULL_TEMPERATURE);
           if (i != 5) n.setMai(s.getMai());else n.setMai(NULL_TEMPERATURE);
           if (i != 6) n.setJuni(s.getJuni());else n.setJuni(NULL_TEMPERATURE);
           if (i != 7) n.setJuli(s.getJuli());else n.setJuli(NULL_TEMPERATURE);
           if (i != 8) n.setAugust(s.getAugust());else n.setAugust(NULL_TEMPERATURE);
           if (i != 9) n.setSeptember(s.getSeptember());else n.setSeptember(NULL_TEMPERATURE);
           if (i != 10) n.setOktober(s.getOktober());else n.setOktober(NULL_TEMPERATURE);
           if (i != 11) n.setNovember(s.getNovember());else n.setNovember(NULL_TEMPERATURE);
           if (i != 12) n.setDezember(s.getDezember());else {n.setDezember(NULL_TEMPERATURE);i=0;}

         weatherWithHoles.add(n);
        }



        return  weatherWithHoles;
    }

}