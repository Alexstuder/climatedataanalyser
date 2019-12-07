package ch.studer.germanclimatedataanalyser.batch.writer;

import ch.studer.germanclimatedataanalyser.model.database.StationWeatherPerYear;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;

import java.util.ArrayList;
import java.util.List;

class ClimateWriterTest {

    private final static String START_DATE = "2018";

    @Value("#{new Integer('${climate.calculation.period.year}')}")
    private int period;
    private static final int stationId = 1 ;

    List<StationWeatherPerYear> stationWeatherPerYearList = new ArrayList<StationWeatherPerYear>();

    @BeforeEach
    void setUp() {

        stationWeatherPerYearList = getStationWeatherPerYearList(START_DATE);

    }

    private List<StationWeatherPerYear> getStationWeatherPerYearList(String startDate,stationId) {
        List<StationWeatherPerYear> l = new ArrayList<>();

        int start = Integer.valueOf(startDate);

        for(int i = start; i >= start-period ;i-- ){
            l.add(getStationWeatherPerYear(i));
        }

        return null;
    }

    private StationWeatherPerYear getStationWeatherPerYear(int i,int station) {
        int stationId = station;
        StationWeatherPerYear s = new StationWeatherPerYear(stationId);
    }

    @Test
    void writeHappyTest() {
        ClimateWriter climateWriter = new ClimateWriter();
        Exception exception = null;

        // Test Happy Flow ClimateWrite and Assert exeption == null
        try {
            climateWriter.write(stationWeatherPerYearList);
        } catch (Exception e) {
            e.printStackTrace();
            exception = e;
        }

        Assertions.assertNull(exception);
    }
    @Test
    void writeNullList() {
    }
    @Test
    void writeListWithHoles() {
    }
    @Test
    void writeWithoutClimateRecord() {
    }
    @Test
    void writeJustOneClimateRecord() {
    }
}