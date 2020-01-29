package ch.studer.germanclimatedataanalyser.service;

import ch.studer.germanclimatedataanalyser.generate.test.data.ClimateTestData;
import ch.studer.germanclimatedataanalyser.generate.test.data.StationTestData;
import ch.studer.germanclimatedataanalyser.model.database.Station;
import ch.studer.germanclimatedataanalyser.model.database.StationClimate;
import ch.studer.germanclimatedataanalyser.model.dto.ClimateAnalyserRequestDto;
import ch.studer.germanclimatedataanalyser.model.dto.ClimateAnalyserResponseDto;
import ch.studer.germanclimatedataanalyser.model.dto.helper.GpsPoint;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;

import javax.persistence.EntityManager;
import java.util.List;

//@RunWith(SpringRunner.class)
//@DataJpaTest
@SpringBootTest
//@AutoConfigureTestEntityManager
@TestPropertySource(locations="classpath:test-it.properties")
@Sql({"classpath:H2Schema.sql"})
class ClimateServiceImplIntegrationTest {

    @Autowired
    ClimateService climateService ;

    @Autowired
    StationService stationService;


     @Autowired
    ClimateAnalyserService climateAnalyserService;

    @Autowired
    private JdbcTemplate jdbcTemplate ;

    private static final Logger LOG = LoggerFactory.getLogger(ClimateServiceImplIntegrationTest.class);
    @BeforeEach
    void setUp() {
        // Prepend the Tables
    /*    jdbcTemplate.execute("DROP Table station");
        jdbcTemplate.execute("DROP Table month");
        jdbcTemplate.execute("DROP Table weather");
        jdbcTemplate.execute("DROP Table climate");
*/
        List<StationClimate> stationClimates =  ClimateTestData.getStationClimate(20);

        climateService.saveAllClimateAtStationId(stationClimates);
        //ReflectionTestUtils.setField(climateService,"period",30);

        List<Station> stations = StationTestData.getStations(15);
        stationService.saveAllStation(stations);

    }

    @Test
    void getClimateByGps() {

        GpsPoint gps1 = new GpsPoint(49,9);
        GpsPoint gps2 = new GpsPoint(38,20);
       List<StationClimate> stationClimates = climateService.getClimateForGpsCoordinates(gps1,gps2);

       for(StationClimate station : stationClimates){
           LOG.info(String.valueOf(station.getStationId()));

       }

       ClimateAnalyserRequestDto climateAnalyserRequestDto = new ClimateAnalyserRequestDto();
       climateAnalyserRequestDto.setBundesland("");
       climateAnalyserRequestDto.setGps1(gps1);
       climateAnalyserRequestDto.setGps2(gps2);

        ClimateAnalyserResponseDto climateAnalyserResponseDto  =
       climateAnalyserService.getClimateAnalyticsByClimateAnalyserRequest(climateAnalyserRequestDto);

        climateAnalyserResponseDto.getErrorMsg();
    }
}