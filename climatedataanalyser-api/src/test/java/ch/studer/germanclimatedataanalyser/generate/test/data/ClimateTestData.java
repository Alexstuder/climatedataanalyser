package ch.studer.germanclimatedataanalyser.generate.test.data;

import ch.studer.germanclimatedataanalyser.model.database.StationClimate;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class ClimateTestData {

    private static int period = 30;
    private List<StationClimate> stationClimates = new ArrayList<StationClimate>();


  public static List<StationClimate> getStationClimate(int beginYear,int endYear,int numberOfStations){

        List<StationClimate> stationClimates = new ArrayList<StationClimate>();

        for (int stationId=1 ; stationId <=numberOfStations ; stationId++){
              for (int year = beginYear ; year <= endYear + stationId ; year++ ){
                  StationClimate stationClimate = new StationClimate(stationId);
                  stationClimate.setEndPeriod(Integer.toString(year));
                  stationClimate.setStartPeriod(Integer.toString(year-period));
                  stationClimate.setJanuar(new BigDecimal(stationId));
                  stationClimate.setFebruar(new BigDecimal(stationId + 1));
                  stationClimate.setMaerz(new BigDecimal(stationId + 2));
                  stationClimate.setApril(new BigDecimal(stationId + 3));
                  stationClimate.setMai(new BigDecimal(stationId + 4));
                  stationClimate.setJuni(new BigDecimal(stationId + 5));
                  stationClimate.setJuli(new BigDecimal(stationId + 6));
                  stationClimate.setAugust(new BigDecimal(stationId + 7));
                  stationClimate.setSeptember(new BigDecimal(stationId + 8));
                  stationClimate.setOktober(new BigDecimal(stationId + 9));
                  stationClimate.setNovember(new BigDecimal(stationId + 10));
                  stationClimate.setDezember(new BigDecimal(stationId + 11));
                  stationClimates.add(stationClimate);
              }
        }


        return  stationClimates;
    }

}
