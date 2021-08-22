package ch.studer.germanclimatedataanalyser.service.db;

import ch.studer.germanclimatedataanalyser.generate.test.data.StationWeatherPerYearTestData;
import ch.studer.germanclimatedataanalyser.model.database.StationWeatherPerYear;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.util.ReflectionTestUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

class StationWeatherServiceImplTest {

    StationWeatherServiceImpl stationWeatherService = new StationWeatherServiceImpl();


    private static final Logger LOG = LoggerFactory.getLogger(StationWeatherServiceImplTest.class);
    // Set Test variable

    private final int range = 4;
    private final int period = 30;

    @BeforeEach
    void setUp() {

        String value = "-999.0000";
        ReflectionTestUtils.setField(stationWeatherService, "NULL_TEMPERATURE_INIT", value);
        ReflectionTestUtils.setField(stationWeatherService, "period", period);
        ReflectionTestUtils.setField(stationWeatherService, "range", range);
    }


    // Tests : complete 1 test records without anual gaps
    //         complete 2 test records with anual gaps

    @Test
    void fillHolesTest() {

//        when(stationWeatherDAO.)

        List<StationWeatherPerYear> stationWeatherPerYearList = new ArrayList<StationWeatherPerYear>();

        // Get First a complete set of 30 weather records (without annual gap and without NULL_TEMPERATURE)
        List<StationWeatherPerYear> weatherComplete = StationWeatherPerYearTestData.getStationWeatherPerYearList("2018", 1, false);

        // Get the 30 complete records and put some NULL_TEMPERATURES into
        List<StationWeatherPerYear> weatherWithHoles = StationWeatherPerYearTestData.getHoles(weatherComplete);

        // remove one Record to test annual gap completion
        weatherWithHoles.remove(15);
        stationWeatherPerYearList.addAll(weatherWithHoles);

        // Test the Service :
        // SetUp  : Just process a List with 1 StationId (group by prepended by ClimateWriter)
        List<StationWeatherPerYear> testList = stationWeatherService.fillHoles(stationWeatherPerYearList);

        int index = 0;
        for (StationWeatherPerYear test : testList) {

            Assertions.assertEquals(0, test.getJanuar().compareTo(weatherComplete.get(index).getJanuar()));
            Assertions.assertEquals(0, test.getFebruar().compareTo(weatherComplete.get(index).getFebruar()));
            Assertions.assertEquals(0, test.getMaerz().compareTo(weatherComplete.get(index).getMaerz()));
            Assertions.assertEquals(0, test.getApril().compareTo(weatherComplete.get(index).getApril()));
            Assertions.assertEquals(0, test.getMai().compareTo(weatherComplete.get(index).getMai()));
            Assertions.assertEquals(0, test.getJuni().compareTo(weatherComplete.get(index).getJuni()));
            Assertions.assertEquals(0, test.getJuli().compareTo(weatherComplete.get(index).getJuli()));
            Assertions.assertEquals(0, test.getAugust().compareTo(weatherComplete.get(index).getAugust()));
            Assertions.assertEquals(0, test.getSeptember().compareTo(weatherComplete.get(index).getSeptember()));
            Assertions.assertEquals(0, test.getOktober().compareTo(weatherComplete.get(index).getOktober()));
            Assertions.assertEquals(0, test.getNovember().compareTo(weatherComplete.get(index).getNovember()));
            Assertions.assertEquals(0, test.getDezember().compareTo(weatherComplete.get(index).getDezember()));

            index++;
        }
    }

    @Test
    void fillHolesWithToMuchNullValuesAtTheBeginingAndEnd() {

        List<StationWeatherPerYear> stationWeatherPerYears = new ArrayList<StationWeatherPerYear>();

        List<StationWeatherPerYear> stationWeatherPerYearsWithHoles = StationWeatherPerYearTestData.getStationWeatherPerYearList("2019", 2, true);

        stationWeatherPerYearsWithHoles.addAll(StationWeatherPerYearTestData.getStationWeatherPerYearList("1989", 2, false));
        stationWeatherPerYearsWithHoles.addAll(StationWeatherPerYearTestData.getStationWeatherPerYearList("1959", 2, true));


        // remove random record between 1989 and 1960 !
        stationWeatherPerYearsWithHoles.remove(20);


        // Test the Service :
        // SetUp  : Just process a List with 1 StationId (group by prepended)
        List<StationWeatherPerYear> testList = stationWeatherService.fillHoles(stationWeatherPerYearsWithHoles);

        // calculate size !
        int size = period + (2 * range);
        Assertions.assertEquals(testList.size(), size);

        for (StationWeatherPerYear stationWeatherPerYear : testList) {
            Assertions.assertEquals(0, stationWeatherPerYear.getJanuar().compareTo(new BigDecimal("-1.111")), "Januar is not Equal");
            Assertions.assertEquals(0, stationWeatherPerYear.getFebruar().compareTo(new BigDecimal("-2.222")), "Februar is not Equal");
            Assertions.assertEquals(0, stationWeatherPerYear.getMaerz().compareTo(new BigDecimal("3.333")), "Marz is not Equal");
            Assertions.assertEquals(0, stationWeatherPerYear.getApril().compareTo(new BigDecimal("4.444")), "April is not Equal");
            Assertions.assertEquals(0, stationWeatherPerYear.getMai().compareTo(new BigDecimal("5.555")), "Mai is not Equal");
            Assertions.assertEquals(0, stationWeatherPerYear.getJuni().compareTo(new BigDecimal("6.666")), "June is not Equal");
            Assertions.assertEquals(0, stationWeatherPerYear.getJuli().compareTo(new BigDecimal("7.777")), "Juli is not Equal");
            Assertions.assertEquals(0, stationWeatherPerYear.getAugust().compareTo(new BigDecimal("8.888")), "August is not Equal");
            Assertions.assertEquals(0, stationWeatherPerYear.getSeptember().compareTo(new BigDecimal("9.999")), "September is not Equal");
            Assertions.assertEquals(0, stationWeatherPerYear.getOktober().compareTo(new BigDecimal("10.100")), "October is not Equal");
            Assertions.assertEquals(0, stationWeatherPerYear.getNovember().compareTo(new BigDecimal("-11.111")), "November is not Equal");
            Assertions.assertEquals(0, stationWeatherPerYear.getDezember().compareTo(new BigDecimal("-12.120")), "December is not Equal");

        }

    }

    @Test
    void fillHolesWithToMuchNullValuesInTheMiddle() {

        List<StationWeatherPerYear> stationWeatherPerYearsWithHoles = StationWeatherPerYearTestData.getStationWeatherPerYearList("2019", 2, true);

        stationWeatherPerYearsWithHoles.addAll(StationWeatherPerYearTestData.getStationWeatherPerYearList("1989", 2, false));
        stationWeatherPerYearsWithHoles.addAll(StationWeatherPerYearTestData.getStationWeatherPerYearList("1959", 2, true));

        //Remove not needed Records
        stationWeatherPerYearsWithHoles.remove(20);


        // Test the Service :
        // SetUp  : Just process a List with 1 StationId (group by prepended)
        List<StationWeatherPerYear> testList = stationWeatherService.fillHoles(stationWeatherPerYearsWithHoles);

        // calculate size !
        int size = period + (2 * range);
        Assertions.assertEquals(testList.size(), size);

        for (StationWeatherPerYear stationWeatherPerYear : testList) {
            Assertions.assertEquals(0, stationWeatherPerYear.getJanuar().compareTo(new BigDecimal("-1.111")), "Januar is not Equal");
            Assertions.assertEquals(0, stationWeatherPerYear.getFebruar().compareTo(new BigDecimal("-2.222")), "Februar is not Equal");
            Assertions.assertEquals(0, stationWeatherPerYear.getMaerz().compareTo(new BigDecimal("3.333")), "Marz is not Equal");
            Assertions.assertEquals(0, stationWeatherPerYear.getApril().compareTo(new BigDecimal("4.444")), "April is not Equal");
            Assertions.assertEquals(0, stationWeatherPerYear.getMai().compareTo(new BigDecimal("5.555")), "Mai is not Equal");
            Assertions.assertEquals(0, stationWeatherPerYear.getJuni().compareTo(new BigDecimal("6.666")), "June is not Equal");
            Assertions.assertEquals(0, stationWeatherPerYear.getJuli().compareTo(new BigDecimal("7.777")), "Juli is not Equal");
            Assertions.assertEquals(0, stationWeatherPerYear.getAugust().compareTo(new BigDecimal("8.888")), "August is not Equal");
            Assertions.assertEquals(0, stationWeatherPerYear.getSeptember().compareTo(new BigDecimal("9.999")), "September is not Equal");
            Assertions.assertEquals(0, stationWeatherPerYear.getOktober().compareTo(new BigDecimal("10.100")), "October is not Equal");
            Assertions.assertEquals(0, stationWeatherPerYear.getNovember().compareTo(new BigDecimal("-11.111")), "November is not Equal");
            Assertions.assertEquals(0, stationWeatherPerYear.getDezember().compareTo(new BigDecimal("-12.120")), "December is not Equal");

        }

    }

}
