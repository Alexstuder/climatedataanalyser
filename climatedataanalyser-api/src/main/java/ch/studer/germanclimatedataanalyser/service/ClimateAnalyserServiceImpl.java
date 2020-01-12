package ch.studer.germanclimatedataanalyser.service;

import ch.studer.germanclimatedataanalyser.model.database.StationClimate;
import ch.studer.germanclimatedataanalyser.model.dto.ClimateAnalyserDto;
import ch.studer.germanclimatedataanalyser.model.dto.ClimateAnalyserTempDto;
import ch.studer.germanclimatedataanalyser.model.helper.SearchCriteria;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

public class ClimateAnalyserServiceImpl implements ClimateAnalyserService {

    @Autowired
    ClimateService climateService;

    @Override
    public ClimateAnalyserDto getClimateAnalyserForBundesland(String bundesland) {
        ClimateAnalyserDto climateAnalyserDto = new ClimateAnalyserDto();


        climateAnalyserDto.setBundesland(bundesland);

        calculateDifferenceClimate(climateAnalyserDto, climateService.getClimateForBundesland(bundesland));

        return climateAnalyserDto;

    }

    private void calculateDifferenceClimate(ClimateAnalyserDto climateAnalyserDto, List<StationClimate> climateForBundesland) {


        // Get the two years, which are to compare
        SearchCriteria searchCriteria = getSearchCriteria(climateForBundesland);
        climateAnalyserDto.setYear(searchCriteria.getYearWithMostRecords());


        //
        climateAnalyserDto.setOriginal(getClimateAggregatedForSearchCriteriaYear(searchCriteria.getYearWithMostRecords(), climateForBundesland));

        climateAnalyserDto.setCompare(getClimateAggregatedForSearchCriteriaYearAndStationIds(searchCriteria, climateForBundesland));


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