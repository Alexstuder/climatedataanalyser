package ch.studer.germanclimatedataanalyser.service;


import ch.studer.germanclimatedataanalyser.model.Month;
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
public class ClimateAtSTationServiceTest {


    @Mock
    MonthService monthService;

    @InjectMocks
    ClimateAtStationServiceImpl climateAtStationService;

    private int stationsId = 1 ;
    private int begin;
    private int numberYears;

    private static final String BEGIN = "BEGIN";
    private static final String END = "END";


    @Test
    public void testClimateAtStationService(){

        // Define the begin Year ; all following Records are descending
        begin = 2018 ;
        numberYears = 90;

        when(monthService.getMonthsByIdOrderDesc(stationsId)).thenReturn(getMonth(stationsId,begin,numberYears));
        assertNotNull(climateAtStationService.getClimateDataBy(stationsId));
       // assertEquals(480,climateAtStationService.getClimateDataBy(stationsId).getClimateRecords().size());

    }

    @Test
    public void testClimateAtStationServiceWithNulls(){

        // Define the begin Year ; all following Records are descending
        begin = 2018 ;
        numberYears = 90;

        when(monthService.getMonthsByIdOrderDesc(stationsId)).thenReturn(getNulls(getMonth(stationsId,begin,numberYears)));
        assertNotNull(climateAtStationService.getClimateDataBy(stationsId));
       // assertEquals(480,climateAtStationService.getClimateDataBy(stationsId).getClimateRecords().size());

    }

    private List<Month> getNulls(List<Month> month) {

        // remove Jan
        month.remove(23);

        // remove Dec
        month.remove(47);

        // remove Jun
        month.remove(88);

       return month;
    }


    private List<Month> getMonth(int stationsId, int beginDate, int numberYears){

        List<Month> months = new ArrayList<Month>();
        int date = beginDate ;

        // Year iteration
        for (int year = 0 ; year < numberYears ; year++ ){

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
                if(modus == BEGIN){
                     date = String.valueOf(year).concat("-01-01");
                } else {
                     date = String.valueOf(year).concat("-01-31");
                }
                break;
            case 2:
                //Feb
                if(modus == BEGIN){
                    date = String.valueOf(year).concat("-02-01");
                } else {
                    date = String.valueOf(year).concat("-02-28");
                }
                break;
            case 3:
                //Mar
                if(modus == BEGIN){
                    date = String.valueOf(year).concat("-03-01");
                } else {
                    date = String.valueOf(year).concat("-03-31");
                }
                break;
            case 4:
                //Apr
                if(modus == BEGIN){
                    date = String.valueOf(year).concat("-04-01");
                } else {
                    date = String.valueOf(year).concat("-04-30");
                }
                break;
            case 5:
                //Mai
                if(modus == BEGIN){
                    date = String.valueOf(year).concat("-05-01");
                } else {
                    date = String.valueOf(year).concat("-05-31");
                }
                break;
            case 6:
                //Jun
                if(modus == BEGIN){
                    date = String.valueOf(year).concat("-06-01");
                } else {
                    date = String.valueOf(year).concat("-06-30");
                }
                break;
            case 7:
                //Jul
                if(modus == BEGIN){
                    date = String.valueOf(year).concat("-07-01");
                } else {
                    date = String.valueOf(year).concat("-07-30");
                }
                break;
            case 8:
                //Aug
                if(modus == BEGIN){
                    date = String.valueOf(year).concat("-08-01");
                } else {
                    date = String.valueOf(year).concat("-08-31");
                }
                break;
            case 9:
                //Sep
                if(modus == BEGIN){
                    date = String.valueOf(year).concat("-09-01");
                } else {
                    date = String.valueOf(year).concat("-09-30");
                }
                break;
            case 10:
                //Oct
                if(modus == BEGIN){
                    date = String.valueOf(year).concat("-10-01");
                } else {
                    date = String.valueOf(year).concat("-10-31");
                }
                break;
            case 11:
                //Nov
                if(modus == BEGIN){
                    date = String.valueOf(year).concat("-11-01");
                } else {
                    date = String.valueOf(year).concat("-11-30");
                }
                break;
            case 12:
                //Dec
                if(modus == BEGIN){
                    date = String.valueOf(year).concat("-12-01");
                } else {
                    date = String.valueOf(year).concat("-12-31");
                }
                break;
            default:
                date = "Somthing went wrong!";
                break;
        }



        return date ;

    }


}
