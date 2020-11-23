package ch.studer.germanclimatedataanalyser.service.ui.climateRecords;

import ch.studer.germanclimatedataanalyser.dao.StationClimateDAO;
import ch.studer.germanclimatedataanalyser.generate.test.data.ClimateTestData;
import ch.studer.germanclimatedataanalyser.model.database.StationClimate;
import ch.studer.germanclimatedataanalyser.model.dto.climaterecords.ClimateRecord;
import ch.studer.germanclimatedataanalyser.model.dto.climaterecords.ClimateRecordsDto;
import ch.studer.germanclimatedataanalyser.model.dto.helper.Bundesland;
import ch.studer.germanclimatedataanalyser.model.dto.helper.GpsPoint;
import org.junit.Ignore;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class ClimateRecordServiceImplTest {

    private static final String NOT_A_BUNDESLAND = "not_a_Bundesland";
    private static final String BUNDESLAND = "valid_Bundesland";
    private static final String GPS_WRONG_FORMAT = "wrong format";
    private static final String GPS_LAT_NOT_VALID = "90.1";
    private static final String GPS_LAT_VALID = "90.0";
    private static final String GPS_LONG_NOT_VALID = "-180.1";
    private static final String GPS_LONG_VALID = "180.0";
    private static final String YEAR_FROM_VALID = "1960";
    private static final String YEAR_FROM_VALID_STRIP = " 1960 ";
    private static final String YEAR_FROM_NOT_VALID = "not_valid";
    private static final String YEAR_FROM_NOT_A_NUMBER = "nonr";
    private static final String YEAR_DISTANCE_NOT_VALID = "not_valid";
    private static final String YEAR_DISTANCE_VALID = "5";
    private static final String YEAR_DISTANCE_VALID_TRIM = " 5 ";

    //@Mock
    //StationService stationService;

    @Mock
    Bundesland bundeslandProofer;

    @Mock
    StationClimateDAO stationClimateDAO;


    @InjectMocks
    ClimateRecordService climateRecordService = new ClimateRecordServiceImpl();

    @Test
    @Ignore
    void proofInput() {

        // get test List<ClimateRecords> as readed from Table
        List<StationClimate> stationClimates = ClimateTestData.getStationClimateOrderByBeginYearAndStationId(1900, 2002, 5);

        // execute getClimateRecords and recife List<ClimateRecords> with calculated diff between the records from DB
        //* Not a Valid Bundesland
        when(bundeslandProofer.proof()).thenReturn(NOT_A_BUNDESLAND + "Bundesland doesn't exist!");
        ClimateRecordsDto climateRecordsDto = climateRecordService.getClimateRecords(NOT_A_BUNDESLAND
                , GPS_LAT_VALID
                , GPS_LONG_VALID
                , GPS_LAT_VALID
                , GPS_LONG_VALID
                , YEAR_FROM_VALID
                , YEAR_DISTANCE_VALID);

        Assertions.assertEquals(NOT_A_BUNDESLAND + "Bundesland doesn't exist!", climateRecordsDto.getErrorMsg());

        //* Valid Bundesland
        when(bundeslandProofer.proof()).thenReturn("");
        when(stationClimateDAO.getClimateForBundeslandFromYearOrderByYearAndStationId(BUNDESLAND, YEAR_FROM_VALID)).thenReturn(stationClimates);
        climateRecordsDto = climateRecordService.getClimateRecords(BUNDESLAND
                , GPS_LAT_VALID
                , GPS_LONG_VALID
                , GPS_LAT_VALID
                , GPS_LONG_VALID
                , YEAR_FROM_VALID
                , YEAR_DISTANCE_VALID);

        Assertions.assertEquals("", climateRecordsDto.getErrorMsg());

        //* Not a valid gps1Lat number format
        when(bundeslandProofer.proof()).thenReturn("");
        climateRecordsDto = climateRecordService.getClimateRecords(BUNDESLAND
                , GPS_WRONG_FORMAT
                , GPS_LONG_VALID
                , GPS_LAT_VALID
                , GPS_LONG_VALID
                , YEAR_FROM_VALID
                , YEAR_DISTANCE_VALID);

        Assertions.assertEquals("wrong format or 180.0 has not a valid coordinate format !", climateRecordsDto.getErrorMsg());
        //* Not a valid gps1Long number format
        when(bundeslandProofer.proof()).thenReturn("");
        climateRecordsDto = climateRecordService.getClimateRecords(BUNDESLAND
                , GPS_LAT_VALID
                , GPS_WRONG_FORMAT
                , GPS_LAT_VALID
                , GPS_LONG_VALID
                , YEAR_FROM_VALID
                , YEAR_DISTANCE_VALID);

        Assertions.assertEquals("90.0 or wrong format has not a valid coordinate format !", climateRecordsDto.getErrorMsg());

        //* Not a valid gps1Lat number format
        when(bundeslandProofer.proof()).thenReturn("");
        climateRecordsDto = climateRecordService.getClimateRecords(BUNDESLAND
                , GPS_LAT_VALID
                , GPS_LONG_VALID
                , GPS_WRONG_FORMAT
                , GPS_LONG_VALID
                , YEAR_FROM_VALID
                , YEAR_DISTANCE_VALID);

        Assertions.assertEquals("wrong format or 180.0 has not a valid coordinate format !", climateRecordsDto.getErrorMsg());
        //* Not a valid gps1Long number format
        when(bundeslandProofer.proof()).thenReturn("");
        climateRecordsDto = climateRecordService.getClimateRecords(BUNDESLAND
                , GPS_LAT_VALID
                , GPS_LONG_VALID
                , GPS_LAT_VALID
                , GPS_WRONG_FORMAT
                , YEAR_FROM_VALID
                , YEAR_DISTANCE_VALID);

        Assertions.assertEquals("90.0 or wrong format has not a valid coordinate format !", climateRecordsDto.getErrorMsg());

        //* Not a valid gps1Lat coordinate
        when(bundeslandProofer.proof()).thenReturn("");
        climateRecordsDto = climateRecordService.getClimateRecords(BUNDESLAND
                , GPS_LAT_NOT_VALID
                , GPS_LONG_VALID
                , GPS_LAT_VALID
                , GPS_LONG_VALID
                , YEAR_FROM_VALID
                , YEAR_DISTANCE_VALID);

        Assertions.assertEquals("90.1 is not a valid latitude !(min : -90 / max : 90)", climateRecordsDto.getErrorMsg());
        //* Not a valid gps1Long coordinate
        when(bundeslandProofer.proof()).thenReturn("");
        climateRecordsDto = climateRecordService.getClimateRecords(BUNDESLAND
                , GPS_LAT_VALID
                , GPS_LONG_NOT_VALID
                , GPS_LAT_VALID
                , GPS_LONG_VALID
                , YEAR_FROM_VALID
                , YEAR_DISTANCE_VALID);

        Assertions.assertEquals(GPS_LONG_NOT_VALID + " is not a valid longitude !(min : -180 / max : 180)", climateRecordsDto.getErrorMsg());
//* Not a valid gps2Lat coordinate
        when(bundeslandProofer.proof()).thenReturn("");
        climateRecordsDto = climateRecordService.getClimateRecords(BUNDESLAND
                , GPS_LAT_VALID
                , GPS_LONG_VALID
                , GPS_LAT_NOT_VALID
                , GPS_LONG_VALID
                , YEAR_FROM_VALID
                , YEAR_DISTANCE_VALID);

        Assertions.assertEquals("90.1 is not a valid latitude !(min : -90 / max : 90)", climateRecordsDto.getErrorMsg());
        //* Not a valid gps2Long coordinate
        when(bundeslandProofer.proof()).thenReturn("");
        climateRecordsDto = climateRecordService.getClimateRecords(BUNDESLAND
                , GPS_LAT_VALID
                , GPS_LONG_VALID
                , GPS_LAT_VALID
                , GPS_LONG_NOT_VALID
                , YEAR_FROM_VALID
                , YEAR_DISTANCE_VALID);

        Assertions.assertEquals(GPS_LONG_NOT_VALID + " is not a valid longitude !(min : -180 / max : 180)", climateRecordsDto.getErrorMsg());

        //* Not a valid year
        when(bundeslandProofer.proof()).thenReturn("");
        climateRecordsDto = climateRecordService.getClimateRecords(BUNDESLAND
                , GPS_LAT_VALID
                , GPS_LONG_VALID
                , GPS_LAT_VALID
                , GPS_LONG_VALID
                , YEAR_FROM_NOT_VALID
                , YEAR_DISTANCE_VALID);

        Assertions.assertEquals(YEAR_FROM_NOT_VALID + " has not a valid year format !", climateRecordsDto.getErrorMsg());

        //* Not a valid year
        when(bundeslandProofer.proof()).thenReturn("");
        climateRecordsDto = climateRecordService.getClimateRecords(BUNDESLAND
                , GPS_LAT_VALID
                , GPS_LONG_VALID
                , GPS_LAT_VALID
                , GPS_LONG_VALID
                , YEAR_FROM_NOT_A_NUMBER
                , YEAR_DISTANCE_VALID);

        Assertions.assertEquals(YEAR_FROM_NOT_A_NUMBER + " is not numerically!", climateRecordsDto.getErrorMsg());

        //* Not a valid year
        when(bundeslandProofer.proof()).thenReturn("");
        climateRecordsDto = climateRecordService.getClimateRecords(BUNDESLAND
                , GPS_LAT_VALID
                , GPS_LONG_VALID
                , GPS_LAT_VALID
                , GPS_LONG_VALID
                , YEAR_FROM_VALID
                , YEAR_DISTANCE_NOT_VALID);

        Assertions.assertEquals(YEAR_DISTANCE_NOT_VALID + " is not a valid number !", climateRecordsDto.getErrorMsg());

        //* Strip Year From : valid
        when(bundeslandProofer.proof()).thenReturn("");
        when(stationClimateDAO.getClimateForBundeslandFromYearOrderByYearAndStationId(BUNDESLAND, YEAR_FROM_VALID_STRIP)).thenReturn(stationClimates);
        climateRecordsDto = climateRecordService.getClimateRecords(BUNDESLAND
                , GPS_LAT_VALID
                , GPS_LONG_VALID
                , GPS_LAT_VALID
                , GPS_LONG_VALID
                , YEAR_FROM_VALID_STRIP
                , YEAR_DISTANCE_VALID);
        Assertions.assertEquals("", climateRecordsDto.getErrorMsg());

        //* Strip Year Distance : valid
        when(bundeslandProofer.proof()).thenReturn("");
        climateRecordsDto = climateRecordService.getClimateRecords(BUNDESLAND
                , GPS_LAT_VALID
                , GPS_LONG_VALID
                , GPS_LAT_VALID
                , GPS_LONG_VALID
                , YEAR_FROM_VALID
                , YEAR_DISTANCE_VALID_TRIM);

        Assertions.assertEquals("", climateRecordsDto.getErrorMsg());


    }

    @Test
    void getClimateRecords() {

        // Get test Input data
        String bundesland = "Berlin";
        String lat1 = "34.56";
        String long1 = "4.56";
        String lat2 = "35.56";
        String long2 = "5.56";
        String yearFrom = "1938";
        String yearDistance = "5";
        GpsPoint gpsPoint1 = new GpsPoint(Double.parseDouble(lat1), Double.parseDouble(long1));
        GpsPoint gpsPoint2 = new GpsPoint(Double.parseDouble(lat2), Double.parseDouble(long2));

        // TestResult :
        // Happy Test !
        List<StationClimate> stationClimates = ClimateTestData.getStationClimateOrderByBeginYearAndStationId(1900, 2002, 5);

        // execute getClimateRecords and recife List<ClimateRecords> with calculated diff between the records from DB
        when(bundeslandProofer.proof()).thenReturn("");
        when(stationClimateDAO.getClimateForBundeslandFromYearOrderByYearAndStationId(bundesland, yearFrom)).thenReturn(stationClimates);
        ClimateRecordsDto climateRecordsDto = climateRecordService.getClimateRecords(bundesland, lat1, long1, lat2, long2, yearFrom, yearDistance);
        Assertions.assertTrue(climateRecordsDto.getErrorMsg().isEmpty());


        // TestResult :
        // no ClimateRecords found for your search criteria : for Bundesland !
        List<StationClimate> emptyStationClimates = new ArrayList<>();
        when(bundeslandProofer.proof()).thenReturn("");
        when(stationClimateDAO.getClimateForBundeslandFromYearOrderByYearAndStationId(bundesland, yearFrom)).thenReturn(emptyStationClimates);
        climateRecordsDto = climateRecordService.getClimateRecords(bundesland, lat1, long1, lat2, long2, yearFrom, yearDistance);
        Assertions.assertEquals("There are no climateRecords for your search criteria !", climateRecordsDto.getErrorMsg());


        // TestResult :
        // no ClimateRecords found for your search criteria : for GPS Coordinates!
        // when(bundeslandProofer.proof()).thenReturn("");
        when(stationClimateDAO.getClimateForGpsCoordinatesFromYearOrderByYearAndStationId(any(GpsPoint.class), any(GpsPoint.class), eq(yearFrom))).thenReturn(emptyStationClimates);
        climateRecordsDto = climateRecordService.getClimateRecords("", lat1, long1, lat2, long2, yearFrom, yearDistance);
        Assertions.assertEquals("There are no climateRecords for your search criteria !", climateRecordsDto.getErrorMsg());


    }

    @Test
    void getStationClimatesFromYearWithDistance() {


        ClimateRecordServiceImpl climateRecordService = new ClimateRecordServiceImpl();
        // get test List<ClimateRecords> as readed from Table
        try {
            Method method = ClimateRecordServiceImpl.class.getDeclaredMethod("getStationClimatesFromYearWithDistance", int.class, List.class);
            method.setAccessible(true);

            // STart Test with one Station
            // Expected Tests return 1 , hit exactly highes position , one more and one under
            List<StationClimate> stationClimates = ClimateTestData.getStationClimateOrderByBeginYearAndStationId(1900, 1900, 1);
            List<StationClimate> stationClimatesRe = (List<StationClimate>) method.invoke(climateRecordService, 5, stationClimates);
            Assertions.assertEquals(1, stationClimatesRe.size());

            stationClimates = ClimateTestData.getStationClimateOrderByBeginYearAndStationId(1900, 1910, 1);
            stationClimatesRe = (List<StationClimate>) method.invoke(climateRecordService, 5, stationClimates);
            Assertions.assertEquals(3, stationClimatesRe.size());

            stationClimates = ClimateTestData.getStationClimateOrderByBeginYearAndStationId(1900, 1911, 1);
            stationClimatesRe = (List<StationClimate>) method.invoke(climateRecordService, 5, stationClimates);
            Assertions.assertEquals(4, stationClimatesRe.size());

            stationClimates = ClimateTestData.getStationClimateOrderByBeginYearAndStationId(1900, 1909, 1);
            stationClimatesRe = (List<StationClimate>) method.invoke(climateRecordService, 5, stationClimates);
            Assertions.assertEquals(3, stationClimatesRe.size());

            // Repeate all tests with 5 Station
            stationClimates = ClimateTestData.getStationClimateOrderByBeginYearAndStationId(1900, 1900, 5);
            stationClimatesRe = (List<StationClimate>) method.invoke(climateRecordService, 5, stationClimates);
            Assertions.assertEquals(5, stationClimatesRe.size());

            stationClimates = ClimateTestData.getStationClimateOrderByBeginYearAndStationId(1900, 1910, 5);
            stationClimatesRe = (List<StationClimate>) method.invoke(climateRecordService, 5, stationClimates);
            Assertions.assertEquals(15, stationClimatesRe.size());

            stationClimates = ClimateTestData.getStationClimateOrderByBeginYearAndStationId(1900, 1911, 5);
            stationClimatesRe = (List<StationClimate>) method.invoke(climateRecordService, 5, stationClimates);
            Assertions.assertEquals(20, stationClimatesRe.size());

            stationClimates = ClimateTestData.getStationClimateOrderByBeginYearAndStationId(1900, 1909, 5);
            stationClimatesRe = (List<StationClimate>) method.invoke(climateRecordService, 5, stationClimates);
            Assertions.assertEquals(15, stationClimatesRe.size());





        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }


    }

    @Test
    void getRelevantYears() {


        // Test Motivation
        //          arguments
        //                  Start,  distanceYear    ,finishYear
        // get 0            not possible
        // get 1            1900,   5               ,1900
        // exactly          1900,   5               ,1910
        // one more         1900,   5               ,1911

        List<String> relevantYears;
        List<String> expected = Arrays.asList("1900");
        ClimateRecordServiceImpl climateRecordService = new ClimateRecordServiceImpl();
        try {
            Method method = ClimateRecordServiceImpl.class
                    .getDeclaredMethod("getRelevantYears", int.class, int.class, int.class);
            method.setAccessible(true);
            relevantYears = (List<String>) method.invoke(climateRecordService, 1900, 5, 1900);

            expected = Arrays.asList("1900", "1905", "1910");
            relevantYears = (List<String>) method.invoke(climateRecordService, 1900, 5, 1910);
            Assertions.assertEquals(expected, relevantYears);

            expected = Arrays.asList("1900", "1905", "1910", "1911");
            relevantYears = (List<String>) method.invoke(climateRecordService, 1900, 5, 1911);
            Assertions.assertEquals(expected, relevantYears);


        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }


    }


    @Test
    void getAverageClimatePerYear() {


        // Test Motivation

        List<ClimateRecord> climateRecords = new ArrayList<ClimateRecord>();
        List<StationClimate>  stationClimates = ClimateTestData.getStationClimateOrderByBeginYearAndStationId(1900, 2020, 5);
        ClimateRecordServiceImpl climateRecordService = new ClimateRecordServiceImpl();

        try {
            Method method = ClimateRecordServiceImpl.class
                    .getDeclaredMethod("getAverageClimatePerYear", List.class);
            method.setAccessible(true);
            climateRecords = (List<ClimateRecord>) method.invoke(climateRecordService,stationClimates);



        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }


    }

}
