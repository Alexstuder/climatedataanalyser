package ch.studer.germanclimatedataanalyser.service.ui.analytics;

import ch.studer.germanclimatedataanalyser.model.database.StationClimate;
import ch.studer.germanclimatedataanalyser.model.database.TemperatureForMonths;
import ch.studer.germanclimatedataanalyser.model.dto.ClimateAnalyserRequestDto;
import ch.studer.germanclimatedataanalyser.model.dto.ClimateAnalyserResponseDto;
import ch.studer.germanclimatedataanalyser.model.dto.TemperatureForMonthDto;
import ch.studer.germanclimatedataanalyser.service.db.ClimateService;
import ch.studer.germanclimatedataanalyser.service.db.StationService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("SpringJavaAutowiredMembersInspection")
public class ClimateAnalyserServiceImpl implements ClimateAnalyserService {
    @Autowired
    ClimateService climateService;


    @Autowired
    StationService stationService;

    @Override
    public ClimateAnalyserResponseDto getClimateAnalyticsByClimateAnalyserRequest(ClimateAnalyserRequestDto climateAnalyserRequestDto) {
        ClimateAnalyserResponseDto climateAnalyserResponseDto;

        // Proof input verification and validation
        climateAnalyserResponseDto = isInputValid(climateAnalyserRequestDto);
        List<StationClimate> stationClimates;

        // Get Climate data twice for a Region
        // first for a year
        // second only for the station ,which existed on a special(userInput) year !
        if (climateAnalyserResponseDto.getErrorMsg().isEmpty()) {

            // Get ClimateDifference for Bundesland
            if (!climateAnalyserRequestDto.getBundesland().isEmpty()) {
                stationClimates = climateService.getClimateForBundesland(climateAnalyserRequestDto.getBundesland());
            } else {
                stationClimates = climateService.getClimateForGpsCoordinates(climateAnalyserRequestDto.getGps1(), climateAnalyserRequestDto.getGps2());

            }

            //calculateDifferenceClimate(climateAnalyserResponseDto, stationClimates);

            climateAnalyserResponseDto = calculateDifferenceClimate(climateAnalyserResponseDto, stationClimates);

            //Get the climate history for a region
            climateAnalyserResponseDto.setClimateHistoryAverageDtos(new ClimateHistoryAnalyserServiceImpl().getClimateHistoryAverageData(climateAnalyserResponseDto.getOriginYear(), stationClimates));
        }
        return climateAnalyserResponseDto;
    }

    private ClimateAnalyserResponseDto isInputValid(ClimateAnalyserRequestDto climateAnalyserRequestDto) {


        ClimateAnalyserResponseDto climateAnalyserResponseDto = new ClimateAnalyserResponseDto();

        String errorMsg = "";


        // Proof if bundesland and GPS Coordinates are Blank
        if (climateAnalyserRequestDto.getBundesland().isBlank() &&
                (climateAnalyserRequestDto.getGps1().isGpsNull() ||
                        climateAnalyserRequestDto.getGps2().isGpsNull())) {
            errorMsg = "Neither a Bundesland nor GPS coordinates have been entered";
        } else {

            // Proof if bundesland and GPS Coordinates are entered at the same Time
            if (!climateAnalyserRequestDto.getBundesland().isBlank()) {

                if (!climateAnalyserRequestDto.getGps1().isGpsNull() ||
                        !climateAnalyserRequestDto.getGps2().isGpsNull()) {
                    errorMsg = "A Bundesland was selected and GPS coordinates entered at the same time !";
                } else {
                    // Proof if bundesland exists
                    if (!stationService.bundeslandExists(climateAnalyserRequestDto.getBundesland())) {
                        errorMsg = "Bundesland : " + climateAnalyserRequestDto.getBundesland() + " doesn't exists !";
                    } else {
                        //Proofed Bundesland put into ResponsDto
                        climateAnalyserResponseDto.setBundesland(climateAnalyserRequestDto.getBundesland());
                    }
                }

            } else {
                // Proof if GPS1 Coordinates are valid
                if (!climateAnalyserRequestDto.getGps1().isLatitudeValid() ||
                        !climateAnalyserRequestDto.getGps1().isLongitudeValid()) {
                    errorMsg = "GPS1 Coordinates are not valid ! : Latitude (-90, 0,90) :" + climateAnalyserRequestDto.getGps1().getLatitude() + " Longitude(-180,0,180)" + climateAnalyserRequestDto.getGps1().getLongitude();
                } else {


                    // Proof if GPS2 Coordinates are valid
                    if (!climateAnalyserRequestDto.getGps2().isLongitudeValid() ||
                            !climateAnalyserRequestDto.getGps2().isLatitudeValid()) {
                        errorMsg = "GPS2 Coordinates are not valid ! : Latitude (-90, 0,90) :" + climateAnalyserRequestDto.getGps2().getLatitude() + " Longitude(-180,0,180)" + climateAnalyserRequestDto.getGps2().getLongitude();

                    } else {
                        //Both GPS Coordinates are Valid
                        climateAnalyserResponseDto.setGps1(climateAnalyserRequestDto.getGps1());
                        climateAnalyserResponseDto.setGps2(climateAnalyserRequestDto.getGps2());
                    }
                }
            }
        }


        // Proof if errorMsg is still empty
        if (errorMsg.isEmpty()) {

            // Proof if the Years Field contain only Numbers

            if (isNumeric(climateAnalyserRequestDto.getYearOrigine())) {

                climateAnalyserResponseDto.setOriginYear(climateAnalyserRequestDto.getYearOrigine());

                if (isNumeric(climateAnalyserRequestDto.getYearToCompare())) {
//                if (!climateAnalyserRequestDto.getYearToCompare().contains("[a-zA-Z]+")){
                    climateAnalyserResponseDto.setYearToCompare(climateAnalyserRequestDto.getYearToCompare());
                } else {
                    errorMsg = "Only Numbers for year to compare are allowed !";
                }

            } else {
                errorMsg = "Only Numbers for origin Year are allowed !";
            }

        }
        // If errorMsg is set or Empty put it into the response !
        climateAnalyserResponseDto.setErrorMsg(errorMsg);

        return climateAnalyserResponseDto;
    }

    private ClimateAnalyserResponseDto calculateDifferenceClimate(ClimateAnalyserResponseDto thisclimateAnalyserResponseDto, List<StationClimate> stationClimates) {

        TemperatureForMonthDto climateAnalyserOrigin = getClimateAggregatedForOriginYear(thisclimateAnalyserResponseDto.getOriginYear(), stationClimates);

        //Proofe if Origine could be aggregated
//        if (isNotEmpty(climateAnalyserOrigin)){
        if (climateAnalyserOrigin.isNotZero()) {

            thisclimateAnalyserResponseDto.setOriginal(climateAnalyserOrigin);
//            climateAnalyserResponseDto.setOriginal(climateAnalyserOrigin);


            // Proof if there are some stationId in the originYear which exist in the year to compare
            TemperatureForMonthDto climateAnalyserCompare = getClimateAggregatedForStationsFromYearToCompare(thisclimateAnalyserResponseDto.getOriginYear(), thisclimateAnalyserResponseDto.getYearToCompare(), stationClimates);
            if (climateAnalyserCompare.isNotZero()) {

                thisclimateAnalyserResponseDto.setCompare(climateAnalyserCompare);
//            climateAnalyserResponseDto.setCompare(climateAnalyserCompare);
            } else {
                thisclimateAnalyserResponseDto.setErrorMsg("No StationId from the year " + thisclimateAnalyserResponseDto.getYearToCompare() + " was found, which may already have existed in the year " + thisclimateAnalyserResponseDto.getOriginYear());
            }

        } else {
            thisclimateAnalyserResponseDto.setErrorMsg("No climate data for the year: " + thisclimateAnalyserResponseDto.getOriginYear() + " found !");
        }

        return thisclimateAnalyserResponseDto;

    }

    private TemperatureForMonthDto getClimateAggregatedForStationsFromYearToCompare(String originYear, String yearToCompare, List<StationClimate> stationClimates) {
        TemperatureForMonthDto temperatureForMonthDto = new TemperatureForMonthDto();

        // Get all stationIds to the year to compare
        List<Integer> stationIds = getStationIdsForYearToCompare(yearToCompare, stationClimates);
        List<TemperatureForMonths> tmpTemperatureForMonths = new ArrayList<TemperatureForMonths>();

        for (StationClimate sc : stationClimates) {
            if (sc.getEndPeriod().contains(originYear) && stationIds.contains(sc.getStationId())) {
                tmpTemperatureForMonths.add(sc.getTemperatureForMonths());
            }
        }
        if (tmpTemperatureForMonths.size() > 0) {
            temperatureForMonthDto = new TemperatureForMonthDto().mapFrom(new TemperatureForMonths().getAverage(tmpTemperatureForMonths));
        }


        return temperatureForMonthDto;
    }

    private List<Integer> getStationIdsForYearToCompare(String yearToCompare, List<StationClimate> stationClimates) {

        List<Integer> stationIds = new ArrayList<Integer>();

        for (StationClimate stationClimate : stationClimates) {
            if (stationClimate.getEndPeriod().contentEquals(yearToCompare)) {
                stationIds.add(stationClimate.getStationId());
            }
        }

        return stationIds;
    }

    private TemperatureForMonthDto getClimateAggregatedForOriginYear(String year, List<StationClimate> stationClimates) {

        TemperatureForMonthDto tempClimate = new TemperatureForMonthDto();
        List<TemperatureForMonths> tmpTemperatureForMonths = new ArrayList<TemperatureForMonths>();

        for (StationClimate sc : stationClimates) {
            if (year.contains(sc.getEndPeriod())) {
                tmpTemperatureForMonths.add(sc.getTemperatureForMonths());
            }
        }
        if (tmpTemperatureForMonths.size() > 0) {
            tempClimate = new TemperatureForMonthDto().mapFrom(new TemperatureForMonths().getAverage(tmpTemperatureForMonths));
        }

        return tempClimate;
    }

    public static boolean isNumeric(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
