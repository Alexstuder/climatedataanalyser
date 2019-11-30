package ch.studer.germanclimatedataanalyser.model;


import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

//@RunWith(MockitoJUnitRunner.class)
public class ClimateDifferenceTest {

    ClimateRecord first  ;

    ClimateRecord second;

    @Before
    public void setUp(){

        first = new ClimateRecord("2019","2000");
        first.setTempJan(1.01d);
        first.setTempFeb(2.01d);
        first.setTempMar(3.01d);
        first.setTempApr(4.01d);
        first.setTempMai(5.01d);
        first.setTempJun(6.01d);
        first.setTempJul(7.01d);
        first.setTempAug(8.01d);
        first.setTempSep(9.01d);
        first.setTempOkt(10.01d);
        first.setTempNov(11.01d);
        first.setTempDez(12.01d);

        second = new ClimateRecord("2000","1980");
        second.setStartDate("1980");
        second.setEndPeriod("2000");
        second.setTempJan(2.01d);
        second.setTempFeb(3.01d);
        second.setTempMar(4.01d);
        second.setTempApr(5.01d);
        second.setTempMai(6.01d);
        second.setTempJun(7.01d);
        second.setTempJul(8.01d);
        second.setTempAug(9.01d);
        second.setTempSep(10.01d);
        second.setTempOkt(11.01d);
        second.setTempNov(12.01d);
        second.setTempDez(13.01d);




    }


//    @Test
//   public void testClimateDifference(){
//        ClimateDifferenceAtStation climateDifferenceAtStation = new ClimateDifferenceAtStation("111");
//        ClimateDifference climateDifference = new ClimateDifference(first,second);
//
//        climateDifferenceAtStation.getClimateDifferences().add(climateDifference);
//
//
//        assertEquals(-1d,climateDifference.getDifference().jan,0.000001d);
//        assertEquals(-1d,climateDifference.getDifference().feb,0.000001d);
//        assertEquals(-1d,climateDifference.getDifference().mar,0.000001d);
//        assertEquals(-1d,climateDifference.getDifference().apr,0.000001d);
//        assertEquals(-1d,climateDifference.getDifference().mai,0.000001d);
//        assertEquals(-1d,climateDifference.getDifference().jun,0.000001d);
//        assertEquals(-1d,climateDifference.getDifference().jul,0.000001d);
//        assertEquals(-1d,climateDifference.getDifference().aug,0.000001d);
//        assertEquals(-1d,climateDifference.getDifference().sep,0.000001d);
//        assertEquals(-1d,climateDifference.getDifference().oct,0.000001d);
//        assertEquals(-1d,climateDifference.getDifference().nov,0.000001d);
//        assertEquals(-1d,climateDifference.getDifference().dec,0.000001d);
//
//    }

}
