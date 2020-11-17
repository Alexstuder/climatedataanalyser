package ch.studer.germanclimatedataanalyser.dao;

import ch.studer.germanclimatedataanalyser.model.database.StationClimate;
import ch.studer.germanclimatedataanalyser.model.dto.helper.GpsPoint;
import ch.studer.germanclimatedataanalyser.service.db.ClimateService;
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
@Sql({"classpath:H2Schema.sql","classpath:climates.sql","classpath:stations.sql"})

class StationClimateImplTest {

    @Autowired
    ClimateService climateService;


    private static final Logger LOG = LoggerFactory.getLogger(StationClimateImplTest.class);

    @Test
    void getClimateForGpsCoordinatesFromYearOrderByYearAndStationId() {

        GpsPoint gpsPoint1 = new GpsPoint(49.7667d,7.5959d);
        GpsPoint gpsPoint2 = new GpsPoint(47.5590d,10.2674d);
        String fromYear = "1990";

        List<StationClimate> stationClimateList = climateService.getClimateForGpsCoordinatesFromYearOrderedByFromYearAndStations(gpsPoint1,gpsPoint2,fromYear);
        for(StationClimate stationClimate : stationClimateList){

        LOG.debug("StationClimate :  {}" ,stationClimate.getStationId());
        }
        LOG.debug("stationCLimateList.size {}" ,stationClimateList.get(800));



    }
}