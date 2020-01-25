package ch.studer.germanclimatedataanalyser.service;

import ch.studer.germanclimatedataanalyser.model.database.StationClimate;
import ch.studer.germanclimatedataanalyser.model.dto.ClimateAnalyserRequestDto;
import ch.studer.germanclimatedataanalyser.model.dto.ClimateAnalyserResponseDto;
import ch.studer.germanclimatedataanalyser.model.dto.ClimateAnalyserTempDto;
import ch.studer.germanclimatedataanalyser.model.dto.helper.GpsPoint;
import ch.studer.germanclimatedataanalyser.model.helper.SearchCriteria;
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
    public ClimateAnalyserResponseDto getClimateAnalyserForBundesland(String bundesland) {
        ClimateAnalyserResponseDto climateAnalyserResponseDto = new ClimateAnalyserResponseDto();


        climateAnalyserResponseDto.setBundesland(bundesland);

        calculateDifferenceClimate(climateAnalyserResponseDto, climateService.getClimateForBundesland(bundesland));

        return climateAnalyserResponseDto;

    }

    @Override
    public ClimateAnalyserResponseDto getClimateAnalyticsByClimateAnalyserRequest(ClimateAnalyserRequestDto climateAnalyserRequestDto) {
        ClimateAnalyserResponseDto climateAnalyserResponseDto = new ClimateAnalyserResponseDto();

        String errorMsg = "";

        errorMsg = inputIsValid(climateAnalyserRequestDto).isBlank() ? "" : inputIsValid(climateAnalyserRequestDto);


        climateAnalyserResponseDto.setErrorMsg(errorMsg);
        return climateAnalyserResponseDto;
    }

    private String inputIsValid(ClimateAnalyserRequestDto climateAnalyserRequestDto) {

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
                    }
                }

            }else{
                // Proof if GPS Coordinates are valid
                if(!climateAnalyserRequestDto.getGps1().isLatitudeValid() ||
                   !climateAnalyserRequestDto.getGps1().isLongitudeValid()) {
                     errorMsg = "GPS1 Coordinates are not valid ! : Latitude (-90, 0,90) :" + climateAnalyserRequestDto.getGps1().getLatitude() + " Longitude(-180,0,180)" + climateAnalyserRequestDto.getGps1().getLongitude();
                } else {


                    // Proof if GPS Coordinates are valid
                    if(!climateAnalyserRequestDto.getGps2().isLongitudeValid() ||
                       !climateAnalyserRequestDto.getGps2().isLatitudeValid()){
                         errorMsg = "GPS2 Coordinates are not valid ! : Latitude (-90, 0,90) :" + climateAnalyserRequestDto.getGps2().getLatitude() + " Longitude(-180,0,180)" + climateAnalyserRequestDto.getGps2().getLongitude();

                    }
                }
            }
        }

        return errorMsg;
    }

    private boolean gpsCoordinatesAreBlank(GpsPoint gps1, GpsPoint gps2) {
        boolean status = false;

        if(gps1.isGpsNull() || gps2.isGpsNull()){
            status = true;
        }

        return status;
    }

    private void calculateDifferenceClimate(ClimateAnalyserResponseDto climateAnalyserResponseDto, List<StationClimate> climateForBundesland) {


        // Get the two years, which are to compare
        SearchCriteria searchCriteria = getSearchCriteria(climateForBundesland);
        climateAnalyserResponseDto.setYear(searchCriteria.getYearWithMostRecords());


        //
        climateAnalyserResponseDto.setOriginal(getClimateAggregatedForSearchCriteriaYear(searchCriteria.getYearWithMostRecords(), climateForBundesland));

        climateAnalyserResponseDto.setCompare(getClimateAggregatedForSearchCriteriaYearAndStationIds(searchCriteria, climateForBundesland));


    }

    private ClimateAnalyserTempDto getClimateAggregatedForSearchCriteriaYearAndStationIds(SearchCriteria searchCriteria, List<StationClimate> climateForBundesland) {
        ClimateAnalyserTempDto climateAnalyserTempDto = new ClimateAnalyserTempDto();

        int counter = 0 ;
        for (StationClimate sc : climateForBundesland) {
            if (sc.getEndPeriod().contains(searchCriteria.getYearWithMostRecords()) && searchCriteria.getStationId().contains(sc.getStationId())) {

                 climateAnalyserTempDto = getClimatAddition(climateAnalyserTempDto,sc);
                 counter++;
            }
        }

        climateAnalyserTempDto = getClimateDivision(climateAnalyserTempDto,counter);


        return climateAnalyserTempDto;
    }

    private SearchCriteria getSearchCriteria(List<StationClimate> climateForBundesland) {

        SearchCriteria searchCriteria = new SearchCriteria();

        List<String> processedYears = new ArrayList<String>();
        String actualYear;
        String newestYear = "0";
        int counter = 0;
        int maxAmount = 0;

        for (StationClimate sc : climateForBundesland) {

            actualYear = sc.getEndPeriod();
            if (!processedYears.contains(actualYear)) {

                for (StationClimate counts : climateForBundesland) {
                    if (actualYear.contains(counts.getEndPeriod())) {
                        counter++;
                    }
                }
                if (counter > maxAmount) {
                    maxAmount = counter;
                    searchCriteria.setYearWithMostRecords(actualYear);
                }
                counter = 0;

                if (Integer.valueOf(newestYear) < Integer.valueOf(actualYear)) {
                    newestYear = actualYear;
                }

                // Add to years List as already processed
                processedYears.add(sc.getEndPeriod());
            }
            ;

        }
        for (StationClimate sc : climateForBundesland) {
            if (newestYear.contains(sc.getEndPeriod()) && !searchCriteria.getStationId().contains(sc.getStationId())) {
                searchCriteria.getStationId().add(sc.getStationId());
            }
        }


        return searchCriteria;
    }

    private ClimateAnalyserTempDto getClimateAggregatedForSearchCriteriaYear(String year, List<StationClimate> climateForBundesland) {

        ClimateAnalyserTempDto climateAnalyserTempDto = new ClimateAnalyserTempDto();
        List<ClimateAnalyserTempDto> climateAnalyserTempDtos = new ArrayList<ClimateAnalyserTempDto>();
        int counter = 0;
        ClimateAnalyserTempDto tempClimate = new ClimateAnalyserTempDto();

        for (StationClimate sc : climateForBundesland) {
            if (year.contains(sc.getEndPeriod())) {
                counter++;

                tempClimate = getClimatAddition(tempClimate, sc);

            }

        }

        tempClimate = getClimateDivision(tempClimate,counter);

        return tempClimate;
    }

    private ClimateAnalyserTempDto getClimateDivision(ClimateAnalyserTempDto tempClimate, int counter) {

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
}