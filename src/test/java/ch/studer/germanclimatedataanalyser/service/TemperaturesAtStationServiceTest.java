package ch.studer.germanclimatedataanalyser.service;


import ch.studer.germanclimatedataanalyser.model.Month;
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

import static org.mockito.Mockito.when;
import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class TemperaturesAtStationServiceTest {


    @Mock
    MonthService monthService;

    @InjectMocks
    TemperaturesAtStationServiceImpl temperaturesAtStationService;

    private int stationId = 1 ;
    private int begin;
    private int numberYears;

    private static final String BEGIN = "BEGIN";
    private static final String END = "END";

    @Before
    public void setUp(){
        temperaturesAtStationService.period = 30;
    }



    @Test
    public void testTemperaturesAtStationService(){

        // HappyFlow Test without Null ;

        // Define the begin Year ; all following Records are descending
        begin = 2018 ;
        numberYears = 90;

        when(monthService.getMonthsByIdOrderDesc(stationId)).thenReturn(getMonth(stationId,begin,numberYears));

        List<TemperatureRecord> temperatureRecords = temperaturesAtStationService.getTemperaturesBy(stationId).getTemperatureRecordList();

        assertNotNull(temperatureRecords);
        assertTrue(isAllMonthTemperatureValid(temperatureRecords));

    }

    @Test
    public void testTemperaturesAtStationServiceWithNulls(){

        // Define the begin Year ; all following Records are descending
        begin = 2018 ;
        numberYears = 90;


        when(monthService.getMonthsByIdOrderDesc(stationId)).thenReturn(getNulls(getMonth(stationId,begin,numberYears)));

        List<TemperatureRecord> temperatureRecords = temperaturesAtStationService.getTemperaturesBy(stationId).getTemperatureRecordList();

        assertEquals(91,temperatureRecords.size());
        assertEquals(01.00,temperatureRecords.get(0).getJan(),0);
        assertEquals(12.00,temperatureRecords.get(90).getDec(),0);
        assertTrue(isAllMonthTemperatureValid(temperatureRecords));

    }

    private boolean isAllMonthTemperatureValid(List<TemperatureRecord> temperatureRecords) {


        for (TemperatureRecord t : temperatureRecords){
            if (t.getJan() != 01.00d) {return false;}
            if (t.getFeb() != 02.00d) {return false;}
            if (t.getMar() != 03.00d) {return false;}
            if (t.getApr() != 04.00d) {return false;}
            if (t.getMai() != 05.00d) {return false;}
            if (t.getJun() != 06.00d) {return false;}
            if (t.getJul() != 07.00d) {return false;}
            if (t.getAug() != 08.00d) {return false;}
            if (t.getSep() != 09.00d) {return false;}
            if (t.getOct() != 10.00d) {return false;}
            if (t.getNov() != 11.00d) {return false;}
            if (t.getDec() != 12.00d) {return false;}
        }

        return true;
    }

    private List<Month> getNulls(List<Month> month) {

        // remove first Jan
        month.remove(11);

        // remove Last
        month.remove(month.size()-12);


        // Get some null on different positions

        for(int i = 0 ; i < month.size() ; i = i +10 ){
            month.remove(i);
        }

       return month;
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
