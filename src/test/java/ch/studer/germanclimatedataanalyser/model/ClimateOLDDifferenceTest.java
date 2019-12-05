package ch.studer.germanclimatedataanalyser.model;


import org.junit.Before;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;

//@RunWith(MockitoJUnitRunner.class)
public class ClimateOLDDifferenceTest {

    ClimateRecord_OLD first  ;

    ClimateRecord_OLD second;

    @Before
    public void setUp(){

        first = new ClimateRecord_OLD("2019","2000");
        first.setTempJan(new BigDecimal(1.01));
        first.setTempFeb(new BigDecimal(2.01d));
        first.setTempMar(new BigDecimal(3.01d));
        first.setTempApr(new BigDecimal(4.01d));
        first.setTempMai(new BigDecimal(5.01d));
        first.setTempJun(new BigDecimal(6.01d));
        first.setTempJul(new BigDecimal(7.01d));
        first.setTempAug(new BigDecimal(8.01d));
        first.setTempSep(new BigDecimal(9.01d));
        first.setTempOkt(new BigDecimal(10.01d));
        first.setTempNov(new BigDecimal(11.01d));
        first.setTempDez(new BigDecimal(12.01d));

        second = new ClimateRecord_OLD("2000","1980");
        second.setStartDate("1980");
        second.setEndPeriod("2000");
        second.setTempJan(new BigDecimal(2.01d));
        second.setTempFeb(new BigDecimal(3.01d));
        second.setTempMar(new BigDecimal(4.01d));
        second.setTempApr(new BigDecimal(5.01d));
        second.setTempMai(new BigDecimal(6.01d));
        second.setTempJun(new BigDecimal(7.01d));
        second.setTempJul(new BigDecimal(8.01d));
        second.setTempAug(new BigDecimal(9.01d));
        second.setTempSep(new BigDecimal(10.01d));
        second.setTempOkt(new BigDecimal(11.01d));
        second.setTempNov(new BigDecimal(12.01d));
        second.setTempDez(new BigDecimal(13.01d));




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
