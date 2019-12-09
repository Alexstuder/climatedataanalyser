package ch.studer.germanclimatedataanalyser.service;

import ch.studer.germanclimatedataanalyser.batch.writer.ClimateWriter;
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
import org.springframework.beans.factory.annotation.Value;
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

    @Value("#{new Integer('${climate.temperature.big.decimal.null.value}')}")
    private static BigDecimal NULL_TEMPERATURE;


    private static final Logger LOG = LoggerFactory.getLogger(StationWeatherServiceImplTest.class);
    @BeforeEach
    void setUp() {

        BigDecimal value = new BigDecimal("-99.999");
        ReflectionTestUtils.setField(stationWeatherService,"NULL_TEMPERATURE",value);
        ReflectionTestUtils.setField(stationWeatherService,"period",30);
    }

    @Test
    void fillHoles() {

        List<StationWeatherPerYear> stationWeatherPerYearList = new ArrayList<StationWeatherPerYear>();

        List<StationWeatherPerYear> weatherComplete = StationWeatherPerYearTestData.getStationWeatherPerYearList("2018",1);

        List<StationWeatherPerYear> weatherWithHoles = StationWeatherPerYearTestData.getHoles(weatherComplete);

        // remove one Record to test completion
        weatherWithHoles.remove(15);
        stationWeatherPerYearList.addAll(weatherWithHoles);

        //StationWeatherService stationWeatherService = new StationWeatherServiceImpl();

        // Test the Service :
        // SetUp  : Just process a List with 1 StationId (group by prepended)
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


        // Add Assertions
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
