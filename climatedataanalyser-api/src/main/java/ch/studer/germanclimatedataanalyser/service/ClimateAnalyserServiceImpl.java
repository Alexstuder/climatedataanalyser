package ch.studer.germanclimatedataanalyser.service;

import ch.studer.germanclimatedataanalyser.model.database.StationClimate;
import ch.studer.germanclimatedataanalyser.model.dto.ClimateAnalyserRequestDto;
import ch.studer.germanclimatedataanalyser.model.dto.ClimateAnalyserResponseDto;
import ch.studer.germanclimatedataanalyser.model.dto.ClimateAnalyserTempDto;
import ch.studer.germanclimatedataanalyser.model.dto.helper.GpsPoint;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

public class ClimateAnalyserServiceImpl implements ClimateAnalyserService {

    @Autowired
    ClimateService climateService;


    @Autowired
    StationService stationService;

    @Override
    public ClimateAnalyserResponseDto getClimateAnalyticsByClimateAnalyserRequest(ClimateAnalyserRequestDto climateAnalyserRequestDto) {
        ClimateAnalyserResponseDto climateAnalyserResponseDto = new ClimateAnalyserResponseDto();

        // Proof input verification and validation
        climateAnalyserResponseDto  = inputIsValid(climateAnalyserRequestDto);
        List<StationClimate> stationClimates = new ArrayList<StationClimate>();

        // Get Climate data twice for a Region
        // first for a year
        // second only for the station ,which existed on a special(userInput) year !
        if (climateAnalyserResponseDto.getErrorMsg().isEmpty()) {

            // Get ClimateDifference for Bundesland
            if (!climateAnalyserRequestDto.getBundesland().isEmpty()) {
                //calculateDifferenceClimate(climateAnalyserResponseDto, climateService.getClimateForBundesland(climateAnalyserRequestDto.getBundesland()));
                stationClimates = climateService.getClimateForBundesland(climateAnalyserRequestDto.getBundesland());

            } else {
                //calculateDifferenceClimate(climateAnalyserResponseDto, climateService.getClimateForGpsCoordinates(climateAnalyserRequestDto.getGps1(),climateAnalyserRequestDto.getGps2()));
                stationClimates = climateService.getClimateForGpsCoordinates(climateAnalyserRequestDto.getGps1(),climateAnalyserRequestDto.getGps2());

            }
            calculateDifferenceClimate(climateAnalyserResponseDto, stationClimates);

            //Get the climate history for a region
            climateAnalyserResponseDto.setClimateHistoryDtos(new ClimateHistoryAnalyserServiceImpl().getClimateHistoryData (climateAnalyserResponseDto.getOriginYear(),stationClimates));
        }






        return climateAnalyserResponseDto;
    }

    private ClimateAnalyserResponseDto inputIsValid(ClimateAnalyserRequestDto climateAnalyserRequestDto) {


        ClimateAnalyserResponseDto climateAnalyserResponseDto = new ClimateAnalyserResponseDto();

        String errorMsg = "";


        // Proof if bundesland and GPS Coordinates are Blank
        if(climateAnalyserRequestDto.getBundesland().isBlank() &&
            gpsCoordinatesAreBlank(climateAnalyserRequestDto.getGps1(),climateAnalyserRequestDto.getGps2())){
            errorMsg="Neither a Bundesland nor GPS coordinates have been entered";
        } else {

           // Proof if bundesland and GPS Coordinates are entered at the same Time
            if(!climateAnalyserRequestDto.getBundesland().isBlank()){

                if(!climateAnalyserRequestDto.getGps1().isGpsNull() ||
                   !climateAnalyserRequestDto.getGps2().isGpsNull()){
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

            }else{
                // Proof if GPS1 Coordinates are valid
                if(!climateAnalyserRequestDto.getGps1().isLatitudeValid() ||
                   !climateAnalyserRequestDto.getGps1().isLongitudeValid()) {
                     errorMsg = "GPS1 Coordinates are not valid ! : Latitude (-90, 0,90) :" + climateAnalyserRequestDto.getGps1().getLatitude() + " Longitude(-180,0,180)" + climateAnalyserRequestDto.getGps1().getLongitude();
                } else {


                    // Proof if GPS2 Coordinates are valid
                    if(!climateAnalyserRequestDto.getGps2().isLongitudeValid() ||
                       !climateAnalyserRequestDto.getGps2().isLatitudeValid()){
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
        if (errorMsg.isEmpty()){

            // Proof if the Years Field contain only Numbers

            if (isNumeric(climateAnalyserRequestDto.getYearOrigine())){

                climateAnalyserResponseDto.setOriginYear(climateAnalyserRequestDto.getYearOrigine());

                if (isNumeric(climateAnalyserRequestDto.getYearToCompare())){
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

    private boolean gpsCoordinatesAreBlank(GpsPoint gps1, GpsPoint gps2) {
        boolean status = false;

        if(gps1.isGpsNull() || gps2.isGpsNull()){
            status = true;
        }

        return status;
    }

    private void calculateDifferenceClimate(ClimateAnalyserResponseDto climateAnalyserResponseDto, List<StationClimate> stationClimates) {

        ClimateAnalyserTempDto climateAnalyserOrigine = getClimateAggregatedForOrigineYear(climateAnalyserResponseDto.getOriginYear(), stationClimates);

        //Proofe if Origine could be aggregated
        if (isNotEmpty(climateAnalyserOrigine)){

            climateAnalyserResponseDto.setOriginal(climateAnalyserOrigine);


            // Proof if there are some stationId in the originYear which exist in the year to compare
            ClimateAnalyserTempDto climateAnalyserCompare = getClimateAggregatedForStationsFromYearToCompare(climateAnalyserResponseDto.getOriginYear(),climateAnalyserResponseDto.getYearToCompare(), stationClimates);
            if (isNotEmpty(climateAnalyserCompare)){

            climateAnalyserResponseDto.setCompare(climateAnalyserCompare);
            } else {
                climateAnalyserResponseDto.setErrorMsg("No StationId from the year "+ climateAnalyserResponseDto.getYearToCompare() +" was found, which may already have existed in the year " + climateAnalyserResponseDto.getOriginYear());
            }

        } else {
            climateAnalyserResponseDto.setErrorMsg("No climate data for the year: "+ climateAnalyserResponseDto.getOriginYear() +" found !");
        }


    }

    private boolean isNotEmpty(ClimateAnalyserTempDto climateAnalyserTempDto) {

        if(climateAnalyserTempDto.getJanuar().add(
           climateAnalyserTempDto.getFebruar().add(
           climateAnalyserTempDto.getMaerz().add(
           climateAnalyserTempDto.getApril().add(
           climateAnalyserTempDto.getMai().add(
           climateAnalyserTempDto.getJuni().add(
           climateAnalyserTempDto.getJuli().add(
           climateAnalyserTempDto.getAugust().add(
           climateAnalyserTempDto.getSeptember().add(
           climateAnalyserTempDto.getOktober().add(
           climateAnalyserTempDto.getNovember().add(
           climateAnalyserTempDto.getDezember()))))))))))).equals(new BigDecimal("0"))){
            return false;
        } else {
            return true ;
        }

    }

    private ClimateAnalyserTempDto getClimateAggregatedForStationsFromYearToCompare(String originYear, String yearToCompare, List<StationClimate> stationClimates) {
        ClimateAnalyserTempDto climateAnalyserTempDto = new ClimateAnalyserTempDto();

        // Get all stationIds to the year to compare
        List<Integer> stationIds = getStationIdsForYearToCompare(yearToCompare,stationClimates);

        int counter = 0 ;
        for (StationClimate sc : stationClimates) {
            if (sc.getEndPeriod().contains(originYear) && stationIds.contains(sc.getStationId())) {

                 climateAnalyserTempDto = getClimatAddition(climateAnalyserTempDto,sc);
                 counter++;
            }
        }

        if (counter>0){
          climateAnalyserTempDto = getClimateDivision(climateAnalyserTempDto,counter);
        }


        return climateAnalyserTempDto;
    }

    private List<Integer> getStationIdsForYearToCompare(String yearToCompare, List<StationClimate> stationClimates) {

      List<Integer> stationIds = new ArrayList<Integer>();

         for(StationClimate stationClimate : stationClimates){
             if(stationClimate.getEndPeriod().contentEquals(yearToCompare)){
                 stationIds.add(stationClimate.getStationId());
             }
         }

      return stationIds;
    }

    private ClimateAnalyserTempDto getClimateAggregatedForOrigineYear(String year, List<StationClimate> stationClimates) {

        int counter = 0;
        ClimateAnalyserTempDto tempClimate = new ClimateAnalyserTempDto();

        for (StationClimate sc : stationClimates) {
            if (year.contains(sc.getEndPeriod())) {
                counter++;

                tempClimate = getClimatAddition(tempClimate, sc);

            }

        }

        if (counter>0){
          tempClimate = getClimateDivision(tempClimate,counter);
        }

        return tempClimate;
    }

    private ClimateAnalyserTempDto getClimateDivision(ClimateAnalyserTempDto tempClimate, int counter) {

        //TODO Remove Code : Same is in TemperatureForMonth
         // get Average by division temperature / years
                tempClimate.setJanuar(tempClimate.getJanuar().divide(BigDecimal.valueOf(counter), 3, RoundingMode.HALF_DOWN));
                tempClimate.setFebruar(tempClimate.getFebruar().divide(BigDecimal.valueOf(counter), 3, RoundingMode.HALF_DOWN));
                tempClimate.setMaerz(tempClimate.getMaerz().divide(BigDecimal.valueOf(counter), 3, RoundingMode.HALF_DOWN));
                tempClimate.setApril(tempClimate.getApril().divide(BigDecimal.valueOf(counter), 3, RoundingMode.HALF_DOWN));
                tempClimate.setMai(tempClimate.getMai().divide(BigDecimal.valueOf(counter), 3, RoundingMode.HALF_DOWN));
                tempClimate.setJuni(tempClimate.getJuni().divide(BigDecimal.valueOf(counter), 3, RoundingMode.HALF_DOWN));
                tempClimate.setJuli(tempClimate.getJuli().divide(BigDecimal.valueOf(counter), 3, RoundingMode.HALF_DOWN));
                tempClimate.setAugust(tempClimate.getAugust().divide(BigDecimal.valueOf(counter), 3, RoundingMode.HALF_DOWN));
                tempClimate.setSeptember(tempClimate.getSeptember().divide(BigDecimal.valueOf(counter), 3, RoundingMode.HALF_DOWN));
                tempClimate.setOktober(tempClimate.getOktober().divide(BigDecimal.valueOf(counter), 3, RoundingMode.HALF_DOWN));
                tempClimate.setNovember(tempClimate.getNovember().divide(BigDecimal.valueOf(counter), 3, RoundingMode.HALF_DOWN));
                tempClimate.setDezember(tempClimate.getDezember().divide(BigDecimal.valueOf(counter), 3, RoundingMode.HALF_DOWN));

        return tempClimate;
    }

    private ClimateAnalyserTempDto getClimatAddition(ClimateAnalyserTempDto tempClimate, StationClimate sc) {

        //TODO Remove Code : Same is in TemperatureForMonth
        tempClimate.setJanuar(tempClimate.getJanuar().add(sc.getJanuar()));
        tempClimate.setFebruar(tempClimate.getFebruar().add(sc.getFebruar()));
        tempClimate.setMaerz(tempClimate.getMaerz().add(sc.getMaerz()));
        tempClimate.setApril(tempClimate.getApril().add(sc.getApril()));
        tempClimate.setMai(tempClimate.getMai().add(sc.getMai()));
        tempClimate.setJuni(tempClimate.getJuni().add(sc.getJuni()));
        tempClimate.setJuli(tempClimate.getJuli().add(sc.getJuli()));
        tempClimate.setAugust(tempClimate.getAugust().add(sc.getAugust()));
        tempClimate.setSeptember(tempClimate.getSeptember().add(sc.getSeptember()));
        tempClimate.setOktober(tempClimate.getOktober().add(sc.getOktober()));
        tempClimate.setNovember(tempClimate.getNovember().add(sc.getNovember()));
        tempClimate.setDezember(tempClimate.getDezember().add(sc.getDezember()));

        return tempClimate;
    }


    public static boolean isNumeric(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch(NumberFormatException e){
            return false;
        }
    }
}
