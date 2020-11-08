package ch.studer.germanclimatedataanalyser.service.ui.climateRecords;

import ch.studer.germanclimatedataanalyser.model.dto.climaterecords.ClimateRecordsDto;
import ch.studer.germanclimatedataanalyser.model.dto.helper.Bundesland;
import ch.studer.germanclimatedataanalyser.service.db.StationService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
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
    private static final String YEAR_FROM_VALID = "1990";
    private static final String YEAR_FROM_VALID_STRIP = " 1990 ";
    private static final String YEAR_FROM_NOT_VALID = "not_valid";
    private static final String YEAR_FROM_NOT_A_NUMBER = "nonr";
    private static final String YEAR_DISTANCE_NOT_VALID = "not_valid";
    private static final String YEAR_DISTANCE_VALID = "5";
    private static final String YEAR_DISTANCE_VALID_TRIM = " 5 ";

    //@Mock
    //StationService stationService;

    @Mock
    Bundesland bundeslandProofer;

    @InjectMocks
    ClimateRecordService climateRecordService = new ClimateRecordServiceImpl();

    @Test
    void proofInput() {
        //* Not a Valid Bundesland
        when(bundeslandProofer.proof()).thenReturn(NOT_A_BUNDESLAND + "Bundesland doesn't exist!");
        ClimateRecordsDto climateRecordsDto = climateRecordService.getClimateRecords(NOT_A_BUNDESLAND
                                                ,GPS_LAT_VALID
                                                ,GPS_LONG_VALID
                                                ,GPS_LAT_VALID
                                                ,GPS_LONG_VALID
                                                ,YEAR_FROM_VALID
                                                ,YEAR_DISTANCE_VALID);

        Assertions.assertEquals(NOT_A_BUNDESLAND + "Bundesland doesn't exist!",climateRecordsDto.getErrorMsg());

        //* Valid Bundesland
        when(bundeslandProofer.proof()).thenReturn("");
        climateRecordsDto = climateRecordService.getClimateRecords(BUNDESLAND
                ,GPS_LAT_VALID
                ,GPS_LONG_VALID
                ,GPS_LAT_VALID
                ,GPS_LONG_VALID
                ,YEAR_FROM_VALID
                ,YEAR_DISTANCE_VALID);

        Assertions.assertEquals("",climateRecordsDto.getErrorMsg());

        //* Not a valid gps1Lat number format
        when(bundeslandProofer.proof()).thenReturn("");
        climateRecordsDto = climateRecordService.getClimateRecords(BUNDESLAND
                ,GPS_WRONG_FORMAT
                ,GPS_LONG_VALID
                ,GPS_LAT_VALID
                ,GPS_LONG_VALID
                ,YEAR_FROM_VALID
                ,YEAR_DISTANCE_VALID);

        Assertions.assertEquals("wrong format or 180.0 has not a valid coordinate format !",climateRecordsDto.getErrorMsg());
        //* Not a valid gps1Long number format
        when(bundeslandProofer.proof()).thenReturn("");
        climateRecordsDto = climateRecordService.getClimateRecords(BUNDESLAND
                ,GPS_LAT_VALID
                ,GPS_WRONG_FORMAT
                ,GPS_LAT_VALID
                ,GPS_LONG_VALID
                ,YEAR_FROM_VALID
                ,YEAR_DISTANCE_VALID);

        Assertions.assertEquals("90.0 or wrong format has not a valid coordinate format !",climateRecordsDto.getErrorMsg());

        //* Not a valid gps1Lat number format
        when(bundeslandProofer.proof()).thenReturn("");
        climateRecordsDto = climateRecordService.getClimateRecords(BUNDESLAND
                ,GPS_LAT_VALID
                ,GPS_LONG_VALID
                ,GPS_WRONG_FORMAT
                ,GPS_LONG_VALID
                ,YEAR_FROM_VALID
                ,YEAR_DISTANCE_VALID);

        Assertions.assertEquals("wrong format or 180.0 has not a valid coordinate format !",climateRecordsDto.getErrorMsg());
        //* Not a valid gps1Long number format
        when(bundeslandProofer.proof()).thenReturn("");
        climateRecordsDto = climateRecordService.getClimateRecords(BUNDESLAND
                ,GPS_LAT_VALID
                ,GPS_LONG_VALID
                ,GPS_LAT_VALID
                ,GPS_WRONG_FORMAT
                ,YEAR_FROM_VALID
                ,YEAR_DISTANCE_VALID);

        Assertions.assertEquals("90.0 or wrong format has not a valid coordinate format !",climateRecordsDto.getErrorMsg());

        //* Not a valid gps1Lat coordinate
        when(bundeslandProofer.proof()).thenReturn("");
        climateRecordsDto = climateRecordService.getClimateRecords(BUNDESLAND
                ,GPS_LAT_NOT_VALID
                ,GPS_LONG_VALID
                ,GPS_LAT_VALID
                ,GPS_LONG_VALID
                ,YEAR_FROM_VALID
                ,YEAR_DISTANCE_VALID);

        Assertions.assertEquals("90.1 is not a valid latitude !(min : -90 / max : 90)",climateRecordsDto.getErrorMsg());
        //* Not a valid gps1Long coordinate
        when(bundeslandProofer.proof()).thenReturn("");
        climateRecordsDto = climateRecordService.getClimateRecords(BUNDESLAND
                ,GPS_LAT_VALID
                ,GPS_LONG_NOT_VALID
                ,GPS_LAT_VALID
                ,GPS_LONG_VALID
                ,YEAR_FROM_VALID
                ,YEAR_DISTANCE_VALID);

        Assertions.assertEquals(GPS_LONG_NOT_VALID +" is not a valid longitude !(min : -180 / max : 180)",climateRecordsDto.getErrorMsg());
//* Not a valid gps2Lat coordinate
        when(bundeslandProofer.proof()).thenReturn("");
        climateRecordsDto = climateRecordService.getClimateRecords(BUNDESLAND
                ,GPS_LAT_VALID
                ,GPS_LONG_VALID
                ,GPS_LAT_NOT_VALID
                ,GPS_LONG_VALID
                ,YEAR_FROM_VALID
                ,YEAR_DISTANCE_VALID);

        Assertions.assertEquals("90.1 is not a valid latitude !(min : -90 / max : 90)",climateRecordsDto.getErrorMsg());
        //* Not a valid gps2Long coordinate
        when(bundeslandProofer.proof()).thenReturn("");
        climateRecordsDto = climateRecordService.getClimateRecords(BUNDESLAND
                ,GPS_LAT_VALID
                ,GPS_LONG_VALID
                ,GPS_LAT_VALID
                ,GPS_LONG_NOT_VALID
                ,YEAR_FROM_VALID
                ,YEAR_DISTANCE_VALID);

        Assertions.assertEquals(GPS_LONG_NOT_VALID +" is not a valid longitude !(min : -180 / max : 180)",climateRecordsDto.getErrorMsg());

        //* Not a valid year
        when(bundeslandProofer.proof()).thenReturn("");
        climateRecordsDto = climateRecordService.getClimateRecords(BUNDESLAND
                ,GPS_LAT_VALID
                ,GPS_LONG_VALID
                ,GPS_LAT_VALID
                ,GPS_LONG_VALID
                ,YEAR_FROM_NOT_VALID
                ,YEAR_DISTANCE_VALID);

        Assertions.assertEquals(YEAR_FROM_NOT_VALID +" has not a valid year format !",climateRecordsDto.getErrorMsg());

        //* Not a valid year
        when(bundeslandProofer.proof()).thenReturn("");
        climateRecordsDto = climateRecordService.getClimateRecords(BUNDESLAND
                ,GPS_LAT_VALID
                ,GPS_LONG_VALID
                ,GPS_LAT_VALID
                ,GPS_LONG_VALID
                ,YEAR_FROM_NOT_A_NUMBER
                ,YEAR_DISTANCE_VALID);

        Assertions.assertEquals(YEAR_FROM_NOT_A_NUMBER +" is not numerically!",climateRecordsDto.getErrorMsg());

        //* Not a valid year
        when(bundeslandProofer.proof()).thenReturn("");
        climateRecordsDto = climateRecordService.getClimateRecords(BUNDESLAND
                ,GPS_LAT_VALID
                ,GPS_LONG_VALID
                ,GPS_LAT_VALID
                ,GPS_LONG_VALID
                ,YEAR_FROM_VALID
                ,YEAR_DISTANCE_NOT_VALID);

        Assertions.assertEquals(YEAR_DISTANCE_NOT_VALID +" is not a valid number !",climateRecordsDto.getErrorMsg());

        //* Strip Year From : valid
        when(bundeslandProofer.proof()).thenReturn("");
        climateRecordsDto = climateRecordService.getClimateRecords(BUNDESLAND
                ,GPS_LAT_VALID
                ,GPS_LONG_VALID
                ,GPS_LAT_VALID
                ,GPS_LONG_VALID
                ,YEAR_FROM_VALID_STRIP
                ,YEAR_DISTANCE_VALID);

        Assertions.assertEquals("",climateRecordsDto.getErrorMsg());

        //* Strip Year Distance : valid
        when(bundeslandProofer.proof()).thenReturn("");
        climateRecordsDto = climateRecordService.getClimateRecords(BUNDESLAND
                ,GPS_LAT_VALID
                ,GPS_LONG_VALID
                ,GPS_LAT_VALID
                ,GPS_LONG_VALID
                ,YEAR_FROM_VALID
                ,YEAR_DISTANCE_VALID_TRIM);

        Assertions.assertEquals("",climateRecordsDto.getErrorMsg());


    }
}
