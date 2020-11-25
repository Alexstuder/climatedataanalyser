package ch.studer.germanclimatedataanalyser.generate.test.data;

import ch.studer.germanclimatedataanalyser.model.database.StationClimate;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class ClimateTestData {

    private static final int period = 29;
    private final List<StationClimate> stationClimates = new ArrayList<StationClimate>();


    public static List<StationClimate> getStationClimateOrderByStationIdAndBeginYear(int beginYear, int endYear, int numberOfStations) {

        List<StationClimate> stationClimates = new ArrayList<StationClimate>();

        for (int stationId = 1; stationId <= numberOfStations; stationId++) {

            for (int year = beginYear; year <= endYear + stationId; year++) {
                StationClimate stationClimate = new StationClimate(stationId);
                stationClimate.setEndPeriod(Integer.toString(year));
                stationClimate.setStartPeriod(Integer.toString(year - period));
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


        return stationClimates;
    }

    public List<StationClimate> removeClimates(String yearToRemove, int[] stationsIdsToRemove, List<StationClimate> stationClimatesList) {
        List<StationClimate> stationClimatesToRemove = new ArrayList<StationClimate>();

        for (StationClimate stationClimate : stationClimatesList) {
            if (stationClimate.getEndPeriod().contentEquals(yearToRemove)) {
                for (int i : stationsIdsToRemove) {
                    if (stationClimate.getStationId() == i) {
                        stationClimatesToRemove.add(stationClimate);
                    }
                }
            }
        }

        // Remove the climateStations
        for (StationClimate toRemove : stationClimatesToRemove) {
            stationClimatesList.remove(toRemove);
        }

        return stationClimatesList;


    }

    public static List<StationClimate> getStationClimateOrderByBeginYearAndStationId(int beginYear, int endYear, int numberOfStations) {

        List<StationClimate> stationClimates = new ArrayList<StationClimate>();

        for (int year = beginYear; year <= endYear; year++) {
            int stationId = 0;
            while (stationId++ < numberOfStations) {
                StationClimate stationClimate = new StationClimate(stationId);
                stationClimate.setEndPeriod(Integer.toString(year));
                stationClimate.setStartPeriod(Integer.toString(year - period));
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

        return stationClimates;
    }

    public static List<StationClimate> getStationClimateOrderByStationIdAndBeginYearRemoveLater(int beginYear, int endYear, int numberOfStations) {

        List<StationClimate> stationClimates = new ArrayList<StationClimate>();

        for (int stationId = 1; stationId <= numberOfStations; stationId++) {

            for (int year = beginYear; year <= endYear; year++) {
                StationClimate stationClimate = new StationClimate(stationId);
                stationClimate.setEndPeriod(Integer.toString(year));
                stationClimate.setStartPeriod(Integer.toString(year - period));
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


        return stationClimates;
    }


}
