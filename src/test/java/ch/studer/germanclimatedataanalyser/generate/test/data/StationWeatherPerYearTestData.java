package ch.studer.germanclimatedataanalyser.generate.test.data;

import ch.studer.germanclimatedataanalyser.model.database.StationWeatherPerYear;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
 public final class StationWeatherPerYearTestData {

    private static int period = 30;

    private static BigDecimal NULL_TEMPERATURE = new BigDecimal("-999.0000");

    public  final static  List<StationWeatherPerYear> getHoles(List<StationWeatherPerYear> weatherComplete) {

        List<StationWeatherPerYear> weatherWithHoles = new ArrayList<StationWeatherPerYear>();

        int i = 0;
        for (StationWeatherPerYear s : weatherComplete) {
            i++;
            StationWeatherPerYear n = new StationWeatherPerYear(s.getStationID());
            n.setYear(s.getYear());
            if (i != 1) n.setJanuar(s.getJanuar());
            else n.setJanuar(NULL_TEMPERATURE);
            if (i != 2) n.setFebruar(s.getFebruar());
            else n.setFebruar(NULL_TEMPERATURE);
            if (i != 3) n.setMaerz(s.getMaerz());
            else n.setMaerz(NULL_TEMPERATURE);
            if (i != 4) n.setApril(s.getApril());
            else n.setApril(NULL_TEMPERATURE);
            if (i != 5) n.setMai(s.getMai());
            else n.setMai(NULL_TEMPERATURE);
            if (i != 6) n.setJuni(s.getJuni());
            else n.setJuni(NULL_TEMPERATURE);
            if (i != 7) n.setJuli(s.getJuli());
            else n.setJuli(NULL_TEMPERATURE);
            if (i != 8) n.setAugust(s.getAugust());
            else n.setAugust(NULL_TEMPERATURE);
            if (i != 9) n.setSeptember(s.getSeptember());
            else n.setSeptember(NULL_TEMPERATURE);
            if (i != 10) n.setOktober(s.getOktober());
            else n.setOktober(NULL_TEMPERATURE);
            if (i != 11) n.setNovember(s.getNovember());
            else n.setNovember(NULL_TEMPERATURE);
            if (i != 12) n.setDezember(s.getDezember());
            else {
                n.setDezember(NULL_TEMPERATURE);
                i = 0;
            }

            weatherWithHoles.add(n);
        }


        return weatherWithHoles;
    }

//    public static List<StationWeatherPerYear> getStationWeatherPerYearList(String startDate,int stationId) {
//        List<StationWeatherPerYear> l = new ArrayList<>();
//
//        int start = Integer.valueOf(startDate);
//
//        for(int i = start; i > start-period ;i-- ){
//            l.add(getStationWeatherPerYear(i,stationId));
//        }
//
//        return l;
//    }
    public static List<StationWeatherPerYear> getStationWeatherPerYearList(String startDate,int stationId,boolean nullValues) {
        List<StationWeatherPerYear> l = new ArrayList<>();

        int start = Integer.valueOf(startDate);

        if (nullValues){
            for(int i = start; i > start-period ;i-- ){
                l.add(new StationWeatherPerYear(stationId,String.valueOf(i)));
                      //  getStationWeatherPerYearNull(i,stationId));
            }
        } else {

            for(int i = start; i > start-period ;i-- ){
                l.add(getStationWeatherPerYear(i,stationId));
            }
        }

        return l;
    }

    private static StationWeatherPerYear getStationWeatherPerYear(int i, int stationId) {

        StationWeatherPerYear s = new StationWeatherPerYear(stationId);
        s.setYear(String.valueOf(i));
        s.setStationId(stationId);
        s.setJanuar(new BigDecimal("-1.111"));
        s.setFebruar(new BigDecimal("-2.222"));
        s.setMaerz(new BigDecimal("3.333"));
        s.setApril(new BigDecimal("4.444"));
        s.setMai(new BigDecimal("5.555"));
        s.setJuni(new BigDecimal("6.666"));
        s.setJuli(new BigDecimal("7.777"));
        s.setAugust(new BigDecimal("8.888"));
        s.setSeptember(new BigDecimal("9.999"));
        s.setOktober(new BigDecimal("10.10"));
        s.setNovember(new BigDecimal("-11.111"));
        s.setDezember(new BigDecimal("-12.12"));

        return s;
    }

}
