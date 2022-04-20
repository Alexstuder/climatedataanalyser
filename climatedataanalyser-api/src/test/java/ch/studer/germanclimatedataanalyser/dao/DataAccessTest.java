package ch.studer.germanclimatedataanalyser.dao;

import ch.studer.germanclimatedataanalyser.generate.test.data.StationTestData;
import ch.studer.germanclimatedataanalyser.model.database.Station;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

@SpringBootTest
@TestPropertySource(locations = "classpath:test-it.properties")
@Sql({"classpath:schema.sql", "classpath:StationClimate/climates.sql", "classpath:StationClimate/stations.sql","classpath:DataAccess/MONTH_.sql","classpath:DataAccess/WEATHER.sql"})
class DataAccessTest {

    @Autowired
    DataAccess dataAccess;
    private static final Long EXPECTED_STATIONS = 9l;
    private static final Long EXPECTED_CLIMATES = 27l;

    private static final Long EXPECTED_WEATHER = 60l;

    private static final Long EXPECTED_MONTH = 115l;

    @Test
    void getMonthDAO() {
    }

    @Test
    void getCountStationDAO() {
        long count = dataAccess.getStationDAO().count();
        Assertions.assertEquals(EXPECTED_STATIONS, count, "Count is expected :" + EXPECTED_STATIONS + " count is actualy :" + count);
    }

    @Test
    void getCountClimateDAO() {
        long count = dataAccess.getStationClimateDAO().count();
        Assertions.assertEquals(EXPECTED_CLIMATES, count, "Count is expected :" + EXPECTED_CLIMATES + " count is actualy :" + count);
    }

    @Test
    void getCountMonthDAO() {
        long count = dataAccess.getMonthDAO().count();
        Assertions.assertEquals(EXPECTED_MONTH, count, "Count is expected :" + EXPECTED_MONTH + " count is actualy :" + count);
    }


    @Test
    void getCountStationWeatherDAO() {
        long count = dataAccess.getStationWeatherDAO().count();
        Assertions.assertEquals(EXPECTED_WEATHER, count, "Count is expected :" + EXPECTED_WEATHER + " count is actualy :" + count);

    }

}
