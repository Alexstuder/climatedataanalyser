package ch.studer.germanclimatedataanalyser.service;

import ch.studer.germanclimatedataanalyser.dao.StationWeatherDAO;
import ch.studer.germanclimatedataanalyser.generate.test.data.StationWeatherPerYearTestData;
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

@SpringBootTest
class StationWeatherServiceImplTest {


    @Mock
    StationWeatherDAO stationWeatherDAO;


    @InjectMocks
    StationWeatherServiceImpl stationWeatherService;

    //@Value("#{new Integer('${climate.temperature.big.decimal.null.value}')}")
    //private static BigDecimal NULL_TEMPERATURE;


    private static final Logger LOG = LoggerFactory.getLogger(StationWeatherServiceImplTest.class);
    @BeforeEach
    void setUp() {

        String value = "-999.0000";
        ReflectionTestUtils.setField(stationWeatherService,"NULL_TEMPERATURE_INIT",value);
        ReflectionTestUtils.setField(stationWeatherService,"period",30);
    }



    // Tests : complete 1 test records without anual gaps
    //         complete 2 test records with anual gaps

    @Test
    void fillHolesTest() {

        List<StationWeatherPerYear> stationWeatherPerYearList = new ArrayList<StationWeatherPerYear>();

        // Get First a complete set of 30 weather records (without annual gap and without NULL_TEMPERATURE)
        List<StationWeatherPerYear> weatherComplete = StationWeatherPerYearTestData.getStationWeatherPerYearList("2018",1,false);

        // Get the 30 complete records and put some NULL_TEMPERATURES into
        List<StationWeatherPerYear> weatherWithHoles = StationWeatherPerYearTestData.getHoles(weatherComplete);

        // remove one Record to test annual gap completion
        weatherWithHoles.remove(15);
        stationWeatherPerYearList.addAll(weatherWithHoles);

        // Test the Service :
        // SetUp  : Just process a List with 1 StationId (group by prepended by ClimateWriter)
        List<StationWeatherPerYear> testList = stationWeatherService.fillHoles(stationWeatherPerYearList);

        int index= 0;
        for(StationWeatherPerYear test: testList){

            Assertions.assertTrue(test.getJanuar().compareTo(weatherComplete.get(index).getJanuar()) == 0);
            Assertions.assertTrue(test.getFebruar().compareTo(weatherComplete.get(index).getFebruar()) == 0);
            Assertions.assertTrue(test.getMaerz().compareTo(weatherComplete.get(index).getMaerz()) == 0);
            Assertions.assertTrue(test.getApril().compareTo(weatherComplete.get(index).getApril()) == 0);
            Assertions.assertTrue(test.getMai().compareTo(weatherComplete.get(index).getMai()) == 0);
            Assertions.assertTrue(test.getJuni().compareTo(weatherComplete.get(index).getJuni()) == 0);
            Assertions.assertTrue(test.getJuli().compareTo(weatherComplete.get(index).getJuli()) == 0);
            Assertions.assertTrue(test.getAugust().compareTo(weatherComplete.get(index).getAugust()) == 0);
            Assertions.assertTrue(test.getSeptember().compareTo(weatherComplete.get(index).getSeptember()) == 0);
            Assertions.assertTrue(test.getOktober().compareTo(weatherComplete.get(index).getOktober()) == 0);
            Assertions.assertTrue(test.getNovember().compareTo(weatherComplete.get(index).getNovember()) == 0);
            Assertions.assertTrue(test.getDezember().compareTo(weatherComplete.get(index).getDezember()) == 0);

           index++;
        }
    }

    @Test
    void fillHolesWithToMuchNullValues(){

        List<StationWeatherPerYear> stationWeatherPerYears = new ArrayList<StationWeatherPerYear>();

        List<StationWeatherPerYear> stationWeatherPerYearsWithHoles = StationWeatherPerYearTestData.getStationWeatherPerYearList("2019",2,true);

        stationWeatherPerYearsWithHoles.addAll(StationWeatherPerYearTestData.getStationWeatherPerYearList("1989",2,false));
        stationWeatherPerYearsWithHoles.addAll(StationWeatherPerYearTestData.getStationWeatherPerYearList("1959",2,true));


        // remove random record between 1989 and 1960 !
        stationWeatherPerYearsWithHoles.remove(20);


        // Test the Service :
        // SetUp  : Just process a List with 1 StationId (group by prepended)
        List<StationWeatherPerYear> testList = stationWeatherService.fillHoles(stationWeatherPerYearsWithHoles);

        Assertions.assertTrue(testList.size()==34);

        for(StationWeatherPerYear stationWeatherPerYear : testList){
            Assertions.assertTrue(stationWeatherPerYear.getJanuar().compareTo(new BigDecimal("-1.111"))==0);
            Assertions.assertTrue(stationWeatherPerYear.getFebruar().compareTo(new BigDecimal("-2.222"))==0);
            Assertions.assertTrue(stationWeatherPerYear.getMaerz().compareTo(new BigDecimal("3.333"))==0);
            Assertions.assertTrue(stationWeatherPerYear.getApril().compareTo(new BigDecimal("4.444"))==0);
            Assertions.assertTrue(stationWeatherPerYear.getMai().compareTo(new BigDecimal("5.555"))==0);
            Assertions.assertTrue(stationWeatherPerYear.getJuni().compareTo(new BigDecimal("6.666"))==0);
            Assertions.assertTrue(stationWeatherPerYear.getJuli().compareTo(new BigDecimal("7.777"))==0);
            Assertions.assertTrue(stationWeatherPerYear.getAugust().compareTo(new BigDecimal("8.888"))==0);
            Assertions.assertTrue(stationWeatherPerYear.getSeptember().compareTo(new BigDecimal("9.999"))==0);
            Assertions.assertTrue(stationWeatherPerYear.getOktober().compareTo(new BigDecimal("10.100"))==0);
            Assertions.assertTrue(stationWeatherPerYear.getNovember().compareTo(new BigDecimal("-11.111"))==0);
            Assertions.assertTrue(stationWeatherPerYear.getDezember().compareTo(new BigDecimal("-12.120"))==0);

        }

    }

    private boolean isWeatherEqual(List<StationWeatherPerYear> h, List<StationWeatherPerYear> c) {
        boolean status = true ;
        int size = 0 ;

        if(h.size() == c.size()){
            size = h.size();


            for (int i =0 ; i < c.size(); i++){
                //TODO Proof all Month !! Not Only Januar !? And Poff if compare cpuld handle the math differences !?

                // if Status turns oncy to false : it will stay false !
               status =  (h.get(i).getJanuar().compareTo(c.get(i).getJanuar()) == 0) ? status : false;
               LOG.debug("Index {}, Status {}",i,status);

            }

        } else {
            LOG.debug("Size are not Equal : Size withHolesWeather : {} .Size completeWeather : {} !",h.size(),c.size());
        }
        return status;
    }
}
