package ch.studer.germanclimatedataanalyser.service.ui.climateRecords;

import ch.studer.germanclimatedataanalyser.dao.StationClimateDAO;
import ch.studer.germanclimatedataanalyser.generate.test.data.ClimateTestData;
import ch.studer.germanclimatedataanalyser.model.database.StationClimate;
import ch.studer.germanclimatedataanalyser.model.dto.climaterecords.ClimateRecordsDto;
import ch.studer.germanclimatedataanalyser.model.dto.helper.Bundesland;
import org.junit.Ignore;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

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
        when(stationClimateDAO.getClimateForBundesland(BUNDESLAND)).thenReturn(stationClimates);
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

        // get test List<ClimateRecords> as readed from Table
        List<StationClimate> stationClimates = ClimateTestData.getStationClimateOrderByBeginYearAndStationId(1900, 2002, 5);

        // execute getClimateRecords and recife List<ClimateRecords> with calculated diff between the records from DB
        when(bundeslandProofer.proof()).thenReturn("");
        when(stationClimateDAO.getClimateForBundesland(bundesland)).thenReturn(stationClimates);
        ClimateRecordsDto climateRecordsDto = climateRecordService.getClimateRecords(bundesland, lat1, long1, lat2, long2, yearFrom, yearDistance);


    }
}
