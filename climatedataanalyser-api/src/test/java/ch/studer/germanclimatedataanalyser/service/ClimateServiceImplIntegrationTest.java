package ch.studer.germanclimatedataanalyser.service;

import ch.studer.germanclimatedataanalyser.generate.test.data.ClimateTestData;
import ch.studer.germanclimatedataanalyser.model.database.StationClimate;
import ch.studer.germanclimatedataanalyser.model.dto.helper.GpsPoint;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.TestPropertySource;

import javax.persistence.EntityManager;
import java.util.List;

//@RunWith(SpringRunner.class)
//@DataJpaTest
@SpringBootTest
//@AutoConfigureTestEntityManager
@TestPropertySource(locations="classpath:test-it.properties")
class ClimateServiceImplIntegrationTest {


    @Autowired
    ClimateService climateService ;

    @Autowired
    EntityManager entityManager;

    @Autowired
    private JdbcTemplate jdbcTemplate ;

    private static final Logger LOG = LoggerFactory.getLogger(ClimateServiceImplIntegrationTest.class);
    @BeforeEach
    void setUp() {
        // Prepend the Tables
        jdbcTemplate.execute("Delete FROM station");
        jdbcTemplate.execute("Delete FROM month");
        jdbcTemplate.execute("Delete FROM weather");
        jdbcTemplate.execute("Delete FROM climate");

        List<StationClimate> stationClimates =  ClimateTestData.getStationClimate(0);

        climateService.saveAllClimateAtStationId(stationClimates);
        //ReflectionTestUtils.setField(climateService,"period",30);
    }

    @Test
    void getClimateByGps() {

        GpsPoint gps1 = new GpsPoint(55,8);
        GpsPoint gps2 = new GpsPoint(54.8,10);
       List<StationClimate> stationClimates = climateService.getClimateForGpsCoordinates(gps1,gps2);
    }
}