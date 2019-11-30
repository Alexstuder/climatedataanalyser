package ch.studer.germanclimatedataanalyser.model;

import ch.studer.germanclimatedataanalyser.model.*;
import ch.studer.germanclimatedataanalyser.service.MonthService;
import ch.studer.germanclimatedataanalyser.service.StationService;
import ch.studer.germanclimatedataanalyser.service.TemperaturesAtStationService;
import javassist.NotFoundException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import javax.persistence.NoResultException;
import java.math.BigDecimal;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@SpringBootTest
@SpringBootConfiguration
@RunWith(MockitoJUnitRunner.class)
@TestPropertySource(properties = {
        "climate.calculation.period.year=30",
})
public class ClimateAtStationServiceTest {

    @Mock
    TemperaturesAtStationService temperaturesAtStationService;

    @Mock
    StationService stationService;

    @InjectMocks
    ClimateAtStation climateAtStation;

    @Mock
    MonthService monthService;

//    @InjectMocks
//    ClimateServiceImpl climateService;

    int stationId = 1 ;
    private static final String STATION_NOT_FOUND = "StationNotFound";
    private static final String STATION_NAME = "StationName";
    private static final String BEGIN = "BEGIN";
    private static final String END = "END";

    int begin;
    int numberYears;

    TemperatureByStationId temperatureByStationId;


    @Before
    public void setUp(){
     climateAtStation.period = 30;

    }


    @Test
    public void testClimateService() throws NotFoundException {



        when(temperaturesAtStationService.getTemperaturesBy(stationId)).thenReturn(getTemperaturesBy(stationId));
        when(stationService.getStation(stationId)).thenReturn(getStationBy(stationId));

        try{
           climateAtStation.getNewClimateAtStation(String.valueOf(stationId));
        } catch (Exception e){
         //TODO ??? what to proof here ?
        }
        assertNotNull(climateAtStation);
        assertTrue(climateAtStation.getClimateRecords().size() == 61);

        // Proof if Difference is Zero
        assertTrue(isZero(climateAtStation.getClimateDifferences()));


    }

@Test
    public void testClimateAtStationWithName() throws NotFoundException {



        when(temperaturesAtStationService.getTemperaturesBy(stationId)).thenReturn(getTemperaturesBy(stationId));
        when(stationService.getStation(STATION_NAME)).thenReturn(getStationBy(stationId));

        try{
           climateAtStation.getNewClimateAtStation(String.valueOf(STATION_NAME));
        } catch (Exception e){
        }
        assertNotNull(climateAtStation);
        assertTrue(climateAtStation.getClimateRecords().size() == 61);

        // Proof if Difference is Zero
        assertTrue(isZero(climateAtStation.getClimateDifferences()));


    }

    @Test
    public void testClimateAtStationNotFound() throws NotFoundException {

        when(stationService.getStation(STATION_NOT_FOUND)).thenReturn(new Station());
        try {
            climateAtStation.getNewClimateAtStation(STATION_NOT_FOUND);
        } catch (NoResultException exe){
            assertEquals("No Station :StationNotFound found! ",exe.getMessage());
        }


    }

    @Test
    public void testClimateAtStationWithIdNotFound() throws NotFoundException {

        when(stationService.getStation(2)).thenReturn(new Station());
        try {
            climateAtStation.getNewClimateAtStation("2");
        } catch (NoResultException exe){
            assertEquals("No Station :2 found! ",exe.getMessage());
        }


    }

    private Station getStationBy(int stationId) {
        Station station = new Station(stationId
                ,new Date(1999,01,01)
                ,new Date(1999,12,31)
                ,new BigDecimal(100)
                ,new BigDecimal(22.22)
                ,new BigDecimal(23.34)
                ,"Name"
                ,"bundesland");

        return station;

    }

    private boolean isZero(List<ClimateDifference> climateDifferences) {

        for (ClimateDifference climateDifference : climateDifferences){
            if (climateDifference.getDifference().getJan() != 0
             || climateDifference.getDifference().getFeb() != 0
             || climateDifference.getDifference().getMar() != 0
             || climateDifference.getDifference().getApr() != 0
             || climateDifference.getDifference().getMai() != 0
             || climateDifference.getDifference().getJun() != 0
             || climateDifference.getDifference().getJul() != 0
             || climateDifference.getDifference().getAug() != 0
             || climateDifference.getDifference().getSep() != 0
             || climateDifference.getDifference().getOct() != 0
             || climateDifference.getDifference().getNov() != 0
             || climateDifference.getDifference().getDec() != 0
            ){
                return false;
            }
        }

        return true;

    }

    private TemperatureByStationId getTemperaturesBy(int stationId) {

        TemperatureByStationId temperatureByStationId = new TemperatureByStationId(stationId);

        int startYear = 2018; // Start from
        int numberYears = 90 ; // Number of Records to return
        temperatureByStationId.setTemperatureRecordList(getTemperatureRecordList(startYear , numberYears));




        return temperatureByStationId;
    }

    private List<TemperatureRecord> getTemperatureRecordList(int startDate, int numberYears) {
        List<TemperatureRecord> temperatureRecords = new ArrayList<>();

        int actualYear;
        actualYear = Integer.valueOf(startDate);


        for (int i = 0 ; i <= numberYears; i++){

            TemperatureRecord t = new TemperatureRecord(String.valueOf(actualYear));
            t.setJan(-1.00);
            t.setFeb(2.00);
            t.setMar(3.00);
            t.setApr(4.00);
            t.setMai(5.00);
            t.setJun(6.00);
            t.setJul(7.00);
            t.setAug(8.00);
            t.setSep(9.00);
            t.setOct(10.00);
            t.setNov(11.00);
            t.setDec(12.00);
            temperatureRecords.add(t);
            actualYear--;
        }




       return temperatureRecords;
    }

    private List<Month> getMonth(int stationsId, int beginDate, int numberYears){

        List<Month> months = new ArrayList<>();
        int date = beginDate ;

        // Year iteration
        for (int year = 0 ; year <= numberYears ; year++ ){

            // Month iteration
            for(int month = 12 ; month >0 ; month--){

                Month m = new Month();
                m.setStationsId(stationsId);
                m.setMessDatumBeginn(Date.valueOf(getDate(BEGIN,date,month)));
                m.setMessDatumEnde(Date.valueOf(getDate(END,date,month)));

                m.setMoTt(month);

                months.add(m);
            }

            //Descending Year
            date--;



        }


        return months;


    }
    private String getDate(String modus,int year,int month){
        String date ;

        switch (month) {
            case 1:
                //Jan
                if(modus.equals(BEGIN)){
                    date = String.valueOf(year).concat("-01-01");
                } else {
                    date = String.valueOf(year).concat("-01-31");
                }
                break;
            case 2:
                //Feb
                if(modus.equals(BEGIN)){
                    date = String.valueOf(year).concat("-02-01");
                } else {
                    date = String.valueOf(year).concat("-02-28");
                }
                break;
            case 3:
                //Mar
                if(modus.equals(BEGIN)){
                    date = String.valueOf(year).concat("-03-01");
                } else {
                    date = String.valueOf(year).concat("-03-31");
                }
                break;
            case 4:
                //Apr
                if(modus.equals(BEGIN)){
                    date = String.valueOf(year).concat("-04-01");
                } else {
                    date = String.valueOf(year).concat("-04-30");
                }
                break;
            case 5:
                //Mai
                if(modus.equals(BEGIN)){
                    date = String.valueOf(year).concat("-05-01");
                } else {
                    date = String.valueOf(year).concat("-05-31");
                }
                break;
            case 6:
                //Jun
                if(modus.equals(BEGIN)){
                    date = String.valueOf(year).concat("-06-01");
                } else {
                    date = String.valueOf(year).concat("-06-30");
                }
                break;
            case 7:
                //Jul
                if(modus.equals(BEGIN)){
                    date = String.valueOf(year).concat("-07-01");
                } else {
                    date = String.valueOf(year).concat("-07-30");
                }
                break;
            case 8:
                //Aug
                if(modus.equals(BEGIN)){
                    date = String.valueOf(year).concat("-08-01");
                } else {
                    date = String.valueOf(year).concat("-08-31");
                }
                break;
            case 9:
                //Sep
                if(modus.equals(BEGIN)){
                    date = String.valueOf(year).concat("-09-01");
                } else {
                    date = String.valueOf(year).concat("-09-30");
                }
                break;
            case 10:
                //Oct
                if(modus.equals(BEGIN)){
                    date = String.valueOf(year).concat("-10-01");
                } else {
                    date = String.valueOf(year).concat("-10-31");
                }
                break;
            case 11:
                //Nov
                if(modus.equals(BEGIN)){
                    date = String.valueOf(year).concat("-11-01");
                } else {
                    date = String.valueOf(year).concat("-11-30");
                }
                break;
            case 12:
                //Dec
                if(modus.equals(BEGIN)){
                    date = String.valueOf(year).concat("-12-01");
                } else {
                    date = String.valueOf(year).concat("-12-31");
                }
                break;
            default:
                date = "Something went wrong!";
                break;
        }



        return date ;

    }


}
