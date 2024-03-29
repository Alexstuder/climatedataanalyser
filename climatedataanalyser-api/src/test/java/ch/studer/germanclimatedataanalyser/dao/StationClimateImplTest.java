package ch.studer.germanclimatedataanalyser.dao;

import ch.studer.germanclimatedataanalyser.model.database.StationClimate;
import ch.studer.germanclimatedataanalyser.model.dto.helper.GpsPoint;
import ch.studer.germanclimatedataanalyser.service.db.ClimateService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

@SpringBootTest
@TestPropertySource(locations = "classpath:test-it.properties")
@Sql({"classpath:schema.sql", "classpath:StationClimate/climates.sql", "classpath:StationClimate/stations.sql"})
class StationClimateImplTest {

    @Autowired
    DataAccess dataAccess;

    private static final Logger LOG = LoggerFactory.getLogger(StationClimateImplTest.class);


    @Test
    void getClimateForGpsCoordinatesFromYearOrderByYearAndStationId() {

        GpsPoint gpsPoint1 = new GpsPoint(20d, 10d);
        GpsPoint gpsPoint2 = new GpsPoint(10d, 20d);
        String fromYear = "1961";

        List<StationClimate> stationClimateList = dataAccess.stationClimateDAO.getClimateForGpsCoordinatesFromYearOrderByYearAndStationId(gpsPoint1, gpsPoint2, fromYear);
        int index = 1;
        for (StationClimate stationClimate : stationClimateList) {

            if (stationClimate.getStationId() == index) {
                index++;
            } else {
                LOG.debug("SQL getClimateForGpsCoordinatesFromYearOrderedByFromYearAndStations : retournierte falsche Records !! ");

            }

        }
        Assertions.assertNotEquals(1, index, "Test wurde nicht durchlaufen !");

    }

    @Test
    void getClimateForBundeslandFromYearOrderByYearAndStationId() {

        final String bundesland = "Baden-Württemberg";
        String fromYear = "1961";

        List<StationClimate> stationClimateList = dataAccess.getStationClimateDAO().getClimateForBundeslandFromYearOrderByYearAndStationId(bundesland, fromYear);
        int index = 1;
        for (StationClimate stationClimate : stationClimateList) {

            if (stationClimate.getStationId() == index) {
                index++;
            } else {
                LOG.debug("SQL getClimateForBundeslandFromYearOrderedByFromYearAndStations : retournierte falsche Records !! ");

            }
            // LOG.debug("index " , stationClimateList.get(800) );

        }
        Assertions.assertNotEquals(1, index, "Test wurde nicht durchlaufen !");

    }

    @Test
    void getStationCount(){


        long count = dataAccess.getStationDAO().count();
        Assertions.assertEquals(9l,count,"DataAccess count(*) test ist wrong");

    }
}
