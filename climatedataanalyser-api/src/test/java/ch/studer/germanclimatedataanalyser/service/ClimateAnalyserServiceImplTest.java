package ch.studer.germanclimatedataanalyser.service;

import ch.studer.germanclimatedataanalyser.generate.test.data.ClimateTestData;
import ch.studer.germanclimatedataanalyser.model.database.StationClimate;
import ch.studer.germanclimatedataanalyser.model.dto.ClimateAnalyserRequestDto;
import ch.studer.germanclimatedataanalyser.model.dto.ClimateAnalyserResponseDto;
import ch.studer.germanclimatedataanalyser.model.dto.helper.GpsPoint;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ClimateAnalyserServiceImplTest {

    @Mock
    ClimateService climateService;

    @Mock
    StationService stationService;

    @InjectMocks
    ClimateAnalyserServiceImpl climateAnalyserService;


    @Test
    void happyTest(){

        //* Get some Test Data for climateService
        List<StationClimate> stationClimates = ClimateTestData.getStationClimate(3);


        //* Define Mock szenario
        when(climateService.getClimateForBundesland("Berlin")).thenReturn(stationClimates);

        //* Execute Test
        ClimateAnalyserResponseDto climateAnalyserResponseDto = climateAnalyserService.getClimateAnalyserForBundesland("Berlin");

        //* Assert most ClimateAnalyseData
        Assertions.assertEquals(climateAnalyserResponseDto.getOriginYear(), "2017");
        Assertions.assertEquals(climateAnalyserResponseDto.getBundesland(), "Berlin");
        Assertions.assertEquals(climateAnalyserResponseDto.getOriginal().getJanuar(), new BigDecimal("2.000"));
        Assertions.assertEquals(climateAnalyserResponseDto.getOriginal().getFebruar(), new BigDecimal("3.000"));
        Assertions.assertEquals(climateAnalyserResponseDto.getOriginal().getMaerz(), new BigDecimal("4.000"));
        Assertions.assertEquals(climateAnalyserResponseDto.getOriginal().getApril(), new BigDecimal("5.000"));
        Assertions.assertEquals(climateAnalyserResponseDto.getOriginal().getMai(), new BigDecimal("6.000"));
        Assertions.assertEquals(climateAnalyserResponseDto.getOriginal().getJuni(), new BigDecimal("7.000"));
        Assertions.assertEquals(climateAnalyserResponseDto.getOriginal().getJuli(), new BigDecimal("8.000"));
        Assertions.assertEquals(climateAnalyserResponseDto.getOriginal().getAugust(), new BigDecimal("9.000"));
        Assertions.assertEquals(climateAnalyserResponseDto.getOriginal().getSeptember(), new BigDecimal("10.000"));
        Assertions.assertEquals(climateAnalyserResponseDto.getOriginal().getOktober(), new BigDecimal("11.000"));
        Assertions.assertEquals(climateAnalyserResponseDto.getOriginal().getNovember(), new BigDecimal("12.000"));
        Assertions.assertEquals(climateAnalyserResponseDto.getOriginal().getDezember(), new BigDecimal("13.000"));


        // Assert Newest
        Assertions.assertEquals(climateAnalyserResponseDto.getCompare().getJanuar(), new BigDecimal("3.000"));
        Assertions.assertEquals(climateAnalyserResponseDto.getCompare().getFebruar(), new BigDecimal("4.000"));
        Assertions.assertEquals(climateAnalyserResponseDto.getCompare().getMaerz(), new BigDecimal("5.000"));
        Assertions.assertEquals(climateAnalyserResponseDto.getCompare().getApril(), new BigDecimal("6.000"));
        Assertions.assertEquals(climateAnalyserResponseDto.getCompare().getMai(), new BigDecimal("7.000"));
        Assertions.assertEquals(climateAnalyserResponseDto.getCompare().getJuni(), new BigDecimal("8.000"));
        Assertions.assertEquals(climateAnalyserResponseDto.getCompare().getJuli(), new BigDecimal("9.000"));
        Assertions.assertEquals(climateAnalyserResponseDto.getCompare().getAugust(), new BigDecimal("10.000"));
        Assertions.assertEquals(climateAnalyserResponseDto.getCompare().getSeptember(), new BigDecimal("11.000"));
        Assertions.assertEquals(climateAnalyserResponseDto.getCompare().getOktober(), new BigDecimal("12.000"));
        Assertions.assertEquals(climateAnalyserResponseDto.getCompare().getNovember(), new BigDecimal("13.000"));
        Assertions.assertEquals(climateAnalyserResponseDto.getCompare().getDezember(), new BigDecimal("14.000"));

    }


    @Test
    void happyInputVerificationAndValidationTests(){

        // **************************************************************
        // Test : Bundesland exists
        String existingBundesland = "Berlin";

        //* Define Mock szenario
        when(stationService.bundeslandExists(existingBundesland)).thenReturn(true);

        //* Get some Test Data for climateService
        List<StationClimate> stationClimates = ClimateTestData.getStationClimate(3);
        //* Define Mock szenario
        when(climateService.getClimateForBundesland("Berlin")).thenReturn(stationClimates);

        //* Define Reuqest Parameter
        ClimateAnalyserRequestDto climateAnalyserRequestDto = new ClimateAnalyserRequestDto();
        climateAnalyserRequestDto.setBundesland(existingBundesland);

        // Execute Test :
        ClimateAnalyserResponseDto climateAnalyserResponseDto = climateAnalyserService.getClimateAnalyticsByClimateAnalyserRequest(climateAnalyserRequestDto);

        //* Assert errorMSg has to be empty
        Assertions.assertEquals("", climateAnalyserResponseDto.getErrorMsg());
        Assertions.assertEquals(existingBundesland, climateAnalyserResponseDto.getBundesland());
        //* Assert most ClimateAnalyseData
        Assertions.assertEquals(climateAnalyserResponseDto.getOriginYear(), "2017");
        Assertions.assertEquals(climateAnalyserResponseDto.getBundesland(), "Berlin");
        Assertions.assertEquals(climateAnalyserResponseDto.getOriginal().getJanuar(), new BigDecimal("2.000"));
        Assertions.assertEquals(climateAnalyserResponseDto.getOriginal().getFebruar(), new BigDecimal("3.000"));
        Assertions.assertEquals(climateAnalyserResponseDto.getOriginal().getMaerz(), new BigDecimal("4.000"));
        Assertions.assertEquals(climateAnalyserResponseDto.getOriginal().getApril(), new BigDecimal("5.000"));
        Assertions.assertEquals(climateAnalyserResponseDto.getOriginal().getMai(), new BigDecimal("6.000"));
        Assertions.assertEquals(climateAnalyserResponseDto.getOriginal().getJuni(), new BigDecimal("7.000"));
        Assertions.assertEquals(climateAnalyserResponseDto.getOriginal().getJuli(), new BigDecimal("8.000"));
        Assertions.assertEquals(climateAnalyserResponseDto.getOriginal().getAugust(), new BigDecimal("9.000"));
        Assertions.assertEquals(climateAnalyserResponseDto.getOriginal().getSeptember(), new BigDecimal("10.000"));
        Assertions.assertEquals(climateAnalyserResponseDto.getOriginal().getOktober(), new BigDecimal("11.000"));
        Assertions.assertEquals(climateAnalyserResponseDto.getOriginal().getNovember(), new BigDecimal("12.000"));
        Assertions.assertEquals(climateAnalyserResponseDto.getOriginal().getDezember(), new BigDecimal("13.000"));


        // Assert Newest
        Assertions.assertEquals(climateAnalyserResponseDto.getCompare().getJanuar(), new BigDecimal("3.000"));
        Assertions.assertEquals(climateAnalyserResponseDto.getCompare().getFebruar(), new BigDecimal("4.000"));
        Assertions.assertEquals(climateAnalyserResponseDto.getCompare().getMaerz(), new BigDecimal("5.000"));
        Assertions.assertEquals(climateAnalyserResponseDto.getCompare().getApril(), new BigDecimal("6.000"));
        Assertions.assertEquals(climateAnalyserResponseDto.getCompare().getMai(), new BigDecimal("7.000"));
        Assertions.assertEquals(climateAnalyserResponseDto.getCompare().getJuni(), new BigDecimal("8.000"));
        Assertions.assertEquals(climateAnalyserResponseDto.getCompare().getJuli(), new BigDecimal("9.000"));
        Assertions.assertEquals(climateAnalyserResponseDto.getCompare().getAugust(), new BigDecimal("10.000"));
        Assertions.assertEquals(climateAnalyserResponseDto.getCompare().getSeptember(), new BigDecimal("11.000"));
        Assertions.assertEquals(climateAnalyserResponseDto.getCompare().getOktober(), new BigDecimal("12.000"));
        Assertions.assertEquals(climateAnalyserResponseDto.getCompare().getNovember(), new BigDecimal("13.000"));
        Assertions.assertEquals(climateAnalyserResponseDto.getCompare().getDezember(), new BigDecimal("14.000"));




        // **************************************************************
        // Test : GPS Coordinates

        //* Define Reuqest Parameter
        // * Execute Test : GPS1 has no valid coordinates(positiv too big values)
        double posValidLatitude = 90.00;
        double posValidLongitude = 180.00;
        double negValidLatitude = -90.00;
        double negValidLongitude = -180.00;

        climateAnalyserRequestDto = new ClimateAnalyserRequestDto();
        climateAnalyserRequestDto.setGps1(new GpsPoint(posValidLatitude,negValidLongitude));
        climateAnalyserRequestDto.setGps2(new GpsPoint(negValidLatitude,posValidLongitude));
        climateAnalyserRequestDto = new ClimateAnalyserRequestDto();
        climateAnalyserRequestDto.setBundesland(existingBundesland);

        // Execute Test :
        climateAnalyserResponseDto = climateAnalyserService.getClimateAnalyticsByClimateAnalyserRequest(climateAnalyserRequestDto);

        //* Assert most ClimateAnalyseData
        Assertions.assertEquals("", climateAnalyserResponseDto.getErrorMsg());




    }

 @Test
    void errorInputVerificationAndValidationTests(){

            ClimateAnalyserRequestDto climateAnalyserRequestDto = new ClimateAnalyserRequestDto();

         // **************************************************************
         // * Execute Test : No Bundesland and no Gps Coordinates entered
         ClimateAnalyserResponseDto climateAnalyserResponseDto = climateAnalyserService.getClimateAnalyticsByClimateAnalyserRequest(climateAnalyserRequestDto);
         //* Assert most ClimateAnalyseData
         Assertions.assertEquals("Neither a Bundesland nor GPS coordinates have been entered", climateAnalyserResponseDto.getErrorMsg());


         // ********************************************************
         // * Execute Test : Bundesland and Gps Coordinates entered
         climateAnalyserRequestDto = new ClimateAnalyserRequestDto();
         climateAnalyserRequestDto.setBundesland("Berlin");
         climateAnalyserRequestDto.setGps1(new GpsPoint(0,0));
         climateAnalyserResponseDto = climateAnalyserService.getClimateAnalyticsByClimateAnalyserRequest(climateAnalyserRequestDto);
         //* Assert most ClimateAnalyseData
         Assertions.assertEquals("A Bundesland was selected and GPS coordinates entered at the same time !", climateAnalyserResponseDto.getErrorMsg());

         // ********************************************
         // * Execute Test : Bundesland does not Exists
         String notExistingBundesland = "Glattfelden";

         //* Define Mock szenario
         when(stationService.bundeslandExists(notExistingBundesland)).thenReturn(false);

         climateAnalyserRequestDto = new ClimateAnalyserRequestDto();
         climateAnalyserRequestDto.setBundesland(notExistingBundesland);
         climateAnalyserResponseDto = climateAnalyserService.getClimateAnalyticsByClimateAnalyserRequest(climateAnalyserRequestDto);
         //* Assert most ClimateAnalyseData
         Assertions.assertEquals("Bundesland : " + notExistingBundesland + " doesn't exists !", climateAnalyserResponseDto.getErrorMsg());

         // ********************************************************
         // * Execute Test : GPS1 has no valid coordinates(positiv too big values)
         double invalidLatitude = 90.001;
         double invalidLongitude = 180.001;
         double validLatitude = 90.00;
         double validLongitude = 180.00;

         climateAnalyserRequestDto = new ClimateAnalyserRequestDto();
         climateAnalyserRequestDto.setGps1(new GpsPoint(invalidLatitude,invalidLongitude));
         climateAnalyserRequestDto.setGps2(new GpsPoint(validLatitude,validLongitude));

         climateAnalyserResponseDto = climateAnalyserService.getClimateAnalyticsByClimateAnalyserRequest(climateAnalyserRequestDto);

         //* Assert most ClimateAnalyseData
         Assertions.assertEquals("GPS1 Coordinates are not valid ! : Latitude (-90, 0,90) :" + invalidLatitude + " Longitude(-180,0,180)" + invalidLongitude , climateAnalyserResponseDto.getErrorMsg());

         // ********************************************************
         // * Execute Test : GPS2 has no valid coordinates (positiv too big values)
         climateAnalyserRequestDto = new ClimateAnalyserRequestDto();
         climateAnalyserRequestDto.setGps1(new GpsPoint(validLatitude,validLongitude));
         climateAnalyserRequestDto.setGps2(new GpsPoint(invalidLatitude,invalidLongitude));

         climateAnalyserResponseDto = climateAnalyserService.getClimateAnalyticsByClimateAnalyserRequest(climateAnalyserRequestDto);

         //* Assert most ClimateAnalyseData
         Assertions.assertEquals("GPS2 Coordinates are not valid ! : Latitude (-90, 0,90) :" + invalidLatitude + " Longitude(-180,0,180)" + invalidLongitude , climateAnalyserResponseDto.getErrorMsg());

         // ********************************************************
         // * Execute Test : GPS1 has no valid coordinates
         invalidLatitude = -90.001;
         invalidLongitude = -180.001;
         validLatitude = 90.00;
         validLongitude = 180.00;

         climateAnalyserRequestDto = new ClimateAnalyserRequestDto();
         climateAnalyserRequestDto.setGps1(new GpsPoint(invalidLatitude,invalidLongitude));
         climateAnalyserRequestDto.setGps2(new GpsPoint(validLatitude,validLongitude));

         climateAnalyserResponseDto = climateAnalyserService.getClimateAnalyticsByClimateAnalyserRequest(climateAnalyserRequestDto);

         //* Assert most ClimateAnalyseData
         Assertions.assertEquals("GPS1 Coordinates are not valid ! : Latitude (-90, 0,90) :" + invalidLatitude + " Longitude(-180,0,180)" + invalidLongitude , climateAnalyserResponseDto.getErrorMsg());

         // ********************************************************
         // * Execute Test : GPS2 has no valid coordinates
         climateAnalyserRequestDto = new ClimateAnalyserRequestDto();
         climateAnalyserRequestDto.setGps1(new GpsPoint(validLatitude,validLongitude));
         climateAnalyserRequestDto.setGps2(new GpsPoint(invalidLatitude,invalidLongitude));

         climateAnalyserResponseDto = climateAnalyserService.getClimateAnalyticsByClimateAnalyserRequest(climateAnalyserRequestDto);

         //* Assert most ClimateAnalyseData
         Assertions.assertEquals("GPS2 Coordinates are not valid ! : Latitude (-90, 0,90) :" + invalidLatitude + " Longitude(-180,0,180)" + invalidLongitude , climateAnalyserResponseDto.getErrorMsg());



    }

}