package ch.studer.germanclimatedataanalyser.service.ui.analytics;

import ch.studer.germanclimatedataanalyser.model.database.StationClimate;
import ch.studer.germanclimatedataanalyser.model.database.TemperatureForMonths;
import ch.studer.germanclimatedataanalyser.model.dto.ClimateHistoryDto;

import java.util.ArrayList;
import java.util.List;

public class ClimateHistoryAnalyserServiceImpl implements ClimateHistoryAnalyserService {

    // TODO : yearDifference as Parameter from GUI
    int yearDifference = 30;

    @Override
    public List<ClimateHistoryDto> getClimateHistoryAverageData(String originYear, List<StationClimate> stationClimates) {
        //Build the ClimateHistoryDtos
        List<ClimateHistoryDto> climateHistoryDtos = new ArrayList<ClimateHistoryDto>();
        //Just for collecting  all Records and calculate the average temperature
        List<TemperatureForMonths> tmpTemperatureForMonths;

        int noResult = 0;
        String endPeriod = originYear;

        // Just to make sure that we didnt hit just a hole ! Do it 5 times !
        // anyway 6 x 30 endPeriod = 1840 ; before this date we should not care too much about climate history
        while (noResult <= 5) {

            tmpTemperatureForMonths = new ArrayList<TemperatureForMonths>();
            ClimateHistoryDto climateHistoryDto = new ClimateHistoryDto();
            for (StationClimate stationClimate : stationClimates) {

                if (stationClimate.getEndPeriod().contentEquals(endPeriod)) {

                    if (climateHistoryDto.getEndPeriod().isEmpty()) {
                        climateHistoryDto.setEndPeriod(stationClimate.getEndPeriod());
                        climateHistoryDto.setStartPeriod(stationClimate.getStartPeriod());
                    }
                    tmpTemperatureForMonths.add(stationClimate.getTemperatureForMonths());
                }
            }
            if (tmpTemperatureForMonths.size() > 0) {
                climateHistoryDto.setClimatesMapFrom(new TemperatureForMonths().getAverage(tmpTemperatureForMonths));
                climateHistoryDtos.add(climateHistoryDto);
            }


            //check if there was noResult in this run !
            if (tmpTemperatureForMonths.size() == 0) {
                noResult++;
            }

            //calculate the next period
            endPeriod = String.valueOf(Integer.parseInt(endPeriod) - yearDifference);

        }
        return climateHistoryDtos;
    }

    @Override
    public List<ClimateHistoryDto> getClimateHistoryEveryStationExistsData(String originYear, List<StationClimate> stationClimates) {


        // **************************************************************
        // Eine Liste aller StationsId aus der stationClimates anfertigen, welche durch alle Perioden exisiteren
        // **************************************************************
        List<Integer> stationList = getStationIdExistsInEveryPeriod(originYear, stationClimates);

        // **************************************************************
        // Alle Records aus StationClimates sammeln, welche auch in der Liste der oben angefertigten StationId List existieren
        // **************************************************************

        // Liste aller climateHistoryDtos zurückgeben
        return getClimateHistoryEveryStationExistsInStationList(originYear, stationClimates, stationList);
    }

    private List<Integer> getStationIdExistsInEveryPeriod(String originYear, List<StationClimate> stationClimates) {

        // **************************************************************
        // Eine Liste aller StationsId aus der stationClimates anfertigen, welche durch alle Perioden exisiteren
        // **************************************************************

        List<Integer> stationList = new ArrayList<Integer>();
        int resultCounter = 0;
        String endPeriod = originYear;
        boolean firstRun = true;
        boolean notFound;

        // Just to make sure that we didnt hit a hole ! Do it 5 times !
        // anyway 6 x 30 endPeriod = 1840 ; before this date we should not care too much about climate history
        while (resultCounter <= 5) {
            List<Integer> tmpStationList = new ArrayList<Integer>();
            notFound = true;
            for (StationClimate stationClimate : stationClimates) {

                if (stationClimate.getEndPeriod().contentEquals(endPeriod)) {
                    tmpStationList.add(stationClimate.getStationId());
                    notFound = false;
                }
            }
            //if First Run get all tmStationList into stationList
            if (!notFound) {
                if (firstRun) {
                    stationList = tmpStationList;
                    firstRun = false;
                } else {
                    stationList = checkIfExistsInCollection(tmpStationList, stationList);
                }
            }

            //check if there was resultCounter in this run !
            if (notFound) {
                resultCounter++;
            }


            //calculate the next period
            endPeriod = String.valueOf(Integer.parseInt(endPeriod) - yearDifference);
        }

        return stationList;
    }


    private List<ClimateHistoryDto> getClimateHistoryEveryStationExistsInStationList(String originYear, List<StationClimate> stationClimates, List<Integer> stationList) {
        List<ClimateHistoryDto> climateHistoryDtos = new ArrayList<ClimateHistoryDto>();

        //Just for collecting  all Records and calculate the average temperature
        List<TemperatureForMonths> tmpTemperatureForMonths;

        int noResult = 0;
        String endPeriod = originYear;

        // Just to make sure that we didnt hit just a hole ! Do it 5 times !
        // anyway 6 x 30 endPeriod = 1840 ; before this date we should not care too much about climate history
        while (noResult <= 5) {

            tmpTemperatureForMonths = new ArrayList<TemperatureForMonths>();
            ClimateHistoryDto climateHistoryDto = new ClimateHistoryDto();
            for (StationClimate stationClimate : stationClimates) {

                if (stationClimate.getEndPeriod().contentEquals(endPeriod)) {

                    if (stationList.indexOf(stationClimate.getStationId()) > -1) {
                        if (climateHistoryDto.getEndPeriod().isEmpty()) {
                            climateHistoryDto.setEndPeriod(stationClimate.getEndPeriod());
                            climateHistoryDto.setStartPeriod(stationClimate.getStartPeriod());
                        }
                        tmpTemperatureForMonths.add(stationClimate.getTemperatureForMonths());

                    }
                }
            }
            if (tmpTemperatureForMonths.size() > 0) {
                climateHistoryDto.setClimatesMapFrom(new TemperatureForMonths().getAverage(tmpTemperatureForMonths));
                climateHistoryDtos.add(climateHistoryDto);
            }


            //check if there was noResult in this run !
            if (tmpTemperatureForMonths.size() == 0) {
                noResult++;
            }

            //calculate the next period
            endPeriod = String.valueOf(Integer.parseInt(endPeriod) - yearDifference);

        }

        return climateHistoryDtos;
    }

    private List<Integer> checkIfExistsInCollection(List<Integer> tmpStationList, List<Integer> stationList) {
        List<Integer> newStationList = new ArrayList<Integer>();

        // Check if tmpStationId exists in collection of existing stationIds
        for (Integer tmpStationId : tmpStationList) {
            if (stationList.indexOf(tmpStationId) != -1) {
                newStationList.add(tmpStationId);
            }
        }


        return newStationList;
    }
}
