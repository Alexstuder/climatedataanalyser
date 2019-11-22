package ch.studer.germanclimatedataanalyser.service;

import ch.studer.germanclimatedataanalyser.model.ClimateAtStation;
import ch.studer.germanclimatedataanalyser.model.Month;
import ch.studer.germanclimatedataanalyser.model.TemperatureByStationId;
import ch.studer.germanclimatedataanalyser.model.TemperatureRecord;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ClimateAtStationServiceTest {

    @Mock
    TemperaturesAtStationService temperaturesAtStationService;

    @Mock
    MonthService monthService;

    @InjectMocks
    ClimateServiceImpl climateService;

    int stationId = 1 ;
    private static final String BEGIN = "BEGIN";
    private static final String END = "END";

    int begin;
    int numberYears;

    TemperatureByStationId temperatureByStationId;


    @Before
    public void setUp(){

        climateService.period = 30;

    }


    @Test
    public void testClimateService(){



        when(temperaturesAtStationService.getTemperaturesBy(stationId)).thenReturn(getTemperaturesBy(stationId));

        ClimateAtStation climateAtStation = climateService.getClimateAtStationId(stationId);
        assertNotNull(climateAtStation);
        assertTrue(climateAtStation.getClimateRecords().size() == 61);
        climateService.getDifference(stationId);

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
